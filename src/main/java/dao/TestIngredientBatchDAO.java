package dao;

import dto.IngredientBatch;
import jdbclib.DALException;

import java.util.ArrayList;
import java.util.List;

public class TestIngredientBatchDAO implements IIngredientBatchDAO {
    private List<IngredientBatch> ingredientBatchList;

    public TestIngredientBatchDAO() {
        ingredientBatchList = new ArrayList<>();
        ingredientBatchList.add(new IngredientBatch(1,1,1.1));
        ingredientBatchList.add(new IngredientBatch(2,1,2.2));
    }

    @Override
    public IngredientBatch getIngredientBatch(int ingredientBatchId) throws DALException {
        IngredientBatch ingredientBatch;
        try {
            ingredientBatch = ingredientBatchList.get(ingredientBatchId - 1);
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
        return ingredientBatch;
    }

    @Override
    public List<IngredientBatch> getIngredientBatchList() throws DALException {
        return ingredientBatchList;
    }

    @Override
    public List<IngredientBatch> getIngredientBatchList(int ingredientId) throws DALException {
        List<IngredientBatch> returnedIngredientBatches = new ArrayList<>();

        for(IngredientBatch i : ingredientBatchList){
            if (i.getIngredientId() == ingredientId)
                returnedIngredientBatches.add(i);
        }

        return returnedIngredientBatches;
    }

    @Override
    public void createIngredientBatch(IngredientBatch ingredientBatch) throws DALException {
        ingredientBatchList.add(ingredientBatch);
    }
}
