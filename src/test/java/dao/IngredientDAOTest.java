package dao;

import dto.Ingredient;
import jdbclib.DBConnector;
import jdbclib.DatabaseConnection;
import jdbclib.IConnector;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class IngredientDAOTest {
    @Test
    public void invalidIngredient_Get() throws Exception{
        final IConnector db = new DBConnector(new DatabaseConnection());
        final IIngredientDAO ingredientDAO = new IngredientDAO(db);
        final Ingredient actualIngredient = ingredientDAO.getIngredient(0);

        assertNull(actualIngredient);
    }

    @Test
    public void getIngredientList() throws Exception {
        final IConnector db = new DBConnector(new DatabaseConnection());
        final IIngredientDAO ingredientDAO = new IngredientDAO(db);

        final List<Ingredient> ingredients = ingredientDAO.getIngredientList();

        assertTrue(ingredients.size() > 0);
    }

    @Test
    public void validIngredient_CRUD() throws Exception {
        final IConnector db = new DBConnector(new DatabaseConnection());
        final IIngredientDAO ingredientDAO = new IngredientDAO(db);
        final Ingredient ingredient =
                new Ingredient(0,"salt", "Salty A/S");

        // Create
        final int id = ingredientDAO.createIngredient(ingredient);
        ingredient.setIngredientId(id);
        assertEquals(ingredient, ingredientDAO.getIngredient(id)); // Check that the product batch exists

        // Delete ingredient again
        db.connectToDatabase();
        db.update("DELETE FROM ingredient WHERE ingredient_id = " + id);
        assertFalse(ingredientDAO.getIngredientList().contains(ingredient)); // Check that the product batch is deleted
        db.close();
    }
}
