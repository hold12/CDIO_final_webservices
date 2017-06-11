/**
 * Created by AndersWOlsen on 11-06-2017.
 */

$("#secure-data").hide();
$("#error").hide();
//$("#userOutput").hide();


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
            // success: function(result) {
            //     console.log("=== Success ===");
            //
            //     if (result.status == 200) {
            //         // $.cookie("auth_token", result.reponseText, { expires: 1});
            //         $("#secure-data").show();
            //         $("#user-json").html(result.responseText);
            //         console.log("Authorized");
            //     } else if (result.status == 401) {
            //         $("#message").html(result.responseText);
            //         $("#error").show();
            //         $("#error-message").html("Not authorized. Please <a href='/login.html'>log in here</a>.");
            //     }
            // },
            // error: function(result) {
            //     console.log("=== Error ===");
            //
            //     if (result.status == 200) {
            //         // $.cookie("auth_token", result.reponseText, { expires: 1});
            //         $("#user-json").html(result.responseText);
            //         $("#secure-data").show();
            //         console.log("Authorized");
            //     } else if (result.status == 401) {
            //         $("#message").html(result.responseText);
            //         $("#error").show();
            //         $("#error-message").html("Not authorized. Please <a href='/login.html'>log in here</a>.");
            //     }
            // },
        });
    });
}
else {
    $("#error").show();
    $("#error-message").html("Not authorized. Please <a href='/login.html'>log in here</a>.");
}




// $("#submit").click(function(e) {
//     var authHeader = "Bearer " + $("#token").val();
//     console.log("Auth Header = " + authHeader);
//
//     $.ajax({
//        url: 'rest/hello/secure',
//         method: 'POST',
//         headers : {'Authorization': authHeader},
//         success: function(result) {
//            console.log("Success, result: " + result.responseText);
//            $("#message").html(result.responseText);
//         },
//         error: function(result) {
//            console.log("Error, result: " + result.responseText);
//            $("#message").html(result.responseText);
//         },
//     });
// });
//
// $.ajax({
//     url: 'rest/hello/secure',
//     type: 'POST',
//     contentType: 'plain/text; charset=UTF-8',
//     data: $("#token").val(),
//     beforeSend: function(xhr) {
//         console.log("Token = " + $("#token").val());
//         xhr.setRequestHeader(("Authorization", "Bearer " + $("#token").val()));
//     },
//
//     success: function(result) {
//         console.log("Status Code: " + result.status + ". Token: " + result.responseText);
//         $("#message").html(result.responseText);
//     },
//
//     error: function(result) {
//         console.log("Status Code: " + result.status + ". Error, reponse: " + result.responseText);
//         $("#message").html(result.responseText);
//     },
// });
