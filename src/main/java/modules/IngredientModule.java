package modules;

import auth.AuthenticationEndpoint;
import dao.IIngredientDAO;
import dao.IngredientDAO;
import jdbclib.DALException;
import jdbclib.DBConnector;
import jdbclib.DatabaseConnection;
import jdbclib.IConnector;
import dto.Ingredient;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.net.URI;
import java.util.List;

@Path("ingredient")
public class IngredientModule {
    @AuthenticationEndpoint.Secured
    @POST
    @Path("get/{ingredientId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Ingredient getIngredient(@PathParam("ingredientId") int id) {
        IConnector db;
        Ingredient ingredient = null;

        try {
            db = new DBConnector(new DatabaseConnection());
            IIngredientDAO ingredientDAO = new IngredientDAO(db);
            ingredient = ingredientDAO.getIngredient(id);
        } catch (Exception e) { // TODO: Implement better exception handling
            System.out.println("Error: " + e.getMessage());
            Response.temporaryRedirect(URI.create("/auth/ingredient/error"));
        }

        return ingredient;
    }

    @AuthenticationEndpoint.Secured
    @POST
    @Path("get/all")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Ingredient> getAllIngredients() {
        try {
            final IConnector db = new DBConnector(new DatabaseConnection());
            final IIngredientDAO ingredientDAO = new IngredientDAO(db);

            return ingredientDAO.getIngredientList();
        } catch (IOException | DALException e) {
            System.out.println(e); // TODO: Throw a better exception and catch frontend
            return null;
        }
    }

    @AuthenticationEndpoint.Secured
    @POST
    @Path("create")
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
            System.out.println(e); // TODO: Throw a better exception and catch frontend
            return null;
        }
    }
}
