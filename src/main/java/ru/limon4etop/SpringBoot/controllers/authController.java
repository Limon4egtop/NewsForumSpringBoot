package ru.limon4etop.SpringBoot.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;
import ru.limon4etop.SpringBoot.Services.impl.UserServiceImp;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

@Controller
public class authController {
    private UserServiceImp userServiceImp;


    @Autowired
    public authController(final UserServiceImp userServiceImp) {
        this.userServiceImp = userServiceImp;
    }

    @GetMapping("/registrUser")
    public RedirectView getRegisterUser(final RedirectAttributes redirectAttributes){
        redirectAttributes.addFlashAttribute("dataAboutUser", null);
        return new RedirectView("/getRegisterUser");
    }

    @GetMapping("/logout")
    public String logout(final HttpServletRequest request) throws ServletException {
        request.logout();
        return "redirect:/";
    }

    @GetMapping("/editUserData/{id}")
    public RedirectView editUserData(@PathVariable("id") final String id,
                               final RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("dataAboutUser", userServiceImp.findUserById(id));
        return new RedirectView("/getRegisterUser");
    }

    @GetMapping("/favicon.ico")
    public String redirectFavicon(){
        return "redirect:/";
    }
}
