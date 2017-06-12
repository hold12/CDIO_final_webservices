package dao;

import dto.ProductBatchComponent;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class IProductBatchComponentDAOTest {
    @Test
    public void getValidProductBatchComponent() throws Exception {
        final IProductBatchComponentDAO productBatchComponentDAO = new TestProductBatchComponentDAO();
        final ProductBatchComponent expectedProductBatchComponent =
                new ProductBatchComponent(1, 1, 0.5,10.0);
        final ProductBatchComponent actualProductBatchComponent = productBatchComponentDAO.getProductBatchComponent(1,1);

        assertEquals(expectedProductBatchComponent,actualProductBatchComponent);
    }

    @Test
    public void getInvalidProductBatchComponent() throws Exception {
        final IProductBatchComponentDAO productBatchComponentDAO = new TestProductBatchComponentDAO();
        final ProductBatchComponent actualProductBatchComponent = productBatchComponentDAO.getProductBatchComponent(0,0);

        assertNull(actualProductBatchComponent);
    }

    @Test
    public void getAllProductBatchComponentes() throws Exception {
        final IProductBatchComponentDAO productBatchComponentDAO = new TestProductBatchComponentDAO();
        final ProductBatchComponent expectedFirstProductBatchComponent =
                new ProductBatchComponent(1,1,0.5,10.0);

        final List<ProductBatchComponent> productBatchComponents = productBatchComponentDAO.getProductBatchComponentList();

        assertTrue(productBatchComponents.size() > 0);
        assertEquals(expectedFirstProductBatchComponent, productBatchComponents.get(0));
    }
}
