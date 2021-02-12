(function ($) {
  $(function () {

    $(document).ready(function () {

      sessionStorage.setItem('username', null);

      $('#login_button').click(function () {
        if ($('#username').val() == 'satheesh' && $('#password').val() == 'satheesh') {
          sessionStorage.setItem('username', 'satheesh');
          window.location.href = "/billing"
        } else {
          alert("Invalid username or password");
        }
      });


    });

  }); // end of document ready

})(jQuery); // end of jQuery name space
