package com.sense.naoto.sense.classes;

import lombok.Getter;
import lombok.Setter;

public class Item {

    @Getter
    @Setter
    private String localDeviceUri;

    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    private String strDate;


    public Item(){}

    public Item(String localDeviceUri, String name, String strDate){
        this.localDeviceUri = localDeviceUri;
        this.name = name;
        this.strDate = strDate;
    }
}
