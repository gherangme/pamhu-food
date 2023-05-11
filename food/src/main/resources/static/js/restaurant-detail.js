$(document).ready(function () {
    $.ajax({
        method: 'GET',
        url: `http://localhost:8080/api/v1/restaurant-detail/getRestaurantById`,
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
                    window.location.href="/restaurant-detail"
                })
            }
        })

        console.log(data);
        let cate;
        if (data[0].data["dishes"] === 0) {
            cate = "Nhà hàng mới"
        } else {
            cate = data[0].data["cate"]
        }
        const html = `<img src="/static/img/brand/${data[0].data["image"]}" class="img-fluid border p-2 mb-auto rounded brand-logo shadow-sm">
                        <div class="ml-3">
                            <h3 class="mb-0 font-weight-bold">${data[0].data["name"]} <small><i
                                    class="mdi mdi-silverware-fork-knife ml-2 mr-1"></i> ${cate}</small>
                            </h3>
                            <div class="restaurant-detail mt-2 mb-3">
                                <span class="badge badge-success"><i class="mdi mdi-ticket-percent-outline"></i> ${data[0].data["couponDTO"]["name"]}</span>
                            </div>
                            <p class="text-muted p-0 mt-2 mb-2">${data[0].data["desc"]}.</p>
                            <p class="mb-0 small">
                                <i class="mdi mdi-star text-warning"></i> <span
                                    class="font-weight-bold text-dark">${data[0].data["rating"]}</span>
                                <i class="fas fa-hand-holding-usd text-dark ml-3 mr-2"></i> ${data[0].data["dishes"]} Dishes
                                <i class="fas fa-map-marked-alt text-dark ml-3 mr-2"></i>${data[0].data["address"]}
                            </p>
                        </div>`
        $('#res-infor').append(html)

        // Display All Dishes
        if (data[0].data["foodDTO"] != null) {
            for (const i in data[0].data["foodDTO"]) {
                const htmlAllDishes = `<a id=${data[0].data["foodDTO"][i]["id"]} href="#" class="text-decoration-none text-dark col-lg-3 col-md-6 mb-4 food-detail" data-toggle="modal" data-target="#myitemsModal">
                                <img src="/static/img/food/${data[0].data["foodDTO"][i]["image"]}" class="img-fluid rounded">
                                <div class="d-flex align-items-center mt-3 mb-2">
                                    <p class="text-black h6 m-0">${data[0].data["foodDTO"][i]["name"]}</p>
                                    <span class="badge badge-success ml-auto">
                                        ${data[0].data["foodDTO"][i]["price"]} VND
                                    </span>
                                </div>
                                <p class="small mb-2"><i class="mdi mdi-star text-warning"></i><span class="font-weight-bold text-dark ml-1">${data[0].data["foodDTO"][i]["star"]}</span>(${data[0].data["foodDTO"][i]["ratingNumber"]}) <i class="mdi mdi-silverware-fork-knife ml-3 mr-1"></i> 
                                ${data[0].data["foodDTO"][i]["categoryDTO"]["name"]} <i class="mdi mdi-qrcode ml-3 mr-1"></i>
                                ${data[0].data["foodDTO"][i]["id"]}
                                </p>
                            </a>`
                $('#all-dishes').append(htmlAllDishes)
            }
        }

        // Lưu nội dung ban đầu của #all-dishes
        const originalContent = $('#all-dishes').html();

        // Display All Cate
        if (data[0].data["categoryDTO"] != null) {
            for (const i in data[0].data["categoryDTO"]) {
                const htmlAllCate = `<li class="nav-item mr-2" role="presentation">
                        <a class="nav-link active border-0 btn btn-light select-cate" id="${data[0].data["categoryDTO"][i]["id"]}" data-toggle="tab" 
                        href="#${data[0].data["categoryDTO"][i]["id"]}" role="tab" aria-controls="${data[0].data["categoryDTO"][i]["id"]}" aria-selected="false">
                            ${data[0].data["categoryDTO"][i]["name"]}
                        </a>
                    </li>`
                $('.list-cate').append(htmlAllCate)

            }
        }

        $('.select-cate').click(function (e) {
            e.preventDefault()
            $('#all-dishes').empty()
            const id = $(this).attr('id')
            console.log(id)
            for (const i in data[0].data["foodDTO"]) {
                if (data[0].data["foodDTO"][i]["categoryDTO"]["id"] == id) {
                    console.log(data[0].data["foodDTO"][i]["categoryDTO"]["id"])
                    const htmlAllDishes = `<a id=${data[0].data["foodDTO"][i]["id"]} href="#" class="text-decoration-none text-dark col-lg-3 col-md-6 mb-4 food-detail" data-toggle="modal" data-target="#myitemsModal">
                                <img src="/static/img/food/${data[0].data["foodDTO"][i]["image"]}" class="img-fluid rounded">
                                <div class="d-flex align-items-center mt-3 mb-2">
                                    <p class="text-black h6 m-0">${data[0].data["foodDTO"][i]["name"]}</p>
                                    <span class="badge badge-success ml-auto">
                                        ${data[0].data["foodDTO"][i]["price"]} VND
                                    </span>
                                </div>
                                <p class="small mb-2"><i class="mdi mdi-star text-warning"></i><span class="font-weight-bold text-dark ml-1">${data[0].data["foodDTO"][i]["star"]}</span>(${data[0].data["foodDTO"][i]["ratingNumber"]})<i class="mdi mdi-silverware-fork-knife ml-3 mr-1"></i> 
                                ${data[0].data["foodDTO"][i]["categoryDTO"]["name"]}<i class="mdi mdi-qrcode ml-3 mr-1">${data[0].data["foodDTO"][i]["id"]}</i>
                                </p>
                            </a>`
                    $('#all-dishes').append(htmlAllDishes)
                }
            }
            foodDetail(data)
        })

        $('#all-tab').click(function (e) {
            e.preventDefault()
            // Khôi phục lại nội dung ban đầu của #all-dishes
            $('#all-dishes').html(originalContent);
            foodDetail(data)
        })

        foodDetail(data)
    })
})


function foodDetail(data) {
    // add item to category
    $('.food-detail').click(function (e) {
        e.preventDefault()

        // Lấy id của món ăn từ phần tử được click
        const id = $(this).attr('id')

        $.ajax({
            url: 'http://localhost:8080/api/v1/food-detail/postIdFoodDetail',
            type: 'POST',
            data: {'id': id, 'idResByUser': data[1].data},
            success: function (data) {window.location.href="/food-detail"}
        });
    })
}