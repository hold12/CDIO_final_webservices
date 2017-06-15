package dao;

import dto.RecipeComponent;
import jdbclib.DALException;

import java.util.ArrayList;
import java.util.List;

public class TestRecipeComponentDAO implements IRecipeComponentDAO {
    List<RecipeComponent> recipeComponentList;

    public TestRecipeComponentDAO() {
        recipeComponentList = new ArrayList<>();
        recipeComponentList.add(new RecipeComponent(1,1,1.1,0.1));
        recipeComponentList.add(new RecipeComponent(1,2,2.2,0.1));
    }

    @Override
    public RecipeComponent getRecipeComponent(int recipeId, int ingredientId) throws DALException {
        RecipeComponent recipeComponent;
        try {
            recipeComponent = recipeComponentList.get(ingredientId-1);
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
        return recipeComponent;
    }

    @Override
    public List<RecipeComponent> getRecipeComponentList(int recipeId) throws DALException {
        List<RecipeComponent> recipeComponents = new ArrayList<>();

        for (RecipeComponent i : recipeComponentList){
            if (i.getRecipeId() == recipeId)
                recipeComponents.add(i);
        }

        return recipeComponents;
    }

    @Override
    public List<RecipeComponent> getRecipeComponentList() throws DALException {
        return recipeComponentList;
    }

    @Override
    public void createRecipeComponent(RecipeComponent recipeComponent) throws DALException {
        recipeComponentList.add(recipeComponent);
    }
}
