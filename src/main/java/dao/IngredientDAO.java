package dao;

import dto.Ingredient;
import jdbclib.DALException;
import jdbclib.IConnector;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class IngredientDAO implements IIngredientDAO{
    private final IConnector db;

    public IngredientDAO(IConnector db) {
        this.db = db;
    }

    @Override
    public Ingredient getIngredient(int ingredientId) throws DALException {
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
            else return new Ingredient(
                    rs.getInt("ingredient_id"),
                    rs.getString("ingredient_name"),
                    rs.getString("supplier")
            );
        } catch (SQLException e) {
            throw new DALException(e);
        } finally {
            db.close();
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
        } catch (SQLException e) {
            throw new DALException(e);
        } finally {
            db.close();
        }

        return list;
    }

    @Override
    public int createIngredient(Ingredient ingredient) throws DALException, DataValidationException {
        int ingredientNameLength = ingredient.getIngredientName().length();
        if(ingredientNameLength < 2 || ingredientNameLength > 20)
            throw new DataValidationException("Length of ingredient name should be between 2 and 20 characters.");

        int supplierLength = ingredient.getSupplier().length();
        if(supplierLength < 2 || supplierLength > 20)
            throw new DataValidationException("Length of supplier should be between 2 and 20 characters.");

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
            else return rs.getInt("ingredient_id");
        } catch (SQLException e) {
            throw new DALException(e);
        } finally {
            db.close();
        }

    }

}
