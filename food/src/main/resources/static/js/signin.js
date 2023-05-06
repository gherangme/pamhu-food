let token = localStorage.getItem('token')
$(document).ready(function () {
    $('#test').click(function (e) {
        e.preventDefault()
        const username = $('#username').val()
        const password = $('#password').val()
        var formData = new FormData();
        formData.append('username', username);
        formData.append('password', password);
        $.ajax({
            url: `http://localhost:8080/api/v1/login/signin`,
            type: 'POST',
            data: formData,
            contentType: false,
            processData: false,
            success: function (data) {
                localStorage.setItem('token', data.data)
                window.location.href = "/home"
            },
            error: function () {
                const alertCustom = `<div class="alert alert-danger custom-alert" style="text-align: center">
                                        Sai tài khoản hoặc mật khẩu, vui lòng kiểm tra lại.
                                      </div>`
                $('#test').append(alertCustom)

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
        })
    })
})