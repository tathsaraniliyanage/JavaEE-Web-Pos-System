function clearTextField() {
    $('#customer-from-id').text('')
    $('#usernameInput').val("");
    $('#fullNameInput').val("");
    $('#streetInput').val("");
    $('#laneInput').val("");
    $('#cityInput').val("");
    $('#emailInput').val("");
}


function loadCustomerTable() {
    $('#customerTable tbody').empty();
    get_customers();
}

$('#customerTable tbody').on('click', 'tr', function () {

    var customer_id = $(this).data('customerid');
    $('#btn-customer-form-submit').attr('data-customer-id', customer_id);

    let text = $('#btn-customer-form-submit').text();
    // if ( text.trim()== 'Submit')
    //     $('#btn-customer-form-submit').attr('data-customer-id', customer_id);

    if($('#btn-customer-form-submit').text()==='Submit')
        $('#btn-customer-form-submit').text('Update');

    find_customer(customer_id);

});

$('#myForm').submit(function (e) {
    e.preventDefault();

    if ($('#btn-customer-form-submit').text() == 'Delete'){
        // let index = customer.findIndex(obj => obj.id === $('#customer-from-id').text());
        // if (index !== -1) {
        //     // customer.splice(index, 1);
        //     delete_customer(index);
        // }
        var customer_id = $('#btn-customer-form-submit').data('customer-id');

        remove_customer(customer_id)

        $('#btn-customer-form-submit').text('Submit');
        clearTextField();

    }else {
        // Regular expressions for validation
        var usernameRegex = /^[a-zA-Z0-9_]{3,20}$/;
        var nameRegex = /^[a-zA-Z]+ [a-zA-Z]+$/;
        var streetRegex = /^[a-zA-Z0-9\s]+$/;
        var cityRegex = /^[a-zA-Z\s]+$/;
        var emailRegex = /^[a-zA-Z0-9._-]+@gmail\.com$/;

        // Validate input fields
        var username = $('#usernameInput').val();
        var fullName = $('#fullNameInput').val();
        var street = $('#streetInput').val();
        var lane = $('#laneInput').val();
        var city = $('#cityInput').val();
        var email = $('#emailInput').val();

        if (!usernameRegex.test(username)) {
            alert('Invalid username. Username must be between 3 and 20 characters and can only contain letters, numbers, and underscores.');
            return;
        }

        if (!nameRegex.test(fullName)) {
            alert('Invalid full name. Please enter your full name in the correct format (e.g., John Doe).');
            return;
        }

        if (!streetRegex.test(street) || !streetRegex.test(lane)) {
            alert('Invalid street or lane. Please enter a valid street and lane.');
            return;
        }

        if (!cityRegex.test(city)) {
            alert('Invalid city. Please enter a valid city name.');
            return;
        }

        if (!emailRegex.test(email)) {
            alert('Invalid email. Please enter a valid Gmail address.');
            return;
        }


        if ($('#btn-customer-form-submit').text() == 'Update') {
            /*customer.map(cut => {
                if (cut.id == $('#customer-from-id').text()) {
                    cut.username = username;
                    cut.fullName = fullName;
                    cut.address.street = street;
                    cut.address.lane = lane;
                    cut.address.city = city;
                    cut.email = email;
                }
            });*/
           
            var customer_id = $('#btn-customer-form-submit').data('customer-id');
            update_customer(customer_id, username,fullName, street, lane, city, email);

            $('#customer-from-id').text('')
            $('#btn-customer-form-submit').text('Submit')
            clearTextField();
            alert('Data Updated successfully!');
        } else {
            var formData = {
                id: 'C0' + (customer.length + 1),
                username: username,
                fullName: fullName,
                address: {
                    street: street,
                    lane: lane,
                    city: city
                },
                email: email
            };

            save_customer(formData)
            clearTextField();
            alert('Form submitted successfully!');
        }
    }
    loadCustomerTable();

});


