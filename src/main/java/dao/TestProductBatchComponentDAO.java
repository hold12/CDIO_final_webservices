package dao;

import dto.ProductBatchComponent;
import jdbclib.DALException;

import java.util.ArrayList;
import java.util.List;

public class TestProductBatchComponentDAO implements IProductBatchComponentDAO {
    private List<ProductBatchComponent> productBatchComponentList;

    public TestProductBatchComponentDAO(){
        productBatchComponentList = new ArrayList<>();
        productBatchComponentList.add(new ProductBatchComponent(1,1,0.5,10.0));
        productBatchComponentList.add(new ProductBatchComponent(1,2,0.5,5.0));
    }

    @Override
    public ProductBatchComponent getProductBatchComponent(int productbatchId, int ingredientbatchId) throws DALException {
        ProductBatchComponent productBatchComponent;
        try {
            productBatchComponent = productBatchComponentList.get(ingredientbatchId-1);
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
        return productBatchComponent;
    }

    @Override
    public List<ProductBatchComponent> getProductBatchComponentList(int productbatchId) throws DALException {
        List<ProductBatchComponent> productBatchComponents = new ArrayList<>();

        for (ProductBatchComponent i : productBatchComponentList){
            if (i.getProductbatchId() == productbatchId)
                productBatchComponents.add(i);
        }

        return productBatchComponents;
    }

    @Override
    public List<ProductBatchComponent> getProductBatchComponentList() throws DALException {
        return productBatchComponentList;
    }
}
