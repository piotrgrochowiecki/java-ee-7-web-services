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

}
