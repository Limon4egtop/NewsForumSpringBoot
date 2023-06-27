package ru.limon4etop.SpringBoot.controllers;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.limon4etop.SpringBoot.models.Warning;
import ru.limon4etop.SpringBoot.repos.postRepo;
import ru.limon4etop.SpringBoot.repos.userRepo;
import ru.limon4etop.SpringBoot.repos.warningRepo;

@Controller
public class adminController {
    private userRepo userRepo;

    private postRepo postRepo;
    private warningRepo warningRepo;

    public adminController(userRepo userRepo, postRepo postRepo, warningRepo warningRepo) {
        this.userRepo = userRepo;
        this.postRepo = postRepo;
        this.warningRepo = warningRepo;
    }

    @GetMapping("/myWarnings")
    private String myWarnings(Model model) {
        model.addAttribute("userWarningList",
                warningRepo.findByUserGetNotification(getAuthenticationUserId()));
        return "warningPage";
    }

    @GetMapping("/allWarningList")
    public String allWarningList(Model model) {
        model.addAttribute("warningList", warningRepo.findAll());
        model.addAttribute("userList", userRepo.findAll());
        return "allWarningPage";
    }

    @GetMapping("/openAddWarningPage")
    public String openAddWarningPage(Model model){
        model.addAttribute("userList", userRepo.findAll());
        return "addWarningPage";
    }

    @PostMapping("/addWarning")
    public String addWarning(@RequestParam(value = "headOfWarning", required = false) String warningTitle,
                             @RequestParam(value = "warningText", required = false) String warningText,
                             @RequestParam(value = "userGetWarning", required = false) String warningUserGet){
        Warning warning = new Warning();
        warning.setHeading(warningTitle);
        warning.setText(warningText);
        warning.setUserGetNotification(warningUserGet);
        warning.setAdminSendNotificationId(getAuthenticationUserId());
        warningRepo.save(warning);
        return "redirect:allWarningList";
    }

    @GetMapping("/acceptWarning/{warningId}")
    public String acceptWarning(@PathVariable("warningId") Integer warnId) {
        Warning warning = new Warning();
        warning.setId(warnId);
        warningRepo.delete(warning);
        return "redirect:/allWarningList";
    }


    public String getAuthenticationUserId() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}



//3) Придумать систему обработки большого количества наплыва оценок.
//4) Комментарии к публикациям