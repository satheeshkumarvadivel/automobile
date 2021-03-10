(function ($) {
  $(function () {

    $(document).ready(function () {

      loadInvoices(1, 10);

      $('#customerNameSearchButton').click(function () {
        loadInvoices(1, 10, $('#customerNameSearch').val());
      });

      $('#clearSearchButton').click(function () {
        $('#customerNameSearch').val('');
        loadInvoices(1, 10);
      });

    });

    function loadInvoices(page, size, search) {
      $('viewInvoice').hide();
      $('#invoices_table').show();
      let invoiceUrl = sessionStorage.getItem('api_host') + "/rest/invoices?page=" + page + "&size=" + size;

      if (search) {
        invoiceUrl += "&search=" + search;
      }
      $.ajax({
        url: invoiceUrl,
        method: 'GET',
        async: false,
        headers: {
          'Content-Type': 'application/json',
          'Authorization': 'Basic ' + sessionStorage.getItem('creds')
        },
        success: function (result) {
          sessionStorage.setItem('invoices', result);
          showInvoicesTable(result.data);
          renderPagination(result);
        },
        error: function (result) {
          $('#invoice_table_body').html('<tr><td colspan="6" style="text-align: center !important; color: red;">Unable to load data</td></tr>')
        }
      });
    }

    function showInvoicesTable(invoices) {
      var rowHtml = "";
      for (let i = 0; i < invoices.length; i++) {
        rowHtml += "<tr><td>" + invoices[i].invoiceNumber + "</td>" +
          "<td>" + invoices[i].invoiceDate + "</td>" +
          "<td>" + invoices[i].customer.customerName + "</td>" +
          "<td>" + invoices[i].vehicleNumber + "</td>" +
          "<td>" + invoices[i].total + " Rs </td>" +
          "<td> <a class='viewInvoiceLink' style='cursor: pointer' invoiceId='" + invoices[i].invoiceNumber + "' >View </a></td></tr>";
      }
      $('#invoice_table_body').html(rowHtml);


      $('.viewInvoiceLink').click(function (e) {
        let invoiceId = e.currentTarget.attributes.invoiceId.value;
        fetchInvoiceById(invoiceId);

      });
    }

    function fetchInvoiceById(invoiceId) {
      let invoiceUrl = sessionStorage.getItem('api_host') + "/rest/invoices/" + invoiceId;
      $.ajax({
        url: invoiceUrl,
        method: 'GET',
        async: false,
        headers: {
          'Content-Type': 'application/json',
          'Authorization': 'Basic ' + sessionStorage.getItem('creds')
        },
        success: function (result) {
          sessionStorage.setItem('current_invoice', JSON.stringify(result));
          showInvoice(result);
        },
        error: function (result) {
          $('#invoice_table_body').html('<tr><td colspan="5" style="text-align: center !important; color: red;">Unable to load data</td></tr>')
        }
      });
    }

    function showInvoice(invoice) {
      $('#invoices_table').hide();
      $('#viewInvoice').show();

      $('#invoice_bill_no').html("Bill No: " + invoice.invoiceNumber);
      $('#invoice_date').html("Date: " + formatDate(invoice.invoiceDate));
      $('#invoice_customer_name').html('<b>Customer Name : </b>' + invoice.customer.customerName);
      $('#invoice_next_oil_service').html('<b>Next Oil Service : </b>' + invoice.nextOilService);
      $('#invoice_vehicle_number').html('<b>Vehicle No : </b>' + invoice.vehicleNumber);

      var rowHtml = "";
      for (let i = 0; i < invoice.products.length; i++) {
        rowHtml += '<tr class="row"><td class="col m1">' + (i + 1) + '</td><td class="col m7">' + invoice.products[i].productName +
          '</td><td class="col m2">' + invoice.products[i].total + '</td></tr>';
      }
      rowHtml += '<tr class="row"><td class="col m1"></td><td class="col m7" style="text-align: right;"><b>TOTAL :</b></td><td class="col m2"><b>' + invoice.total + '</b></td></tr>';
      $('#invoiceProductTableBody').html(rowHtml);
      $('#invoice_remarks').html("Remarks: " + invoice.remarks);

    }

    function formatDate(oldDate) {
      let dateArray = oldDate.split('-');
      return dateArray[2] + '/' + dateArray[1] + '/' + dateArray[0];
    }

    function renderPagination(invoices) {
      var paginationHtml = '<li class="disabled"><a href="#!">&lt</a></li>';
      for (let i = 0; i < invoices.totalPages; i++) {
        let page = (i + 1);
        if (page == invoices.page) {
          paginationHtml += '<li class="active"><a href="#">' + page + '</a></li>';
        } else {
          paginationHtml += '<li class="waves-effect"><a class="pageButton" page="' + page + '">' + page + '</a></li>';
        }
      }
      paginationHtml += '<li class="disabled"><a href="#!">&gt</a></li>';
      $('#invoice_table_pagination').html(paginationHtml);

      $('#invoice_table_pagination .pageButton').click(function (e) {
        let page = e.currentTarget.attributes.page.value;
        loadInvoices(page, 10);
      });

    }

  }); // end of document ready

})(jQuery); // end of jQuery name space
