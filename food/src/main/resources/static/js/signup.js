$(document).ready(function () {
    $('#create-button').click(function (e) {
        e.preventDefault()
        const fullName = $('#fullName').val(),
            username = $('#username').val(),
            password = $('#password').val(),
            address = $('#address').val(),
            phone = $('#phone').val()

        const jsonData = {
            fullName: fullName,
            username: username,
            password: password,
            address: address,
            phone: phone
        }
        $.ajax({
            url: 'http://localhost:8080/api/v1/signup/postUserInfor',
            type: 'POST',
            headers: {'Content-Type': 'application/json'},
            data: JSON.stringify(jsonData),
            success: function (data) {
                if (data.statusCode === 200) {
                    window.location.href = "/otp"
                } else {
                    const alertCustom = `<div class="alert alert-danger custom-alert" style="text-align: center">
                                        ${data.desc}
                                      </div>`
                    $('#create-button').append(alertCustom)

                    $('.custom-alert').css({
                        'position': 'fixed',
                        'top': 0,
                        'left': 0,
                        'width': '100%',
                        'z-index': '9999'
                    });

                    // Set time out
                    setTimeout(function(){
                        $(".alert").remove();
                    }, 3000);
                }
            }
        })
    })
})