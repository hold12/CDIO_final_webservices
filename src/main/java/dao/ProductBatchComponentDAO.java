package dao;

import dto.ProductBatchComponent;
import jdbclib.DALException;
import jdbclib.IConnector;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductBatchComponentDAO implements IProductBatchComponentDAO {
    private IConnector db;

    public ProductBatchComponentDAO(IConnector db) {
        this.db = db;
    }

    @Override
    public ProductBatchComponent getProductBatchComponent(int productbatchId, int ingredientbatchId) throws DALException {
        ProductBatchComponent returnedProductBatchComponent;

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
            if (!rs.first()) {
                returnedProductBatchComponent = null;
            } else {
                returnedProductBatchComponent = new ProductBatchComponent(
                        rs.getInt("productbatch_id"),
                        rs.getInt("ingredientbatch_id"),
                        rs.getDouble("tare"),
                        rs.getDouble("net_weight")
                );
            }

            db.close();

            return returnedProductBatchComponent;
        } catch (SQLException e) {
            throw new DALException(e);
        }
    }

    @Override
    public List<ProductBatchComponent> getProductBatchComponentList(int productbatchId) throws DALException {
        List<ProductBatchComponent> list = new ArrayList<>();

        ResultSet rs = db.query(Queries.getFormatted(
                "productbatchcomponent.select.where.productbatchid",
                Integer.toString(productbatchId)
        ));

        try {
            while (rs.next()) {
                list.add(new ProductBatchComponent(
                        rs.getInt("productbatch_id"),
                        rs.getInt("ingredientbatch_id"),
                        rs.getDouble("tare"),
                        rs.getDouble("net_weight")
                ));
            }
            db.close();
        } catch (SQLException e) {
            throw new DALException(e);
        }

        return list;
    }

    @Override
    public List<ProductBatchComponent> getProductBatchComponentList() throws DALException {
        List<ProductBatchComponent> list = new ArrayList<>();

        ResultSet rs = db.query(Queries.getFormatted(
                "productbatchcomponent.select.all"
        ));

        try {
            while (rs.next()) {
                list.add(new ProductBatchComponent(
                        rs.getInt("productbatch_id"),
                        rs.getInt("ingredientbatch_id"),
                        rs.getDouble("tare"),
                        rs.getDouble("net_weight")
                ));
            }
            db.close();
        } catch (SQLException e) {
            throw new DALException(e);
        }

        return list;
    }
}
