(function ($) {
  $(function () {

    $(document).ready(function () {

      sessionStorage.setItem('username', null);

      $('#login_button').click(function () {
        if ($('#username').val() == 'admin' && $('#password').val() == 'admin') {
          sessionStorage.setItem('username', 'admin');
          window.location.href = "/billing";
        } else {
          M.toast({ html: 'Invalid Username or Password!', classes: 'red'});
        }
      });


    });

  }); // end of document ready

})(jQuery); // end of jQuery name space
