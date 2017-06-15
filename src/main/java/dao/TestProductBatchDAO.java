package dao;

import dto.ProductBatch;
import jdbclib.DALException;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class TestProductBatchDAO implements IProductBatchDAO {
    List<ProductBatch> productBatchList;

    public TestProductBatchDAO(){
        productBatchList = new ArrayList<>();
        productBatchList.add(
                new ProductBatch(
                        1,
                        1,
                        "saltwater",
                        new Timestamp(2017,6,12,8,55,0,0),
                        new Timestamp(2017,6,12,9,20,0,0),
                        2,
                        1,
                        "AB"
                ));
        productBatchList.add(
                new ProductBatch(
                        2,
                        2,
                        "lemonwater",
                        new Timestamp(2017,6,12,9,55,0,0),
                        null,
                        0,
                        2,
                        "CD"
                ));
    }

    @Override
    public ProductBatch getProductBatch(int productBatchId) throws DALException {
        ProductBatch productBatch;
        try {
            productBatch = productBatchList.get(productBatchId - 1);
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
        return productBatch;
    }

    @Override
    public List<ProductBatch> getProductBatchList() throws DALException {
        return productBatchList;
    }

    @Override
    public int createProductBatch(ProductBatch productBatch) throws DALException {
        productBatchList.add(productBatch);
        return productBatch.getProductbatchId();
    }
}
