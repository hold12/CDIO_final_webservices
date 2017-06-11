/**
 * Created by AndersWOlsen on 11-06-2017.
 */
function getSecureData(wantedUrl, handleData) {
    if (!!$.cookie("auth_token")) {
        var authHeader = "Bearer " + $.cookie("auth_token");
        console.log("Auth Header = " + authHeader);

        $.ajax({
            url: wantedUrl,
            method: 'POST',
            contentType: 'application/json; charset=UTF-8',
            headers : {'Authorization': authHeader},
            complete: function(result) {
                if (result.status == 200) {
                    // var test = result.responseText;
                    // console.log("response: " + test);
                    handleData(result.responseText);
                } else if (result.status == 401) {
                    return "not authorized";
                }
            }
        });
    }
    else {
        return "not authorized";
    }
}

