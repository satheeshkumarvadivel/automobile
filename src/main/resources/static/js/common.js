(function ($) {
  $(function () {

    $(document).ready(function () {
      sessionStorage.setItem('api_host', 'http://65.0.149.38');
      if (sessionStorage.getItem('creds') == "null") {
        window.location.href = "/login";
      }
    });

  }); // end of document ready

})(jQuery); // end of jQuery name space
