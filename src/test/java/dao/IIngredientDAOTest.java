package dao;

import dto.Ingredient;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class IIngredientDAOTest {
    @Test
    public void getValidIngredient() throws Exception {
        final IIngredientDAO ingredientDAO = new TestIngredientDAO();
        final Ingredient expectedIngredient = new Ingredient(1,"water","Water supplier");

        final Ingredient actualIngredient = ingredientDAO.getIngredient(expectedIngredient.getIngredientId());

        assertEquals(expectedIngredient,actualIngredient);
    }

    @Test
    public void getInvalidIngredient() throws Exception {
        final IIngredientDAO ingredientDAO = new TestIngredientDAO();

        final Ingredient actualIngredient = ingredientDAO.getIngredient(0);

        assertNull(actualIngredient);
    }

    @Test
    public void getAllIngredients() throws Exception {
        final IIngredientDAO ingredientDAO = new TestIngredientDAO();
        final Ingredient expectedFirstIngredient = new Ingredient(1,"water","Water supplier");

        final List<Ingredient> ingredients = ingredientDAO.getIngredientList();

        assertTrue(ingredients.size() > 0);
        assertEquals(expectedFirstIngredient, ingredients.get(0));
    }

    @Test
    public void createIngredient() throws Exception {
        final IIngredientDAO ingredientDAO = new TestIngredientDAO();
        final Ingredient newIngredient = new Ingredient(3, "salt","Salty A/S");

        ingredientDAO.createIngredient(newIngredient);

        final List<Ingredient> ingredients = ingredientDAO.getIngredientList();

        assertTrue(ingredients.contains(newIngredient));
    }
}
