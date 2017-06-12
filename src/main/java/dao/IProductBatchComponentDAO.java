package dao;

import dto.ProductBatchComponent;
import jdbclib.DALException;

import java.util.List;

public interface IProductBatchComponentDAO {
    ProductBatchComponent getProductBatchComponent(int productbatchId, int ingredientbatchId) throws DALException;
    List<ProductBatchComponent> getProductBatchComponentList(int productbatchId) throws DALException;
    List<ProductBatchComponent> getProductBatchComponentList() throws DALException;
}
