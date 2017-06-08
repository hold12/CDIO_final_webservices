/**
 * Created by awo on 08/06/17.
 */
$("#submit-login").click(function(e) {
    var form = $("#login-form").serializeJSON();
    console.log(form);

    $.ajax({
        url : "auth/user/login",
        type: "POST",
        contentType: "application/json; charset=utf-8",
        data: form,
        success: function(data) {
            console.log("Successfully posted...");
        },
        error: function(jqXHR, textStatus, errorThrown) {
            console.log("Error on POST. Status: " + textStatus + " Error: " + errorThrown);
        }
    });
    e.preventDefault();

    setTimeout(function(){}, 30);
});