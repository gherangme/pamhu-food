$(document).ready(function () {
    $('#reset-password').click(function (e) {
        e.preventDefault()
        const newPassword = $('#new-password').val()
        const otp = $('#otp').val()

        $.ajax({
            url: `http://localhost:8080/api/v1/forgot/changePassword`,
            type: 'PUT',
            data: {'password': newPassword, 'OTPByUser': otp},
            success: function (data) {
                if (data.data) {
                    const alertCustom = `<div class="alert alert-success custom-alert" style="text-align: center">
                                        ${data.desc}
                                      </div>`
                    $('#reset-password').append(alertCustom)

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
                } else {
                    const alertCustom = `<div class="alert alert-danger custom-alert" style="text-align: center">
                                        ${data.desc}
                                      </div>`
                    $('#reset-password').append(alertCustom)

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