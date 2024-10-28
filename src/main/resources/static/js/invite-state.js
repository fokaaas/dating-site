const invitation = (userId) => ({
  NONE: {
    innerText: "Invite",
    className: "btn-success",
    action: `/users/${userId}/invitations`,
  },
  PENDING_INVITER: {
    innerText: "Invited",
    className: "btn-secondary",
    disabled: true,
  },
  ACCEPTED: {
    innerText: "Remove Connection",
    className: "btn-danger",
    action: `/users/${userId}/invitations-reject`,
  },
  PENDING_INVITEE: {
    innerText: "Accept Invitation",
    className: "btn-success",
    action: `/users/${userId}/invitations-accept`,
  },
});

document.addEventListener("DOMContentLoaded", function () {
  const invitationState = document.getElementById("actionButton").getAttribute("data-invitation-state");
  const userId = document.getElementById("actionButton").getAttribute("data-user-id");
  configureButtonState(invitationState, userId);
  managePhoneVisibility(invitationState);
});

function configureButtonState(invitationState, userId) {
  const actionButton = document.getElementById("actionButton");
  const actionForm = document.getElementById("actionForm");
  const invitationConfig = invitation(userId)[invitationState];
  actionButton.innerText = invitationConfig.innerText;
  actionButton.className = `btn ${invitationConfig.className}`;
  actionButton.disabled = invitationConfig.disabled;
  actionForm.action = invitationConfig.action;
}

function managePhoneVisibility(invitationState) {
  const phoneParagraph = document.getElementById("phoneParagraph");
  if (invitationState === "ACCEPTED") {
    phoneParagraph.style.display = "block";
  } else {
    phoneParagraph.style.display = "none";
  }
}

