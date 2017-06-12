package dao;

import dto.IngredientBatch;
import jdbclib.DALException;

import java.util.List;

public interface IIngredientBatchDAO {
    IngredientBatch getIngredientBatch(int ingredientBatchId) throws DALException;
    List<IngredientBatch> getIngredientBatchList() throws DALException;
    List<IngredientBatch> getIngredientBatchList(int ingredientId) throws DALException;
    int createIngredientBatch(IngredientBatch ingredientBatch) throws DALException;
}
