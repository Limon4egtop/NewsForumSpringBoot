package ru.limon4etop.SpringBoot.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.limon4etop.SpringBoot.Services.impl.PostServiceImp;
import ru.limon4etop.SpringBoot.Services.impl.UserServiceImp;

@Controller
public class openPagesController extends mainController{

    @Autowired
    public openPagesController(final PostServiceImp postServiceImp,
                               final UserServiceImp userServiceImp) {
        super(userServiceImp, postServiceImp);
        this.userServiceImp = userServiceImp;
    }


    @GetMapping("/getAddWarningPage")
    public String getAddWarningPage() {
        return "addWarningPage";
    }

    @GetMapping("/getAllDataAboutNotMeUser")
    public String getAllDataAboutNotMeUser() {
        return "allDataAboutNotMeUser";
    }
    @GetMapping("/getAllDataAboutUser")
    public String getAllDataAboutUser() {
        return "allDataAboutUser";
    }
    @GetMapping("/getAllUserList")
    public String getAllUserList() {
        return "allUserList";
    }
    @GetMapping("/getAllWarningPage")
    public String getAllWarningPage() {
        return "allWarningPage";
    }
    @GetMapping("/getEditPostPage")
    public String getEditPostPage() {
        return "editPostPage";
    }

    @GetMapping("/getMainPage")
    public String getMainPage(final Model model) {
        model.addAttribute("userList", userServiceImp.getAllUsersData());
        return "mainPage";
    }
    @GetMapping("/getMainPostPage")
    public String getMainPostPage() {
        return "mainPostPage";
    }
    @GetMapping("/getMyPost")
    public String getMyPost() {
        return "myPost";
    }
    @GetMapping("/getRegisterUser")
    public String getRegisterUser() {
        return "registerUser";
    }
    @GetMapping("/getUserSubscription")
    public String getUserSubscription() {
        return "userSubscription";
    }
    @GetMapping("/getWarningPage")
    public String getWarningPage() {
        return "warningPage";
    }
}
