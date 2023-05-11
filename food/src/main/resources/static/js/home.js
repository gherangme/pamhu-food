$(document).ready(function () {
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
                    token = null;
                    localStorage.removeItem('token');
                    window.location.href = "/home"
                })
            }
        })

        console.log(data.data)

        for (let i = 0; i < 6; i++) {
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
        url: `http://localhost:8080/api/v1/food/getAll`,
    }).done(function (data) {
        console.log(data.data)
        for (let i = 0; i < 6; i++) {
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