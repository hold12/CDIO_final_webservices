package dao;

import dto.Recipe;
import jdbclib.DALException;

import java.util.ArrayList;
import java.util.List;

public class TestRecipeDAO implements IRecipeDAO {
    List<Recipe> recipeList;

    public TestRecipeDAO() {
        recipeList = new ArrayList<>();
        recipeList.add(new Recipe(1, "saltwater"));
        recipeList.add(new Recipe(2, "lemonwater"));
    }

    @Override
    public Recipe getRecipe(int recipeId) throws DALException {
        Recipe recipe;

        try {
            recipe = recipeList.get(recipeId-1);
        } catch (IndexOutOfBoundsException e) {
            return null;
        }

        return recipe;
    }

    @Override
    public List<Recipe> getRecipeList() throws DALException {
        return recipeList;
    }

    @Override
    public void createRecipe(Recipe recipe) throws DALException {
        recipeList.add(recipe);
    }
}
