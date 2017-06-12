package dao;

import dto.Recipe;
import jdbclib.DBConnector;
import jdbclib.DatabaseConnection;
import jdbclib.IConnector;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;

public class RecipeDAOTest {
    @Test
    public void invalidRecipe_Get() throws Exception{
        final IConnector db = new DBConnector(new DatabaseConnection());
        final IRecipeDAO recipeDAO = new RecipeDAO(db);
        final Recipe actualRecipe = recipeDAO.getRecipe(0);

        assertNull(actualRecipe);
    }

    @Test
    public void validRecipe_CRUD() throws Exception{
        final IConnector db = new DBConnector(new DatabaseConnection());
        final IRecipeDAO recipeDAO = new RecipeDAO(db);
        final Recipe recipe =
                new Recipe(0, "icecubes");

        // Create
        final int id = recipeDAO.createRecipe(recipe);
        recipe.setRecipeId(id);
        assertEquals(recipe, recipeDAO.getRecipe(id)); // Check that the recipe exists

        // Delete recipe again
        db.connectToDatabase();
        db.update("DELETE FROM recipe WHERE recipe_id = " + id);
        assertFalse(recipeDAO.getRecipeList().contains(recipe)); // Check that the recipe is deleted
        db.close();
    }

}
