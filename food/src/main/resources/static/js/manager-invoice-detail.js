$(document).ready(function () {
    $.ajax({
        url: 'http://localhost:8080/api/v1/manager/invoice/getInforInvoiceById',
        type: 'GET',
        headers: {
            'Authorization': 'Bearer ' + token
        },
        success: function (data) {
            console.log(data)
            $('#hey-subject').html('Hey '+data.data["name"]+',')
            $('.totalPrice-invoice').html(data.data["totalPrice"])
            $('.id-invoice').html('#'+data.data["id"])
            $('.date-invoice').html(data.data["date"])
            $('.name-invoice').html(data.data["name"])
            $('.address-invoice').html(data.data["address"])
            $('.phone-invoice').html(data.data["phone"])

            let subTotal = 0;
            for (const i in data.data["foodDTOList"]) {
                const htmlListFood = `<tr>
                                    <td class="px-0">${data.data["foodDTOList"][i]["name"]} x${data.data["foodDTOList"][i]["amount"]}</td>
                                    <td class="text-end px-0">${data.data["foodDTOList"][i]["price"]}</td>
                                </tr>`
                $('.list-food').append(htmlListFood)
                subTotal += data.data["foodDTOList"][i]["amount"] * data.data["foodDTOList"][i]["price"]
            }

            $('.subTotal-invoice').html(subTotal)

            $('.discount-invoice').html('- '+data.data["voucher"])
            $('.totalPrice').html(data.data["totalPrice"]+' VND')
        }
    })
})