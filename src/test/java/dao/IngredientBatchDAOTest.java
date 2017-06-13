package dao;

import dto.IngredientBatch;
import jdbclib.DBConnector;
import jdbclib.DatabaseConnection;
import jdbclib.IConnector;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class IngredientBatchDAOTest {
    @Test
    public void getInvalidIngredientBatch() throws Exception {
        final IConnector db = new DBConnector(new DatabaseConnection());
        final IIngredientBatchDAO ingredientBatchDAO = new IngredientBatchDAO(db);
        final IngredientBatch actualIngredientBatch = ingredientBatchDAO.getIngredientBatch(0);

        assertNull(actualIngredientBatch);
    }

    @Test
    public void getIngredientBatchList() throws Exception {
        final IConnector db = new DBConnector(new DatabaseConnection());
        final IIngredientBatchDAO ingredientBatchDAO = new IngredientBatchDAO(db);

        final List<IngredientBatch> ingredientBatches = ingredientBatchDAO.getIngredientBatchList();

        assertTrue(ingredientBatches.size() > 0);
    }

    @Test
    public void ingredientBatchDAO_CRUD_Test() throws Exception {
        final IConnector db = new DBConnector(new DatabaseConnection());
        final IIngredientBatchDAO ingredientBatchDAO = new IngredientBatchDAO(db);
        final IngredientBatch ingredientBatch =
                new IngredientBatch(
                        0,
                        1,
                        1.1
                );

        // Create
        final int id = ingredientBatchDAO.createIngredientBatch(ingredientBatch);
        ingredientBatch.setIngredientBatchId(id);
        assertEquals(ingredientBatch, ingredientBatchDAO.getIngredientBatch(id)); // Check that the ingredient batch exists

        // Delete ingredient batch again
        db.connectToDatabase();
        db.update("DELETE FROM ingredientbatch WHERE ingredientbatch_id = " + id);
        assertFalse(ingredientBatchDAO.getIngredientBatchList().contains(ingredientBatch)); // Check that the ingredient batch is deleted
        db.close();
    }
}