package dao;

import dto.ProductBatchComponent;
import jdbclib.DBConnector;
import jdbclib.DatabaseConnection;
import jdbclib.IConnector;
import org.junit.Test;

import static org.junit.Assert.assertNull;

public class ProductBatchComponentDAOTest {
    @Test
    public void getInvalidProductBatchComponent() throws Exception {
        final IConnector db = new DBConnector(new DatabaseConnection());
        final IProductBatchComponentDAO productBatchComponentDAO = new ProductBatchComponentDAO(db);
        final ProductBatchComponent actualProductBatchComponent = productBatchComponentDAO.getProductBatchComponent(0,0);

        assertNull(actualProductBatchComponent);
    }
}
