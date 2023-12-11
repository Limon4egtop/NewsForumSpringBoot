package ru.limon4etop.SpringBoot.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;
import ru.limon4etop.SpringBoot.Services.impl.PostServiceImp;
import ru.limon4etop.SpringBoot.Services.impl.UserServiceImp;
import ru.limon4etop.SpringBoot.models.Post;


import java.util.ArrayList;
import java.util.List;

@Controller
public class homePageController extends mainController{

    @Autowired
    public homePageController(final PostServiceImp postServiceImp,
                              final UserServiceImp userServiceImp) {
        super(userServiceImp, postServiceImp);
    }

    @GetMapping("/")
    public RedirectView getMainPage(final RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("postsList",
                                            postServiceImp.findAllPostsByOrderByDateCreateDesc());
        return new RedirectView("/getMainPage");
    }

    @PostMapping("/updatePostList")
    public RedirectView updatePostList(@RequestParam(name = "postCategory", required = false) final String newsCategory,
                                       @RequestParam(name = "searchText", required = false) final String searchText,
                                       final RedirectAttributes redirectAttributes) {
        if (newsCategory != null) {
            redirectAttributes.addFlashAttribute("postsList",
                    postServiceImp.findPostsByNewsCategory(newsCategory));
            return new RedirectView("/getMainPage");
        }
        if (!searchText.isEmpty()){
            redirectAttributes.addFlashAttribute("postsList", generateSendPostList(searchText));
            return new RedirectView("/getMainPage");
        }
        return new RedirectView("/");
    }

    private List<Post> generateSendPostList(final String searchText) {
        List<Post> sendPostList = new ArrayList<>();
        for (Post post : postServiceImp.findAllPosts()) {
            if (post.getHeader().contains(searchText) || post.getPostData().contains(searchText)){
                sendPostList.add(post);
            }
        }
        return sendPostList;
    }
}
