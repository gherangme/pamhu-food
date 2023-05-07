$(document).ready(function () {
    $('#otp-button').click(function (e) {
        e.preventDefault()
        const OTPByUser = $('#otp').val()
        $.ajax({
            url: 'http://localhost:8080/api/v1/signup/addUser',
            type: 'POST',
            data: {'OTPByUser': OTPByUser},
            success: function (data) {
                const alertCustom = `<div class="alert alert-success custom-alert" style="text-align: center">
                                        ${data.desc}
                                      </div>`
                $('#otp-button').append(alertCustom)

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