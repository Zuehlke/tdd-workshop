package org.zuhlke.arquillian;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;

@RunWith(Arquillian.class)
public class Arquillian_UserServiceIntegrationTest {

    @Inject
    UserService userService;
    @Inject
    NameService nameService;

    @Deployment
    public static JavaArchive createDeployment() {
        JavaArchive jar = ShrinkWrap.create(JavaArchive.class)
                .addClass(org.zuhlke.arquillian.UserService.class)
                .addClass(org.zuhlke.arquillian.NameService.class)
                .addClass(NameServiceMock.class)
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
        System.out.println(jar.toString(true));
        return jar;
    }

    @Test
    public void whenUserIdIsProvided_thenRetrievedNameIsCorrect() {
        Assert.assertEquals("Mock user name", nameService.getUserName("              "));

        String testName = userService.getUserName("SomeId");

        Assert.assertEquals("Mock user name", testName);
    }
}
