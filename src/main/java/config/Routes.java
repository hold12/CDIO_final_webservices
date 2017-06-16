package config;

/**
 * Created by AndersWOlsen on 12-06-2017.
 */
public class Routes {
	// ==== Authentication ====
    public final static String AUTH_ROOT = "auth";
    public final static String AUTH_AUTHENTICATION = "authentication";

    // ==== Modules ====
    public final static String MODULE_ROOT = "module";

    // ==== Home Module ====
    public final static String MODULE_HOME = "home";
	public final static String MODULE_HOME_GETLOGGEDUSER = "getLoggedUser";
	public final static String MODULE_HOME_TEST = "test";

	// ==== User Module ====
    public final static String MODULE_USER = "user";
	public final static String MODULE_USER_GET_ID = "userId";
	public final static String MODULE_USER_GET = "get/{" + MODULE_USER_GET_ID + "}";
	public final static String MODULE_USER_ALL = "get/all";
	public final static String MODULE_USER_UPDATE = "update";
	public final static String MODULE_USER_CREATE = "create";
	public final static String MODULE_USER_GENERATEPASSWORD = "update/password";

	// ==== Ingredient Module
	public final static String MODULE_INGREDIENT = "ingredient";
	public final static String MODULE_INGREDIENT_GET_ID = "ingredientId";
	public final static String MODULE_INGREDIENT_GET = "get/{" + MODULE_INGREDIENT_GET_ID + "}";
	public final static String MODULE_INGREDIENT_ALL = "get/all";
	public final static String MODULE_INGREDIENT_CREATE = "create";

	// ==== Recipe Module
	public final static String MODULE_RECIPE = "recipe";
	public final static String MODULE_RECIPE_GET_ID = "recipeId";
	public final static String MODULE_RECIPE_GET = "get/{" + MODULE_RECIPE_GET_ID + "}";
	public final static String MODULE_RECIPE_ALL = "get/all";
	public final static String MODULE_RECIPE_CREATE = "create";

	// ==== Recipe Component Module
    public final static String MODULE_RECIPECOMPONENT = "recipecomponent";
    public final static String MODULE_RECIPECOMPONENT_GET_RECIPEID = "recipeId";
    public final static String MODULE_RECIPECOMPONENT_ALL = "get/all/{" + MODULE_RECIPECOMPONENT_GET_RECIPEID + "}";
    public final static String MODULE_RECIPECOMPONENT_CREATE = "create";

    // ==== Ingredient Batch Module
	public final static String MODULE_INGREDIENTBATCH = "ingredientbatch";
	public final static String MODULE_INGREDIENTBATCH_GET_ID = "ingredientBatchId";
	public final static String MODULE_INGREDIENTBATCH_GET = "get/{" + MODULE_INGREDIENTBATCH_GET_ID + "}";
	public final static String MODULE_INGREDIENTBATCH_ALL = "get/all";
	public final static String MODULE_INGREDIENTBATCH_CREATE = "create";

	// ==== Product Batch ====
	public final static String MODULE_PRODUCTBATCH = "productbatch";
	public final static String MODULE_PRODUCTBATCH_GET_ID = "productbatchId";
	public final static String MODULE_PRODUCTBATCH_GET = "get/{" + MODULE_PRODUCTBATCH_GET_ID + "}";
	public final static String MODULE_PRODUCTBATCH_ALL = "get/all";
	public final static String MODULE_PRODUCTBATCH_CREATE = "create";

	// ==== Product Batch Component ====
	public final static String MODULE_PRODUCTBATCHCOMPONENT = "productbatchcomponent";
	public final static String MODULE_PRODUCTBATCHCOMPONENT_GET_PRODUCTBATCHID = "productbatchId";
	public final static String MODULE_PRODUCTBATCHCOMPONENT_ALL = "get/all/{" + MODULE_PRODUCTBATCHCOMPONENT_GET_PRODUCTBATCHID + "}";

	// ==== Role Module ====
	public final static String MODULE_ROLE = "role";
	public final static String MODULE_ROLE_GET_ALL = "get/all";
}