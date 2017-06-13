// var userjson;



$(function() {
    getSecureData("/module/home/getLoggedUser", function(userjson) {
        // console.log(out);

        // console.log(userjson);
        getPermissions(userjson, function(output) {
            console.log("permissions");
            for (i = 0; i < output.length; i++) {
                switch (output[i]) {
                    case "productbatch.read":
                        $("#side-nav-bar").append("<a href='#' class='mdl-navigation__link'>Product Batch Administration</a>");
                        break;
                    case "ingredientbatch.read":
                        $("#side-nav-bar").append("<a href='#' class='mdl-navigation__link'>Ingredient Batch Administration</a>");
                        break;
                    case "ingredient.read":
                        $("#side-nav-bar").append("<a href='#' class='mdl-navigation__link'>Ingredient Administration</a>");
                        break;
                    case "ingredientbatch.read":
                        $("#side-nav-bar").append("<a href='#' class='mdl-navigation__link'>ingredient Batch Administration</a>");
                        break;
                    case "user.read":
                        $("#side-nav-bar").append("<a href='#' class='mdl-navigation__link'>User Administration</a>");
                        getSecureData("/module/user/get/all", function(allUsers) {
                            getUsers(allUsers);
                        });

                        break;
                    case "recipe.read":
                        $("#side-nav-bar").append("<a href='#' class='mdl-navigation__link'>Recipe Administration</a>");
                        break;
                }
            }
        });;
    });
});
