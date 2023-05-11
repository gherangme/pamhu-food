let idRestaurant = 0;

$(document).ready(function () {
    $.ajax({
        method: 'GET',
        url: `http://localhost:8080/api/v1/food-detail/GetFoodById`,
    }).done(function (data) {

        idRestaurant = data[2].data

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
                    token = null;
                    localStorage.removeItem('token');
                    window.location.href = "/food-detail"
                })
            }
        })

        console.log(data[1].data)
        const html = `<a href="#" id=${data[0].data["id"]} class="text-decoration-none text-dark col-lg-6 col-md-6 mb-4 select-dish" data-toggle="modal"
                            data-target="#myitemsModal">
                            <img src="/static/img/food/${data[0].data["image"]}" class="img-fluid rounded">
                            <div class="d-flex align-items-center mt-3 mb-2">
                                <p class="text-black h6 m-0">${data[0].data["name"]}</p>
                                <span class="ml-auto"><i class="mdi mdi-star text-warning"></i> <span
                                        class="font-weight-bold text-dark ml-1">${data[0].data["star"]}</span>(${data[0].data["ratingNumber"]}) <i
                                        class="mdi mdi-silverware-fork-knife ml-2 mr-1"></i> ${data[0].data["categoryDTO"]["name"]}</span>
                            </div>
                            <div style="text-align: right; margin-top: 10px;">
                                <button id=${data[0].data["id"]} type="submit" class="btn btn-primary pull-right">Add to Cart</button>
                            </div>
                        </a>`
        $('#infor-food').append(html)

        selectDish(data[2].data)
        if (data[1].data.length > 1) {
            $('#total-comment').html(data[1].data.length + ' Comments')
        } else {
            $('#total-comment').html(data[1].data.length + ' Comment')
        }
        for (const i in data[1].data) {
            const htmlListRating = `<hr>
                                    <div class="media">
                                    <a class="pull-left" href="#"><i style="margin-right: 13px;"
                                            class="mdi mdi-account-circle-outline h1"></i></a>
                                    <div class="media-body">
                                        <h6 class="media-heading">${data[1].data[i]["fullName"]}</h6>
                                        <div id="rating-star-${data[1].data[i]["id"]}">
<!--                                            <a href=""><span class="fa fa-star checked"></span></a>-->
<!--                                            <a href=""><span class="fa fa-star checked"></span></a>-->
<!--                                            <a href=""><span class="fa fa-star checked"></span></a>-->
<!--                                            <a href=""><span class="fa fa-star checked"></span></a>-->
<!--                                            <a href=""><span class="fa fa-star checked"></span></a>-->
                                        </div>
                                        <p style="margin-top: 13px; color: black">${data[1].data[i]["comment"]}</p>
                                        <ul class="list-unstyled list-inline media-detail pull-left">
                                            <li><i class="fa fa-calendar"></i> ${data[1].data[i]["date"]}</li>
                                        </ul>
                                    </div>
                                </div>`
            $('#list-rating').append(htmlListRating)
            for (let j = 0; j < data[1].data[i]["star"]; j++) {
                const htmlRatingStar= `<a href=""><span class="fa fa-star rating-color"></span></a>`
                $('#rating-star-'+data[1].data[i]["id"]).append(htmlRatingStar)
            }
        }

        let star = 0;
        $('#star-1').click(function (e) {
            e.preventDefault()
            $('#star-1').attr('class', 'rating-color')
            star = 1;
        })
        $('#star-2').click(function (e) {
            e.preventDefault()
            $('#star-1').attr('class', 'rating-color')
            $('#star-2').attr('class', 'rating-color')
            star = 2;
        })
        $('#star-3').click(function (e) {
            e.preventDefault()
            $('#star-1').attr('class', 'rating-color')
            $('#star-2').attr('class', 'rating-color')
            $('#star-3').attr('class', 'rating-color')
            star = 3;
        })
        $('#star-4').click(function (e) {
            e.preventDefault()
            $('#star-1').attr('class', 'rating-color')
            $('#star-2').attr('class', 'rating-color')
            $('#star-3').attr('class', 'rating-color')
            $('#star-4').attr('class', 'rating-color')
            star = 4;
        })
        $('#star-5').click(function (e) {
            e.preventDefault()
            $('#star-1').attr('class', 'rating-color')
            $('#star-2').attr('class', 'rating-color')
            $('#star-3').attr('class', 'rating-color')
            $('#star-4').attr('class', 'rating-color')
            $('#star-5').attr('class', 'rating-color')
            star = 5;
        })

        const htmlRatingButton = `<button id="${data[0].data["id"]}" type="submit" class="btn btn-primary pull-right comment-btn">Comment</button>`
        $('#comment-btn').append(htmlRatingButton)
        $('.comment-btn').click(function (e) {
            e.preventDefault()
            const comment = $('#comment').val()
            const id = $(this).attr('id')
            $.ajax({
                url: 'http://localhost:8080/api/v1/rating-food/postInforRatingFood',
                type: 'POST',
                headers: {'Authorization': 'Bearer ' + token},
                data: {'id': id, 'token': token, 'star': star, 'comment': comment},
                success: function (data) {
                    console.log(data)
                    const alertCustom = `<div class="alert alert-info custom-alert" style="text-align: center">
                                        ${data.desc}
                                      </div>`
                    $('.comment-btn').append(alertCustom)

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
                    }, 3000);
                },
                error: function (xhr, status, error) {
                    const alertCustom = `<div class="alert alert-info custom-alert" style="text-align: center">
                                        Vui lòng đăng nhập trước khi thực hiện thao tác này.
                                      </div>`
                    $('.comment-btn').append(alertCustom)

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
                    }, 3000);
                }
            })
        })

    })
})

function selectDish(data) {
    // add item to category
    $('.select-dish').click(function (e) {
        e.preventDefault()

        // Lấy id của món ăn từ phần tử được click
        const id = $(this).attr('id')
        $.ajax({
            url: 'http://localhost:8080/api/v1/user/cart/postIdFood',
            type: 'POST',
            headers: {'Authorization': 'Bearer ' + token},
            data: {'idFoodByUser': id, 'token': token, 'idResByUser': data},
            success: function (data) {
                window.location.href = "/cart"
            },
            error: function (xhr, status, error) {
                const alertCustom = `<div class="alert alert-info custom-alert" style="text-align: center">
                                        Vui lòng đăng nhập trước khi thực hiện thao tác này.
                                      </div>`
                $('.select-dish').append(alertCustom)

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
                }, 3000);
            }
        });
    })
}