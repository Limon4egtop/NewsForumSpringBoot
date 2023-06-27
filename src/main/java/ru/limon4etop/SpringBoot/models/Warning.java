package ru.limon4etop.SpringBoot.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Warning {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String userGetNotification;
    private String adminSendNotificationId;
    private String heading;
    private String text;

    public String getAdminSendNotificationId() {
        return adminSendNotificationId;
    }

    public void setAdminSendNotificationId(String adminSendNotificationId) {
        this.adminSendNotificationId = adminSendNotificationId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserGetNotification() {
        return userGetNotification;
    }

    public void setUserGetNotification(String userGetNotification) {
        this.userGetNotification = userGetNotification;
    }

    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
