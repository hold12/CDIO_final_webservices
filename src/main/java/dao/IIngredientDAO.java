package dao;

import dto.Ingredient;
import jdbclib.DALException;

import java.util.List;

public interface IIngredientDAO {
    Ingredient getIngredient(int ingredientId) throws DALException;
    List<Ingredient> getIngredientList() throws DALException;
    int createIngredient(Ingredient ingredient) throws DALException, DataValidationException;
}
