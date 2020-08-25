package org.example.library.jaxrs;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;

import javax.servlet.http.HttpServletResponse;

import org.jboss.resteasy.core.Dispatcher;
import org.jboss.resteasy.mock.MockDispatcherFactory;
import org.jboss.resteasy.mock.MockHttpRequest;
import org.jboss.resteasy.mock.MockHttpResponse;
import org.jboss.resteasy.plugins.server.resourcefactory.POJOResourceFactory;
import org.jboss.resteasy.spi.metadata.DefaultResourceClass;
import org.junit.jupiter.api.Test;

public class CatalogTest {

    @Test
    void testHelloWorld() throws UnsupportedEncodingException, URISyntaxException {

        Dispatcher dispatcher = MockDispatcherFactory.createDispatcher();

        POJOResourceFactory noDefaults = new POJOResourceFactory(new DefaultResourceClass(Catalog.class, null));
        dispatcher.getRegistry().addResourceFactory(noDefaults);
        MockHttpRequest request = MockHttpRequest.get("/catalog/helloWorld");
        MockHttpResponse response = new MockHttpResponse();

        dispatcher.invoke(request, response);

        assertEquals(HttpServletResponse.SC_OK, response.getStatus());
        assertEquals(Catalog.HELLO_WORLD, response.getContentAsString());
    }
}
