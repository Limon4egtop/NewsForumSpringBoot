package ru.limon4etop.SpringBoot.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String userSendCommentId;
    private Integer postId;
    private String text;

    public Comment(String userSendCommentId, Integer postId, String text) {
        this.userSendCommentId = userSendCommentId;
        this.postId = postId;
        this.text = text;
    }

    public Comment() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserSendCommentId() {
        return userSendCommentId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
