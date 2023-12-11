package ru.limon4etop.SpringBoot.controllers;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;
import ru.limon4etop.SpringBoot.Services.impl.UserServiceImp;
import ru.limon4etop.SpringBoot.Services.impl.WarningServiceImp;
import ru.limon4etop.SpringBoot.models.Warning;

@Controller
public class adminController {
    private UserServiceImp userServiceImp;
    private WarningServiceImp warningServiceImp;

    public adminController(final UserServiceImp userServiceImp,
                           final WarningServiceImp warningServiceImp) {
        this.userServiceImp = userServiceImp;
        this.warningServiceImp = warningServiceImp;
    }

    @GetMapping("/myWarnings")
    public RedirectView myWarnings(final RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("userWarningList",
                warningServiceImp.findByUserGetNotification(getAuthenticationUserId()));
        return new RedirectView("/getWarningPage");
    }

    @GetMapping("/allWarningList")
    public RedirectView allWarningList(final RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("warningList", warningServiceImp.getAllWarnings());
        redirectAttributes.addFlashAttribute("userList", userServiceImp.getAllUsersData());
        return new RedirectView("/getAllWarningPage");
    }

    @GetMapping("/openAddWarningPage")
    public RedirectView openAddWarningPage(final RedirectAttributes redirectAttributes){
        redirectAttributes.addFlashAttribute("userList", userServiceImp.getAllUsersData());
        return new RedirectView("/getAddWarningPage");
    }

    @PostMapping("/addWarning")
    public RedirectView addWarning(@ModelAttribute("addWarning") final Warning warningData) {
        warningData.setAdminSendNotificationId(getAuthenticationUserId());
        warningServiceImp.addWarning(warningData);
        return new RedirectView("/allWarningList");
    }

    @GetMapping("/acceptWarning/{warningId}")
    public String acceptWarning(@PathVariable("warningId") final Integer warnId) {
        warningServiceImp.deleteWarning(warnId);
        return "redirect:/allWarningList";
    }


    private String getAuthenticationUserId() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}