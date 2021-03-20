(function ($) {
  $(function () {
    $(document).ready(function () {

      let invoice = JSON.parse(localStorage.getItem('current_invoice'));

      renderInvoicePrintPage(invoice);


    });

    function renderInvoicePrintPage(invoice) {
      $('#invoiceNumber').html(invoice.invoiceNumber);
      $('#customerName').html(invoice.customer.customerName);
      $('#nextOilService').html(invoice.nextOilService);
      $('#vehicleNumber').html(invoice.vehicleNumber);
      $('#remarks').html(invoice.remarks);
      $('#invoiceDate').html(formatDate(invoice.invoiceDate));


      var rowHtml = "";
      for (let i = 0; i < invoice.products.length; i++) {
        rowHtml += '<tr><td>' + (i + 1) + '</td><td>' + invoice.products[i].productName +
          '</td><td>' + invoice.products[i].total + '</td></tr>';
      }
      rowHtml += '<tr><td class="lastrow"></td><td class="lastrow" style="font-weight: bold; text-align: right;">TOTAL : </td><td class="lastrow" style="font-weight: bold;">' + invoice.total + '</td></tr>';
      $('#invoiceBody').html(rowHtml);

    }

    function formatDate(oldDate) {
      let dateArray = oldDate.split('-');
      return dateArray[2] + '/' + dateArray[1] + '/' + dateArray[0];
    }

  });



})(jQuery); // end of jQuery name space
