$(document).ready(function () {
    $.ajax({
        url: `http://localhost:8080/api/v1/user/order/getAllOrderByIdUser`,
        type: 'GET',
        headers: {
            'Authorization': 'Bearer ' + token
        },
        success: function (data) {

            $.ajax({
                url: `http://localhost:8080/api/v1/login/getInforUserByToken`,
                type: 'POST',
                data: {'token': token},
                success: function (data) {
                    if (data.statusCode === 200) {
                        const htmlInforUser = `<p class="mb-0 text-white">${data.data}</p>
                    <p class="mb-0 text-white-50 small"><a id="logout" href="/home"
                                                           class="__cf_email__"
                                                           data-cfemail="1a7f627b776a767f5a7d777b737634797577">[logout]</a>
                    </p>`
                        $('#infor-user').append(htmlInforUser)

                        $('#cart-btn').click(function (e) {
                            e.preventDefault()

                            $.ajax({
                                url: `http://localhost:8080/api/v1/user/cart/postCheckCart/`+token,
                                type: 'POST',
                                data: {'token': token},
                                headers: {'Authorization': 'Bearer ' + token},
                                success: function (data) {
                                    if (data.data) {
                                        window.location.href = "/cart"
                                    }
                                }
                            })
                        })
                    } else {
                        const htmlInforUser = `<p class="mb-0 text-white">${data.data}</p>
                    <p class="mb-0 text-white-50 small"><a href="/login"
                                                           class="__cf_email__"
                                                           data-cfemail="1a7f627b776a767f5a7d777b737634797577">[login]</a>
                    </p>`
                        $('#infor-user').append(htmlInforUser)

                        $('#cart-btn').click(function (e) {
                            e.preventDefault()
                            window.location.href="/401"
                        })
                    }

                    $('#logout').click(function (e) {
                        e.preventDefault()
                        $.ajax({
                            url: `http://localhost:8080/api/v1/login/logout`,
                            type: 'GET',
                            // data: {'token': token},
                            success: function (data) {
                                if (data.data) {
                                    token = null;
                                    document.cookie = "username=; expires=Thu, 01 Jan 1970 00:00:00 UTC; path=/;";
                                    localStorage.removeItem('token');
                                    window.location.href = "/home"
                                }
                            }
                        })
                    })
                }
            })

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

    $('#order-btn').click(function (e) {
        e.preventDefault()
        $.ajax({
            type: 'GET',
            url: `http://localhost:8080/api/v1/user/order/getTokenUser/` + token,
            headers: {'Authorization': 'Bearer ' + token},
            success: function (data) {
                if (data.data) {
                    window.location.href = "/order"
                } else {
                    window.location.href = "/401"
                }
            }
        })
    })
})