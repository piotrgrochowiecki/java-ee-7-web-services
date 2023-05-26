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
    public List<Application> getAll() { //http://localhost:8080/javaee-7.0/rest/applications
        List<Application> testList = new ArrayList<>();

        testList.add(new Application(1, "name 1", "desc 1"));
        testList.add(new Application(2, "name 2", "desc 2"));
        testList.add(new Application(3, "name 3", "desc 3"));

        return testList;

    }

    @POST
    public Application addApplication(Application application) {
        return application;
    }

}
