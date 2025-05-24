package org.example.gateway.dto;

public class GatewayUserCreateRequest {
    private String login;
    private String name;
    private int age;
    private String gender;
    private String haircolor;
    private String password;

    public GatewayUserCreateRequest() {}

    public String getLogin() {
        return login;
    }
    public void setLogin(String login) {
        this.login = login;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getAge() {
        return age;
    }
    public void setAge(int age) {
        this.age = age;
    }
    public String getGender() {
        return gender;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }
    public String getHaircolor() {
        return haircolor;
    }
    public void setHaircolor(String haircolor) {
        this.haircolor = haircolor;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
}
