(function ($) {
  $(function () {

    $(document).ready(function () {
      localStorage.setItem('api_host', 'http://localhost:8080');
      if (localStorage.getItem('username') == null || localStorage.getItem('username') != 'ssvmotors') {
        window.location.href = "/login";
      }
    });

  }); // end of document ready

})(jQuery); // end of jQuery name space
