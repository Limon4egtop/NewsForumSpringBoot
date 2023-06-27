package ru.limon4etop.SpringBoot.controllers;

import org.bouncycastle.math.raw.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.limon4etop.SpringBoot.models.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import ru.limon4etop.SpringBoot.repos.postRepo;
import ru.limon4etop.SpringBoot.repos.userRepo;
import ru.limon4etop.SpringBoot.repos.ratingRepo;
import ru.limon4etop.SpringBoot.repos.subscriptionRepo;
import ru.limon4etop.SpringBoot.repos.commentRepo;


@Controller
public class userController {
    private userRepo userRepo;

    private postRepo postRepo;
    private ratingRepo ratingRepo;
    private subscriptionRepo subscriptionRepo;
    private commentRepo commentRepo;

    @Autowired
    public userController(userRepo userRepo, postRepo postRepo, ratingRepo ratingRepo, subscriptionRepo subscriptionRepo, commentRepo commentRepo) {
        this.userRepo = userRepo;
        this.postRepo = postRepo;
        this.ratingRepo = ratingRepo;
        this.subscriptionRepo = subscriptionRepo;
        this.commentRepo = commentRepo;
    }

    @PostMapping("/newUser")
    public String newUser(@RequestParam(value = "firstName", required = false) String firstName,
                  @RequestParam(value = "lastName", required = false) String lastName,
                  @RequestParam(value = "phoneNumber", required = false) String phoneNumber,
                  @RequestParam(value = "email", required = false) String email,
                  Model model){
        if ((!Objects.equals(firstName, "")) && (!Objects.equals(lastName, "")) &&
                (!Objects.equals(phoneNumber, "")) && (!Objects.equals(email, ""))) {
            UserData userData = new UserData();
            userData.setEmail(email);
            userData.setFirstName(firstName);
            userData.setLastName(lastName);
            userData.setPhoneNumber(phoneNumber);
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();     //берем инфу об авторезированном пользователе
            userData.setId(authentication.getName());        //получем id авторизованного пользователя
            userData.setRating(0);
            userRepo.save(userData);
            model.addAttribute("status", "ok");
        }
        else {
            model.addAttribute("status", "error");
        }
        return "mainPage";
    }

    @PostMapping("/addNewPost")
    public String addNewPost(@RequestParam(value = "headOfPost", required = false) String headerPost,
                             @RequestParam(value = "postData", required = false) String postText,
                             @RequestParam(value = "postCategory", required = false) String postCategory,
                             Model model) {
        if (!Objects.equals(postText, "")) {
            Post post = new Post();
            post.setUserId(getAuthenticationUserId());
            post.setHeader(headerPost);
            post.setDateCreate(Date.from(Instant.now()));
            post.setPostData(postText);
            post.setNewsCategory(postCategory);
            postRepo.save(post);
            model.addAttribute("status", "post added");
        }
        return "mainPostPage";
    }

    @GetMapping("/addReating/{id}/{postId}/{sendRating}")
    public String addReating(@PathVariable("id") String postUserId,
                             @PathVariable("postId") Integer postId,
                             @PathVariable("sendRating") Integer ratData) {
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
        return "redirect:/";
    }

    @GetMapping("/favicon.ico")
    public String redirectFavicon(){
        return "redirect:/";
    }

    public void addNewRating(Integer ratData, String postUserId, String addUserId, Integer postId){
        Rating rating = new Rating();
        rating.setPostId(postId);
        rating.setRatData(ratData);
        rating.setUserId(postUserId);
        rating.setAddUserId(addUserId);
        if (ratingRepo.findByUserIdAndAddUserId(postUserId, addUserId) != null){
            rating.setId(ratingRepo.findByUserIdAndAddUserId(postUserId, addUserId).getId());
        }
        ratingRepo.save(rating);
        List<Rating> ratingList = ratingRepo.findByUserId(postUserId);
        UserData userData = userRepo.findUserById(postUserId);
        Integer ratSumm = 0;
        for (Rating rating1 : ratingList) {
            ratSumm += rating1.getRatData();
        }
        userData.setRating(ratSumm);
        userRepo.save(userData);
    }

    @GetMapping("/openPost/{id}")
    public String openPost(@PathVariable("id") Integer id,
                           Model model) {
        Post post = new Post();
        post = postRepo.findById(id);
        model.addAttribute("post", post);
        model.addAttribute("user", userRepo.findUserById(getAuthenticationUserId()));
        return "myPost";
    }

    @GetMapping("/editPost/{id}")
    public String editPost(@PathVariable("id") Integer id,
                           Model model) {
        model.addAttribute("postId", id);
        model.addAttribute("postData", postRepo.findById(id).getPostData());
        model.addAttribute("postUserName", userRepo.findUserById(postRepo.findById(id).getUserId()).getFirstName());
        return "editPostPage";
    }

    @GetMapping("/editUserData/{id}")
    public String editUserData(@PathVariable("id") String id,
                               Model model) {
        UserData user = userRepo.findUserById(id);
        model.addAttribute("userId", user.getId());
        model.addAttribute("userFirstName", user.getFirstName());
        model.addAttribute("userLastName", user.getLastName());
        model.addAttribute("userPhoneNumber", user.getPhoneNumber());
        model.addAttribute("userEmail", user.getEmail());
        return "registrUser";
    }

    @GetMapping("/userLogin")
    public String userLogin() {
        if (userRepo.findUserById(getAuthenticationUserId()) == null) {
            return "registrUser";
        }
        return "mainPage";
    }

