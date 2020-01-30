package org.akj.mongo.bean;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserInfo implements Serializable {
    private String firstName;

    private String middleName;

    private String lastName;

    private int age;

    private GenderEnum gender;

    private String phone;

    private String telPhone;

}
