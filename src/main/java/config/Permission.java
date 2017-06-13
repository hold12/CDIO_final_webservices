package config;

/**
 * Created by AndersWOlsen on 12-06-2017.
 */
public enum Permission {
	USER_CREATE,
	USER_UPDATE,
	USER_DELETE,
	USER_READ,
	INGREDIENT_CREATE,
	INGREDIENT_READ,
	RECIPE_CREATE,
	RECIPE_READ,
	INGREDIENTBATCH_CREATE,
	INGREDIENTBATCH_READ,
	PRODUCTBATCH_CREATE,
	PRODUCTBATCH_READ;


    @Override
    public String toString() {
        return this.name().toLowerCase().replace("_", ".");
    }
}
