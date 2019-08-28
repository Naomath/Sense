package com.sense.naoto.sense.classes;

import org.apache.commons.lang.RandomStringUtils;

import lombok.Getter;
import lombok.Setter;

public class FashionItem {

    @Getter
    @Setter
    private String localDeviceUri;

    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    private String strDate;

    @Getter
    @Setter
    private String prefKey;

    @Getter
    @Setter
    private String imageCode;
    //base64


    public FashionItem(){}

    public FashionItem(String localDeviceUri, String name, String strDate, String prefKey){
        this.localDeviceUri = localDeviceUri;
        this.name = name;
        this.strDate = strDate;
        this.prefKey = prefKey;
    }

}
