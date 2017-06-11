package dao;

import dto.IngredientBatch;
import jdbclib.DALException;
import jdbclib.IConnector;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class IngredientBatchDAO implements IIngredientBatchDAO {
    private IConnector db;

    public IngredientBatchDAO(IConnector db) {
        this.db = db;
    }

    @Override
    public IngredientBatch getIngredientBatch(int ingredientBatchId) throws DALException {
        IngredientBatch returnedIngredientBatch;

        try {
            db.connectToDatabase();
        } catch(ClassNotFoundException | SQLException e) {
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
                    rs.getDouble("amount")
            );

            db.close();

            return returnedIngredientBatch;
        } catch (SQLException e) {
            throw new DALException(e);
        }
    }

    @Override
    public List<IngredientBatch> getIngredientBatchList() throws DALException {
        List<IngredientBatch> list = new ArrayList<>();

        ResultSet rs = db.query(Queries.getFormatted(
                "ingredientbatch.select.all"
        ));

        try {
            while (rs.next()) {
                list.add(new IngredientBatch(
                        rs.getInt("ingredientbatch_id"),
                        rs.getInt("ingredient_id"),
                        rs.getDouble("amount")
                ));
            }
            db.close();
        } catch (SQLException e) {
            throw new DALException(e);
        }

        return list;
    }

    @Override
    public List<IngredientBatch> getIngredientBatchList(int ingredientId) throws DALException {
        List<IngredientBatch> list = new ArrayList<>();

        ResultSet rs = db.query(Queries.getFormatted(
                "ingredientbatch.select.where.ingredientid",
                Integer.toString(ingredientId)
        ));

        try {
            while (rs.next()) {
                list.add(new IngredientBatch(
                        rs.getInt("ingredientbatch_id"),
                        rs.getInt("ingredient_id"),
                        rs.getDouble("amount")
                ));
            }
            db.close();
        } catch (SQLException e) {
            throw new DALException(e);
        }

        return list;
    }

    @Override
    public void createIngredientBatch(IngredientBatch ingredientBatch) throws DALException {
        db.update(Queries.getFormatted(
                "ingredientbatch.insert",
                Integer.toString(ingredientBatch.getIngredientId()),
                Double.toString(ingredientBatch.getAmount())
        ));
    }
}
