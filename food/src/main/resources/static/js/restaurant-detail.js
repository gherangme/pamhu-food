$(document).ready(function () {
    $.ajax({
        method: 'GET',
        url: `http://localhost:8080/api/v1/restaurant-detail/getRestaurantById`,
    }).done(function (data) {
        const htmlUser = `<p class="mb-0 text-white">Guess</p>
                    <p class="mb-0 text-white-50 small"><a href="/login" class="__cf_email__" data-cfemail="8ce9f4ede1fce0e9ccebe1ede5e0a2efe3e1">
                    Login</a></p>`
        $('#user-infor').append(htmlUser)
        console.log(data);
        let cate;
        if (data.data["dishes"] === 0) {
            cate = "Nhà hàng mới"
        } else {
            cate = data.data["cate"]
        }
        const html = `<img src="/static/img/brand/${data.data["image"]}" class="img-fluid border p-2 mb-auto rounded brand-logo shadow-sm">
                        <div class="ml-3">
                            <h3 class="mb-0 font-weight-bold">${data.data["name"]} <small><i
                                    class="mdi mdi-silverware-fork-knife ml-2 mr-1"></i> ${cate}</small>
                            </h3>
                            <div class="restaurant-detail mt-2 mb-3">
                                <span class="badge badge-light"><i class="mdi mdi-truck-fast-outline"></i> Free delivery</span>
                                <span class="badge badge-success"><i class="mdi mdi-ticket-percent-outline"></i> 55% OFF</span>
                                <span class="badge badge-info"><i
                                        class="mdi mdi-clock-outline"></i> Opens at 12 PM</span>
                            </div>
                            <p class="text-muted p-0 mt-2 mb-2">${data.data["desc"]}.</p>
                            <p class="mb-0 small">
                                <i class="mdi mdi-star text-warning"></i> <span
                                    class="font-weight-bold text-dark">${data.data["rating"]}</span> - 500+ Ratings
                                <i class="fas fa-hand-holding-usd text-dark ml-3 mr-2"></i> ${data.data["dishes"]} Dishes
                                <i class="fas fa-map-marked-alt text-dark ml-3 mr-2"></i>${data.data["address"]}
                            </p>
                        </div>`
        $('#res-infor').append(html)

        // Display All Dishes
        if (data.data["foodDTO"] != null) {
            for (const i in data.data["foodDTO"]) {
                const htmlAllDishes = `<a id=${data.data["foodDTO"][i]["id"]} href="#" class="text-decoration-none text-dark col-lg-3 col-md-6 mb-4 add-category" data-toggle="modal" data-target="#myitemsModal">
                                <img src="/static/img/food/${data.data["foodDTO"][i]["image"]}" class="img-fluid rounded">
                                <div class="d-flex align-items-center mt-3 mb-2">
                                    <p class="text-black h6 m-0">${data.data["foodDTO"][i]["name"]}</p>
                                    <span class="badge badge-success ml-auto">
                                        ${data.data["foodDTO"][i]["price"]} VND
                                    </span>
                                </div>
                                <p class="small mb-2"><i class="mdi mdi-star text-warning"></i><span class="font-weight-bold text-dark ml-1">${data.data["foodDTO"][i]["star"]}</span>(${data.data["foodDTO"][i]["ratingNumber"]}) <i class="mdi mdi-silverware-fork-knife ml-3 mr-1"></i> 
                                ${data.data["foodDTO"][i]["categoryDTO"]["name"]} <i class="mdi mdi-qrcode ml-3 mr-1"></i>
                                ${data.data["foodDTO"][i]["id"]}
                                </p>
                            </a>`
                $('#all-dishes').append(htmlAllDishes)
            }
        }

        // Lưu nội dung ban đầu của #all-dishes
        const originalContent = $('#all-dishes').html();

        // Display All Cate
        if (data.data["categoryDTO"] != null) {
            for (const i in data.data["categoryDTO"]) {
                const htmlAllCate = `<li class="nav-item mr-2" role="presentation">
                        <a class="nav-link active border-0 btn btn-light select-cate" id="${data.data["categoryDTO"][i]["id"]}" data-toggle="tab" 
                        href="#${data.data["categoryDTO"][i]["id"]}" role="tab" aria-controls="${data.data["categoryDTO"][i]["id"]}" aria-selected="false">
                            ${data.data["categoryDTO"][i]["name"]}
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
            for (const i in data.data["foodDTO"]) {
                if (data.data["foodDTO"][i]["categoryDTO"]["id"] == id) {
                    console.log(data.data["foodDTO"][i]["categoryDTO"]["id"])
                    const htmlAllDishes = `<a id=${data.data["foodDTO"][i]["id"]} href="#" class="text-decoration-none text-dark col-lg-3 col-md-6 mb-4 add-category" data-toggle="modal" data-target="#myitemsModal">
                                <img src="/static/img/food/${data.data["foodDTO"][i]["image"]}" class="img-fluid rounded">
                                <div class="d-flex align-items-center mt-3 mb-2">
                                    <p class="text-black h6 m-0">${data.data["foodDTO"][i]["name"]}</p>
                                    <span class="badge badge-success ml-auto">
                                        ${data.data["foodDTO"][i]["price"]} VND
                                    </span>
                                </div>
                                <p class="small mb-2"><i class="mdi mdi-star text-warning"></i><span class="font-weight-bold text-dark ml-1">${data.data["foodDTO"][i]["star"]}</span>(${data.data["foodDTO"][i]["ratingNumber"]})<i class="mdi mdi-silverware-fork-knife ml-3 mr-1"></i> 
                                ${data.data["foodDTO"][i]["categoryDTO"]["name"]}<i class="mdi mdi-qrcode ml-3 mr-1">${data.data["foodDTO"][i]["id"]}</i>
                                </p>
                            </a>`
                    $('#all-dishes').append(htmlAllDishes)
                }
            }
            addCate()
        })

        $('#all-tab').click(function (e) {
            e.preventDefault()
            // Khôi phục lại nội dung ban đầu của #all-dishes
            $('#all-dishes').html(originalContent);
            addCate()
        })

        addCate()
    })
})

function addCate() {
    // add item to category
    $('.add-category').click(function (e) {
        e.preventDefault()

        // Lấy id của món ăn từ phần tử được click
        const id = $(this).attr('id')

        $.ajax({
            url: 'http://localhost:8080/api/v1/user/cart/postIdFood',
            type: 'POST',
            headers: {'Authorization': 'Bearer ' + token},
            data: {'id': id, 'token': token},
            success: function (data) {window.location.href="/cart"},
            error: function (xhr, status, error) {
                const alertCustom = `<div class="alert alert-info custom-alert" style="text-align: center">
                                        Vui lòng đăng nhập trước khi thực hiện thao tác này.
                                      </div>`
                $('.add-category').append(alertCustom)

                $('.custom-alert').css({
                    'position': 'fixed',
                    'top': 0,
                    'left': 0,
                    'width': '100%',
                    'z-index': '9999'
                });

                // Set time out
                setTimeout(function(){
                    $(".alert").remove();
                }, 3000);
            }
        });
    })
}