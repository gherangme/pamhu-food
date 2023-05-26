$(document).ready(function () {

    console.log(token)

    $.ajax({
        method: 'GET',
        url: `http://localhost:8080/api/v1/restaurant/getAll`,
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
                } else {
                    const htmlInforUser = `<p class="mb-0 text-white">${data.data}</p>
                    <p class="mb-0 text-white-50 small"><a href="/login"
                                                           class="__cf_email__"
                                                           data-cfemail="1a7f627b776a767f5a7d777b737634797577">[login]</a>
                    </p>`
                    $('#infor-user').append(htmlInforUser)
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

                $('#cart-btn').click(function (e) {
                    window.location.href="/cart"
                })
            }
        })

        $.ajax({
            url: 'http://localhost:8080/api/v1/category/getAll',
            type: 'GET',
            success: function (data) {
                for (const i in data.data) {
                    const htmlListCategory = `<a id="${data.data[i]["id"]}" href="listing.html" class="category-btn text-decoration-none col-xl-2 col-md-4 mb-4">
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