package ru.limon4etop.SpringBoot.Services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.limon4etop.SpringBoot.Services.CommentService;
import ru.limon4etop.SpringBoot.models.Comment;
import ru.limon4etop.SpringBoot.repos.commentRepo;

import java.util.List;

@Service
public class CommentServiceImp implements CommentService {
    private commentRepo commentRepo;

    @Autowired
    public CommentServiceImp(commentRepo commentRepo) {
        this.commentRepo = commentRepo;
    }

    @Override
    public void saveComent(Comment comment) {
       this.commentRepo.save(comment);
    }

    @Override
    public void deleteComment(Comment comment) {
        this.commentRepo.delete(comment);
    }

    @Override
    public List<Comment> findByPostId(Integer postId) {
        return this.commentRepo.findByPostId(postId);
    }
}
