$(document).ready(function () {
    $.ajax({
        method: 'GET',
        url: `http://localhost:8080/api/v1/restaurant/getAll`,
    }).done(function (data) {
        for (const i in data.data) {
            const html = `<a id=${data.data[i]["id"]} href="#" class="text-decoration-none text-dark col-lg-3 col-md-6 mb-4 restaurant-detail" data-toggle="modal"
                       data-target="#myitemsModal">
                        <img src="/static/img/brand/${data.data[i]["image"]}" class="img-fluid rounded">
                        <div class="d-flex align-items-center mt-3 mb-2">
                            <p class="text-black h6 m-0">${data.data[i]["name"]}
                                <span class="badge badge-light ml-auto"><i class="mdi mdi-truck-fast-outline"></i> 
                                    ${data.data[i]["freeShip"]}
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
// console.log(data);
// console.log(data.data)
// console.log(data.data[0]["name"])
// for (const i in data.data) {
//     const html = `<a id=${data.data[i]["id"]} href="#" class="text-decoration-none text-dark col-lg-3 col-md-6 mb-4 restaurant-detail" data-toggle="modal"
//                    data-target="#myitemsModal">
//                     <img src="/static/img/brand/${data.data[i]["image"]}" class="img-fluid rounded">
//                     <div class="d-flex align-items-center mt-3 mb-2">
//                         <p class="text-black h6 m-0">${data.data[i]["name"]}
//                             <span class="badge badge-light ml-auto"><i class="mdi mdi-truck-fast-outline"></i>
//                                 ${data.data[i]["freeShip"]}
//                             </span>
//                         </p>
//                     </div>
//                     <p class="small mb-2"><i class="mdi mdi-star text-warning"></i> <span
//                             class="font-weight-bold text-dark ml-1">${data.data[i]["rating"]}</span> <i
//                             class="mdi mdi-silverware-fork-knife ml-2 mr-1"></i> ${data.data[i]["cate"]} <i
//                             class="mdi mdi-map-marker ml-2 mr-1"></i>${data.data[i]["address"]}</p>
//                 </a>`
//     $('#list-restaurant').append(html)
// }
//
// $('.restaurant-detail').click(function (e) {
//     e.preventDefault()
//     const id = $(this).attr('id')
//     console.log(id)
//     $.ajax({
//         method: 'POST',
//         url: `http://localhost:8080/api/v1/restaurant-detail/postIdRestaurant`,
//         data: {
//             'id': id
//         }
//     }).done(function (data) {
//         window.location.href="/restaurant-detail"
//     })
// })

// }).catch(error => {
//     alert(error.message)
//     window.location.href="/403";
// });