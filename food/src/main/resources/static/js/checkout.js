let totalPriceFinal = 0;
$(document).ready(function () {
    $.ajax({
        url: 'http://localhost:8080/api/v1/user/checkout/getInforCheckout',
        type: 'GET',
        headers: {
            'Authorization': 'Bearer ' + token
        },
        success: function (data) {

            console.log(data)
            $('#fullName').val(data[0].data["fullName"])
            $('#username').val(data[0].data["username"])
            $('#address').val(data[0].data["address"])
            $('#phone').val(data[0].data["phone"])

            $('#number-item').html(data[1].data.length)

            for (const i in data[1].data) {
                const html = `<li class="list-group-item d-flex justify-content-between lh-condensed">
                              <div>
                                <h6 class="my-0">${data[1].data[i]["name"]}</h6>
                                <small class="text-muted" value="x${data[1].data[i]["amount"]}">x${data[1].data[i]["amount"]}</small>
                              </div>
                              <span class="text-muted">${data[1].data[i]["price"]}</span>
                            </li>`
                $('#list-food').append(html)
            }

            const htmlTotalPrice = `<li class="list-group-item d-flex justify-content-between">
                              <span>Total (VND)</span>
                              <strong id="totalPrice"></strong>
                            </li>`
            $('#list-food').append(htmlTotalPrice)
            $('#totalPrice').html(parseFloat(data[2].data))
            totalPriceFinal = parseFloat(data[2].data)
            $('#voucher-btn').click(function () {
                const voucher = $('#code-voucher').val();
                console.log(voucher);
                $.ajax({
                    url: 'http://localhost:8080/api/v1/user/checkout/putVoucher',
                    type: 'PUT',
                    headers: {
                        'Authorization': 'Bearer ' + token
                    },
                    data: {'promotionCode': voucher, 'idRes': idRestaurant},
                    success: function (data) {
                        console.log(data);
                        const totalPriceAfterVoucher = parseInt(data[2].data);
                        totalPriceFinal = totalPriceAfterVoucher;
                        if (data[2].statusCode == 200) {
                            $('#totalPrice').val(totalPriceAfterVoucher); // sửa đổi giá trị của ô input
                        } else {
                            alert(data[2].desc);
                        }
                    }
                });
            });

            $('#checkout-button').click(function (e) {
                e.preventDefault()
                const fullName = $('#fullName').val(),
                    username = $('#username').val(),
                    address = $('#address').val(),
                    phone = $('#phone').val()
                console.log(fullName + username + address + phone + parseFloat(data[1].desc))
                const jsonData = {
                    fullName: fullName,
                    username: username,
                    address: address,
                    phone: phone,
                    totalPrice: totalPriceFinal
                }
                $.ajax({
                    url: 'http://localhost:8080/api/v1/user/checkout/checkout',
                    type: 'PUT',
                    headers: {
                        'Authorization': 'Bearer ' + token,
                        'Content-Type': 'application/json'
                    },
                    data: JSON.stringify(jsonData),
                    success: function (data) {
                        const alertCustom = `<div class="alert alert-success custom-alert" style="text-align: center">
                                        ${data.desc}.
                                      </div>`
                        $('#checkout-button').append(alertCustom)

                        $('.custom-alert').css({
                            'position': 'fixed',
                            'top': 0,
                            'left': 0,
                            'width': '100%',
                            'z-index': '9999'
                        });

                        // Set time out
                        setTimeout(function () {
                            $(".alert").remove();
                        }, 5000);
                    },
                    error: function (xhr, status, error) {
                        console.log('that bai')
                    }
                })
            })
        }
    })
})