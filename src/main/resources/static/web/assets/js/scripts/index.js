/*  Video Slider */
const menuBtn = document.querySelector(".menu-btn");
const navigation = document.querySelector(".navigation");

menuBtn.addEventListener("click", () =>{
    menuBtn.classList.toggle("active");
    navigation.classList.toggle("active");
});

const btns = document.querySelectorAll(".nav-btn")
const slides = document.querySelectorAll(".video-slide")

let sliderNav = function(manual){
    btns.forEach((btn) => {
        btn.classList.remove("active")
    });
    slides.forEach((slide) => {
        slide.classList.remove("active")
    });
    btns[manual].classList.add("active");
    slides[manual].classList.add("active");
}
btns.forEach((btn, i) => {
    btn.addEventListener("click", () =>{
        sliderNav(i);
    });
});
let i = 0;
const interval = setInterval(function() {
sliderNav(i);
if(i<slides.length-1){i++;}else{i=0;}
}, 10000);

/* Login */
const formOpenBtn = document.querySelector("#form-open"),
home = document.querySelector(".home"),
formContainer = document.querySelector(".form-container"),
formCloseBtn = document.querySelector(".form-close"),
signupBtn = document.querySelector("#signup"),
loginBtn = document.querySelector("#login"),
pwShowHide = document.querySelectorAll(".pw-hide");

formOpenBtn.addEventListener("click", () => home.classList.add("show"))
formCloseBtn.addEventListener("click", () => home.classList.remove("show"))

pwShowHide.forEach(icon => {
    icon.addEventListener("click", () =>{
        let getPwInput = icon.parentElement.querySelector("input")
        if(getPwInput.type === "password"){
            getPwInput.type = "text";
            icon.classList.replace("fa-eye-slash","fa-eye")
        }else{
            getPwInput.type = "password";
            icon.classList.replace("fa-eye", "fa-eye-slash")
        }
    });
});

signupBtn.addEventListener("click", (e) =>{
    e.preventDefault();
    formContainer.classList.add("active");
})

loginBtn.addEventListener("click", (e) =>{
    e.preventDefault();
    formContainer.classList.remove("active");
})

formOpenBtn.addEventListener("click", () =>{
    menuBtn.classList.toggle("active");
    navigation.classList.toggle("active");
});