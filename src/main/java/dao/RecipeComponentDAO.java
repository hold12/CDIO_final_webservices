package dao;

import dto.RecipeComponent;
import jdbclib.DALException;
import jdbclib.IConnector;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RecipeComponentDAO implements IRecipeComponentDAO {
    private final IConnector db;

    public RecipeComponentDAO(IConnector db) {
        this.db = db;
    }

    @Override
    public RecipeComponent getRecipeComponent(int recipeId, int ingredientId) throws DALException {
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
            if (!rs.first()) return null;
            else return new RecipeComponent(
                        rs.getInt("recipe_id"),
                        rs.getInt("ingredient_id"),
                        rs.getDouble("nominated_net_weight"),
                        rs.getDouble("tolerance")
                );
        } catch (SQLException e) {
            throw new DALException(e);
        } finally {
            db.close();
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
        } catch (SQLException e) {
            throw new DALException(e);
        } finally {
            db.close();
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
        } catch (SQLException e) {
            throw new DALException(e);
        } finally {
            db.close();
        }

        return list;
    }

    @Override
    public void createRecipeComponent(RecipeComponent recipeComponent) throws DALException, DataValidationException {
        Double netWeight = recipeComponent.getNominatedNetWeight();
        if (netWeight < 0.05 || netWeight > 20.0)
            throw new DataValidationException("Nominated net weight should be between 0,05 and 20,0 kg");

        Double tolerance = recipeComponent.getTolerance();
        if (tolerance < 0.1 || tolerance > 10.0)
            throw new DataValidationException("Tolerance should be between 0,1% and 10,0%");

        try {
            db.connectToDatabase();
        } catch(ClassNotFoundException | SQLException e) {
            throw new DALException(e);
        }

        db.update(Queries.getFormatted(
                "recipecomponent.insert",
                Integer.toString(recipeComponent.getRecipeId()),
                Integer.toString(recipeComponent.getIngredientId()),
                Double.toString(netWeight),
                Double.toString(tolerance)
        ));

        db.close();
    }
}
