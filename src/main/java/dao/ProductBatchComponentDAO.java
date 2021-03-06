package dao;

import dto.ProductBatchComponent;
import jdbclib.DALException;
import jdbclib.IConnector;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductBatchComponentDAO implements IProductBatchComponentDAO {
    private final IConnector db;

    public ProductBatchComponentDAO(IConnector db) {
        this.db = db;
    }

    @Override
    public ProductBatchComponent getProductBatchComponent(int productbatchId, int ingredientbatchId) throws DALException {
        try {
            db.connectToDatabase();
        } catch (ClassNotFoundException | SQLException e) {
            throw new DALException(e);
        }

        ResultSet rs = db.query(Queries.getFormatted(
                "productbatchcomponent.select.where.id",
                Integer.toString(productbatchId),
                Integer.toString(ingredientbatchId)
        ));

        try {
            if (!rs.first()) return null;
            else return new ProductBatchComponent(
                        rs.getInt("productbatch_id"),
                        rs.getInt("ingredientbatch_id"),
                        rs.getInt("ingredient_id"),
                        rs.getString("ingredient_name"),
                        rs.getString("supplier"),
                        rs.getDouble("tare"),
                        rs.getDouble("net_weight")
                );
        } catch (SQLException e) {
            throw new DALException(e);
        } finally {
            db.close();
        }
    }

    @Override
    public List<ProductBatchComponent> getProductBatchComponentList(int productbatchId) throws DALException {
        List<ProductBatchComponent> list = new ArrayList<>();

        try {
            db.connectToDatabase();
        } catch (ClassNotFoundException | SQLException e) {
            throw new DALException(e);
        }

        ResultSet rs = db.query(Queries.getFormatted(
                "productbatchcomponent.select.where.productbatchid",
                Integer.toString(productbatchId)
        ));

        try {
            while (rs.next()) {
                list.add(new ProductBatchComponent(
                        rs.getInt("productbatch_id"),
                        rs.getInt("ingredientbatch_id"),
                        rs.getInt("ingredient_id"),
                        rs.getString("ingredient_name"),
                        rs.getString("supplier"),
                        rs.getDouble("tare"),
                        rs.getDouble("net_weight")
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
    public List<ProductBatchComponent> getProductBatchComponentList() throws DALException {
        List<ProductBatchComponent> list = new ArrayList<>();

        try {
            db.connectToDatabase();
        } catch (ClassNotFoundException | SQLException e) {
            throw new DALException(e);
        }

        ResultSet rs = db.query(Queries.getFormatted(
                "productbatchcomponent.select.all"
        ));

        try {
            while (rs.next()) {
                list.add(new ProductBatchComponent(
                        rs.getInt("productbatch_id"),
                        rs.getInt("ingredientbatch_id"),
                        rs.getInt("ingredient_id"),
                        rs.getString("ingredient_name"),
                        rs.getString("supplier"),
                        rs.getDouble("tare"),
                        rs.getDouble("net_weight")
                ));
            }
        } catch (SQLException e) {
            throw new DALException(e);
        } finally {
            db.close();
        }

        return list;
    }
}
