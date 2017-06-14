package dao;

import dto.ProductBatchComponent;
import dto.RecipeComponent;
import jdbclib.DBConnector;
import jdbclib.DatabaseConnection;
import jdbclib.IConnector;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class RecipeComponentDAOTest {
    @Test
    public void getInvalidRecipeComponent() throws Exception {
        final IConnector db = new DBConnector(new DatabaseConnection());
        final IRecipeComponentDAO recipeComponentDAO = new RecipeComponentDAO(db);
        final RecipeComponent actualRecipeComponent = recipeComponentDAO.getRecipeComponent(0,0);

        assertNull(actualRecipeComponent);
    }

    @Test
    public void getRecipeComponentList() throws Exception {
        final IConnector db = new DBConnector(new DatabaseConnection());
        final IRecipeComponentDAO recipeComponentDAO = new RecipeComponentDAO(db);

        final List<RecipeComponent> recipeComponents = recipeComponentDAO.getRecipeComponentList();

        assertTrue(recipeComponents.size() > 0);
    }

}
