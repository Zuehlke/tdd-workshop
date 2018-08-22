package org.zuhlke.basket;

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.zuhlke.ApplicationConfig;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.ext.RuntimeDelegate;
import java.net.InetSocketAddress;
import java.net.URI;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class BasketRestTest {

    public static final int PORT = 12345;
    public static final String BASE_URL = "http://localhost:" + PORT + "/";

    private HttpServer server;
    private Client client;

    @Before
    public void setUp() throws Exception {
        URI uri = UriBuilder.fromUri("http://localhost/").port(PORT).build();

        // Create an HTTP server listening at port 8282
        server = HttpServer.create(new InetSocketAddress(uri.getPort()), 0);
        // Create a handler wrapping the JAX-RS application
        HttpHandler handler = RuntimeDelegate.getInstance().createEndpoint(new ApplicationConfig(), HttpHandler.class);
        // Map JAX-RS handler to the server root
        server.createContext(uri.getPath(), handler);
        // Start the server
        server.start();

        client = ClientBuilder.newClient();
    }

    @After
    public void tearDown() {
        server.stop(0);
    }

    @Test
    public void getBaskets_emptyDb_emptyList() {
        // Given

        // When
        Response response = client.target(BASE_URL).request().get();

        // Then
        assertEquals(200, response.getStatus());
        List<Basket> body = response.readEntity(new GenericType<List<Basket>>() {
        });
        assertEquals(0, body.size());
    }

    @Test
    public void createBaskets_emptyDb_basketPersistedAndReturned() {
        // Given

        // When
        Response response = client.target(BASE_URL).request().put(Entity.json(new Basket()));

        // Then
        assertEquals(200, response.getStatus());
        Basket basket = response.readEntity(Basket.class);
        assertEquals("Total: 0.00", basket.getSummary(Basket.SGD));
        assertNotNull(basket.getId());

        // Cleanup
        Response delete = client.target(BASE_URL + basket.getId()).request().delete();
        assertEquals(204, delete.getStatus());

    }

    @Test
    public void getBaskets_basketsInDb_returned() {
        // Given
        client.target(BASE_URL).request().put(Entity.json(new Basket()));

        // When
        Response response = client.target(BASE_URL).request().get();

        // Then
        assertEquals(200, response.getStatus());
        List<Basket> body = response.readEntity(new GenericType<List<Basket>>() {
        });
        assertEquals(1, body.size());

        // Cleanup
        Response delete = client.target(BASE_URL + body.get(0).getId()).request().delete();
        assertEquals(204, delete.getStatus());
    }
}