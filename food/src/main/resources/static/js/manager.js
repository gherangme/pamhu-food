$(document).ready(function () {
    $.ajax({
        url: 'http://localhost:8080/api/v1/manager/',
        type: 'GET',
        headers: {
            'Authorization': 'Bearer ' + token
        },
        success: function (data) {
            console.log(data)
        }, error: function (xhr, status, error) {
            window.location.href="/403"
        }
    })
})