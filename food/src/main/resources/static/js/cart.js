$(document).ready(function () {
    $.ajax({
        url: 'http://localhost:8080/api/v1/user/cart/getFoodById',
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
                        window.location.href="/home"
                    })
                }
            })

            console.log(data.data)

            const numberItem = data.data.length
            if (numberItem < 2) {
                $('#number-items').html('Cart - '+numberItem+' item')
            } else {
                $('#number-items').html('Cart - '+numberItem+' items')
            }

            let totalPrice = 0;
            for (const i in data.data) {
                totalPrice += data.data[i]["price"] * data.data[i]["amount"]
            }

            const htmlTotalPrice = `<li
                                      class="list-group-item d-flex justify-content-between align-items-center border-0 px-0 pb-0">
                                      Products
                                      <span>${totalPrice} VND</span>
                                    </li>
                                    <li class="list-group-item d-flex justify-content-between align-items-center px-0">
                                      Shipping
                                      <span>Gratis</span>
                                    </li>
                                    <li
                                      class="list-group-item d-flex justify-content-between align-items-center border-0 px-0 mb-3">
                                      <div>
                                        <strong>Total amount</strong>
                                        <strong>
                                          <p class="mb-0">(including VAT)</p>
                                        </strong>
                                      </div>
                                      <span><strong>${totalPrice} VND</strong></span>
                                    </li>`
            $('#totalPrice').append(htmlTotalPrice)

            for (const i in data.data) {
                const html = `<div class="row remove-${data.data[i]["id"]}">
                                    <div class="col-lg-3 col-md-12 mb-4 mb-lg-0">
                                      <!-- Image -->
                                      <div class="bg-image hover-overlay hover-zoom ripple rounded" data-mdb-ripple-color="light">
                                        <img src="/static/img/food/${data.data[i]["image"]}"
                                          class="w-100" />
                                        <a href="#!">
                                          <div class="mask" style="background-color: rgba(251, 251, 251, 0.2)"></div>
                                        </a>
                                      </div>
                                      <!-- Image -->
                                    </div>
                      
                                    <div class="col-lg-5 col-md-6 mb-4 mb-lg-0">
                                      <!-- Data -->
                                      <p><strong>Name: ${data.data[i]["name"]}</strong></p>
                                      <p>Category: ${data.data[i]["categoryDTO"]["name"]}</p>
                                      <!-- Price -->
                                      <p>Price: <span class="price-${data.data[i]["id"]}" value="${data.data[i]["price"]}">${data.data[i]["price"]}</span> VND</p>
                     
                                      <button type="button" class="btn btn-primary btn-sm me-1 mb-2 delete-order-item" value="${data.data[i]["id"]}" data-mdb-toggle="tooltip"
                                        title="Remove item">
                                        <i class="fas fa-trash"></i>
                                      </button>
                                      <button type="button" class="btn btn-danger btn-sm mb-2" data-mdb-toggle="tooltip"
                                        title="Move to the wish list">
                                        <i class="fas fa-heart"></i>
                                      </button>
                                      <!-- Data -->
                                    </div>
                                    
                                    <div class="col-lg-4 col-md-6 mb-4 mb-lg-0">
                                        <p style="text-align: center;">Quantity</p>
                                            <div>
                                                <!-- Quantity -->
                                                <div class="d-flex mb-4" style="max-width: 300px">
                                                    <button class="btn btn-primary px-3 me-2"
                                                    onclick="this.parentNode.querySelector('input[type=number]').stepDown()">
                                                    <i class="fas fa-minus"></i>
                                                    </button>
                                
                                                    <div class="form-outline">
                                                        
                                                    <input id="form1-${data.data[i]["id"]}" min="0" name="quantity" value="${data.data[i]["amount"]}" type="number" class="form-control" />
                                                    
                                                    </div>
                                
                                                    <button class="btn btn-primary px-3 ms-2"
                                                    onclick="this.parentNode.querySelector('input[type=number]').stepUp()">
                                                    <i class="fas fa-plus"></i>
                                                    </button>
                                            </div>
                                      </div>
                                      <!-- Quantity -->
                                      
                                      <!-- SAVE -->
                                      <div style="text-align: right">
                                            <button class="btn btn-primary px-2 save-order" id="${data.data[i]["id"]}">
                                                <i class="fa fa-cart-plus"></i> SAVE
                                            </button>
                                        </div>
                                      <!-- Price -->
                                    </div>
                                  </div>
                                  `
                $('#show-item').append(html)

                // Xoá <hr> cuối cho đẹp
                if (i != data.data.length - 1) {
                    const htmlHr = `<hr class="my-4" />`
                    $('#show-item').append(htmlHr)
                }
            }

            $('.delete-order-item').click(function (e) {
                e.preventDefault()
                const idFood = $(this).val()

                // var formData = new FormData();
                // formData.append('idFood', idFood);

                $.ajax({
                    type: 'DELETE',
                    url: 'http://localhost:8080/api/v1/user/cart/deleteOrderItem/' + idFood,
                    headers: {'Authorization': 'Bearer ' + token},
                    contentType: false,
                    processData: false,
                    success: function(data) {
                        const alertCustom = `<div class="alert alert-info custom-alert" style="text-align: center">
                                        Xoá thành công.
                                      </div>`
                        $('.delete-order-item').append(alertCustom)
                        $('.remove-'+idFood).remove()
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
                            window.location.href="/cart"
                        }, 2000);
                    }
                })
            })

            $('.save-order').click(function (e) {
                e.preventDefault()

                const idFood = $(this).attr('id')
                const amount = $('#form1-' + idFood).val()

                // parseFloat() để tránh lỗi là không lấy được phần thập phân của giá trị này.
                const price = parseFloat($('.price-' + idFood).attr('value'))

                const jsonData = {
                    amount: amount,
                    idFood: idFood,
                    price: price,
                }
                $.ajax({
                    url: 'http://localhost:8080/api/v1/user/cart/postOrderItem',
                    type: 'POST',
                    headers: {'Authorization': 'Bearer ' + token,
                        'Content-Type': 'application/json'},
                    data: JSON.stringify(jsonData),
                    success: function (data) {
                        const alertCustom = `<div class="alert alert-success custom-alert" style="text-align: center">
                                        Cập nhật thành công.
                                      </div>`
                        $('.save-order').append(alertCustom)

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
                            window.location.href="/cart"
                        }, 2000);
                    },
                    error: function (xhr, status, error) {
                        console.log('that bai')
                    }
                })
            })
            $('#checkout-button').click(function (e) {
                e.preventDefault()
                $.ajax({
                    url: 'http://localhost:8080/api/v1/user/checkout/postInforCheckout',
                    type: 'POST',
                    headers: {'Authorization': 'Bearer ' + token},
                    data: {'token': token, 'price': totalPrice},
                    success: function (data) {window.location.href="/checkout"},
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
                        setTimeout(function(){
                            $(".alert").remove();
                        }, 3000);
                    }
                });
            })
        },
        error: function (xhr, status, error) {

        }
    });
})