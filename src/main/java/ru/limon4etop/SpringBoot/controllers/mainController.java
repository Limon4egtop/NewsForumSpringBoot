package ru.limon4etop.SpringBoot.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import ru.limon4etop.SpringBoot.Services.impl.PostServiceImp;
import ru.limon4etop.SpringBoot.Services.impl.UserServiceImp;

@Controller
public class mainController {

    protected PostServiceImp postServiceImp;
    protected UserServiceImp userServiceImp;

    @Autowired
    public mainController(final UserServiceImp userServiceImp,
                          final PostServiceImp postServiceImp) {
        this.userServiceImp = userServiceImp;
        this.postServiceImp = postServiceImp;
    }

    protected String getAuthenticationUserId() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}
