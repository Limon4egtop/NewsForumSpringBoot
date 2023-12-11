package ru.limon4etop.SpringBoot.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;
import ru.limon4etop.SpringBoot.Services.impl.PostServiceImp;
import ru.limon4etop.SpringBoot.Services.impl.RatingServiceImp;
import ru.limon4etop.SpringBoot.Services.impl.SubscritionServiceImp;
import ru.limon4etop.SpringBoot.Services.impl.UserServiceImp;
import ru.limon4etop.SpringBoot.models.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


import ru.limon4etop.SpringBoot.models.UserData;

@Controller
public class userController extends mainController{
    private RatingServiceImp ratingServiceImp;
    private SubscritionServiceImp subscritionServiceImp;

    @Autowired
    public userController(final RatingServiceImp ratingServiceImp,
                          final SubscritionServiceImp subscritionServiceImp,
                          final PostServiceImp postServiceImp,
                          final UserServiceImp userServiceImp) {
        super(userServiceImp, postServiceImp);
        this.ratingServiceImp = ratingServiceImp;
        this.subscritionServiceImp = subscritionServiceImp;
    }

    @GetMapping("/allUserListPage")
    public RedirectView allUserListPage(final RedirectAttributes redirectAttributes){
        redirectAttributes.addFlashAttribute("userList", userServiceImp.getAllUsersData());
        return new RedirectView("/getAllUserList");
    }

    @PostMapping("/refreshUserData")
    public RedirectView refreshUserData(@ModelAttribute("refreshUserData") final UserData userData,
                                        final RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("status",
                saveUserData(userData));
        return new RedirectView("/getMainPage");
    }

    private String saveUserData (final UserData userData) {
        if ((!Objects.equals(userData.getFirstName(), "")) && (!Objects.equals(userData.getLastName(), "")) &&
                (!Objects.equals(userData.getPhoneNumber(), "")) && (!Objects.equals(userData.getEmail(), ""))) {
            userData.setId(getAuthenticationUserId());
            userServiceImp.addUser(userData);
            return "ok";
        }
        else {
            return "error";
        }
    }

    @GetMapping("/addRatingToUser/{id}/{postId}/{sendRating}")
    public RedirectView calculateUserRating(@PathVariable("id") final String postUserId,
                                      @PathVariable("postId") final Integer postId,
                                      @PathVariable("sendRating") final Integer ratData) {
        // TODO: избавиться с помощью паттернов
        switch (ratData) {
            case 1:
                addNewRating(-2, postUserId, getAuthenticationUserId(), postId);
                break;
            case 2:
                addNewRating(-1, postUserId, getAuthenticationUserId(), postId);
                break;
            case 3:
                addNewRating(0, postUserId, getAuthenticationUserId(), postId);
                break;
            case 4:
                addNewRating(1, postUserId, getAuthenticationUserId(), postId);
                break;
            case 5:
                addNewRating(2, postUserId, getAuthenticationUserId(), postId);
                break;
        }
        return new RedirectView("/");
    }

    private void addNewRating(final Integer ratData,
                             final String postUserId,
                             final String addUserId,
                             final Integer postId) {
        Rating rating = new Rating(postUserId, addUserId, postId, ratData);
        if (ratingServiceImp.findByUserIdAndAddUserId(postUserId, addUserId) != null){
            rating.setId(ratingServiceImp.findByUserIdAndAddUserId(postUserId, addUserId).getId());
        }
        ratingServiceImp.saveRatingData(rating);
        UserData userData = userServiceImp.findUserById(postUserId);
        Integer ratSumm = 0;
        for (Rating rating1 : ratingServiceImp.findByUserId(postUserId)) {
            ratSumm += rating1.getRatData();
        }
        userData.setRating(ratSumm);
        userServiceImp.addUser(userData);
    }

    @GetMapping("/userLogin")
    public String userLogin() {
        if (userServiceImp.findUserById(getAuthenticationUserId()) == null) {
            return "redirect:/getRegisterUser";
        }
        return "redirect:/getMainPage";
    }

    @GetMapping("/Subscribe/{id}")
    public RedirectView Subscribe(@PathVariable("id") final String userId) {
        subscritionServiceImp.saveSubscritionData(editSubscriptionData(userId));
        return new RedirectView("/getDataAboutUser/"+userId);
    }

    @GetMapping("/Unsubscribe/{id}")
    public RedirectView Unsubscribe(@PathVariable("id") final String userId) {
        subscritionServiceImp.deleteSubscrition(editSubscriptionData(userId));
        return new RedirectView("/getDataAboutUser/"+userId);
    }

    private Subscription editSubscriptionData (final String userId) {
        Subscription subscription = new Subscription(getAuthenticationUserId(), userId);
        subscription.setId(checkSubscriptionData(userId));
        return subscription;
    }

    private Integer checkSubscriptionData(final String userId) {
        if (subscritionServiceImp.findSubscrByFollowUserIdAndSubscrUserId(getAuthenticationUserId(), userId) != null) {
            return subscritionServiceImp.findSubscrByFollowUserIdAndSubscrUserId(getAuthenticationUserId(), userId).getId();
        }
        return null;
    }

    @GetMapping("/getDataAboutUser/{id}")
    public RedirectView getDataAboutUser(@PathVariable("id") final String userId,
                                   final RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("userInfo", userServiceImp.findUserById(userId));
        redirectAttributes.addFlashAttribute("postsList", postServiceImp.findPostsByUserId(userId));
        if (Objects.equals(getAuthenticationUserId(), userId)) {
            return new RedirectView("/getAllDataAboutUser");
        }
        else {
            if (subscritionServiceImp.findSubscrByFollowUserIdAndSubscrUserId(getAuthenticationUserId(), userId) != null){
                redirectAttributes.addFlashAttribute("subscrStatus", "True");
            }
            else {
                redirectAttributes.addFlashAttribute("subscrStatus", "False");
            }
            return new RedirectView("/getAllDataAboutNotMeUser");
        }
    }

    @GetMapping("/allDataAboutUser")
    public RedirectView allDataAboutUser(final RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("userInfo", userServiceImp.findUserById(getAuthenticationUserId()));
        redirectAttributes.addFlashAttribute("postsList", postServiceImp.findPostsByUserId(getAuthenticationUserId()));
        return new RedirectView("/getAllDataAboutUser");
    }

    @GetMapping("/mySubscription")
    public RedirectView mySubscription() {
        return new RedirectView("/userSubscription/"+getAuthenticationUserId());
    }

    @GetMapping("/userSubscription/{id}")
    public RedirectView userSubscriptionData(@PathVariable("id") final String id,
                                             final RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("subscrUsersDataList", getDataAboutSubscriptionUsers(id));
        return new RedirectView("/getUserSubscription");
    }

    private List<UserData> getDataAboutSubscriptionUsers(final String id) {
        List<UserData> userDataList = new ArrayList<>();
        for (Subscription subscription : subscritionServiceImp.getUserByFollowId(id)) {
            userDataList.add(userServiceImp.findUserById(subscription.getSubscribeUserId()));
        }
        return userDataList;
    }
}
