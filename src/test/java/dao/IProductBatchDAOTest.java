package dao;

import dto.ProductBatch;
import org.junit.Test;

import java.sql.Timestamp;
import java.util.List;

import static org.junit.Assert.*;

public class IProductBatchDAOTest {
    @Test
    public void getValidProductBatch() throws Exception {
        final IProductBatchDAO ProductBatchDAO = new TestProductBatchDAO();
        final ProductBatch expectedProductBatch =
                new ProductBatch(
                        1,
                        new Timestamp(2017,6,12,8,55,0,0),
                        new Timestamp(2017,6,12,9,20,0,0),
                        2,
                        1,
                        1
                );
        final ProductBatch actualProductBatch = ProductBatchDAO.getProductBatch(1);

        assertEquals(expectedProductBatch,actualProductBatch);
    }

    @Test
    public void getInvalidProductBatch() throws Exception {
        final IProductBatchDAO ProductBatchDAO = new TestProductBatchDAO();
        final ProductBatch actualProductBatch = ProductBatchDAO.getProductBatch(0);

        assertNull(actualProductBatch);
    }

    @Test
    public void getAllProductBatches() throws Exception {
        final IProductBatchDAO productBatchDAO = new TestProductBatchDAO();
        final ProductBatch expectedFirstProductBatch =
                new ProductBatch(
                        1,
                        new Timestamp(2017,6,12,8,55,0,0),
                        new Timestamp(2017,6,12,9,20,0,0),
                        2,
                        1,
                        1
                );

        final List<ProductBatch> productBatches = productBatchDAO.getProductBatchList();

        assertTrue(productBatches.size() > 0);
        assertEquals(expectedFirstProductBatch, productBatches.get(0));
    }

    @Test
    public void createProductBatch() throws Exception {
        final IProductBatchDAO productBatchDAO = new TestProductBatchDAO();
        final ProductBatch newProductBatch =
                new ProductBatch(
                    3,
                    new Timestamp(2017,6,12,12,55,0,0),
                    null,
                    0,
                    2,
                    1
                );

        productBatchDAO.createProductBatch(newProductBatch);
        final List<ProductBatch> list = productBatchDAO.getProductBatchList();

        assertTrue(list.contains(newProductBatch));
    }

}
