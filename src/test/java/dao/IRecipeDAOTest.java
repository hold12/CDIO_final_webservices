package dao;

import dto.Recipe;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class IRecipeDAOTest {
    @Test
    public void getValidRecipe() throws Exception {
        final IRecipeDAO recipeDAO = new TestRecipeDAO();
        final Recipe expectedRecipe = new Recipe(1,"saltwater");
        final Recipe actualRecipe = recipeDAO.getRecipe(1);

        assertEquals(expectedRecipe,actualRecipe);
    }

    @Test
    public void getInvalidRecipe() throws Exception {
        final IRecipeDAO recipeDAO = new TestRecipeDAO();
        final Recipe actualRecipe = recipeDAO.getRecipe(0);

        assertNull(actualRecipe);
    }

    @Test
    public void getRecipeList() throws Exception {
        final IRecipeDAO recipeDAO = new TestRecipeDAO();
        final List<Recipe> list = recipeDAO.getRecipeList();
        final Recipe expectedFirstRecipe = new Recipe(1,"saltwater");

        assertTrue(list.size() > 0);
        assertEquals(expectedFirstRecipe, list.get(0));
    }

    @Test
    public void createRecipe() throws Exception {
        final IRecipeDAO recipeDAO = new TestRecipeDAO();
        final Recipe newRecipe = new Recipe(3,"lemonsalt");

        recipeDAO.createRecipe(newRecipe);
        final List<Recipe> recipes = recipeDAO.getRecipeList();

        assertTrue(recipes.contains(newRecipe));
    }

}
