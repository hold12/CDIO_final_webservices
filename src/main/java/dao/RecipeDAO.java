package dao;

import dto.Recipe;
import jdbclib.DALException;
import jdbclib.IConnector;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RecipeDAO implements IRecipeDAO {
    private IConnector db;

    public RecipeDAO(IConnector db) {
        this.db = db;
    }

    @Override
    public Recipe getRecipe(int recipeId) throws DALException {
        Recipe returnedRecipe;

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

            returnedRecipe = new Recipe(
                    rs.getInt("recipe_id"),
                    rs.getString("recipe_name")
            );

            return returnedRecipe;
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
            return list;
        } catch (SQLException e) {
            throw new DALException(e);
        } finally {
            db.close();
        }
    }

    @Override
    public int createRecipe(Recipe recipe) throws DALException {
        int id;
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

            id = rs.getInt("recipe_id");

            return id;
        } catch (SQLException e) {
            throw new DALException(e);
        } finally {
            db.close();
        }
    }
}
