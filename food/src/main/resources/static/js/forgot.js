$(document).ready(function () {
    $('#forgot-btn').click(function (e) {
        e.preventDefault()
        const username = $('#username').val()

        $.ajax({
            url: 'http://localhost:8080/api/v1/forgot/postUsername',
            type: 'POST',
            data: {'username': username},
            success: function (data) {
                if (data.statusCode === 200) {
                    window.location.href="/forgot-otp"
                } else {
                    const alertCustom = `<div class="alert alert-danger custom-alert" style="text-align: center">
                                        ${data.desc}
                                      </div>`
                    $('#forgot-btn').append(alertCustom)

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