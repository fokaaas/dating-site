package org.spring.datingsite.entity;

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

    // Getters

    public String getFromUserId() {
        return fromUserId;
    }

    public String getToUserId() {
        return toUserId;
    }

    public boolean getIsAccepted() {
        return isAccepted;
    }

    // Setters

    public void setFromUserId(String fromUserId) {
        this.fromUserId = fromUserId;
    }

    public void setToUserId(String toUserId) {
        this.toUserId = toUserId;
    }

    public void setIsAccepted(boolean isAccepted) {
        this.isAccepted = isAccepted;
    }
}
