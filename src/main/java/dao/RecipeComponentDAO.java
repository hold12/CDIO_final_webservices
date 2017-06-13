package dao;

import dto.RecipeComponent;
import jdbclib.DALException;
import jdbclib.IConnector;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RecipeComponentDAO implements IRecipeComponentDAO {
    private IConnector db;

    public RecipeComponentDAO(IConnector db) {
        this.db = db;
    }

    @Override
    public RecipeComponent getRecipeComponent(int recipeId, int ingredientId) throws DALException {
        RecipeComponent recipeComponent;

        try {
            db.connectToDatabase();
        } catch (ClassNotFoundException | SQLException e) {
            throw new DALException(e);
        }

        ResultSet rs = db.query(Queries.getFormatted(
                "recipecomponent.select.where.id",
                Integer.toString(recipeId),
                Integer.toString(ingredientId)
        ));

        try {
            if (!rs.first()) {
                recipeComponent = null;
            } else {
                recipeComponent = new RecipeComponent(
                        rs.getInt("recipe_id"),
                        rs.getInt("ingredient_id"),
                        rs.getDouble("nominated_net_weight"),
                        rs.getDouble("tolerance")
                );
            }

            db.close();

            return recipeComponent;
        } catch (SQLException e) {
            throw new DALException(e);
        }
    }

    @Override
    public List<RecipeComponent> getRecipeComponentList(int recipeId) throws DALException {
        List<RecipeComponent> list = new ArrayList<>();

        try {
            db.connectToDatabase();
        } catch(ClassNotFoundException | SQLException e) {
            throw new DALException(e);
        }

        ResultSet rs = db.query(
                Queries.getFormatted("recipecomponent.select.where.recipeid",
                        Integer.toString(recipeId))
        );

        try {
            while (rs.next()) {
                list.add(new RecipeComponent(
                        rs.getInt("recipe_id"),
                        rs.getInt("ingredient_id"),
                        rs.getDouble("nominated_net_weight"),
                        rs.getDouble("tolerance"))
                );
            }
            db.close();
        } catch (SQLException e) {
            throw new DALException(e);
        }

        return list;
    }

    @Override
    public List<RecipeComponent> getRecipeComponentList() throws DALException {
        List<RecipeComponent> list = new ArrayList<>();

        try {
            db.connectToDatabase();
        } catch(ClassNotFoundException | SQLException e) {
            throw new DALException(e);
        }

        ResultSet rs = db.query(
                Queries.getSQL("recipecomponent.select.all")
        );

        try {
            while (rs.next()) {
                list.add(new RecipeComponent(
                        rs.getInt("recipe_id"),
                        rs.getInt("ingredient_id"),
                        rs.getDouble("nominated_net_weight"),
                        rs.getDouble("tolerance"))
                );
            }
            db.close();
        } catch (SQLException e) {
            throw new DALException(e);
        }

        return list;
    }

    @Override
    public void createRecipeComponent(RecipeComponent recipeComponent) throws DALException {
        db.update(Queries.getFormatted(
                "recipecomponent.insert",
                Integer.toString(recipeComponent.getRecipeId()),
                Integer.toString(recipeComponent.getIngredientId()),
                Double.toString(recipeComponent.getNominatedNetWeight()),
                Double.toString(recipeComponent.getTolerance())
        ));
    }
}
