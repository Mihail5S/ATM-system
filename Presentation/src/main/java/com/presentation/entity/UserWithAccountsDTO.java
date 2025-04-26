package com.presentation.entity;

import Model.Gender;
import Model.HairColor;
import java.util.List;

public class UserWithAccountsDTO {
    private String login;
    private String name;
    private int age;
    private Gender gender;
    private HairColor haircolor;
    private List<BankAccountDTO> accounts;

    public UserWithAccountsDTO() {}

    public UserWithAccountsDTO(String login, String name, int age,
                               Gender gender, HairColor haircolor,
                               List<BankAccountDTO> accounts) {
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

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public HairColor getHaircolor() {
        return haircolor;
    }

    public void setHaircolor(HairColor haircolor) {
        this.haircolor = haircolor;
    }

    public List<BankAccountDTO> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<BankAccountDTO> accounts) {
        this.accounts = accounts;
    }
}