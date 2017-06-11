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

            db.close();

            return returnedRecipe;
        } catch (SQLException e) {
            throw new DALException(e);
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
            db.close();
        } catch (SQLException e) {
            throw new DALException(e);
        }

        return list;
    }

    @Override
    public void createRecipe(Recipe recipe) throws DALException {
        db.update(Queries.getFormatted(
                "recipe.insert",
                recipe.getRecipeName()
        ));
    }
}
