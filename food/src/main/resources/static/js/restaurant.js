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
                    window.location.href="/restaurant"
                })
            }
        })

        for (const i in data.data) {
            const html = `<a id=${data.data[i]["id"]} href="#" class="text-decoration-none text-dark col-lg-3 col-md-6 mb-4 restaurant-detail" data-toggle="modal"
                       data-target="#myitemsModal">
                        <img src="/static/img/brand/${data.data[i]["image"]}" class="img-fluid rounded">
                        <div class="d-flex align-items-center mt-3 mb-2">
                            <p class="text-black h6 m-0">${data.data[i]["name"]}
                                <span class="badge badge-success ml-auto">
                                <i class="mdi mdi-ticket-percent-outline"></i> 
                                ${data.data[i]["couponDTO"]["name"]}</span>
                                </span>
                            </p>
                        </div>
                        <p class="small mb-2"><i class="mdi mdi-star text-warning"></i> <span
                                class="font-weight-bold text-dark ml-1">${data.data[i]["rating"]}</span> <i
                                class="mdi mdi-silverware-fork-knife ml-2 mr-1"></i> ${data.data[i]["cate"]} <i
                                class="mdi mdi-map-marker ml-2 mr-1"></i>${data.data[i]["address"]}</p>
                    </a>`
            $('#list-restaurant').append(html)
        }

        $('.restaurant-detail').click(function (e) {
            e.preventDefault()
            const id = $(this).attr('id')
            console.log(id)
            $.ajax({
                method: 'GET',
                url: `http://localhost:8080/api/v1/restaurant-detail/getIdRestaurant/`+id
            }).done(function (data) {
                window.location.href="/restaurant-detail"
            })
        })
    })
})