// // Ajax functions to communicate with backend
// function get_customers() {
//     $.ajax({
//         url: '/customers',
//         method: 'GET',
//         success: function(data) {
//             // Handle the data received from backend to populate the table
//             data.forEach(customer => {
//                 $('#customerTable tbody').append(`<tr data-customerid="${customer.id}"><td>${customer.username}</td><td>${customer.fullName}</td><td>${customer.address.street}</td><td>${customer.address.lane}</td><td>${customer.address.city}</td><td>${customer.email}</td></tr>`);
//             });
//         },
//         error: function(error) {
//             alert('Error loading customer data');
//         }
//     });
// }

// function find_customer(customer_id) {
//     $.ajax({
//         url: `/customers/${customer_id}`,
//         method: 'GET',
//         success: function(customer) {
//             $('#customer-from-id').text(customer.id);
//             $('#usernameInput').val(customer.username);
//             $('#fullNameInput').val(customer.fullName);
//             $('#streetInput').val(customer.address.street);
//             $('#laneInput').val(customer.address.lane);
//             $('#cityInput').val(customer.address.city);
//             $('#emailInput').val(customer.email);
//         },
//         error: function(error) {
//             alert('Error finding customer data');
//         }
//     });
// }

// function save_customer(formData) {
//     $.ajax({
//         url: '/customers',
//         method: 'POST',
//         contentType: 'application/json',
//         data: JSON.stringify(formData),
//         success: function() {
//             loadCustomerTable();
//         },
//         error: function(error) {
//             alert('Error saving customer data');
//         }
//     });
// }

// function update_customer(customer_id, username, fullName, street, lane, city, email) {
//     var updatedData = {
//         username: username,
//         fullName: fullName,
//         address: {
//             street: street,
//             lane: lane,
//             city: city
//         },
//         email: email
//     };
//     $.ajax({
//         url: `/customers/${customer_id}`,
//         method: 'PUT',
//         contentType: 'application/json',
//         data: JSON.stringify(updatedData),
//         success: function() {
//             loadCustomerTable();
//         },
//         error: function(error) {
//             alert('Error updating customer data');
//         }
//     });
// }

function delete_customer(customer_id) {
    $('#btn-customer-form-submit').text("Delete");

}


























// function clearTextField() {
//     $('#customer-from-id').text('');
//     $('#usernameInput').val('');
//     $('#fullNameInput').val('');
//     $('#streetInput').val('');
//     $('#laneInput').val('');
//     $('#cityInput').val('');
//     $('#emailInput').val('');
// }

// function loadCustomerTable() {
//     $('#customerTable tbody').empty();
//     get_customers();
// }

// $('#customerTable tbody').on('click', 'tr', function () {
//     var customer_id = $(this).data('customerid');
//     let text = $('#btn-customer-form-submit').text();
//     if (text.trim() == 'Submit') {
//         $('#btn-customer-form-submit').text('Update');
//     }
//     find_customer(customer_id);
// });

// $('#myForm').submit(function (e) {
//     e.preventDefault();

//     if ($('#btn-customer-form-submit').text() == 'Delete') {
//         let customer_id = $('#customer-from-id').text();
//         delete_customer(customer_id);
//         $('#btn-customer-form-submit').text('Submit');
//         clearTextField();
//     } else {
//         // Regular expressions for validation
//         var usernameRegex = /^[a-zA-Z0-9_]{3,20}$/;
//         var nameRegex = /^[a-zA-Z]+ [a-zA-Z]+$/;
//         var streetRegex = /^[a-zA-Z0-9\s]+$/;
//         var cityRegex = /^[a-zA-Z\s]+$/;
//         var emailRegex = /^[a-zA-Z0-9._-]+@gmail\.com$/;

//         // Validate input fields
//         var username = $('#usernameInput').val();
//         var fullName = $('#fullNameInput').val();
//         var street = $('#streetInput').val();
//         var lane = $('#laneInput').val();
//         var city = $('#cityInput').val();
//         var email = $('#emailInput').val();

