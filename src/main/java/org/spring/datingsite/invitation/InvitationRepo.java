package org.spring.datingsite.invitation;

import org.spring.datingsite.invitation.entity.InvitationEntity;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public class InvitationRepo {
    private final ArrayList<InvitationEntity> invitations = new ArrayList<>(); // db mock

    public void create(InvitationEntity invitation) {
        invitations.add(invitation);
    }

    public void update(String fromUserId, String toUserId, boolean isAccepted) {
        for (InvitationEntity invitation : invitations) {
            if (invitation.getFromUserId().equals(fromUserId) && invitation.getToUserId().equals(toUserId)) {
                invitation.setAccepted(isAccepted);
            }
        }
    }

    public void delete(String fromUserId, String toUserId) {
        invitations.removeIf(invitation -> invitation.getFromUserId().equals(fromUserId) && invitation.getToUserId().equals(toUserId));
    }

    public ArrayList<InvitationEntity> findManyByUserId(String userId) {
        ArrayList<InvitationEntity> result = new ArrayList<>();
        for (InvitationEntity invitation : invitations) {
            if (invitation.getToUserId().equals(userId)) {
                result.add(invitation);
            }
        }
        return result;
    }

    public InvitationEntity find(String fromUserId, String toUserId) {
        for (InvitationEntity invitation : invitations) {
            if (invitation.getFromUserId().equals(fromUserId) && invitation.getToUserId().equals(toUserId)) {
                return invitation;
            }
        }
        return null;
    }
}
