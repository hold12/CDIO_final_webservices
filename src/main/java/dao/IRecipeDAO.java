package dao;

import dto.Recipe;
import jdbclib.DALException;

import java.util.List;

public interface IRecipeDAO {
    Recipe getRecipe(int recipeId) throws DALException;
    List<Recipe> getRecipeList() throws DALException;
    void createRecipe(Recipe recipe) throws DALException;
}