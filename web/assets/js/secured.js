/**
 * Created by AndersWOlsen on 11-06-2017.
 */


$("#submit").click(function(e) {
    var authHeader = "Bearer " + $("#token").val();
    console.log("Auth Header = " + authHeader);

    $.ajax({
       url: 'rest/hello/secure',
        method: 'POST',
        headers : {'Authorization': authHeader},
        success: function(result) {
           console.log("Success, result: " + result.responseText);
           $("#message").html(result.responseText);
        },
        error: function(result) {
           console.log("Error, result: " + result.responseText);
           $("#message").html(result.responseText);
        },
    });

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
});
