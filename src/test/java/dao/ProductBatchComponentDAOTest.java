package dao;

import dto.ProductBatchComponent;
import jdbclib.DBConnector;
import jdbclib.DatabaseConnection;
import jdbclib.IConnector;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class ProductBatchComponentDAOTest {
    @Test
    public void getInvalidProductBatchComponent() throws Exception {
        final IConnector db = new DBConnector(new DatabaseConnection());
        final IProductBatchComponentDAO productBatchComponentDAO = new ProductBatchComponentDAO(db);
        final ProductBatchComponent actualProductBatchComponent = productBatchComponentDAO.getProductBatchComponent(0,0);

        assertNull(actualProductBatchComponent);
    }

    /*@Test
    public void getProductBatchComponentList() throws Exception {
        final IConnector db = new DBConnector(new DatabaseConnection());
        final IProductBatchComponentDAO productBatchComponentDAO = new ProductBatchComponentDAO(db);

        final List<ProductBatchComponent> productBatchComponents = productBatchComponentDAO.getProductBatchComponentList();

        assertTrue(productBatchComponents.size() > 0);
    }*/
}
