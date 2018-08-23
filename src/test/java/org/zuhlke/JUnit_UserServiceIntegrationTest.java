package org.zuhlke;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

public class JUnit_UserServiceIntegrationTest {

    @Test
    public void whenUserIdIsProvided_thenRetrievedNameIsCorrect() {
        NameService mock = Mockito.mock(NameService.class);
        Mockito.when(mock.getUserName("SomeId")).thenReturn("Mock user name");

        String testName = new UserService(mock).getUserName("SomeId");

        Assert.assertEquals("Mock user name", testName);
    }
}
