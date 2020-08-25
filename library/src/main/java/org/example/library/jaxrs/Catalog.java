package org.example.library.jaxrs;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("/catalog")
public class Catalog {

    static final String HELLO_WORLD = "Hello World!";
    @GET
    @Path("/helloWorld")
    public String helloWorld() {
        return HELLO_WORLD;
    }
}
