package dao;

import dto.RecipeComponent;
import jdbclib.DBConnector;
import jdbclib.DatabaseConnection;
import jdbclib.IConnector;
import org.junit.Test;

import static org.junit.Assert.assertNull;

public class RecipeComponentDAOTest {
    @Test
    public void getInvalidRecipeComponent() throws Exception {
        final IConnector db = new DBConnector(new DatabaseConnection());
        final IRecipeComponentDAO recipeComponentDAO = new RecipeComponentDAO(db);
        final RecipeComponent actualRecipeComponent = recipeComponentDAO.getRecipeComponent(0,0);

        assertNull(actualRecipeComponent);
    }

}
