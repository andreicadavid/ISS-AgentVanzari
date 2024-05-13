package com.example.program_agentvanzari.Domain;

import java.io.Serializable;

public class User extends Entity implements Serializable {
    private String username;
    private String password;
    public User(int userId, String username, String password) {
        super(userId);
        this.username = username;
        this.password = password;
    }

    @Override
    public int getId() {return super.getId();}
    public String getUsername() {return username;}
    public String getPassword() {return password;}

    @Override
    public void setId(int id) {
        super.setId(id);
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
