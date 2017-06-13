package module;

import config.Permission;
import config.Routes;
import auth.AuthenticationEndpoint;
import dao.IIngredientDAO;
import dao.IngredientDAO;
import dto.Ingredient;
import jdbclib.DALException;
import jdbclib.DBConnector;
import jdbclib.DatabaseConnection;
import jdbclib.IConnector;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.util.List;

@Path(Routes.MODULE_INGREDIENT)
public class IngredientModule {
    @AuthenticationEndpoint.Secured(Permission.INGREDIENT_READ)
    @POST
    @Path(Routes.MODULE_INGREDIENT_GET)
    @Produces(MediaType.APPLICATION_JSON)
    public Ingredient getIngredient(@PathParam(Routes.MODULE_INGREDIENT_GET_ID) int id) {
        try {
            final IConnector db = new DBConnector(new DatabaseConnection());
            final IIngredientDAO ingredientDAO = new IngredientDAO(db);

            return ingredientDAO.getIngredient(id);
        } catch (IOException | DALException e) {
            System.err.println(e.getMessage()); // TODO: Throw a better exception and catch frontend
            return null;
        }
    }

    @AuthenticationEndpoint.Secured(Permission.INGREDIENT_READ)
    @POST
    @Path(Routes.MODULE_INGREDIENT_ALL)
    @Produces(MediaType.APPLICATION_JSON)
    public List<Ingredient> getAllIngredients() {
        try {
            final IConnector db = new DBConnector(new DatabaseConnection());
            final IIngredientDAO ingredientDAO = new IngredientDAO(db);

            return ingredientDAO.getIngredientList();
        } catch (IOException | DALException e) {
            System.err.println(e.getMessage()); // TODO: Throw a better exception and catch frontend
            return null;
        }
    }

    @AuthenticationEndpoint.Secured(Permission.INGREDIENT_CREATE)
    @POST
    @Path(Routes.MODULE_INGREDIENT_CREATE)
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Ingredient createIngredient(Ingredient ingredient) {
        try {
            final IConnector db = new DBConnector(new DatabaseConnection());
            final IIngredientDAO ingredientDAO = new IngredientDAO(db);
            final int ingredientId = ingredientDAO.createIngredient(ingredient);

            ingredient.setIngredientId(ingredientId);

            return ingredient;
        } catch (IOException | DALException e) {
            System.err.println(e.getMessage()); // TODO: Throw a better exception and catch frontend
            return null;
        }
    }
}
