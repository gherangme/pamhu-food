$(document).ready(function () {

    console.log(token)

    $.ajax({
        method: 'GET',
        url: `http://localhost:8080/api/v1/restaurant/getAllPageHome`,
    }).done(function (data) {
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

        $.ajax({
            url: 'http://localhost:8080/api/v1/category/getAllPageHome',
            type: 'GET',
            success: function (data) {
                for (const i in data.data) {
                    const htmlListCategory = `<a id="${data.data[i]["id"]}" href="#" class="text-decoration-none col-xl-2 col-md-4 mb-4 category-btn">
                        <div class="rounded py-4 bg-white shadow-sm text-center">
<!--                            <i class="mdi mdi-fire bg-danger text-white osahan-icon mx-auto rounded-pill"></i>-->
                            <h6 class="mb-1 mt-3">${data.data[i]["name"]}</h6>
                        </div>
                    </a>`
                    $('#list-category').append(htmlListCategory)
                }

                $('.category-btn').click(function (e) {
                    e.preventDefault()
                    const id = $(this).attr('id')

                    $.ajax({
                        url: 'http://localhost:8080/api/v1/food/postIdCategory',
                        type: 'POST',
                        data: {'id': id},
                        success: function (data) {
                            window.location.href = "/food"
                        }
                    })
                })
            }
        })

        console.log(data.data)

        for (const i in data.data) {
            const html = `<a id=${data.data[i]["id"]} href="detail.html" class="text-dark text-decoration-none col-xl-4 col-lg-12 col-md-12 restaurant-detail">
                        <div class="bg-white shadow-sm rounded d-flex align-items-center p-1 mb-4 osahan-list">
                            <div class="bg-light p-3 rounded">
                                <img src="/static/img/brand/${data.data[i]["image"]}" class="img-fluid">
                            </div>
                            <div class="mx-3 py-2 w-100">
                                <p class="mb-2 text-black">${data.data[i]["name"]}</p>
                                <p class="small mb-2">
                                    <i class="mdi mdi-star text-warning mr-1"></i> <span
                                        class="font-weight-bold text-dark">${data.data[i]["rating"]}</span>
                                    <i class="mdi mdi-silverware-fork-knife ml-3 mr-1"></i> ${data.data[i]["cate"]}
                                </p>
                                <p class="mb-0 text-muted d-flex align-items-center">
                                <span class="badge badge-success">
                                <i class="mdi mdi-ticket-percent-outline"></i> 
                                ${data.data[i]["couponDTO"]["name"]}</span>
                                    <span class="small ml-auto"><i class="mdi mdi-map-marker"></i> ${data.data[i]["address"]}</span>
                                </p>
                            </div>
                        </div>
                    </a>`
            $('#list-restaurant').append(html)
        }

        $('.restaurant-detail').click(function (e) {
            e.preventDefault()
            const id = $(this).attr('id')
            console.log(id)
            $.ajax({
                method: 'GET',
                url: `http://localhost:8080/api/v1/restaurant-detail/getIdRestaurant/` + id
            }).done(function (data) {
                window.location.href = "/restaurant-detail"
            })
        })

        $('#see-all-res').click(function (e) {
            e.preventDefault()
            window.location.href = "/restaurant"
        })
    })

    $.ajax({
        method: 'GET',
        url: `http://localhost:8080/api/v1/food/getAllPageHome`,
    }).done(function (data) {
        console.log(data.data)
        for (const i in data.data) {
            const html = `<a value="${data.data[i]["restaurantDTO"]["id"]}" id="${data.data[i]["id"]}" href="#" class="text-decoration-none col-xl-4 col-md-4 mb-4 food-detail" data-toggle="modal"
                       data-target="#myitemsModal">
                        <img src="/static/img/food/${data.data[i]["image"]}" class="img-fluid rounded">
                        <div class="d-flex align-items-center mt-3">
                            <p class="text-black h6 m-0">${data.data[i]["name"]}</p>
                            <span class="badge badge-success ml-auto">${data.data[i]["price"]} VND</span>
                        </div>
                        <p class="small mb-2" style="color: black;"><i class="mdi mdi-star text-warning"></i> <span
                                class="font-weight-bold text-dark ml-1">${data.data[i]["star"]}</span>(${data.data[i]["ratingNumber"]}) <i
                                class="mdi mdi-silverware-fork-knife ml-2 mr-1"></i> ${data.data[i]["categoryDTO"]["name"]} <i
                                class="mdi mdi-home ml-2 mr-1"></i>${data.data[i]["restaurantDTO"]["name"]}</p>
                    </a>`
            $('#list-food').append(html)
        }

        foodDetail()

        $('#see-all-food').click(function (e) {
            e.preventDefault()
            window.location.href = "/food"
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
})

function foodDetail() {
    // add item to category
    $('.food-detail').click(function (e) {
        e.preventDefault()

        // Lấy id của món ăn từ phần tử được click
        const id = $(this).attr('id')
        const idRes = $(this).attr('value')

        $.ajax({
            url: 'http://localhost:8080/api/v1/food-detail/postIdFoodDetail',
            type: 'POST',
            data: {'id': id, 'idResByUser': idRes},
            success: function (data) {
                window.location.href = "/food-detail"
            }
        });
    })
}