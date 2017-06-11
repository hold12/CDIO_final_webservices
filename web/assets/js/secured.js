/**
 * Created by AndersWOlsen on 11-06-2017.
 */

$("#secure-data").hide();
$("#error").hide();


if (!!$.cookie("auth_token")) {
    var authHeader = "Bearer " + $.cookie("auth_token");
    console.log("Auth Header = " + authHeader);
    $("#secure-data").show();

    $("#submit-user").click(function (e) {
        e.preventDefault();

        $.ajax({
            url: 'module/user/get/' + $("#userId").val(),
            method: 'POST',
            contentType: 'application/json; charset=UTF-8',
            headers : {'Authorization': authHeader},
            complete: function(result) {
                console.log("");

                if (result.status == 200) {
                    // $.cookie("auth_token", result.reponseText, { expires: 1});
                    $("#user-json").html(result.responseText);
                    $("#secure-data").show();
                    console.log("Authorized");
                } else if (result.status == 401) {
                    console.log("Not authorized");
                    $("#message").html(result.responseText);
                    $("#error").show();
                    $("#error-message").html("Not authorized. Please <a href='/login.html'>log in here</a>.");
                }
            }
        });
    });
}
else {
    $("#error").show();
    $("#error-message").html("Not authorized. Please <a href='/login.html'>log in here</a>.");
}