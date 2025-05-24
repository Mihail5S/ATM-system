package org.example.gateway.dto;

public class FriendDto {
    private String login;


    public FriendDto() {}

    public FriendDto(String login) {
        this.login = login;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }
}
