package module;

import config.Routes;
import auth.AuthenticationEndpoint;
import config.Permission;
import dao.IIngredientBatchDAO;
import dao.IngredientBatchDAO;
import dto.IngredientBatch;
import jdbclib.DALException;
import jdbclib.DBConnector;
import jdbclib.DatabaseConnection;
import jdbclib.IConnector;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.List;

@Path(Routes.MODULE_INGREDIENTBATCH)
public class IngredientBatchModule {
	@AuthenticationEndpoint.Secured(Permission.INGREDIENTBATCH_READ)
	@POST
	@Path(Routes.MODULE_INGREDIENTBATCH_GET)
	@Produces(MediaType.APPLICATION_JSON)
	public IngredientBatch getIngredientBatch(@PathParam(Routes.MODULE_INGREDIENTBATCH_GET_ID) int id) {
		try {
			final IConnector db = new DBConnector(new DatabaseConnection());
			final IIngredientBatchDAO ingredientBatchDAO = new IngredientBatchDAO(db);

			return ingredientBatchDAO.getIngredientBatch(id);
		} catch (IOException | DALException e) {
			System.err.println(e.getMessage()); // TODO: Throw a better exception and catch frontend
			return null;
		}
	}
	@AuthenticationEndpoint.Secured(Permission.INGREDIENTBATCH_READ)
	@POST
	@Path(Routes.MODULE_INGREDIENTBATCH_ALL)
	@Produces(MediaType.APPLICATION_JSON)
	public List<IngredientBatch> getIngredientBatchList() {
		try {
			final IConnector db = new DBConnector(new DatabaseConnection());
			final IIngredientBatchDAO ingredientBatchDAO = new IngredientBatchDAO(db);

			return ingredientBatchDAO.getIngredientBatchList();
		} catch (IOException | DALException e) {
			System.err.println(e.getMessage()); // TODO: Throw a better exception and catch frontend
			return null;
		}
	}

	@AuthenticationEndpoint.Secured(Permission.INGREDIENTBATCH_CREATE)
	@POST
	@Path(Routes.MODULE_INGREDIENTBATCH_CREATE)
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createIngredient(IngredientBatch ingredientBatch) {
		System.err.println("I got this: " + ingredientBatch.toString());
		try {
			final IConnector db = new DBConnector(new DatabaseConnection());
			final IIngredientBatchDAO ingredientBatchDAO = new IngredientBatchDAO(db);
			final int ingredientBatchId = ingredientBatchDAO.createIngredientBatch(ingredientBatch);

			ingredientBatch.setIngredientBatchId(ingredientBatchId);
//			return ingredientBatch;
			return Response.status(Response.Status.OK).build();
		} catch (IOException | DALException e) {
			System.err.println(e.getMessage()); // TODO: Throw a better exception and catch frontend
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}
	}
}