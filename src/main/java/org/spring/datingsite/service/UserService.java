package org.spring.datingsite.service;

import org.spring.datingsite.entity.InvitationEntity;
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

    public UserEntity[] getAllUsers(String currentUserId) {
        ArrayList<UserEntity> users = userRepository.findMany();
        users.forEach(user -> user.setAge(calculateAge(user.getBirthDate())));
        return users.stream()
                .filter(user -> !user.getId().equals(currentUserId))
                .toArray(UserEntity[]::new);
    }

    private int calculateAge(LocalDate birthDate) {
        return Period.between(birthDate, LocalDate.now()).getYears();
    }

    public UserEntity getUser(String userId) {
        UserEntity user = userRepository.findById(userId);
        user.setAge(calculateAge(user.getBirthDate()));
        return user;
    }

    public void updateUser(UserEntity updatedUser) {
        UserEntity existingUser = userRepository.findById(updatedUser.getId());
        if (existingUser == null) {
            throw new IllegalArgumentException("User not found");
        }

        existingUser.setFirstName(updatedUser.getFirstName());
        existingUser.setMiddleName(updatedUser.getMiddleName());
        existingUser.setLastName(updatedUser.getLastName());
        existingUser.setSex(updatedUser.getSex());
        existingUser.setPhoto(updatedUser.getPhoto());
        existingUser.setEmail(updatedUser.getEmail());
        existingUser.setPhoneNumber(updatedUser.getPhoneNumber());
        existingUser.setBirthDate(updatedUser.getBirthDate());
        existingUser.setResidence(updatedUser.getResidence());
        existingUser.setAboutMe(updatedUser.getAboutMe());

        existingUser.setAge(calculateAge(existingUser.getBirthDate()));

        userRepository.update(existingUser);
    }

    public List<UserEntity> getMen() {
        return userRepository.findManyUsers().stream()
                .filter(user -> "Male".equalsIgnoreCase(user.getSex()))
                .collect(Collectors.toList());
    }

    public List<UserEntity> getWomen() {
        return userRepository.findManyUsers().stream()
                .filter(user -> "Female".equalsIgnoreCase(user.getSex()))
                .collect(Collectors.toList());
    }

    public List<UserEntity> searchUsers(String keyword, Integer minAge, Integer maxAge, String location) {
        return userRepository.findManyUsers().stream()
                .filter(user ->
                        (keyword == null ||
                                user.getFirstName().toLowerCase().contains(keyword.toLowerCase()) ||
                                user.getLastName().toLowerCase().contains(keyword.toLowerCase()))
                )
                .filter(user ->
                        (minAge == null || user.getAge() >= minAge) &&
                                (maxAge == null || user.getAge() <= maxAge)
                )
                .filter(user ->
                        (location == null || user.getResidence().toLowerCase().contains(location.toLowerCase()))
                )
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

    public UserEntity[] getInviters(String userId) {
        ArrayList<InvitationEntity> invitations = invitationRepo.findManyByUserId(userId);
        ArrayList<UserEntity> inviters = new ArrayList<>();
        invitations.forEach(invitation -> {
            if (!invitation.getIsAccepted()) {
                UserEntity user = userRepository.findById(invitation.getFromUserId());
                user.setAge(calculateAge(user.getBirthDate()));
                inviters.add(user);
            }
        });
        return inviters.toArray(UserEntity[]::new);
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