    @GetMapping("/Subscribe/{id}")
    public String Subscribe(@PathVariable("id") String userId) {
        Subscription subscription = new Subscription();
        subscription.setSubscribeUserId(userId);
        subscription.setFollowUserId(SecurityContextHolder.getContext().getAuthentication().getName());
        if (subscriptionRepo.findByFollowUserIdAndSubscribeUserId(SecurityContextHolder.getContext()
                .getAuthentication().getName(), userId) != null) {
            subscription.setId((subscriptionRepo.findByFollowUserIdAndSubscribeUserId(SecurityContextHolder.getContext()
                    .getAuthentication().getName(), userId).getId()));
        }
        subscriptionRepo.save(subscription);
        return "redirect:/getDataAboutUser/"+userId;
    }

    @GetMapping("/Unsubscribe/{id}")
    public String Unsubscribe(@PathVariable("id") String userId) {
        Subscription subscription = new Subscription();
        subscription.setSubscribeUserId(userId);
        subscription.setFollowUserId(SecurityContextHolder.getContext().getAuthentication().getName());
        if (subscriptionRepo.findByFollowUserIdAndSubscribeUserId(SecurityContextHolder.getContext()
                .getAuthentication().getName(), userId) != null) {
            subscription.setId((subscriptionRepo.findByFollowUserIdAndSubscribeUserId(SecurityContextHolder.getContext()
                    .getAuthentication().getName(), userId).getId()));
        }
        subscriptionRepo.delete(subscription);
        return "redirect:/getDataAboutUser/"+userId;
    }

    @PostMapping("/editedPostSend/{id}")
    public String editedPostSend(@PathVariable("id") Integer id,
                                 @RequestParam(value = "postData", required = false) String postText,
                                 Model model) {
        if (!Objects.equals(postText, "")) {
            Post post = new Post();
            post.setId(id);
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            post.setUserId(authentication.getName());
            post.setPostData(postText);
            postRepo.save(post);
            model.addAttribute("status", "post edited");
        }
        return "mainPostPage";
    }

    @GetMapping("/deletePost/{id}")
    public String deletePost(@PathVariable("id") Integer id,
                             Model model){
        postRepo.delete(postRepo.findById(id));
        model.addAttribute("status", "post deletsd");
        return "mainPage";
    }

    @GetMapping("/getDataAboutUser/{id}")
    public String getDataAboutUser(@PathVariable("id") String id,
                                   Model model) {
        model.addAttribute("userInfo", userRepo.findUserById(id));
        model.addAttribute("postsList", postRepo.findByUserId(id));
        if (Objects.equals(getAuthenticationUserId(), id)) {
            return "allDataAboutUser";
        }
        else {
            if (subscriptionRepo.findByFollowUserIdAndSubscribeUserId(getAuthenticationUserId(), id) != null){
                model.addAttribute("subscrStatus", "True");
            }
            else {
                model.addAttribute("subscrStatus", "False");
            }
            model.addAttribute("postsList", postRepo.findByUserId(id));
            return "allDataAboutNotMeUser";
        }
    }

    @GetMapping("/allDataAboutUser")
    public String allDataAboutUser(Model model) {
        model.addAttribute("userInfo", userRepo.findUserById(getAuthenticationUserId()));
        model.addAttribute("postsList", postRepo.findByUserId(getAuthenticationUserId()));
        return "allDataAboutUser";
    }

    @GetMapping("/mySubscription")
    public String mySubscription(Model model) {
        return "redirect:/userSubscription/"+getAuthenticationUserId();
    }

    @GetMapping("/userSubscription/{id}")
    public String userSubscription(@PathVariable("id") String id,
                                  Model model) {
        List<UserData> userDataList = new ArrayList<>();
        for (Subscription subscr : subscriptionRepo.findByFollowUserId(id)) {
            userDataList.add(userRepo.findUserById(subscr.getSubscribeUserId()));
        }
        model.addAttribute("subscrUsersDataList", userDataList);
        return "userSubscription";
    }

    @PostMapping("/updatePostListInUserProfile/{id}")
    public String updatePostListInUserProfile(@PathVariable("id") String userId,
                                @RequestParam(name = "postCategory", required = false) String newsCategory,
                                 Model model) {
        model.addAttribute("userInfo", userRepo.findUserById(userId));
        if (newsCategory != null) {
            model.addAttribute("postsList",
                    postRepo.findByNewsCategoryAndUserIdOrderByDateCreate(newsCategory, userId));
        }
        else {
            model.addAttribute("postsList", postRepo.findByUserId(userId));
        }
        if (Objects.equals(getAuthenticationUserId(), userId)) {
            return "allDataAboutUser";
        }
        else {
            if (subscriptionRepo.findByFollowUserIdAndSubscribeUserId(getAuthenticationUserId(), userId) != null){
                model.addAttribute("subscrStatus", "True");
            }
            else {
                model.addAttribute("subscrStatus", "False");
            }
            return "allDataAboutNotMeUser";
        }
    }

    @PostMapping("/addComment/{postId}")
    public String addComment(@PathVariable("postId") Integer postId,
                             @RequestParam(value = "commentText", required = false) String textOfComment){
        Comment comment = new Comment();
        comment.setPostId(postId);
        comment.setText(textOfComment);
        comment.setUserSendCommentId(getAuthenticationUserId());
        commentRepo.save(comment);
        return "redirect:/openPost/{postId}";
    }

    public String getAuthenticationUserId() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}
