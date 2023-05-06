$(document).ready(function () {
    $.ajax({
        method: 'GET',
        url: `http://localhost:8080/api/v1/restaurant/getAll`,
    }).done(function (data) {
        for (let i = 0; i<6; i++) {
            const html = `<a href="detail.html" class="text-dark text-decoration-none col-xl-4 col-lg-12 col-md-12">
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
                                <p class="mb-0 text-muted d-flex align-items-center"><span class="badge badge-light"><i
                                        class="mdi mdi-truck-fast-outline"></i> ${data.data[i]["freeShip"]}</span>
                                    <span class="small ml-auto"><i class="mdi mdi-map-marker"></i> ${data.data[i]["address"]}</span>
                                </p>
                            </div>
                        </div>
                    </a>`
            $('#list-restaurant').append(html)
        }

        $('#see-all-res').click(function (e) {
            e.preventDefault()
            window.location.href="/restaurant"
        })
    })

    $.ajax({
        method: 'GET',
        url: `http://localhost:8080/api/v1/food/getAll`,
    }).done(function (data) {
        for (let i = 0; i<6; i++) {
            const html = `<a href="#" class="text-decoration-none col-xl-4 col-md-4 mb-4" data-toggle="modal"
                       data-target="#myitemsModal">
                        <img src="/static/img/food/${data.data[i]["image"]}" class="img-fluid rounded">
                        <div class="d-flex align-items-center mt-3">
                            <p class="text-black h6 m-0">${data.data[i]["name"]}</p>
                            <span class="badge badge-success ml-auto">${data.data[i]["price"]} VND</span>
                        </div>
                    </a>`
            $('#list-food').append(html)
        }

        $('#see-all-food').click(function (e) {
            e.preventDefault()
            window.location.href="/food"
        })
    })
})

// fetch(`http://localhost:8080/api/v1/restaurant/getAll`, {
//     method: 'GET',
//     headers: {
//         'Authorization': `Bearer ${localStorage.getItem('token')}`
//     }
// }).then(response => {
//     if (!response.ok) {
//         throw new Error('Network response was not ok');
//     }
//     return response.json();
// }).then(data => {
//     for (let i = 0; i<6; i++) {
//         const html = `<a href="detail.html" class="text-dark text-decoration-none col-xl-4 col-lg-12 col-md-12">
//                         <div class="bg-white shadow-sm rounded d-flex align-items-center p-1 mb-4 osahan-list">
//                             <div class="bg-light p-3 rounded">
//                                 <img src="/static/img/brand/${data.data[i]["image"]}" class="img-fluid">
//                             </div>
//                             <div class="mx-3 py-2 w-100">
//                                 <p class="mb-2 text-black">${data.data[i]["name"]}</p>
//                                 <p class="small mb-2">
//                                     <i class="mdi mdi-star text-warning mr-1"></i> <span
//                                         class="font-weight-bold text-dark">${data.data[i]["rating"]}</span>
//                                     <i class="mdi mdi-silverware-fork-knife ml-3 mr-1"></i> ${data.data[i]["cate"]}
//                                 </p>
//                                 <p class="mb-0 text-muted d-flex align-items-center"><span class="badge badge-light"><i
//                                         class="mdi mdi-truck-fast-outline"></i> ${data.data[i]["freeShip"]}</span>
//                                     <span class="small ml-auto"><i class="mdi mdi-map-marker"></i> ${data.data[i]["address"]}</span>
//                                 </p>
//                             </div>
//                         </div>
//                     </a>`
//         $('#list-restaurant').append(html)
//     }
//
//     $('#see-all-res').click(function (e) {
//         e.preventDefault()
//         window.location.href="/restaurant"
//     })
// }).catch(error => {
//     window.location.href="/403";
// });
//
// fetch(`http://localhost:8080/api/v1/food/getAll`, {
//     method: 'GET',
//     headers: {
//         'Authorization': `Bearer ${localStorage.getItem('token')}`
//     }
// }).then(response => {
//     if (!response.ok) {
//         throw new Error('Network response was not ok');
//     }
//     return response.json();
// }).then(data => {
//     for (let i = 0; i<6; i++) {
//         const html = `<a href="#" class="text-decoration-none col-xl-4 col-md-4 mb-4" data-toggle="modal"
//                        data-target="#myitemsModal">
//                         <img src="/static/img/food/${data.data[i]["image"]}" class="img-fluid rounded">
//                         <div class="d-flex align-items-center mt-3">
//                             <p class="text-black h6 m-0">${data.data[i]["name"]}</p>
//                             <span class="badge badge-success ml-auto">${data.data[i]["price"]} đ</span>
//                         </div>
//                     </a>`
//         $('#list-food').append(html)
//     }
//
//     $('#see-all-food').click(function (e) {
//         e.preventDefault()
//         window.location.href="/food"
//     })
// }).catch(error => {
//     window.location.href="/403";
// });
//
// $(document).ready(function () {
    // console.log("localStorage: " + localStorage.getItem('token'))
    // $('#send-otp').click(function (e) {
    //     e.preventDefault()
    //     const otp = $('#confirm-otp').val()
    //
    //     $.ajax({
    //         method: 'GET',
    //         url: `http://localhost:8080/api/v1/restaurant/getAll`,
    //         data: {
    //             'inputOTP': otp
    //         }
    //     }).done(function (data){
    //         if(data.statusCode === 400){
    //             alert(data.desc)
    //         }else {
    //             alert(data.desc)
    //         }
    //     })
    // })
// })