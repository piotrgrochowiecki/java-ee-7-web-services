package src.main.java.com.keysoft.rest;

import src.main.java.com.keysoft.model.Application;
import src.main.java.com.keysoft.utils.Database;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import javax.xml.crypto.Data;
import java.sql.ResultSet;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Path("applications")
@Produces("application/json")
@Consumes("application/json")
public class ApplicationResource {

    Logger logger = Logger.getAnonymousLogger();

    @GET
    public Response getAll() { //http://localhost:8080/javaee-7.0/rest/applications
        List<Application> applicationList = new ArrayList<>();

        try {
            Statement statement = Database.getConnection()
                    .createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM tza_application");

            while(resultSet.next()) {
                applicationList.add(new Application(resultSet.getInt(1),
                                                    resultSet.getString(2),
                                                    resultSet.getString(3)));
            }
        } catch(Exception exception) {
            exception.printStackTrace();
        }

        return Response.status(200).entity(applicationList).build();

    }

    @GET
    @Path("{id}")
    public Response getApplication(@PathParam("id") int id) {
        Application application = new Application();

        try {
            Statement statement = Database.getConnection()
                    .createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM tza_application WHERE id = " + id);

            while(resultSet.next()) {
                application.setId(resultSet.getInt(1));
                application.setName(resultSet.getString(2));
                application.setDescription(resultSet.getString(3));
            }
        } catch(Exception exception) {
            exception.printStackTrace();
        }

        return Response.status(200).entity(application).build();

    }

    @POST
    public Response addApplication(Application application) {
        try {
            Statement statement = Database.getConnection()
                    .createStatement();

            statement.executeUpdate(
                    "INSERT INTO tza_application VALUES (" + application.getId() + ",'" + application.getName() + "','" + application.getDescription() + "')");
        } catch(SQLIntegrityConstraintViolationException exception) {
            exception.printStackTrace();
            return Response.status(409).build();
        } catch(Exception exception) {
            exception.printStackTrace();
            return Response.status(403).build();
        }

        return Response.status(201).build();
    }

    @PUT
    public Response updateApplication(Application application) {
        try {
            Statement statement = Database.getConnection()
                    .createStatement();
            statement.executeUpdate("UPDATE tza_application SET name = '" + application.getName() + "', description = '" + application.getDescription() + "' WHERE id = " + application.getId());
            logger.info("Application has been updated. New data is " + application.toString());
        } catch(Exception exception) {
            exception.printStackTrace();
            return Response.status(403).build();
        }
        return Response.status(200).build();
    }

    @DELETE
    @Path("{id}")
    public Response deleteApplication(@PathParam("id") int id) {
        try {
            Statement statement = Database.getConnection()
                    .createStatement();
            statement.executeUpdate("DELETE FROM tza_application WHERE id = " + id);
            logger.info("Application with id " + id + " has been deleted.");
        } catch(Exception exception) {
            exception.printStackTrace();
            return Response.status(403).build();
        }

        return Response.status(204).build();
    }

}
