package modules;

import auth.AuthenticationEndpoint;
import dao.IIngredientBatchDAO;
import dao.IngredientBatchDAO;
import dto.IngredientBatch;
import jdbclib.DALException;
import jdbclib.DBConnector;
import jdbclib.DatabaseConnection;
import jdbclib.IConnector;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.util.List;

@Path("ingredientbatch")
public class IngredientBatchModule {
	@AuthenticationEndpoint.Secured
	@POST
	@Path("get/{ingredientBatchId}")
	@Produces(MediaType.APPLICATION_JSON)
	public IngredientBatch getIngredientBatch(@PathParam("ingredientBatchId") int id) {
		try {
			final IConnector db = new DBConnector(new DatabaseConnection());
			final IIngredientBatchDAO ingredientBatchDAO = new IngredientBatchDAO(db);

			return ingredientBatchDAO.getIngredientBatch(id);
		} catch (IOException | DALException e) {
			System.err.println(e.getMessage()); // TODO: Throw a better exception and catch frontend
			return null;
		}
	}
	@AuthenticationEndpoint.Secured
	@POST
	@Path("get/all")
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

	@AuthenticationEndpoint.Secured
	@POST
	@Path("create")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public IngredientBatch createIngredient(IngredientBatch ingredientBatch) {
		try {
			final IConnector db = new DBConnector(new DatabaseConnection());
			final IIngredientBatchDAO ingredientBatchDAO = new IngredientBatchDAO(db);
			final int ingredientBatchId = ingredientBatchDAO.createIngredientBatch(ingredientBatch);

			ingredientBatch.setIngredientBatchId(ingredientBatchId);

			return ingredientBatch;
		} catch (IOException | DALException e) {
			System.err.println(e.getMessage()); // TODO: Throw a better exception and catch frontend
			return null;
		}
	}
}