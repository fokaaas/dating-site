package org.spring.datingsite.invitation.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InvitationEntity {
    private String fromUserId;
    private String toUserId;
    private boolean isAccepted;

    public InvitationEntity() {}

    public InvitationEntity(String fromUserId, String toUserId, boolean isAccepted) {
        this.fromUserId = fromUserId;
        this.toUserId = toUserId;
        this.isAccepted = isAccepted;
    }
}
