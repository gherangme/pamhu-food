fetch(`http://localhost:8080/api/v1/food/getAll`, {
    method: 'GET',
    headers: {
        'Authorization': `Bearer ${localStorage.getItem('token')}`
    }
}).then(response => {
    if (!response.ok) {
        throw new Error('Network response was not ok');
    }
    return response.json();
}).then(data => {
    console.log(data.data)
    for (const i in data.data) {
        const html = `<a href="#" class="text-decoration-none text-dark col-lg-3 col-md-6 mb-4" data-toggle="modal"
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

    $('#see-all-food').click(function (e) {
        e.preventDefault()
        window.location.href="/food"
    })
}).catch(error => {
    window.location.href="/403";
});

$(document).ready(function () {
})