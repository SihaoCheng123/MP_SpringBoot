package com.mealplanner.mealplanner.dto;

import com.mealplanner.mealplanner.models.Users;

public class LoginResponse {
    private Long id;
    private String name;
    private String email;
    private String phone;
    private String password;
    private int age;
    private String token;

    public LoginResponse(Users user, String token) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.name = user.getUser_data().getName();
        this.phone = user.getUser_data().getPhone();
        this.age = user.getUser_data().getAge();
        this.token = token;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
