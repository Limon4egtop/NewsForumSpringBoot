package ru.limon4etop.SpringBoot.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Subscription {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer id;
    String followUserId;      //тот кто подписан
    String subscribeUserId;   //тот на кого подписаны

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFollowUserId() {
        return followUserId;
    }

    public void setFollowUserId(String followUserId) {
        this.followUserId = followUserId;
    }

    public String getSubscribeUserId() {
        return subscribeUserId;
    }

    public void setSubscribeUserId(String subscribeUserId) {
        this.subscribeUserId = subscribeUserId;
    }
}
