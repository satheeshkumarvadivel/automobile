(function ($) {
  $(function () {
    sessionStorage.setItem('productRowCount', 1);
    initProductAutoComplete(1);
    showTodaysDate();

    $('#productTable').on('click', '#addProductBtn', function (e) {
      appendNewProductRow(e);
    });

    $('#productTable').on('click', '#removeProductBtn', function (e) {
      $(e.currentTarget).parent().parent().remove();
      redrawSerialNumber();
    });

    $('#productTable').on('keyup', '.price', function (e) {
      updateItemTotal(e);
      updateTotalPrice();
    });

    $('#productTable').on('keyup', '.gst', function (e) {
      updateItemTotal(e);
      updateTotalPrice();
    });

    $('#productTable').on('keyup', '.quantity', function (e) {
      updateItemTotal(e);
      updateTotalPrice();
    });

    $('#save_button').click(function () {
      saveBill();
    });

  }); // end of document ready

  function showTodaysDate() {
    var today = new Date();
    var dd = String(today.getDate()).padStart(2, '0');
    var mm = String(today.getMonth() + 1).padStart(2, '0');
    var yyyy = today.getFullYear();
    var date = dd + "/" + mm + "/" + yyyy;
    $('#dateDiv').html('Date : ' + date);
  }

  function appendNewProductRow(e) {
    let count = parseInt(sessionStorage.getItem('productRowCount'));
    sessionStorage.setItem('productRowCount', count + 1);
    let productRowCount = parseInt(sessionStorage.getItem('productRowCount'));
    let productRow = `
    <tr class="row" id="productline_` + productRowCount + `">
      <td class="col m1" style="padding: 30px;" id="product_sno_` + productRowCount + `">` + productRowCount + `</td>
      <td class="col m6">
      <div class="input-field">
        <input type="text" id="product_` + productRowCount + `" class="autocomplete">
      </div>
      </td>
      <td class="col m1">
      <div class="input-field col">
        <input id="price_` + productRowCount + `" type="text" class="price">
      </div>
      </td>
      <td class="col m1">
      <div class="input-field col">
        <input id="gst_` + productRowCount + `" type="text" value="12" class="gst">
      </div>
      </td>
      <td class="col m1">
      <div class="input-field col">
        <input id="quantity_` + productRowCount + `" type="text" value="1" class="quantity">
      </div>
      </td>
      <td class="col m1">
      <div class="input-field col">
        <input id="total_` + productRowCount + `" type="text"  class="total">
      </div>
      </td>
      <td class="col m1" style="padding-top: 30px;">
      <a id="addProductBtn" class="btn-floating btn-small waves-effect waves-light blue small">+</a>
      <a id="removeProductBtn" class="btn-floating btn-small waves-effect waves-light red small">-</a>
      </td>
    </tr>`;


    $(e.currentTarget).parent().parent().parent().append(productRow);
    initProductAutoComplete(productRowCount);
    calculateTotal();
  }

  function calculateTotal() {
    $('#totalRow').remove();
    let totalRow = `
    <tr class="row" id="totalRow">
      <td class="col m1"></td>
      <td class="col m9" style="text-align: right;"><b>TOTAL :</b></td>
      <td class="col m1" style="margin-left: 5px;"><b id="total_amount">0.0</b></td>
      <td class="col m1"></td>
    </tr>`;
    $('#productTable').append(totalRow);
    updateTotalPrice();
  }

  function redrawSerialNumber() {
    let productRowCount = parseInt(sessionStorage.getItem('productRowCount'));
    let count = 0;
    for (var i = 1; i <= productRowCount; i++) {
      let snoElement = $('#product_sno_' + i);
      if (snoElement.length > 0) {
        count++;
        $('#product_sno_' + i).html(count);
        $('#product_sno_' + i).attr('id', 'product_sno_' + count);

      }
    }
    sessionStorage.setItem('productRowCount', count);
    updateTotalPrice();
  }

  function updateTotalPrice() {
    var totalPrice = 0;
    $('.total').each(function (index, item) {
      if ($(this).val() && $(this).val() != "") {
        totalPrice += parseFloat($(this).val());
      }
    });
    $('#total_amount').html(totalPrice);
  }

  function updateItemTotal(e) {
    let price = e.currentTarget.parentElement.parentElement.parentElement.getElementsByClassName('price')[0].value;
    let gst = e.currentTarget.parentElement.parentElement.parentElement.getElementsByClassName('gst')[0].value;
    let quantity = e.currentTarget.parentElement.parentElement.parentElement.getElementsByClassName('quantity')[0].value;

    price = (price == "") ? 0 : parseFloat(price);

    let itemTotal = (price + (price * (parseFloat(gst) / 100))) * parseInt(quantity);
    e.currentTarget.parentElement.parentElement.parentElement.getElementsByClassName('total')[0].value = itemTotal;
  }

  function saveBill() {
    let productCount = parseInt(sessionStorage.getItem('productRowCount'));
    let invoice = {};
    let customer = {};
    customer.customerName = $('#customer_name').val();
    invoice.customer = customer;
    invoice.vehicleNumber = $('#vehicle_no').val();
    invoice.nextOilService = $('#next_oil_service').val();
    invoice.remarks = $('#remarks').val();
    invoice.products = [];
    for (var i = 1; i <= productCount; i++) {
      let product = {};
      product.productName = $('#product_' + i).val();
      product.price = $('#price_' + i).val();
      invoice.products.push(product);
    }
    invoice.total = $('#total_amount').html();
    console.log(JSON.stringify(invoice));

    let invoiceUrl = sessionStorage.getItem('api_host') + "/invoices"
    $.ajax({
      url: invoiceUrl,
      method: 'POST',
      data: JSON.stringify(invoice),
      async: false,
      headers: {
        'Content-Type': 'application/json'
      },
      success: function (result) {
        M.toast({ html: 'Invoice created successfully!', classes: 'green', completeCallback: function () { location.reload(); } });
      },
      error: function (result) {
        console.log(JSON.stringify(result));
        try {
          M.toast({ html: 'Error: ' + result.responseJSON.message, classes: 'red' });
        } catch (error) {
          M.toast({ html: 'Error: Unable to create Invoice.', classes: 'red' });
        }
      }
    });

  }

  function initProductAutoComplete(productRowCount) {
    $('#product_' + productRowCount).last().autocomplete({
      data: {
        "Digital Pulse Oximeter": null,
        "Omran BP Apparatus": null,
        "OT Light 8 LED": null,
        "N95 mask 200pcs box": null,
        "N95 mask 100pcs box": null
      },
      minLength: 0,
      sortFunction: function (a, b, inputString) {
        return a.indexOf(inputString) - b.indexOf(inputString);
      }
    });
  }



})(jQuery); // end of jQuery name space
