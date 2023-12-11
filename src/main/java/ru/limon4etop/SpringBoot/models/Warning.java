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

    public Warning(String userGetNotification, String adminSendNotificationId, String heading, String text) {
        this.userGetNotification = userGetNotification;
        this.adminSendNotificationId = adminSendNotificationId;
        this.heading = heading;
        this.text = text;
    }

    public Warning(Integer id) {
        this.id = id;
    }

    public Warning() {

    }

    public String getAdminSendNotificationId() {
        return adminSendNotificationId;
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

    public String getHeading() {
        return heading;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setUserGetNotification(String userGetNotification) {
        this.userGetNotification = userGetNotification;
    }

    public void setAdminSendNotificationId(String adminSendNotificationId) {
        this.adminSendNotificationId = adminSendNotificationId;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }
}
