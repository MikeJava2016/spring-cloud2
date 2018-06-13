package com.user.entity;

import java.util.List;

public class User {
    private Integer id;

    private String age;

    private String userName;

    private String password;
    
    private List<Order> orders;

    public Integer getId() {
        return id;
    }

    public User setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getAge() {
        return age;
    }

    public User setAge(String age) {
        this.age = age == null ? null : age.trim();
        return this;
    }

    public String getUserName() {
        return userName;
    }

    public User setUserName(String userName) {
       this.userName = userName == null ? null : userName.trim();
       return this;
    }

    public String getPassword() {
        return password;
    }

    public User setPassword(String password) {
        this.password = password == null ? null : password.trim();
        return this;
    }

	public List<Order> getOrders() {
		return orders;
	}

	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}
    
    
}