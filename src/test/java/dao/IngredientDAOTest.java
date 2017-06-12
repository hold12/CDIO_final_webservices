package dao;

import dto.Ingredient;
import jdbclib.DBConnector;
import jdbclib.DatabaseConnection;
import jdbclib.IConnector;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;

public class IngredientDAOTest {
    @Test
    public void invalidIngredient_Get() throws Exception{
        final IConnector db = new DBConnector(new DatabaseConnection());
        final IIngredientDAO ingredientDAO = new IngredientDAO(db);
        final Ingredient actualIngredient = ingredientDAO.getIngredient(0);

        assertNull(actualIngredient);
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
