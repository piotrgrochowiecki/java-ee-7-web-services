package src.main.java.com.keysoft.rest;

import src.main.java.com.keysoft.model.Application;
import src.main.java.com.keysoft.utils.Database;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.sql.ResultSet;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Path("applications")
@Produces("application/json")
@Consumes("application/json")
public class ApplicationResource {

    @GET
    public Response getAll() { //http://localhost:8080/javaee-7.0/rest/applications
        List<Application> applications = new ArrayList<>();

        try {
            Statement statement = Database.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery("select * from tza_application");

            while(resultSet.next()) {
                applications.add(new Application(resultSet.getInt(1),
                        resultSet.getString(2), resultSet.getString(3)));
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        return Response.status(200).entity(applications).build();
    }

    @POST
    public Response addApplication(Application application){

        try {
            Statement statement = Database.getConnection().createStatement();
            statement.executeUpdate("INSERT INTO TZA_APPLICATION " +
                    " VALUES (" + application.getId() + ",'" + application.getName() +
                    "','" + application.getDescription() + "')");
        } catch (SQLIntegrityConstraintViolationException exception) {
            exception.printStackTrace();
            return Response.status(409).build();
        } catch (Exception exception) {
            exception.printStackTrace();
            return Response.status(403).build();
        }

        return Response.status(201).build();
    }

    @PUT
    public Response updateApplication(Application application) {
        try {
            Statement statement = Database.getConnection().createStatement();
            statement.executeUpdate("UPDATE TZA_APPLICATION " +
                    " SET name = '" + application.getName() + "', " +
                    " description = '" + application.getDescription() + "'" +
                    " WHERE id=" + application.getId());
        } catch (Exception exception) {
            exception.printStackTrace();
            return Response.status(403).build();
        }

        return Response.status(200).build();
    }

    @GET
    @Path("{id}")
    public Response getApplication(@PathParam("id") int id) {
        Application application = new Application();

        try {

            Statement statement = Database.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery("select * from tza_application where id = " + id);

            while(resultSet.next()) {
                application = new Application(resultSet.getInt(1),
                        resultSet.getString(2), resultSet.getString(3));
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        return Response.status(200).entity(application).build();
    }

    @GET
    @Path("{id}/{name}")
    public Response getApplication(@PathParam("id") int id, @PathParam("name") String name) {
        Application application = new Application();

        try {

            Statement statement = Database.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery("select * from tza_application where id = " + id + " and name = '" + name +"'");

            while(resultSet.next()) {
                application = new Application(resultSet.getInt(1),
                        resultSet.getString(2), resultSet.getString(3));
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        return Response.status(200).entity(application).build();
    }

    @DELETE
    @Path("{id}")
    public Response deleteApplication(@PathParam("id") int id) {
        try {
            Statement statement = Database.getConnection().createStatement();
            statement.executeUpdate("DELETE FROM TZA_APPLICATION " +
                    " WHERE id=" + id);
        } catch (Exception exception) {
            exception.printStackTrace();
            return Response.status(403).build();
        }

        return Response.status(204).build();

    }

}
