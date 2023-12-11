package ru.limon4etop.SpringBoot.Services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.limon4etop.SpringBoot.Services.PostService;
import ru.limon4etop.SpringBoot.models.Post;
import ru.limon4etop.SpringBoot.repos.postRepo;

import java.util.List;

@Service
public class PostServiceImp implements PostService {
    private postRepo postRepo;

    @Autowired
    public PostServiceImp(final postRepo postRepo) {
        this.postRepo = postRepo;
    }

    @Override
    public void addPost(final Post postData) {
        postRepo.save(postData);
    }

    @Override
    public void editPost(final Post postData) {
        postRepo.save(postData);
    }

    @Override
    public void deletePost(final Integer id) {
        postRepo.delete(postRepo.findById(id));
    }

    @Override
    public Post findPostById(final Integer postId) {
        return postRepo.findById(postId);
    }

    @Override
    public List<Post> findPostByNewsCategoryAndUserId(final String newsCategory, final String userId) {
        return postRepo.findByNewsCategoryAndUserIdOrderByDateCreate(newsCategory, userId);
    }

    @Override
    public List<Post> findPostsByUserId(final String userId) {
        return postRepo.findByUserId(userId);
    }

    @Override
    public List<Post> findAllPostsByOrderByDateCreateDesc() {
        return postRepo.findAllByOrderByDateCreateDesc();
    }

    @Override
    public List<Post> findPostsByNewsCategory(final String newsCategory) {
        return postRepo.findByNewsCategoryOrderByDateCreate(newsCategory);
    }

    @Override
    public List<Post> findAllPosts() {
        return postRepo.findAll();
    }
}
