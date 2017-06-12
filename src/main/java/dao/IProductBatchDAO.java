package dao;

import dto.ProductBatch;
import jdbclib.DALException;

import java.util.List;

public interface IProductBatchDAO {
    ProductBatch getProductBatch(int productBatchId) throws DALException;
    List<ProductBatch> getProductBatchList() throws DALException;
    int createProductBatch(ProductBatch productBatch) throws DALException;
}