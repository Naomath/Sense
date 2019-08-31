package com.sense.naoto.sense.classes;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

public class FashionItem implements Serializable {

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
    private List<String> usedFashionPrefKeys = new ArrayList<>();

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
