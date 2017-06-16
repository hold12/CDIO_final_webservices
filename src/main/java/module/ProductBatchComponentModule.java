package module;

import auth.AuthenticationEndpoint;
import config.Permission;
import config.Routes;
import dao.IProductBatchComponentDAO;
import dao.ProductBatchComponentDAO;
import dto.ProductBatchComponent;
import jdbclib.DALException;
import jdbclib.DBConnector;
import jdbclib.DatabaseConnection;
import jdbclib.IConnector;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.util.List;

@Path(Routes.MODULE_PRODUCTBATCHCOMPONENT)
public class ProductBatchComponentModule {
    @AuthenticationEndpoint.Secured(Permission.PRODUCTBATCH_READ)
    @POST
    @Path(Routes.MODULE_PRODUCTBATCHCOMPONENT_GET_PRODUCTBATCHID)
    @Produces(MediaType.APPLICATION_JSON)
    public List<ProductBatchComponent> getProductBatchComponentList(@PathParam(Routes.MODULE_PRODUCTBATCHCOMPONENT_GET_PRODUCTBATCHID) int productBatchId) {
        try {
            final IConnector db = new DBConnector(new DatabaseConnection());
            final IProductBatchComponentDAO productBatchComponentDAO = new ProductBatchComponentDAO(db);

            return productBatchComponentDAO.getProductBatchComponentList(productBatchId);
        } catch (IOException | DALException e) {
            System.err.println(e.getMessage()); // TODO: Throw a better exception and catch frontend
            return null;
        }
    }

}
