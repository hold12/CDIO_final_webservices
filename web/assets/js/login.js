$(function() {
    // console.log("jQuery works");
    if (!!$.cookie("auth_token")) {
        if (confirm("Authentication token already exists. Remove existing token?")) {
            $.removeCookie("auth_token");
        } else {
            $(location).attr('href', '/');
        }
    }
});

$("#login-btn").click(function(e) {
    e.preventDefault();
    var form = $("#login-form").serializeJSON();
    // console.log(form);

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
                // console.log("Cookie: " + $.cookie("auth_token"));
                $(location).attr('href', '/home.html');
            } else if (result.status == 401) {
                // console.log("Failure :'(");
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