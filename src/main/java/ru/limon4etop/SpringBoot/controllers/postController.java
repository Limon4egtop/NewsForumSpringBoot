package ru.limon4etop.SpringBoot.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;
import ru.limon4etop.SpringBoot.Services.impl.CommentServiceImp;
import ru.limon4etop.SpringBoot.Services.impl.PostServiceImp;
import ru.limon4etop.SpringBoot.Services.impl.SubscritionServiceImp;
import ru.limon4etop.SpringBoot.Services.impl.UserServiceImp;
import ru.limon4etop.SpringBoot.models.Comment;
import ru.limon4etop.SpringBoot.models.Post;

import java.time.Instant;
import java.util.Date;
import java.util.Objects;

@Controller
public class postController extends mainController{

    private CommentServiceImp commentServiceImp;

    private SubscritionServiceImp subscritionServiceImp;

    @Autowired
    public postController(final CommentServiceImp commentServiceImp,
                          final SubscritionServiceImp subscritionServiceImp,
                          final PostServiceImp postServiceImp,
                          final UserServiceImp userServiceImp) {
        super(userServiceImp, postServiceImp);
        this.commentServiceImp = commentServiceImp;
        this.subscritionServiceImp = subscritionServiceImp;
    }

    @PostMapping("/addNewPost")
    public RedirectView addNewPost(@ModelAttribute("addNewPostData") final Post postData,
                                    final RedirectAttributes redirectAttributes) {
        if (!Objects.equals(postData.getPostData(), "")) {
            postData.setDateCreate(Date.from(Instant.now()));
            postData.setUserId(getAuthenticationUserId());
            postServiceImp.addPost(postData);
            redirectAttributes.addFlashAttribute("status", "post added");
        }
        return new RedirectView("/getMainPostPage");
    }

    @GetMapping("/openPost/{id}")
    public RedirectView openPost(@PathVariable("id") final Integer postId,
                           final RedirectAttributes redirectAttributes) {
        Post post = postServiceImp.findPostById(postId);
        redirectAttributes.addFlashAttribute("post", post);
        redirectAttributes.addFlashAttribute("user", userServiceImp.findUserById(getAuthenticationUserId()));
        redirectAttributes.addFlashAttribute("commentList", commentServiceImp.findByPostId(postId));
        return new RedirectView("/getMyPost");
    }

    @GetMapping("/editPost/{id}")
    public RedirectView editPost(@PathVariable("id") final Integer postId,
                           final RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("postId", postId);
        redirectAttributes.addFlashAttribute("postData", postServiceImp.findPostById(postId).getPostData());
        redirectAttributes.addFlashAttribute("postUserName",
                userServiceImp.findUserById(postServiceImp.findPostById(postId).getUserId()).getFirstName());
        return new RedirectView("/getEditPostPage");
    }

    @PostMapping("/saveEditPost/{id}")
    public RedirectView saveEditPost(@PathVariable("id") final Integer id,
                               @RequestParam(value = "postData", required = false) final String postText,
                               final RedirectAttributes redirectAttributes) {
        if (!Objects.equals(postText, "")) {
            Post post = new Post(id, postText, getAuthenticationUserId());
            postServiceImp.addPost(post);
            redirectAttributes.addFlashAttribute("status", "post edited");
        }
        return new RedirectView("/getMainPostPage");
    }

    @GetMapping("/deletePost/{id}")
    public RedirectView deletePost(@PathVariable("id") final Integer id,
                             final RedirectAttributes redirectAttributes){
        postServiceImp.deletePost(id);
        redirectAttributes.addFlashAttribute("status", "post deletsd");
        return new RedirectView("/getMainPage");
    }

    @PostMapping("/updatePostListInUserProfile/{id}")
    public RedirectView updatePostListInUserProfile(@PathVariable("id") final String userId,
                                              @RequestParam(name = "postCategory", required = false) final String newsCategory,
                                              final RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("userInfo", userServiceImp.findUserById(userId));
        if (newsCategory != null) {
            redirectAttributes.addFlashAttribute("postsList",
                    postServiceImp.findPostByNewsCategoryAndUserId(newsCategory, userId));
        }
        else {
            redirectAttributes.addFlashAttribute("postsList", postServiceImp.findPostsByUserId(userId));
        }
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

    @PostMapping("/addComment/{postId}")
    public RedirectView addComment(@PathVariable("postId") final Integer postId,
                             @RequestParam(value = "commentText", required = false) final String textOfComment){
        Comment comment = new Comment(getAuthenticationUserId(), postId, textOfComment);
        commentServiceImp.saveComent(comment);
        return new RedirectView("/openPost/{postId}");
    }
}
