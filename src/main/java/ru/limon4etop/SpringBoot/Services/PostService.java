package ru.limon4etop.SpringBoot.Services;

import ru.limon4etop.SpringBoot.models.Post;

import java.util.List;

public interface PostService {
    void addPost(final Post postData);
    void editPost(final Post postData);
    void deletePost(final Integer id);
    Post findPostById(final Integer postId);

    List<Post> findPostByNewsCategoryAndUserId(final String newsCategory,
                                               final String userId);
    List<Post> findPostsByUserId(final String userId);

    List<Post> findAllPostsByOrderByDateCreateDesc();
    List<Post> findPostsByNewsCategory(final String newsCategory);
    List<Post> findAllPosts();
}
