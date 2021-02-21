(function ($) {
  $(function () {

    $(document).ready(function () {
      sessionStorage.setItem('api_host', 'http://65.0.149.38');
      if (sessionStorage.getItem('username') == null || sessionStorage.getItem('username') != 'satheesh') {
        window.location.href = "/login";
      }
    });

  }); // end of document ready

})(jQuery); // end of jQuery name space
