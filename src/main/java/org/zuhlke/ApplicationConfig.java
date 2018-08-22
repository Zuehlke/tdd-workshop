package org.zuhlke;

import org.zuhlke.basket.BasketRest;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

@ApplicationPath("rs")
public class ApplicationConfig extends Application {
    @Override
    public Set<Class<?>> getClasses() {
        HashSet<Class<?>> c = new HashSet<>();
        c.add(BasketRest.class);
        return c;
    }
}
