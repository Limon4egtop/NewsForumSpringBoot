package ru.limon4etop.SpringBoot.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.limon4etop.SpringBoot.repos.postRepo;
import ru.limon4etop.SpringBoot.repos.userRepo;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

@Controller
public class mainController {
    private userRepo userRepo;

    private postRepo postRepo;

    @Autowired
    public mainController(userRepo userRepo, postRepo postRepo) {
        this.userRepo = userRepo;
        this.postRepo = postRepo;
    }

    @GetMapping("/")
    public String getMainPage(Model model) {
        model.addAttribute("postsList", postRepo.findAllByOrderByDateCreateDesc());
        model.addAttribute("userList", userRepo.findAll());
        return "mainPage";
    }

    @GetMapping ("/getMainPostPage")
    public String getMainPostPage() {
        return "mainPostPage";
    }

    @GetMapping("/registrUser")
        public String getRegistrUser(){
            return "registrUser";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) throws ServletException {
        request.logout();
        return "redirect:/";
    }

    @PostMapping("/updatePostList")
    public String updatePostList(@RequestParam(name = "postCategory", required = false) String newsCategory,
                                 Model model) {
        if (newsCategory != null) {
            model.addAttribute("postsList",
                    postRepo.findByNewsCategoryOrderByDateCreate(newsCategory));
            model.addAttribute("userList", userRepo.findAll());
            return "mainPage";
        }
        return "redirect:/";
    }

    @GetMapping("/edipUserData/{id}")
    public String edipUserData(@PathVariable("id") String id,
                               Model model) {
        model.addAttribute("userInfo", userRepo.findUserById(id));
        return "editUserData";
    }

    @GetMapping("/allUserList")
    public String allUserList(Model model){
        model.addAttribute("userList", userRepo.findAll());
        return "allUserList";
    }
}
