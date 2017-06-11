package dao;

import dto.Ingredient;
import jdbclib.DALException;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class TestIngredientDAO implements IIngredientDAO {
    private List<Ingredient> ingredientList;

    public TestIngredientDAO(){
        ingredientList = new ArrayList<>();
        ingredientList.add(new Ingredient(1,"water","Water supplier"));
        ingredientList.add(new Ingredient(2,"lemon","Fruit supplier"));
    }

    @Override
    public Ingredient getIngredient(int ingredientId) throws DALException {
        Ingredient returnedIngredient;
        try {
            returnedIngredient = ingredientList.get(ingredientId-1);
        } catch (ArrayIndexOutOfBoundsException e) {
            returnedIngredient = null;
        }
        return returnedIngredient;
    }

    @Override
    public List<Ingredient> getIngredientList() throws DALException {
        return ingredientList;
    }

    @Override
    public void createIngredient(Ingredient ingredient) throws DALException {
        ingredientList.add(ingredient);
    }
}
