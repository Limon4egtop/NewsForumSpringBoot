package ru.limon4etop.SpringBoot.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Rating {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer id;
    String userId;
    String addUserId;
    Integer postId;
    Integer ratData;

    public Rating(String userId, String addUserId, Integer postId, Integer ratData) {
        this.userId = userId;
        this.addUserId = addUserId;
        this.postId = postId;
        this.ratData = ratData;
    }

    public Rating() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRatData() {
        return ratData;
    }
}
