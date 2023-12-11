package ru.limon4etop.SpringBoot.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String header;
    private Date dateCreate;
    private String postData;
    private String userId;
    private String newsCategory;

    public Post(String header, Date dateCreate, String postData, String userId, String newsCategory) {
        this.header = header;
        this.dateCreate = dateCreate;
        this.postData = postData;
        this.userId = userId;
        this.newsCategory = newsCategory;
    }

    public Post(Integer id, String postData, String userId) {
        this.id = id;
        this.postData = postData;
        this.userId = userId;
    }

    public Post() {

    }

    public String getNewsCategory() {
        return newsCategory;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public Date getDateCreate() {
        return dateCreate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPostData() {
        return postData;
    }

    public String getUserId() {
        return userId;
    }

    public void setDateCreate(Date dateCreate) {
        this.dateCreate = dateCreate;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setPostData(String postData) {
        this.postData = postData;
    }

    public void setNewsCategory(String newsCategory) {
        this.newsCategory = newsCategory;
    }
}
