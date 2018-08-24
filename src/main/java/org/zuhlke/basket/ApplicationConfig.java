package org.zuhlke.basket;

import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

public class ApplicationConfig extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> c = new HashSet<>();
        c.add(BasketRest.class);
        return c;
    }
}
