package com.example.ritu.database1app.activity;

/**
 * Created by ritu on 12/26/2017.
 */

public class ContactModel {
    private String ID , name , email , phone ,imageURL;

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getImageURL(){return imageURL;}

    public void setImageURL(String imageURL){ this.imageURL = imageURL; }

    @Override
    public String toString() {
        return "ContactModel{" +
                "ID='" + ID + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
