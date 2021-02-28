(function ($) {
  $(function () {

    $(document).ready(function () {
      sessionStorage.setItem('api_host', 'http://localhost:8001');
      if (sessionStorage.getItem('username') == null || sessionStorage.getItem('username') != 'ssvmotors') {
        window.location.href = "/login";
      }
    });

  }); // end of document ready

})(jQuery); // end of jQuery name space
