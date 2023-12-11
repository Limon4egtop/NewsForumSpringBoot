package ru.limon4etop.SpringBoot.models;


import javax.persistence.Entity;
import javax.persistence.Id;

@Entity     //объявляем что это таблица
public class UserData {
    @Id         //указываем что этот параметр id
    private String id;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private Integer rating;

    public UserData(String id, String firstName, String lastName, String phoneNumber, String email,Integer rating) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.rating = rating;
    }

    public UserData() {
        this.rating = 0;
    }

    public Integer getRating() {
        return rating;
    }
    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }


    public String getLastName() {
        return lastName;
    }


    public String getPhoneNumber() {
        return phoneNumber;
    }


    public String getEmail() {
        return email;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
