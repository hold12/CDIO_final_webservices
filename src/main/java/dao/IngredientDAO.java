package dao;

import dto.Ingredient;
import jdbclib.DALException;
import jdbclib.IConnector;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class IngredientDAO implements IIngredientDAO{
    private IConnector db;

    public IngredientDAO(IConnector db) {
        this.db = db;
    }

    @Override
    public Ingredient getIngredient(int ingredientId) throws DALException {
        Ingredient returnedIngredient;

        try {
            db.connectToDatabase();
        } catch(ClassNotFoundException | SQLException e) {
            throw new DALException(e);
        }

        ResultSet rs = db.query(Queries.getFormatted(
                "ingredient.select.where.id",
                Integer.toString(ingredientId)
        ));

        try {
            if (!rs.first()) return null;

            returnedIngredient = new Ingredient(
                    rs.getInt("ingredient_id"),
                    rs.getString("ingredient_name"),
                    rs.getString("supplier")
            );

            db.close();

            return returnedIngredient;
        } catch (SQLException e) {
            throw new DALException(e);
        }
    }

    @Override
    public List<Ingredient> getIngredientList() throws DALException {
        List<Ingredient> list = new ArrayList<>();

        try {
            db.connectToDatabase();
        } catch(ClassNotFoundException | SQLException e) {
            throw new DALException(e);
        }

        ResultSet rs = db.query(
                Queries.getSQL("ingredient.select.all")
        );

        try {
            while (rs.next()) {
                list.add(new Ingredient(
                        rs.getInt("ingredient_id"),
                        rs.getString("ingredient_name"),
                        rs.getString("supplier"))
                );
            }
            db.close();
        } catch (SQLException e) {
            throw new DALException(e);
        }

        return list;
    }

    @Override
    public int createIngredient(Ingredient ingredient) throws DALException {
        int id;
        try {
            db.connectToDatabase();
        } catch (ClassNotFoundException | SQLException e) {
            throw new DALException(e);
        }

        ResultSet rs = db.query(Queries.getFormatted(
                "ingredient.insert",
                ingredient.getIngredientName(),
                ingredient.getSupplier()
        ));


        try {
            if (!rs.first()) return -1;

            id = rs.getInt("ingredient_id");

            db.close();

            return id;
        } catch (SQLException e) {
            throw new DALException(e);
        }
    }

}
