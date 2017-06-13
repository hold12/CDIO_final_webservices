/**
 * Created by awo on 08/06/17.
 */
// $("#submit-login").click(function(e) {
//     var form = $("#login-form").serializeJSON();
//     console.log(form);
//
//     $.ajax({
//         url : "auth/user/login",
//         type: "POST",
//         contentType: "application/json; charset=utf-8",
//         data: form,
//         success: function(data) {
//             console.log("Successfully posted...");
//         },
//         error: function(jqXHR, textStatus, errorThrown) {
//             console.log("Error on POST. Status: " + textStatus + " Error: " + errorThrown);
//         }
//     });
//     e.preventDefault();
//
//     setTimeout(function(){}, 30);
// });

$(function() {
   if (!!$.cookie("auth_token")) {
       if (confirm("Authentication token already exists. Remove existing token?")) {
           $.removeCookie("auth_token");
       } else {
           $(location).attr('href', '/');
       }
   }
});

$("#submit-login").click(function(e) {
   var form = $("#login-form").serializeJSON();
   console.log(form);

   $.ajax({
       url: 'auth/authentication',
       type: 'POST',
       contentType: 'application/json; charset=UTF-8',
       data: form,
       complete: function(result) {
          //console.log("Status Code: " + result.status + ". Authentication Complete " + result.responseText);
          if (result.status == 200) {
              // $.cookie("auth_token", result.reponseText, { expires: 1});
              $.cookie("auth_token", "" + result.responseText);
              console.log("Cookie: " + $.cookie("auth_token"));
              $(location).attr('href', '/');
          } else if (result.status == 401) {
              $.removeCookie("auth_token");
          }
       },

       success: function(result) {
          console.log("Status Code: " + result.status + ". Token: " + result.responseText);
       },

       error: function(result) {
          console.log("Status Code: " + result.status + ". Error, reponse: " + result.responseText);
       },
   });
   // session.isAuthenticated(true);
});