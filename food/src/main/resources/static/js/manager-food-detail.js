$(document).ready(function () {

    $.ajax({
        url: 'http://localhost:8080/api/v1/manager/getInforPageManager/' + token,
        type: 'GET',
        headers: {
            'Authorization': 'Bearer ' + token
        },
        success: function (data) {
            $('#name-manager').html(data[1].data)
        }
    })

    $.ajax({
        url: 'http://localhost:8080/api/v1/manager/food/getFoodDetailById',
        type: 'GET',
        headers: {
            'Authorization': 'Bearer ' + token
        },
        success: function (data) {
            console.log(data)
            $('#name-manager').html(data[0].data)
            $('#food-name').val(data[1].data["name"])
            $('#food-price').val(data[1].data["price"])
            $('#food-name-image').html(data[1].data["image"])

            if (data[1].data["categoryDTOList"] != null) {
                for (const i in data[1].data["categoryDTOList"]) {
                    const html = `<option value="${data[1].data["categoryDTOList"][i]["id"]}">${data[1].data["categoryDTOList"][i]["name"]}</option>`
                    $('#category-list').append(html)
                }
                document.getElementById('category-list').value = data[1].data["categoryDTO"]["id"]
            }

            $('#update-btn').click(function (e) {
                e.preventDefault()

                const name = $('#food-name').val(),
                    price = $('#food-price').val(),
                    idCate = $('#category-list').val()
                var image = $('#image-food')[0].files[0]
                if (image == null) {
                    alert('Vui lòng chọn hình ảnh')
                } else {
                    var formData = new FormData();
                    formData.append('name', name)
                    formData.append('price', price)
                    formData.append('idCate', idCate)
                    formData.append('file', image)

                    $.ajax({
                        url: `http://localhost:8080/api/v1/manager/food/updateFood`,
                        type: 'PUT',
                        headers: {'Authorization': 'Bearer ' + token},
                        data: formData,
                        contentType: false,
                        processData: false,
                        success: function (data) {
                            alert(data.desc)
                            window.location.href = "/manager-food-detail"
                        }
                    })
                }
            })

            $('#delete-btn').click(function (e) {
                e.preventDefault()
                $.ajax({
                    url: `http://localhost:8080/api/v1/manager/food/deleteFoodById/`,
                    type: 'DELETE',
                    headers: {'Authorization': 'Bearer ' + token},
                    success: function (data) {
                        if (data.data) {
                            alert(data.desc)
                            window.location.href="/manager"
                        }
                    }
                })
            })

        }
    })
})