package dao;

import dto.ProductBatch;
import jdbclib.DALException;
import jdbclib.IConnector;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class ProductBatchDAO implements IProductBatchDAO {
    private final IConnector db;

    public ProductBatchDAO(IConnector db) {
        this.db = db;
    }

    @Override
    public ProductBatch getProductBatch(int productBatchId) throws DALException {
        try {
            db.connectToDatabase();
        } catch (ClassNotFoundException | SQLException e) {
            throw new DALException(e);
        }

        ResultSet rs = db.query(Queries.getFormatted(
                "productbatch.select.where.id", Integer.toString(productBatchId)
        ));

        try {
            if (!rs.first()) return null;
            else return new ProductBatch(
                    rs.getInt("productbatch_id"),
                    rs.getInt("recipe_id"),
                    rs.getString("recipe_name"),
                    rs.getTimestamp("created_time"),
                    rs.getTimestamp("finished_time"),
                    rs.getInt("status"),
                    rs.getInt("user_id"),
                    rs.getString("initials")
            );
        } catch (SQLException e) {
            throw new DALException(e);
        } finally {
            db.close();
        }
    }

    @Override
    public List<ProductBatch> getProductBatchList() throws DALException {
        List<ProductBatch> list = new ArrayList<>();

        try {
            db.connectToDatabase();
        } catch (ClassNotFoundException | SQLException e) {
            throw new DALException(e);
        }

        ResultSet rs = db.query(Queries.getFormatted(
                "productbatch.select.all"
        ));

        try {
            while (rs.next()) {
                list.add(new ProductBatch(
                        rs.getInt("productbatch_id"),
                        rs.getInt("recipe_id"),
                        rs.getString("recipe_name"),
                        rs.getTimestamp("created_time"),
                        rs.getTimestamp("finished_time"),
                        rs.getInt("status"),
                        rs.getInt("user_id"),
                        rs.getString("initials")
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
    public int createProductBatch(ProductBatch productBatch) throws DALException {
        try {
            db.connectToDatabase();
        } catch (ClassNotFoundException | SQLException e) {
            throw new DALException(e);
        }

        productBatch.setCreatedTime(new Timestamp(System.currentTimeMillis()));

        ResultSet rs = db.query(Queries.getFormatted(
                "productbatch.insert",
                productBatch.getCreatedTime().toString(),
                Integer.toString(productBatch.getRecipeId()),
                Integer.toString(productBatch.getUserId())
        ));

        try {
            if (!rs.first()) return -1;
            else return rs.getInt("productbatch_id");
        } catch (SQLException e) {
            throw new DALException(e);
        } finally {
            db.close();
        }
    }
}
