package module;

import auth.AuthenticationEndpoint;
import config.Permission;
import config.Routes;
import dao.IRecipeComponentDAO;
import dao.RecipeComponentDAO;
import dto.RecipeComponent;
import jdbclib.DALException;
import jdbclib.DBConnector;
import jdbclib.DatabaseConnection;
import jdbclib.IConnector;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.util.List;

@Path(Routes.MODULE_RECIPECOMPONENT)
public class RecipeComponentModule {
    @AuthenticationEndpoint.Secured(Permission.RECIPE_READ)
    @POST
    @Path(Routes.MODULE_RECIPECOMPONENT_ALL)
    @Produces(MediaType.APPLICATION_JSON)
    public List<RecipeComponent> getRecipeComponentList(@PathParam(Routes.MODULE_RECIPECOMPONENT_GET_RECIPEID) int recipeId) {
        try {
            final IConnector db = new DBConnector(new DatabaseConnection());
            final IRecipeComponentDAO recipeComponentDAO = new RecipeComponentDAO(db);

            return recipeComponentDAO.getRecipeComponentList(recipeId);
        } catch (IOException | DALException e) {
            System.err.println(e.getMessage()); // TODO: Throw a better exception and catch frontend
            return null;
        }
    }

    @AuthenticationEndpoint.Secured(Permission.RECIPE_CREATE)
    @POST
    @Path(Routes.MODULE_RECIPECOMPONENT_CREATE)
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public RecipeComponent createRecipeComponent(RecipeComponent recipeComponent) {
        try {
            final IConnector db = new DBConnector(new DatabaseConnection());
            final IRecipeComponentDAO recipeComponentDAO = new RecipeComponentDAO(db);
            recipeComponentDAO.createRecipeComponent(recipeComponent);

            return recipeComponent;
        } catch (IOException | DALException e) {
            System.err.println(e.getMessage()); // TODO: Throw a better exception and catch frontend
            return null;
        }
    }
}
