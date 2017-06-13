package module;

import auth.AuthenticationEndpoint;
import config.Permission;
import config.Routes;
import dao.IProductBatchDAO;
import dao.ProductBatchDAO;
import dto.ProductBatch;
import jdbclib.DALException;
import jdbclib.DBConnector;
import jdbclib.DatabaseConnection;
import jdbclib.IConnector;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.util.List;

@Path(Routes.MODULE_PRODUCTBATCH)
public class ProductBatchModule {
    @AuthenticationEndpoint.Secured(Permission.PRODUCTBATCH_READ)
    @POST
    @Path(Routes.MODULE_PRODUCTBATCH_GET)
    @Produces(MediaType.APPLICATION_JSON)
    public ProductBatch getProductBatch(@PathParam(Routes.MODULE_PRODUCTBATCH_GET_ID) int id) {
        try {
            final IConnector db = new DBConnector(new DatabaseConnection());
            final IProductBatchDAO productBatchDAO = new ProductBatchDAO(db);

            return productBatchDAO.getProductBatch(id);
        } catch (IOException | DALException e) {
            System.err.println(e.getMessage()); // TODO: Throw a better exception and catch frontend
            return null;
        }
    }
    @AuthenticationEndpoint.Secured(Permission.PRODUCTBATCH_READ)
    @POST
    @Path(Routes.MODULE_PRODUCTBATCH_ALL)
    @Produces(MediaType.APPLICATION_JSON)
    public List<ProductBatch> getProductBatchList() {
        try {
            final IConnector db = new DBConnector(new DatabaseConnection());
            final IProductBatchDAO productBatchDAO = new ProductBatchDAO(db);

            return productBatchDAO.getProductBatchList();
        } catch (IOException | DALException e) {
            System.err.println(e.getMessage()); // TODO: Throw a better exception and catch frontend
            return null;
        }
    }

    @AuthenticationEndpoint.Secured(Permission.PRODUCTBATCH_CREATE)
    @POST
    @Path(Routes.MODULE_PRODUCTBATCH_CREATE)
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public ProductBatch createProduct(ProductBatch productBatch) {
        try {
            final IConnector db = new DBConnector(new DatabaseConnection());
            final IProductBatchDAO productBatchDAO = new ProductBatchDAO(db);
            final int productBatchId = productBatchDAO.createProductBatch(productBatch);

            productBatch.setProductbatchId(productBatchId);

            return productBatch;
        } catch (IOException | DALException e) {
            System.err.println(e.getMessage()); // TODO: Throw a better exception and catch frontend
            return null;
        }
    }

}
