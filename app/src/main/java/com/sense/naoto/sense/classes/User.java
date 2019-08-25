package com.sense.naoto.sense.classes;

import org.apache.commons.lang.RandomStringUtils;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

public class User {

    @Getter
    @Setter
    private String userName;

    @Getter
    @Setter
    private String userId;

    @Getter
    @Setter
    private int postNumber;

    @Getter
    @Setter
    private int followingNumber;

    @Getter
    @Setter
    private int followerNumber;

    @Getter
    @Setter
    private String iconImage;

    @Getter
    @Setter
    private List<String> imageIds;
    //これはDatabaseのuploadIdの配列

    public User(){}

    public static String newUserId(){
        return RandomStringUtils.randomAlphabetic(10);
    }

}
