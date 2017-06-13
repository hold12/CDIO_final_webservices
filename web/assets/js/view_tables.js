function getUsers(jsonString) {
    var jsonObject = JSON.parse(jsonString);
    $("#table-column").append("<thead><tr>");
    $("#table-column").append("<th>UserID</th>");
    $("#table-column").append("<th>firstname</th>");
    $("#table-column").append("<th>lastname</th>");
    $("#table-column").append("<th>Initials</th>");
    $("#table-column").append("<th>Password</th>");
    $("#table-column").append("<th>Roles</th>");
    $("#table-column").append("</tr></thead><body>");
    for (i = 0; i < Object.keys(jsonObject).length; i++) {
        // console.log(jsonObject[0]);
        $("#table-column").append("<tr class=\"active\">");
        $("#table-column").append("<td>"+jsonObject[i].userId+"</td>");
        $("#table-column").append("<td>"+jsonObject[i].firstname+"</td>");
        $("#table-column").append("<td>"+jsonObject[i].lastname+"</td>");
        $("#table-column").append("<td>"+jsonObject[i].initials+"</td>");
        $("#table-column").append("<td>"+jsonObject[i].password+"</td>");
        // $("#table-column").append("<td>" + jsonObject[i].roles.role_name + "</td>");
        $("#table-column").append("<td id='roleCol" + i + "'>");
        for (j = 0; j < Object.keys(jsonObject[i].roles).length; j++) {
            // console.log("obj = " + jsonObject[i].roles[j].role_name);
            // console.log("#rolesCol" + i);
            // $("#roles_col" + i).append(jsonObject[i].roles[j].role_name + ", ");
            if (j === Object.keys(jsonObject[i].roles).length - 1) {
                $("#roleCol" + i).append(jsonObject[i].roles[j].role_name);
            } else {
                $("#roleCol" + i).append(jsonObject[i].roles[j].role_name + ", ");
            }
        }
        $("#table-column").append("</td>");
        $("#table-column").append("</tr>");
    }
    $("#table-column").append("</tbody>");
};