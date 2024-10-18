document.addEventListener("DOMContentLoaded", function () {
  const errorMessage = null;
  if (errorMessage) {
    const errorModal = new bootstrap.Modal(document.getElementById('errorModal'));
    errorModal.show();
  }
});