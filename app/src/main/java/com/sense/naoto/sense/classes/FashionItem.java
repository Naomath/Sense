package com.sense.naoto.sense.classes;


import com.sense.naoto.sense.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

public class FashionItem implements Serializable {

    //定数
    private static final int TYPE_TOPS = 0;
    private static final int TYPE_BOTTOMS = 1;
    private static final int TYPE_ACCESSORIES = 2;
    private static final int TYPE_OUTER = 3;
    private static final int TYPE_OTHER = 4;

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
    private int type;

    @Getter
    @Setter
    private String imageCode;
    //base64

    public FashionItem() {
    }

    public FashionItem(String localDeviceUri, String name, String strDate, String prefKey, int type) {
        this.localDeviceUri = localDeviceUri;
        this.name = name;
        this.strDate = strDate;
        this.prefKey = prefKey;
        this.type = type;
    }

    public static int getTypeResID(int type) {
        //それぞれのtypeにそったdrawableのidを返す
        switch (type) {
            case TYPE_TOPS:
                return R.drawable.tops_icon;

            case TYPE_BOTTOMS:
                return R.drawable.bottoms_icon;

            case TYPE_ACCESSORIES:
                return R.drawable.accessories_icon;

            default:
                return R.drawable.other_icon;
        }
    }

    public static String getTypeName(int type){
        switch (type) {
            case TYPE_TOPS:
                return "TOP";

            case TYPE_BOTTOMS:
                return "BOTTOM";

            case TYPE_ACCESSORIES:
                return "ACCESSORY";

            default:
                return "OTHER";
        }
    }
}
