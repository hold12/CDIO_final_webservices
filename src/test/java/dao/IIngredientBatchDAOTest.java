package dao;

import dto.IngredientBatch;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class IIngredientBatchDAOTest {
    @Test
    public void getValidIngredientBatch() throws Exception {
        final IIngredientBatchDAO ingredientBatchDAO = new TestIngredientBatchDAO();
        final IngredientBatch expectedIngredientBatch = new IngredientBatch(1,1,1.1);
        final IngredientBatch actualIngredientBatch = ingredientBatchDAO.getIngredientBatch(1);

        assertEquals(expectedIngredientBatch,actualIngredientBatch);
    }

    @Test
    public void getInvalidIngredientBatch() throws Exception {
        final IIngredientBatchDAO ingredientBatchDAO = new TestIngredientBatchDAO();
        final IngredientBatch ingredientBatch = ingredientBatchDAO.getIngredientBatch(3);

        assertNull(ingredientBatch);
    }

    @Test
    public void getAllIngredientBatches() throws Exception {
        final IIngredientBatchDAO ingredientBatchDAO = new TestIngredientBatchDAO();
        final List<IngredientBatch> list = ingredientBatchDAO.getIngredientBatchList();
        final IngredientBatch expectedFirstIngredientBatch = new IngredientBatch(1,1,1.1);

        assertEquals(expectedFirstIngredientBatch,list.get(0));
        assertTrue(list.size() > 0);
    }

    @Test
    public void getIngredient1Batches() throws Exception {
        final IIngredientBatchDAO ingredientBatchDAO = new TestIngredientBatchDAO();
        final List<IngredientBatch> list = ingredientBatchDAO.getIngredientBatchList(1);
        final IngredientBatch expectedFirstIngredientBatch = new IngredientBatch(1,1,1.1);

        assertEquals(expectedFirstIngredientBatch,list.get(0));
        assertTrue(list.size() > 0);
    }

    @Test
    public void createIngredientBatches() throws Exception {
        final IIngredientBatchDAO ingredientBatchDAO = new TestIngredientBatchDAO();
        final IngredientBatch newIngredientBatch = new IngredientBatch(3,2,3.3);

        ingredientBatchDAO.createIngredientBatch(newIngredientBatch);
        final List<IngredientBatch> list = ingredientBatchDAO.getIngredientBatchList();

        assertTrue(list.contains(newIngredientBatch));
    }
}
