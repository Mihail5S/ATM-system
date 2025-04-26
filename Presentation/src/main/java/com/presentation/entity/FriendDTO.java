package com.presentation.entity;

public class FriendDTO {
    private String login;


    public FriendDTO() {}

    public FriendDTO(String login) {
        this.login = login;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }
}

