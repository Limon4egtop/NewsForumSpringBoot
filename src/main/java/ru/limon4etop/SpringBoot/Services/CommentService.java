package ru.limon4etop.SpringBoot.Services;

import ru.limon4etop.SpringBoot.models.Comment;

import java.util.List;

public interface CommentService {
    void saveComent(final Comment comment);
    void deleteComment(final Comment comment);
    List<Comment> findByPostId(final Integer postId);
}
