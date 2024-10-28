document.addEventListener("DOMContentLoaded", function () {
  const invitationState = document.getElementById("actionButton").getAttribute("data-invitation-state");
  const userId = document.getElementById("actionButton").getAttribute("data-user-id");
  configureButtonState(invitationState, userId);
  managePhoneVisibility(invitationState);
});

function configureButtonState(invitationState, userId) {
  const actionButton = document.getElementById("actionButton");
  const actionForm = document.getElementById("actionForm");

  if (invitationState === "NONE") {
    actionButton.innerText = "Invite";
    actionButton.classList.add("btn-success");
    actionForm.action = `/users/${userId}/invitations`;
  } else if (invitationState === "PENDING_INVITER") {
    actionButton.innerText = "Invited";
    actionButton.classList.add("btn-secondary");
    actionButton.disabled = true;
  } else if (invitationState === "ACCEPTED") {
    actionButton.innerText = "Remove Connection";
    actionButton.classList.add("btn-danger");
    actionForm.action = `/users/${userId}/invitations-reject`;
  } else if (invitationState === "PENDING_INVITEE") {
    actionButton.innerText = "Accept Invitation";
    actionButton.classList.add("btn-success");
    actionForm.action = `/users/${userId}/invitations-accept`;
  }
}

function managePhoneVisibility(invitationState) {
  const phoneParagraph = document.getElementById("phoneParagraph");
  if (invitationState === "ACCEPTED") {
    phoneParagraph.style.display = "block";
  } else {
    phoneParagraph.style.display = "none";
  }
}

