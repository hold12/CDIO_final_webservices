package dao;

import dto.RecipeComponent;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class IRecipeComponentDAOTest {
    @Test
    public void getValidRecipeComponent() throws Exception {
        final IRecipeComponentDAO RecipeComponentDAO = new TestRecipeComponentDAO();
        final RecipeComponent expectedRecipeComponent =
                new RecipeComponent(1, 1, 1.1, 0.1);
        final RecipeComponent actualRecipeComponent = RecipeComponentDAO.getRecipeComponent(1,1);

        assertEquals(expectedRecipeComponent,actualRecipeComponent);
    }

    @Test
    public void getInvalidRecipeComponent() throws Exception {
        final IRecipeComponentDAO RecipeComponentDAO = new TestRecipeComponentDAO();
        final RecipeComponent actualRecipeComponent = RecipeComponentDAO.getRecipeComponent(0,0);

        assertNull(actualRecipeComponent);
    }

    @Test
    public void getAllRecipeComponentes() throws Exception {
        final IRecipeComponentDAO RecipeComponentDAO = new TestRecipeComponentDAO();
        final RecipeComponent expectedFirstRecipeComponent =
                new RecipeComponent(1,1,1.1,0.1);

        final List<RecipeComponent> RecipeComponents = RecipeComponentDAO.getRecipeComponentList();

        assertTrue(RecipeComponents.size() > 0);
        assertEquals(expectedFirstRecipeComponent, RecipeComponents.get(0));
    }

    @Test
    public void createRecipeComponent() throws Exception {
        final IRecipeComponentDAO RecipeComponentDAO = new TestRecipeComponentDAO();
        final RecipeComponent newRecipeComponent =
                new RecipeComponent(2,1,3.3,0.2);

        RecipeComponentDAO.createRecipeComponent(newRecipeComponent);
        final List<RecipeComponent> list = RecipeComponentDAO.getRecipeComponentList();

        assertTrue(list.contains(newRecipeComponent));
    }

}
