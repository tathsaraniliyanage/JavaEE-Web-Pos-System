function loadCustomerTable() {
    $.ajax({
        url: 'http://localhost:8080/webpos2_war_exploded/customer',  // Ensure this matches the servlet URL pattern
        method: 'GET',
        success: function (data) {

            console.log(data)
            $('#customerTable tbody').empty(); // Clear existing rows
            data.data.forEach(item => {
                var row = `<tr data-customerid="${item.id}">
                <td>${item.id}</td>
                <td>${item.username}</td>
                <td>${item.fullName}</td>
                <td>${item.street}</td>
                <td>${item.lane}</td>
                <td>${item.city}</td>
                <td>${item.email}</td>
                <td><button onclick="delete_customer('${item.id}')" class="border-0 bg-white"><i class="fa-solid fa-trash"></i></button></td>
            </tr>`;
                $('#customerTable tbody').append(row);
            });
        },
        error: function (error) {
            alert('Error loading customer data');
        }
    });
}

function remove_customer(customer_id) {
    console.log(customer_id);
    $.ajax({
        url: `http://localhost:8080/webpos2_war_exploded/customer?customerId=${customer_id}`,
        method: 'DELETE',
        success: function(data) {
            console.log(data);
        },
        error: function(error) {
            alert('Error delete customer data');
        }
    });
}

function update_customer(id,username, fullName, street, lane, city, email) {
    var customer={
        "id": id,
        "username":username,
        "fullName":fullName,
        "street":street,
        "lane":lane,
        "city":city,
        "email":email
    }
    $.ajax({
        url: `http://localhost:8080/webpos2_war_exploded/customer?customerId=${id}`,
        method: 'PATCH',
        contentType: 'application/json',
        data: JSON.stringify(customer),
        success: function(data) {
            console.log(JSON.parse(data));
        },
        error: function(error) {
            alert('Error loading customer data');
        }
    });
}

function save_customer(formData) {
    // customer.push(formData);

    var customer={
        "id": formData.id,
        "username":formData.username,
        "fullName":formData.fullName,
        "street":formData.address.street,
        "lane":formData.address.lane,
        "city":formData.address.city,
        "email":formData.email
    }

    $.ajax({
                url: 'http://localhost:8080/webpos2_war_exploded/customer',
                method: 'POST',
                contentType: 'application/json',
                data: JSON.stringify(customer),
                success: function(data) {
                    // Handle the data received from backend to populate the table
                    var customer=JSON.parse(data).data;
                    $('#customerTable tbody')
                    .append(`
                        <tr data-customerid="${customer.id}">
                            <td>${customer.username}</td>
                            <td>${customer.fullName}</td>
                            <td>${customer.street}</td>
                            <td>${customer.lane}</td>
                            <td>${customer.city}</td>
                            <td>${customer.email}</td> 
                            <td> 
                                <button 
                                    data-customerid="${customer.id}"
                                    onclick='customer_remove()' 
                                    class='border-0 bg-white' > 
                                        <i class="fa-solid fa-trash" ></i>
                                </button>
                            </td>
                        </tr>`);

                    console.log(JSON.parse(data));
                },
                error: function(error) {
                    alert('Error loading customer data');
                }
            });
}

function get_customers() {

    console.log("get_customers")
}

function find_customer(customer_id) {
    $.ajax({
        url: `http://localhost:8080/webpos2_war_exploded/customer?customerId=${customer_id}`,
        method: 'GET',
        success: function(data) {
            
            var customer=data.data;

            console.log(data.data)

            $('#customer-from-id').text(customer.id);
             $('#usernameInput').val(customer.username);
            $('#fullNameInput').val(customer.fullName);
             $('#streetInput').val(customer.street);
            $('#laneInput').val(customer.lane);
            $('#cityInput').val(customer.city);
            $('#emailInput').val(customer.email);
        }
    });
   

}
