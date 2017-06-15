package dao;

import dto.RecipeComponent;
import jdbclib.DALException;

import java.util.List;

public interface IRecipeComponentDAO {
    RecipeComponent getRecipeComponent(int recipeId, int ingredientId) throws DALException;
    List<RecipeComponent> getRecipeComponentList(int recipeId) throws DALException;
    List<RecipeComponent> getRecipeComponentList() throws DALException;
    void createRecipeComponent(RecipeComponent recipeComponent) throws DALException, DataValidationException;
}
