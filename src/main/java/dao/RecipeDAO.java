package dao;

import dto.Recipe;
import jdbclib.DALException;
import jdbclib.IConnector;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RecipeDAO implements IRecipeDAO {
    private final IConnector db;

    public RecipeDAO(IConnector db) {
        this.db = db;
    }

    @Override
    public Recipe getRecipe(int recipeId) throws DALException {
        try {
            db.connectToDatabase();
        } catch(ClassNotFoundException | SQLException e) {
            throw new DALException(e);
        }

        ResultSet rs = db.query(Queries.getFormatted(
                "recipe.select.where.id",
                Integer.toString(recipeId)
        ));

        try {
            if (!rs.first()) return null;
            else return new Recipe(
                    rs.getInt("recipe_id"),
                    rs.getString("recipe_name")
            );
        } catch (SQLException e) {
            throw new DALException(e);
        } finally {
            db.close();
        }
    }

    @Override
    public List<Recipe> getRecipeList() throws DALException {
        List<Recipe> list = new ArrayList<>();

        try {
            db.connectToDatabase();
        } catch(ClassNotFoundException | SQLException e) {
            throw new DALException(e);
        }

        ResultSet rs = db.query(
                Queries.getSQL("recipe.select.all")
        );

        try {
            while (rs.next()) {
                list.add(new Recipe(
                        rs.getInt("recipe_id"),
                        rs.getString("recipe_name"))
                );
            }
        } catch (SQLException e) {
            throw new DALException(e);
        } finally {
            db.close();
        }

        return list;
    }

    @Override
    public int createRecipe(Recipe recipe) throws DALException, DataValidationException {
        int recipeNameLength = recipe.getRecipeName().length();
        if (recipeNameLength < 2 || recipeNameLength > 20)
            throw new DataValidationException("Recipe name should be between 2 and 20 characters");

        try {
            db.connectToDatabase();
        } catch (ClassNotFoundException | SQLException e) {
            throw new DALException(e);
        }

        ResultSet rs = db.query(Queries.getFormatted(
                "recipe.insert",
                recipe.getRecipeName()
        ));

        try {
            if (!rs.first()) return -1;
            else return rs.getInt("recipe_id");
        } catch (SQLException e) {
            throw new DALException(e);
        } finally {
            db.close();
        }
    }
}
