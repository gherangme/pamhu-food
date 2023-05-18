$(document).ready(function () {
    $.ajax({
        url: 'http://localhost:8080/api/v1/manager/getInforPageManager/' + token,
        type: 'GET',
        headers: {
            'Authorization': 'Bearer ' + token
        },
        success: function (data) {
            console.log(data)

            // Show Infor Statistic
            $('#name-manager').html(data[1].data)
            $('#img-res').attr('src', '/static/img/brand/'+data[0].data["restaurantDetailDTO"]["image"])
            $('#name-res').html(data[0].data["restaurantDetailDTO"]["name"])
            $('#address-res').html('(địa chỉ: '+data[0].data["restaurantDetailDTO"]["address"]+')')
            $('#des-res').html(data[0].data["restaurantDetailDTO"]["desc"])
            $('#star-res').html(data[0].data["restaurantDetailDTO"]["rating"])
            $('#total-income-res').html(data[0].data["totalIncome"])
            $('#total-order-res').html(data[0].data["totalOrders"])
            $('#total-category-res').html(data[0].data["totalCategorys"])
            $('#total-food-res').html(data[0].data["totalFoods"])

            // Show List Food
            for (const i in data[0].data["foodDTOList"]) {
                const htmlListFood = `<a id="${data[0].data["foodDTOList"][i]["id"]}" href="#" class="list-group-item edit-food"><span>
                                        ${data[0].data["foodDTOList"][i]["name"]} 
                                        (${data[0].data["foodDTOList"][i]["categoryDTO"]["name"]})<em class="pull-right text-muted small">${data[0].data["foodDTOList"][i]["price"]} VND</em>
                                        
                                        </span>
                                    </a>`
                $('#list-food').append(htmlListFood)
            }

            // Show List Cate
            const seen = []; // khởi tạo mảng seen
            for (const i in data[0].data["categoryDTOList"]) {
                const name = data[0].data["categoryDTOList"][i]["name"];
                if (!seen.includes(name)) { // kiểm tra xem tên danh mục đã được hiển thị chưa
                    const htmlListCate = `<a href="#" class="list-group-item">${name}</a>`;
                    $('#list-cate').append(htmlListCate);
                    seen.push(name); // thêm tên danh mục vào mảng seen sau khi đã hiển thị
                }
            }

            // Show List Orders
            for (const i in data[2].data) {
                const htmlListOrders = `<a href="#" id="${data[2].data[i]["id"]}" class="list-group-item order-detail-btn">#${data[2].data[i]["id"]} (${data[2].data[i]["date"]})
                                        <span class="pull-right text-muted small"><em>${data[2].data[i]["totalPrice"]} VND</em>
                                        </span>
                                    </a>`
                $('#list-orders').append(htmlListOrders)
            }

            // Order-detail-btn
            $('.order-detail-btn').click(function (e) {
                e.preventDefault()
                const id = $(this).attr('id')
                $.ajax({
                    url: 'http://localhost:8080/api/v1/manager/invoice/postIdOrder',
                    type: 'POST',
                    data: {'idOrderByUser': id},
                    headers: {'Authorization': 'Bearer ' + token},
                    success: function (data) {
                        if (data.data) {
                            window.location.href="/manager-invoice-detail"
                        }
                    }
                })
            })

            // Show Promotion
            $('#name-coupon').html(data[0].data["couponDTO"]["name"])
            $('#price-coupon').html(data[0].data["couponDTO"]["voucher"])
            
            $('.edit-food').click(function (e) {
                e.preventDefault()
                const id = $(this).attr('id')
                $.ajax({
                    url: 'http://localhost:8080/api/v1/manager/food/postIdFoodDetail',
                    type: 'POST',
                    data: {'idFood': id, 'tokenOfUser': token},
                    headers: {'Authorization': 'Bearer ' + token},
                    success: function (data) {
                        if (data.statusCode === 200) {
                            window.location.href="/manager-food-detail"
                        }
                    }
                })
            })

            $('.promotion-btn').click(function (e) {
                e.preventDefault()
                const id = data[0].data["couponDTO"]["id"]
                $.ajax({
                    url: 'http://localhost:8080/api/v1/manager/promotion/postIdPromotion',
                    type: 'POST',
                    data: {'id': id},
                    headers: {'Authorization': 'Bearer ' + token},
                    success: function (data) {
                        if (data.data) {
                            window.location.href="/manager-promotion-detail"
                        }
                    }
                })
            })

            $('.add-new-food').click(function (e) {
                e.preventDefault()
                $.ajax({
                    url: 'http://localhost:8080/api/v1/manager/food/postIdFoodDetail',
                    type: 'POST',
                    data: {'idFood': 0, 'tokenOfUser': token},
                    headers: {'Authorization': 'Bearer ' + token},
                    success: function (data) {
                        if (data.statusCode === 200) {
                            window.location.href="/manager-food-add"
                        }
                    }
                })
            })

        }, error: function (xhr, status, error) {
            window.location.href="/403"
        }
    })
})