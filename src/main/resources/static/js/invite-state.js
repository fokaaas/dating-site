document.addEventListener("DOMContentLoaded", function () {
  const invitationState = document.getElementById("actionButton").getAttribute("data-invitation-state");
  const userId = document.getElementById("actionButton").getAttribute("data-user-id");

  configureButtonState(invitationState, userId);

  const actionForm = document.getElementById("actionForm");
  actionForm.addEventListener("submit", handleFormSubmit);
});

function configureButtonState(invitationState, userId) {
  const actionButton = document.getElementById("actionButton");
  const actionForm = document.getElementById("actionForm");

  console.log(invitationState);

  if (invitationState === "NONE") {
    actionButton.innerText = "Invite";
    actionButton.classList.add("btn-success");
    actionForm.action = `/users/${userId}/invitations`;
    actionForm.method = "POST";
  } else if (invitationState === "PENDING") {
    actionButton.innerText = "Invited";
    actionButton.classList.add("btn-secondary");
    actionButton.disabled = true;
  } else if (invitationState === "ACCEPTED") {
    actionButton.innerText = "Remove Connection";
    actionButton.classList.add("btn-danger");
    actionForm.action = `/users/${userId}/invitations`;
    actionForm.method = "DELETE";
  }
}

function handleFormSubmit() {
  const actionButton = document.getElementById("actionButton");

  if (actionButton.innerText === "Invite") {
    actionButton.innerText = "Invited";
    actionButton.classList.remove("btn-success");
    actionButton.classList.add("btn-secondary");
    actionButton.disabled = true;
  }

  else if (actionButton.innerText === "Remove Connection") {
    actionButton.innerText = "Invite";
    actionButton.classList.remove("btn-danger");
    actionButton.classList.add("btn-success");
    actionButton.disabled = false;
  }
}
