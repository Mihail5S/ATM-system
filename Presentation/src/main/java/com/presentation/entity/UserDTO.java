package com.presentation.entity;

import Model.Gender;
import Model.HairColor;
import Model.User;

public class UserDTO {
    private String login;
    private String name;
    private int age;
    private Gender gender;
    private HairColor haircolor;

    public UserDTO() {
    }


        public static UserDTO toUserDto(User user) {
            UserDTO userDTO = new UserDTO();
            userDTO.login = user.getLogin();
            userDTO.name = user.getName();
            userDTO.age = user.getAge();
            userDTO.gender = user.getGender();
            userDTO.haircolor = user.getHaircolor();
            return userDTO;
        }


    public String getLogin() {
        return login;
    }
    public String getName() {
        return name;
    }
    public int getAge() {
        return age;
    }
    public Gender getGender() {
        return gender;
    }
    public HairColor getHaircolor() {
        return haircolor;
    }
}
