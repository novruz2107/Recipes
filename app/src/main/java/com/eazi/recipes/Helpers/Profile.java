package com.eazi.recipes.Helpers;

/**
 * Created by Novruz Engineer on 11/18/2018.
 */

public class Profile {

    private String name, surname, email, phone, password;
    private boolean isMale, isVegan, isDiabetic;

    public Profile() {
    }

    public Profile(String name, String surname, String email, String phone, boolean isMale, boolean isVegan, boolean isDiabetic) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.phone = phone;
        this.isMale = isMale;
        this.isVegan = isVegan;
        this.isDiabetic = isDiabetic;
    }

    public Profile(String name, String surname, String email, String phone, boolean isVegan, boolean isDiabetic) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.isVegan = isVegan;
        this.isDiabetic = isDiabetic;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isMale() {
        return isMale;
    }

    public void setMale(boolean male) {
        isMale = male;
    }

    public boolean isVegan() {
        return isVegan;
    }

    public void setVegan(boolean vegan) {
        isVegan = vegan;
    }

    public boolean isDiabetic() {
        return isDiabetic;
    }

    public void setDiabetic(boolean diabetic) {
        isDiabetic = diabetic;
    }
}
