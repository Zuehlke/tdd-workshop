package org.zuhlke.arquillian;

import javax.enterprise.inject.Specializes;

@Specializes
public class NameServiceMock extends NameService {
    @Override
    public String getUserName(String id) {
        return "Mock user name";
    }
}
