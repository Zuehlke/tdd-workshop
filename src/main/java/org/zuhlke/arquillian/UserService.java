package org.zuhlke.arquillian;

import javax.inject.Inject;

public class UserService {

    private NameService nameService;

    @Inject
    public UserService(NameService nameService) {
        this.nameService = nameService;
    }

    public String getUserName(String id) {
        return nameService.getUserName(id);
    }
}