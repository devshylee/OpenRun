const loginBtn = document.querySelector(".login-btn");
const modal = document.getElementById("loginModal");
const closeBtn = document.querySelector(".modal-content .close");

loginBtn.addEventListener("click", () => {
  modal.style.display = "block";
});

closeBtn.addEventListener("click", () => {
  modal.style.display = "none";
});

window.addEventListener("click", (e) => {
  if (e.target === modal) {
    modal.style.display = "none";
  }
});
