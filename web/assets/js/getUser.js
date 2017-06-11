if (!!$.cookie("auth_token")) {
    var authHeader = "Bearer " + $.cookie("auth_token");
    console.log("Auth Header = " + authHeader);

    $.ajax({
        url: '/module/home/getLoggedUser',
        method: 'POST',
        contentType: 'application/json; charset=UTF-8',
        headers : {'Authorization': authHeader},
        complete: function(result) {
            console.log("");

            if (result.status == 200) {
                console.log("Authorized");
                $("#user-json").html(result.responseText);
            } else if (result.status == 401) {
                console.log("Not authorized");
            }
        }
    });
}
else {
    console.log("Not logged in");
}