package com.keysoft;


import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.logging.Logger;

public class RestClient {

    public static void main(String[] args) {
        Logger logger = Logger.getAnonymousLogger();

        Client client = ClientBuilder.newClient();

        //Get single application
//        Application application = client
//                .target("http://localhost:8080/javaee-7.0/rest/applications")
//                .path("{id}")
//                .resolveTemplate("id", 1)
//                .request()
//                .get(Application.class);
//
//        System.out.println("Returned application: " + application.toString());

        //Create a single application
//        client.target("http://localhost:8080/javaee-7.0/rest/applications")
//                .request()
//                .post(Entity.entity(new Application(59, "Channel List", "TV guide app"),
//                                    MediaType.APPLICATION_JSON_TYPE),
//                      Application.class);
//
//        logger.info("Application created");

        //Return a list of applications
        List<Application> applications = client.target("http://localhost:8080/javaee-7.0/rest")
                .path("applications")
                .request(MediaType.APPLICATION_JSON)
                .get(new GenericType<List<Application>>() {});

        logger.info("Returned applications: " + applications.toString());
    }

}
