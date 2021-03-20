(function ($) {
  $(function () {

    $(document).ready(function () {

      localStorage.setItem('username', null);

      $('#login_button').click(function () {
        if ($('#username').val() == 'ssvmotors' && $('#password').val() == 'tn30ae5515') {
          localStorage.setItem('username', 'ssvmotors');
          window.location.href = "/billing";
        } else {
          M.toast({ html: 'Invalid Username or Password!', classes: 'red'});
        }
      });


    });

  }); // end of document ready

})(jQuery); // end of jQuery name space
