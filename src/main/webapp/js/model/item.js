
function save_item(item_obj) {
    console.log(JSON.stringify(item_obj));
    $.ajax({
        url: 'http://localhost:8080/webpos2_war_exploded/item',
        method: 'POST',
        contentType: 'application/json',
        data: JSON.stringify(item_obj),
        success: function(data) {
            console.log(JSON.parse(data));
        },
        error: function(error) {
            alert('Error loading customer data');
        }
    });
    // item.push(item_obj);
}

function update_item(item_code) {
    let itemIndex = item.findIndex(value => value.code === item_code);
    item[itemIndex].item_name = name;
    item[itemIndex].qty = Number(qty);
    item[itemIndex].price = Number(price);
    item[itemIndex].description = description;
    item[itemIndex].image = $('#preview').attr('src');
}

function delete_item(number) {
    item.splice(number, 1);
}

function pagination(startIndex,endIndex) {
   return  item.slice(startIndex, endIndex)
}

function get_items(tbody) {
   
    item.map(item => {
        var row = ` <tr data-id= ${item.code} class="shadow-sm rounded-3 mb-2" style="" >
                        <td scope="row" class="d-flex justify-content-center flex-wrap" style="width: 125px"><img src=${item.image} height="70px"></td>
                        <td>${item.code}</td>
                        <td>${item.item_name}</td>
                        <td>${item.qty}</td>
                        <td>LKR. ${item.price}</td>
                        <td>${item.description}</td>
                      </tr>`;
        tbody.append(row);

    });
}