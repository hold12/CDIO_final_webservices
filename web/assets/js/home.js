function getPermissions(jsonObj, result) {

    var final_html = "";
    var arr = [];
    var obj = JSON.parse(jsonObj);
    console.log(obj);
    for (i = 0; i < Object.keys(obj.roles).length ; i++) {
        for (j = 0; j < Object.keys(obj.roles[i].permissions).length; j++) {
            arr.push(obj.roles[i].permissions[j]);
        }
    }
    result(arr);
}

$("#log-out-btn").click(function(e) {
    $.removeCookie("auth_token");
    $(location).attr('href', '/');
});

$(function() {
    if (!!$.cookie("auth_token")) {
        console.log("Logged in...");
    } else {
        $(location).attr('href', '/');
    }
});