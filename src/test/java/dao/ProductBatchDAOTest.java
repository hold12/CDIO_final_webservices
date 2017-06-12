package dao;

import dto.ProductBatch;
import jdbclib.DBConnector;
import jdbclib.DatabaseConnection;
import jdbclib.IConnector;
import org.junit.Test;

import java.sql.Timestamp;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;

public class ProductBatchDAOTest {
    @Test
    public void getInvalidProductBatch() throws Exception {
        final IConnector db = new DBConnector(new DatabaseConnection());
        final IProductBatchDAO productBatchDAO = new ProductBatchDAO(db);
        final ProductBatch actualProductBatch = productBatchDAO.getProductBatch(0);

        assertNull(actualProductBatch);
    }

    @Test
    public void productBatchDAO_CRUD_Test() throws Exception {
        final IConnector db = new DBConnector(new DatabaseConnection());
        final IProductBatchDAO productBatchDAO = new ProductBatchDAO(db);
        final ProductBatch productBatch =
                new ProductBatch(
                        0,
                        new Timestamp(117,6,12,10,35,0,0),
                        null,
                        0,
                        1,
                        1
                );

        // Create
        final int id = productBatchDAO.createProductBatch(productBatch);
        productBatch.setProductbatchId(id);
        assertEquals(productBatch, productBatchDAO.getProductBatch(id)); // Check that the product batch exists

        // Delete product batch again
        db.connectToDatabase();
        db.update("DELETE FROM productbatch WHERE productbatch_id = " + id);
        assertFalse(productBatchDAO.getProductBatchList().contains(productBatch)); // Check that the product batch is deleted
        db.close();
    }
}
