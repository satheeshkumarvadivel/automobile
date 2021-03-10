(function ($) {
  $(function () {

    $(document).ready(function () {

      sessionStorage.setItem('creds', null);

      $('#login_button').click(function () {

        sessionStorage.setItem('creds', btoa($('#username').val() + ':' + $('#password').val()));
        let loginUrl = sessionStorage.getItem('api_host') + "/rest/token"

        $.ajax({
          url: loginUrl,
          method: 'GET',
          async: false,
          headers: {
            'Content-Type': 'application/json',
            'Authorization': 'Basic ' + sessionStorage.getItem('creds')
          },
          success: function (result) {
            window.location.href = "/billing";
          },
          error: function (result) {
            M.toast({ html: 'Invalid Username or Password!', classes: 'red' });
          }
        });
      });


    });

  }); // end of document ready

})(jQuery); // end of jQuery name space
