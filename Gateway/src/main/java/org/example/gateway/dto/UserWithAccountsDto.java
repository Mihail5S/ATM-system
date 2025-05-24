package org.example.gateway.dto;

import java.util.List;

public class UserWithAccountsDto {
    private String login;
    private String name;
    private int age;
    private String gender;
    private String haircolor;
    private List<BankAccountDto> accounts;

    public UserWithAccountsDto() {}

    public UserWithAccountsDto(String login, String name, int age,
                               String gender, String haircolor,
                               List<BankAccountDto> accounts) {
        this.login = login;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.haircolor = haircolor;
        this.accounts = accounts;
    }
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
    public List<BankAccountDto> getAccounts() {
        return accounts;
    }
    public void setAccounts(List<BankAccountDto> accounts) {
        this.accounts = accounts;
    }
}