//         if (!usernameRegex.test(username)) {
//             alert('Invalid username. Username must be between 3 and 20 characters and can only contain letters, numbers, and underscores.');
//             return;
//         }

//         if (!nameRegex.test(fullName)) {
//             alert('Invalid full name. Please enter your full name in the correct format (e.g., John Doe).');
//             return;
//         }

//         if (!streetRegex.test(street) || !streetRegex.test(lane)) {
//             alert('Invalid street or lane. Please enter a valid street and lane.');
//             return;
//         }

//         if (!cityRegex.test(city)) {
//             alert('Invalid city. Please enter a valid city name.');
//             return;
//         }

//         if (!emailRegex.test(email)) {
//             alert('Invalid email. Please enter a valid Gmail address.');
//             return;
//         }

//         if ($('#btn-customer-form-submit').text() == 'Update') {
//             let customer_id = $('#customer-from-id').text();
//             update_customer(customer_id, username, fullName, street, lane, city, email);
//             $('#btn-customer-form-submit').text('Submit');
//             alert('Data Updated successfully!');
//         } else {
//             var formData = {
//                 username: username,
//                 fullName: fullName,
//                 address: {
//                     street: street,
//                     lane: lane,
//                     city: city
//                 },
//                 email: email
//             };
//             save_customer(formData);
//             alert('Form submitted successfully!');
//         }
//         clearTextField();
//         loadCustomerTable();
//     }
// });

// // Ajax functions to communicate with backend
// function get_customers() {
//     $.ajax({
//         url: '/customers',
//         method: 'GET',
//         success: function (data) {
//             data.forEach(customer => {
//                 $('#customerTable tbody').append(`<tr data-customerid="${customer.id}"><td>${customer.username}</td><td>${customer.fullName}</td><td>${customer.address.street}</td><td>${customer.address.lane}</td><td>${customer.address.city}</td><td>${customer.email}</td></tr>`);
//             });
//         },
//         error: function () {
//             alert('Error loading customer data');
//         }
//     });
// }

// function find_customer(customer_id) {
//     $.ajax({
//         url: `/customers/${customer_id}`,
//         method: 'GET',
//         success: function (customer) {
//             $('#customer-from-id').text(customer.id);
//             $('#usernameInput').val(customer.username);
//             $('#fullNameInput').val(customer.fullName);
//             $('#streetInput').val(customer.address.street);
//             $('#laneInput').val(customer.address.lane);
//             $('#cityInput').val(customer.address.city);
//             $('#emailInput').val(customer.email);
//         },
//         error: function () {
//             alert('Error finding customer data');
//         }
//     });
// }

// function save_customer(formData) {
//     $.ajax({
//         url: '/customers',
//         method: 'POST',
//         contentType: 'application/json',
//         data: JSON.stringify(formData),
//         success: function () {
//             loadCustomerTable();
//         },
//         error: function () {
//             alert('Error saving customer data');
//         }
//     });
// }

// function update_customer(customer_id, username, fullName, street, lane, city, email) {
//     var updatedData = {
//         id: customer_id,
//         username: username,
//         fullName: fullName,
//         address: {
//             street: street,
//             lane: lane,
//             city: city
//         },
//         email: email
//     };
//     $.ajax({
//         url: `/customers/${customer_id}`,
//         method: 'PUT',
//         contentType: 'application/json',
//         data: JSON.stringify(updatedData),
//         success: function () {
//             loadCustomerTable();
//         },
//         error: function () {
//             alert('Error updating customer data');
//         }
//     });
// }

// function delete_customer(customer_id) {
//     $.ajax({
//         url: `/customers/${customer_id}`,
//         method: 'DELETE',
//         success: function () {
//             loadCustomerTable();
//         },
//         error: function () {
//             alert('Error deleting customer data');
//         }
//     });
// }
