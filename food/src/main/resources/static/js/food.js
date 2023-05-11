$(document).ready(function () {
    $.ajax({
        url: `http://localhost:8080/api/v1/food/getAll`,
        type: 'GET',
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
                        window.location.href = "/food"
                    })
                }
            })

            console.log(data.data)
            for (const i in data.data) {
                const html = `<a value="${data.data[i]["restaurantDTO"]["id"]}" id="${data.data[i]["id"]}" href="#" class="text-decoration-none text-dark col-lg-3 col-md-6 mb-4 food-detail" data-toggle="modal"
                       data-target="#myitemsModal">
                        <img src="/static/img/food/${data.data[i]["image"]}" class="img-fluid rounded">
                        <div class="d-flex align-items-center mt-3 mb-2">
                            <p class="text-black h6 m-0">${data.data[i]["name"]}</p>
<!--                            <span class="badge badge-light ml-auto"><i class="mdi mdi-truck-fast-outline"></i> Free delivery</span>-->
                            <span class="badge badge-success ml-auto">${data.data[i]["price"]} đ</span>
                        </div>
                        <p class="small mb-2"><i class="mdi mdi-star text-warning"></i> <span
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
        }
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