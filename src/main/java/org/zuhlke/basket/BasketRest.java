package org.zuhlke.basket;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.util.ArrayList;
import java.util.List;

@Path("/")
public class BasketRest {

    @GET
    @Produces("application/json")
    public List<Basket> getBaskets() {
        // TODO
        return new ArrayList<>();
    }
}
