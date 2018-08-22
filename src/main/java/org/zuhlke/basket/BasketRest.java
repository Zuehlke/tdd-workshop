package org.zuhlke.basket;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public class BasketRest {

    // TODO should only create one EntityManagerFactory, for now creating the BasketService only once. But this is a design decision that needs refactoring ASAP!
    private static final BasketService BASKET_SERVICE = new BasketService();

    @GET
    public List<Basket> getBaskets() {
        return BASKET_SERVICE.getBaskets();
    }

    @PUT
    public Basket createBasket(Basket toPersist) {
        int id = BASKET_SERVICE.persistBasket(toPersist);

        return new Basket(id, toPersist.getItemMap());
    }

    @DELETE
    @Path("/{id}")
    public void deleteBasket(@PathParam("id") int id) {
        BASKET_SERVICE.deleteBasket(id);
    }
}
