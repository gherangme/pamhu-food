$(document).ready(function () {
    $.ajax({
        url: 'http://localhost:8080/api/v1/manager/promotion/getInforPromotion',
        type: 'GET',
        headers: {
            'Authorization': 'Bearer ' + token
        },
        success: function (data) {
            console.log(data)
            if (data[1].data != null) {
                for (const i in data[1].data) {
                    const html = `<option value="${data[1].data[i]["id"]}">${data[1].data[i]["name"]}</option>`
                    $('#promotion-edit').append(html)
                }

                document.getElementById('promotion-edit').value = data[0].data["id"]
            }

            $('#update-btn').click(function (e) {
                e.preventDefault()
                const id = $('#promotion-edit').val()

                $.ajax({
                    url: `http://localhost:8080/api/v1/manager/promotion/updatePromotion`,
                    type: 'PUT',
                    data: {'id': id, 'token': token},
                    headers: {
                        'Authorization': 'Bearer ' + token
                    },
                    success: function (data) {
                        if (data.data) {
                            alert(data.desc)
                        } else {
                            alert(data.desc)
                        }
                    }
                })
            })
        }
    })
})