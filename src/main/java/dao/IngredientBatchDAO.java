package dao;

import dto.IngredientBatch;
import jdbclib.DALException;
import jdbclib.IConnector;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class IngredientBatchDAO implements IIngredientBatchDAO {
    private final IConnector db;

    public IngredientBatchDAO(IConnector db) {
        this.db = db;
    }

    @Override
    public IngredientBatch getIngredientBatch(int ingredientBatchId) throws DALException {
        IngredientBatch returnedIngredientBatch;

        try {
            db.connectToDatabase();
        } catch (ClassNotFoundException | SQLException e) {
            throw new DALException(e);
        }

        ResultSet rs = db.query(Queries.getFormatted(
                "ingredientbatch.select.where.id",
                Integer.toString(ingredientBatchId)
        ));

        try {
            if (!rs.first()) return null;

            returnedIngredientBatch = new IngredientBatch(
                    rs.getInt("ingredientbatch_id"),
                    rs.getInt("ingredient_id"),
                    rs.getString("ingredient_name"),
                    rs.getString("supplier"),
                    rs.getDouble("amount")
            );

            return returnedIngredientBatch;
        } catch (SQLException e) {
            throw new DALException(e);
        } finally {
            db.close();
        }
    }

    @Override
    public List<IngredientBatch> getIngredientBatchList() throws DALException {
        List<IngredientBatch> list = new ArrayList<>();

        try {
            db.connectToDatabase();
        } catch (ClassNotFoundException | SQLException e) {
            throw new DALException(e);
        }

        ResultSet rs = db.query(Queries.getFormatted(
                "ingredientbatch.select.all"
        ));

        try {
            while (rs.next()) {
                list.add(new IngredientBatch(
                        rs.getInt("ingredientbatch_id"),
                        rs.getInt("ingredient_id"),
                        rs.getString("ingredient_name"),
                        rs.getString("supplier"),
                        rs.getDouble("amount")
                ));
            }
        } catch (SQLException e) {
            throw new DALException(e);
        } finally {
            db.close();
        }

        return list;
    }

    @Override
    public List<IngredientBatch> getIngredientBatchList(int ingredientId) throws DALException {
        List<IngredientBatch> list = new ArrayList<>();

        try {
            db.connectToDatabase();
        } catch (ClassNotFoundException | SQLException e) {
            throw new DALException(e);
        }

        ResultSet rs = db.query(Queries.getFormatted(
                "ingredientbatch.select.where.ingredientid",
                Integer.toString(ingredientId)
        ));

        try {
            while (rs.next()) {
                list.add(new IngredientBatch(
                        rs.getInt("ingredientbatch_id"),
                        rs.getInt("ingredient_id"),
                        rs.getString("ingredient_name"),
                        rs.getString("supplier"),
                        rs.getDouble("amount")
                ));
            }
        } catch (SQLException e) {
            throw new DALException(e);
        } finally {
            db.close();
        }

        return list;
    }

    @Override
    public int createIngredientBatch(IngredientBatch ingredientBatch) throws DALException {
        try {
            db.connectToDatabase();
        } catch (ClassNotFoundException | SQLException e) {
            throw new DALException(e);
        }

        ResultSet rs = db.query(Queries.getFormatted(
                "ingredientbatch.insert",
                Integer.toString(ingredientBatch.getIngredientId()),
                Double.toString(ingredientBatch.getAmount())
        ));

        try {
            if (!rs.first()) return -1;
            else return rs.getInt("ingredientbatch_id");
        } catch (SQLException e) {
            throw new DALException(e);
        } finally {
            db.close();
        }
    }
}