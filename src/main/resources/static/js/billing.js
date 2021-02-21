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
      <td class="col m7">
      <div class="input-field">
        <input type="text" id="product_` + productRowCount + `" class="autocomplete">
        <label for="product_` + productRowCount + `">Product</label>
      </div>
      </td>
      <td class="col m2">
      <div class="input-field col">
        <input id="price_` + productRowCount + `" type="text"  class="price">
        <label for="price_` + productRowCount + `">Price </label>
      </div>
      </td>
      <td class="col m2" style="padding-top: 30px;">
      <a id="addProductBtn" class="btn-floating btn-small waves-effect waves-light blue small"><i
        class="material-icons">add</i></a>
      <a id="removeProductBtn" class="btn-floating btn-small waves-effect waves-light red small"><i
        class="material-icons">remove</i></a>
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
      <td class="col m7" style="text-align: right;"><b>TOTAL :</b></td>
      <td class="col m2" style="margin-left: 11.25px;"><b id="total_amount">0.0</b></td>
      <td class="col m2"></td>
    </tr>`;
    $('#productTable').append(totalRow);
    updateTotalPrice();
  }

  function redrawSerialNumber() {
    let productRowCount = parseInt(sessionStorage.getItem('productRowCount'));
    let count = 0;
    let price = 0;
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
    $('.price').each(function (index, item) {
      if ($(this).val() && $(this).val() != "") {
        totalPrice += parseFloat($(this).val());
      }
    });
    $('#total_amount').html(totalPrice);
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
        alert("Invoice created.");
        location.reload();
      },
      error: function (result) {
        alert("Error");
      }
    });

  }

  function initProductAutoComplete(productRowCount) {
    $('#product_' + productRowCount).last().autocomplete({
      data: {
        "Engine remove and refitting work": null,
        "Engine overheating work": null,
        "Gear box remove and refitting work": null,
        "Gear box overheating work": null,
        "Oil service labour": null,
        "Coolant oil changing": null,
        "Sub frame remove and fitting work": null,
        "Power steering box RGR": null,
        "Lower arm changing labour": null,
        "Front sturt assembling changing labour": null,
        "Rear sturt assembling changing labour": null,
        "All wheel brake cleaning labour": null,
        "Front brake pad changing labour": null,
        "Rear brake lining changing labour": null,
        "Engine pad changing labour": null,
        "Gear box pad changing labour": null,
        "Fuel tank remove and fitting": null,
        "Front steering knukel remove and fitting": null,
        "Oil sum remove and fitting work": null,
        "Wheel bearing changing leath work": null,
        "Brake drum facing leath work": null,
        "Brake disc facing leath work": null,
        "Fly wheel facing leath work": null,
        "ABS motor remove and fitting": null,
        "Brake boost assembling changing": null,
        "Brake master cylinder changing": null,
        "Clutch master cylinder changing": null,
        "Wheel cylinder changing work": null,
        "Dashboard remove and fitting work": null,
        "Head light bulb changing": null,
        "Tail lamp bulb changing": null,
        "Tail lamp assembling changing": null,
        "Head light assembling changing": null,
        "Drive shaft changing": null,
        "Radiater remove and fitting work": null,
        "A/C condencer changing work": null,
        "Oil coolar changing work": null,
        "Gear rod cable changing work": null,
        "Front bumper remove and fitting work": null,
        "Rear bumper remove and fitting work": null,
        "Bonnet tinkering work": null,
        "Door tinkering work": null,
        "Quarter panel tinkering work": null,
        "Running board tinkering work": null,
        "Front glass changing": null,
        "Door winding machine changing": null,
        "Tharbow remove and fitting work": null,
        "Power steering pump remove and fitting work": null,
        "Power steering pump service work": null,
        "Steering box service work": null,
        "Head remove and refitting work": null,
        "Timing belt changing work": null,
        "Water pump changing work": null,
        "Suspension bush changing (Leath)": null,
        "Hand brake cable changing": null,
        "Mat flat changing": null,
        "Wheel cylinder changing": null,
        "Front suspension remove and fitting": null,
        "Rear suspension remove and fitting": null,
        "A/C compressor remove and fitting": null,
        "A/C gas filling work": null,
        "Door lock changing": null,
        "Timing chain kit changing": null,
        "Oil pump changing": null,
        "Gas kit service": null,
        "Carburator service": null,
        "Alternator remove and fitting": null,
        "Alternator service work": null,
        "Self motor remove and fitting": null,
        "Self motor service": null,
        "Radiator service": null,
        "Engine oil": null,
        "Gear box oil": null,
        "Crown oil": null,
        "Coolant oil": null,
        "Steering box oil": null,
        "Brake oil": null,
        "Oil filter": null,
        "Fuel filter": null,
        "Air filter": null,
        "Radiator fan": null,
        "Radiator": null,
        "Lower arm": null,
        "Sturt assembly": null,
        "Ling rod": null,
        "End ball joint": null,
        "Rec end ball joint": null,
        "Oil cooler": null,
        "Injector": null,
        "Injector bush washer": null,
        "Piston": null,
        "Piston rings": null,
        "Cannict rod bearing": null,
        "Main bearing": null,
        "Thurst washer": null,
        "Head gasket": null,
        "Spark plug": null,
        "Heater plug": null,
        "Wheel bearing (front wheel)": null,
        "Brake pad": null,
        "Rear brake lining set": null,
        "Wheel bearing (rear wheel)": null,
        "ACC belt": null,
        "ACC belt tensnar": null,
        "ACC belt Idler": null,
        "Timing belt": null,
        "Timing belt tensnar bearing": null,
        "Timing belt Idler bearing": null,
        "Coolant tank": null,
        "Clutch plate kit": null,
        "Clutch reclisar bearing": null,
        "Grease": null,
        "Oil seal": null,
        "Drive Shaft": null,
        "Alternator": null,
        "A/C compressor": null,
        "Head light bulb": null,
        "Brake light bulb": null,
        "Fog lamp bulb": null,
        "Indicator bulb": null,
        "Reverse light bulb": null
      },
      minLength: 0,
      sortFunction: function (a, b, inputString) {
        return a.indexOf(inputString) - b.indexOf(inputString);
      }
    });
  }



})(jQuery); // end of jQuery name space
