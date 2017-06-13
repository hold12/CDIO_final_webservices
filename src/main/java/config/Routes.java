package config;

/**
 * Created by AndersWOlsen on 12-06-2017.
 */
public class Routes {
    public final static String AUTH_ROOT = "auth";
    public final static String AUTH_AUTHENTICATION = "authentication";
    public final static String MODULE_ROOT = "module";
    public final static String MODULE_HOME = "home";
	public final static String MODULE_HOME_GETLOGGEDUSER = "getLoggedUser";
	public final static String MODULE_HOME_TEST = "test";
    public final static String MODULE_USER = "user";
	public final static String MODULE_USER_GETUSER = "get/{userId}";
	public final static String MODULE_USER_ALL = "get/all";
	public final static String MODULE_USER_UPDATE = "update";
	public final static String MODULE_USER_CREATE = "create";
	public final static String MODULE_INGREDIENT = "ingredient";
	public final static String MODULE_INGREDIENT_GETUSER = "get/{ingredientId}";
	public final static String MODULE_INGREDIENT_ALL = "get/all";
	public final static String MODULE_INGREDIENT_CREATE = "create";
}
