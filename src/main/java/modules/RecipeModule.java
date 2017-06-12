package modules;

import auth.AuthenticationEndpoint;
import dao.IRecipeDAO;
import dao.RecipeDAO;
import dto.Recipe;
import jdbclib.DALException;
import jdbclib.DBConnector;
import jdbclib.DatabaseConnection;
import jdbclib.IConnector;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.util.List;

@Path("recipe")
public class RecipeModule {
    @AuthenticationEndpoint.Secured
    @POST
    @Path("get/{recipeId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Recipe getRecipe(@PathParam("recipeId") int id) {
        try {
            final IConnector db = new DBConnector(new DatabaseConnection());
            final IRecipeDAO recipeDAO = new RecipeDAO(db);

            return recipeDAO.getRecipe(id);
        } catch (IOException | DALException e) {
            System.err.println(e.getMessage()); // TODO: Throw a better exception and catch frontend
            return null;
        }
    }

    @AuthenticationEndpoint.Secured
    @POST
    @Path("get/all")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Recipe> getAllRecipes() {
        try {
            final IConnector db = new DBConnector(new DatabaseConnection());
            final IRecipeDAO recipeDAO = new RecipeDAO(db);

            return recipeDAO.getRecipeList();
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
    public Recipe createRecipe(Recipe recipe) {
        try {
            final IConnector db = new DBConnector(new DatabaseConnection());
            final IRecipeDAO recipeDAO = new RecipeDAO(db);
            final int recipeId = recipeDAO.createRecipe(recipe);

            recipe.setRecipeId(recipeId);

            return recipe;
        } catch (IOException | DALException e) {
            System.err.println(e.getMessage()); // TODO: Throw a better exception and catch frontend
            return null;
        }
    }
}
