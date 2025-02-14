const navLinks = document.querySelectorAll('.navbar .nav-item .nav-link');
const url = new URL(window.location.href);
const baseUrl = url.origin + url.pathname;
navLinks.forEach(navLink => {
    debugger
    if(baseUrl.endsWith(navLink.href)){
        navLink.classList.add('active')
    }else{
        navLink.classList.remove('active')
    }
})
