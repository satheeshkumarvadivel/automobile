(function ($) {
  $(function () {

    $(document).ready(function () {
      if (sessionStorage.getItem('username') == null || sessionStorage.getItem('username') != 'satheesh') {
        window.location.href = "/login";
      }
    });

  }); // end of document ready

})(jQuery); // end of jQuery name space
