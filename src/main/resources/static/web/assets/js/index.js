const { createApp } = Vue;

createApp({
  data() {
    return {
      /* Sign In */
      email: "",
      password: "",
      /* Sign Up */
      firstName: "",
      lastName: "",
      emailUp: "",
      passwordUp: "",
    };
  },
  methods: {
    signIn() {
      axios
        .post("/api/login", `email=${this.email}&password=${this.password}`)
        .then((response) => (window.location.href = "/web/accounts.html"))
        .catch((error) => Swal.fire({
          icon: "error",
          title: "Oops...",
          text: "wrong email or password",
        }));
    },
    signUp() {
      axios
        .post(
          "/api/register",
          `firstName=${this.firstName}&lastName=${this.lastName}&email=${this.emailUp}&password=${this.passwordUp}`
        )
        .then((response) =>
          axios
            .post(
              "/api/login",
              `email=${this.emailUp}&password=${this.passwordUp}`
            ))
            .then((response) => (window.location.href = "/web/accounts.html"))
        .catch((error) => Swal.fire({
          icon: "error",
          title: "Oops...",
          text: error.response.data,
        }));
    },
    
  },
}).mount("#app");

/*  Video Slider */
const menuBtn = document.querySelector(".menu-btn");
const navigation = document.querySelector(".navigation");

menuBtn.addEventListener("click", () => {
  menuBtn.classList.toggle("active");
  navigation.classList.toggle("active");
});

const btns = document.querySelectorAll(".nav-btn");
const slides = document.querySelectorAll(".video-slide");

let sliderNav = function (manual) {
  btns.forEach((btn) => {
    btn.classList.remove("active");
  });
  slides.forEach((slide) => {
    slide.classList.remove("active");
  });
  btns[manual].classList.add("active");
  slides[manual].classList.add("active");
};
btns.forEach((btn, i) => {
  btn.addEventListener("click", () => {
    sliderNav(i);
  });
});
let i = 0;
const interval = setInterval(function () {
  sliderNav(i);
  if (i < slides.length - 1) {
    i++;
  } else {
    i = 0;
  }
}, 10000);

/* Login */
const formOpenBtn = document.querySelector("#form-open"),
  home = document.querySelector(".home"),
  formContainer = document.querySelector(".form-container"),
  formCloseBtn = document.querySelector(".form-close"),
  signupBtn = document.querySelector("#signup"),
  loginBtn = document.querySelector("#login"),
  pwShowHide = document.querySelectorAll(".pw-hide"),
  infoHome = document.querySelector("#infoHome"),
  infoServices = document.querySelector("#infoServices");

formOpenBtn.addEventListener("click", () => home.classList.add("show"));
formCloseBtn.addEventListener("click", () => home.classList.remove("show"));

pwShowHide.forEach((icon) => {
  icon.addEventListener("click", () => {
    let getPwInput = icon.parentElement.querySelector("input");
    if (getPwInput.type === "password") {
      getPwInput.type = "text";
      icon.classList.replace("fa-eye-slash", "fa-eye");
    } else {
      getPwInput.type = "password";
      icon.classList.replace("fa-eye", "fa-eye-slash");
    }
  });
});

signupBtn.addEventListener("click", (e) => {
  e.preventDefault();
  formContainer.classList.add("active");
});

loginBtn.addEventListener("click", (e) => {
  e.preventDefault();
  formContainer.classList.remove("active");
});
/* Close menu Open Login */
formOpenBtn.addEventListener("click", () => {
  menuBtn.classList.toggle("active");
  navigation.classList.toggle("active");
});
/* Close menu Pages */
infoHome.addEventListener("click", () =>{
  menuBtn.classList.toggle("active");
  navigation.classList.toggle("active");
});
infoServices.addEventListener("click", () =>{
  menuBtn.classList.toggle("active");
  navigation.classList.toggle("active");
});

