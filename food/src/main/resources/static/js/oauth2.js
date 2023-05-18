$(document).ready(function () {

    $.ajax({
        method: 'GET',
        url: 'http://localhost:8080/api/v1/login/signinByOAuth2',
    }).done(function (data) {
        localStorage.setItem('token', data.data);
        token = data.data
    })
})