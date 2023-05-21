$(document).ready(function () {
    $.ajax({
        url: `http://localhost:8080/api/v1/user/order/getAllOrderByIdUser`,
        type: 'GET',
        headers: {
            'Authorization': 'Bearer ' + token
        },
        success: function (data) {
            if (data.statusCode === 200) {
                console.log(data)
                for (const i in data.data) {
                    const htmlListOrder = `<div class="col-lg-4 col-md-6">
                            <div class="bg-white shadow-sm rounded p-3 mb-4">
                                <div class="d-flex align-items-center mb-1">
                                    <h6 class="mb-0">${data.data[i]["restaurantDTO"]["name"]}</h6>
                                    <p class="badge badge-success mb-0 ml-auto"><i class="mdi mdi-check-circle"></i>
                                        Completed</p>
                                </div>
                                <div class="d-flex align-items-center">
                                    <p class="small"><i class="mdi mdi-calendar mr-1"></i>${data.data[i]["date"]}</p>
                                </div>
                                <p class="text-dark mb-2"><span class="mr-2 text-black">Voucher</span> ${data.data[i]["date"]}</p>
                                <p class="text-dark mb-2"><span class="mr-2 text-black">Total</span> ${data.data[i]["totalPrice"]} VND</p>
                                <div class="d-flex align-items-center row pt-2 mt-3">
                                    <div class="col-6 pr-2">
                                        <a id="${data.data[i]["id"]}" href="#" class="btn btn-primary btn-block order-detail-btn" data-toggle="modal"
                                           data-target="#detailsModal">Details</a>
                                    </div>
                                </div>
                            </div>
                        </div>`
                    $('#list-orders').append(htmlListOrder)
                }

                $('.order-detail-btn').click(function (e) {
                    e.preventDefault()
                    const id = $(this).attr('id')

                    $.ajax({
                        url: `http://localhost:8080/api/v1/user/orderDetail/postIdOrder`,
                        type: 'POST',
                        data: {'idOrderByUser': id},
                        headers: {
                            'Authorization': 'Bearer ' + token
                        },
                        success: function (data) {
                            if (data.statusCode === 200) {
                                window.location.href="/order-detail"
                            } else {
                                window.location.href="/401"
                            }
                        }
                    })
                })

            } else {
                window.location.href = "/401"
            }
        }
    })
})