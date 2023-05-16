$(document).ready(function () {

    $.ajax({
        method: 'GET',
        url: 'http://localhost:8080/api/v1/login/signinByOAuth2Google',
    }).done(function (data) {
        token = data.data;
    })
})