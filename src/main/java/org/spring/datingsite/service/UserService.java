package org.spring.datingsite.service;

import org.spring.datingsite.entity.InvitationEntity;
import org.spring.datingsite.entity.SearchEntity;
import org.spring.datingsite.entity.UserEntity;
import org.spring.datingsite.enums.InvitationStateEnum;
import org.spring.datingsite.exception.EmailAlreadyExistsException;
import org.spring.datingsite.exception.InvalidPasswordException;
import org.spring.datingsite.repository.InvitationRepo;
import org.spring.datingsite.repository.UserRepository;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final InvitationRepo invitationRepo;

    public UserService(UserRepository userRepository , InvitationRepo invitationRepo) {
        this.userRepository = userRepository;
        this.invitationRepo = invitationRepo;
    }

    public String createUser(UserEntity user) {
        UserEntity existingUser = userRepository.findByEmail(user.getEmail());
        if (existingUser != null) {
            throw new EmailAlreadyExistsException(user.getEmail());
        }
        String token = generateUUIDToken();
        user.setSession(token);
        user.setId(generateUUIDToken());
        user.setPassword(hashPassword(user.getPassword()));
        userRepository.create(user);
        return token;
    }

    private String generateUUIDToken() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }

    private String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    private boolean isPasswordMatch(String password, String hashedPassword) {
        return BCrypt.checkpw(password, hashedPassword);
    }

    public String login(UserEntity user) {
        UserEntity existingUser = userRepository.findByEmail(user.getEmail());
        if (!isPasswordMatch(user.getPassword(), existingUser.getPassword())) {
            throw new InvalidPasswordException();
        }
        String token = generateUUIDToken();
        existingUser.setSession(token);
        existingUser.setId(generateUUIDToken());
        return token;
    }

    public UserEntity getUserFromToken(String token) {
        return userRepository.findBySession(token);
    }

    private int calculateAge(LocalDate birthDate) {
        return Period.between(birthDate, LocalDate.now()).getYears();
    }

    public UserEntity getUser(String userId) {
        UserEntity user = userRepository.findById(userId);
        user.setAge(calculateAge(user.getBirthDate()));
        return user;
    }

    public void updateUser(UserEntity currentUser, UserEntity updatedUser) {
        currentUser.setFirstName(updatedUser.getFirstName());
        currentUser.setMiddleName(updatedUser.getMiddleName());
        currentUser.setLastName(updatedUser.getLastName());
        currentUser.setSex(updatedUser.getSex());
        currentUser.setPhoto(updatedUser.getPhoto());
        currentUser.setEmail(updatedUser.getEmail());
        currentUser.setPhoneNumber(updatedUser.getPhoneNumber());
        currentUser.setBirthDate(updatedUser.getBirthDate());
        currentUser.setResidence(updatedUser.getResidence());
        currentUser.setAboutMe(updatedUser.getAboutMe());

        currentUser.setAge(calculateAge(updatedUser.getBirthDate()));

        userRepository.update(currentUser);
    }

    public List<UserEntity> getUsers(String currentUserId, SearchEntity search) {
        return userRepository.findMany().stream()
                .filter(user -> !user.getId().equals(currentUserId))
                .filter(user -> (search.getSex() == null || search.getSex().isEmpty() || user.getSex().equals(search.getSex()))
                )
                .filter(user ->
                        (search.getKeyword() == null ||
                                user.getFirstName().toLowerCase().contains(search.getKeyword().toLowerCase()) ||
                                user.getLastName().toLowerCase().contains(search.getKeyword().toLowerCase()))
                )
                .filter(user ->
                        (search.getMinAge() == null || calculateAge(user.getBirthDate()) >= search.getMinAge()) &&
                                (search.getMaxAge() == null || calculateAge(user.getBirthDate()) <= search.getMaxAge())
                )
                .filter(user ->
                        (search.getLocation() == null || user.getResidence().toLowerCase().contains(search.getLocation().toLowerCase()))
                )
                .peek(user -> user.setAge(calculateAge(user.getBirthDate())))
                .collect(Collectors.toList());
    }

    public void inviteUser(String currentUserId, String userId) {
        invitationRepo.create(new InvitationEntity(currentUserId, userId, false));
    }

    public void acceptInvitation(String fromUserId, String toUserId) {
        invitationRepo.update(fromUserId, toUserId, true);
    }

    public void rejectInvitation(String fromUserId, String toUserId) {
        InvitationEntity invitation = invitationRepo.find(fromUserId, toUserId);
        if (invitation == null) {
            invitationRepo.delete(toUserId, fromUserId);
        }
        invitationRepo.delete(fromUserId, toUserId);
    }

    public ArrayList<UserEntity> getInviters(String userId) {
        ArrayList<InvitationEntity> invitations = invitationRepo.findManyByUserId(userId);
        ArrayList<UserEntity> inviters = new ArrayList<>();
        invitations.forEach(invitation -> {
            if (!invitation.getIsAccepted()) {
                UserEntity user = userRepository.findById(invitation.getFromUserId());
                user.setAge(calculateAge(user.getBirthDate()));
                inviters.add(user);
            }
        });
        return inviters;
    }

    public InvitationStateEnum getInvitationState(String fromUserId, String toUserId) {
        InvitationEntity fromUserInvitation = invitationRepo.find(fromUserId, toUserId);
        if (fromUserInvitation != null) return getState(fromUserInvitation, true);
        InvitationEntity toUserInvitation = invitationRepo.find(toUserId, fromUserId);
        if (toUserInvitation != null) return getState(toUserInvitation, false);
        return InvitationStateEnum.NONE;
    }

    private InvitationStateEnum getState(InvitationEntity invitation, boolean isFromUser) {
        return invitation.getIsAccepted()
                ? InvitationStateEnum.ACCEPTED
                : isFromUser
                    ? InvitationStateEnum.PENDING_INVITER
                    : InvitationStateEnum.PENDING_INVITEE;
    }
}
