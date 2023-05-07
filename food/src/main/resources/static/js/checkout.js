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
            $('#totalPrice').html(parseFloat(data[1].desc))

            // Promote coupon làm sau
            // const htmlPromote = `<div class="text-success">
            //                     <h6 class="my-0">Promo code</h6>
            //                     <small>EXAMPLECODE</small>
            //                   </div>
            //                   <span class="text-success">-$5</span>`
            // $('#get-promote').val(htmlPromote)
        }
    })
})