package com.keysoft;


import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

public class RestClient {

    public static void main(String[] args) {
        Client client = ClientBuilder.newClient();

        Application application = client
                .target("http://localhost:8080/javaee-7.0/rest/applications")
                .path("{id}")
                .resolveTemplate("id", 1)
                .request()
                .get(Application.class);

        System.out.println("Returned application: " + application.toString());
    }

}
