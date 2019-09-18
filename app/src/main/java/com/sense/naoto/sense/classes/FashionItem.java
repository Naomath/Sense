package com.sense.naoto.sense.classes;


import com.sense.naoto.sense.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

public class FashionItem implements Serializable {

    //定数
    public static final int TYPE_TOPS = 0;
    public static final int TYPE_BOTTOMS = 1;
    public static final int TYPE_OUTER = 2;
    public static final int TYPE_DRESS = 3;
    public static final int TYPE_BAG = 4;
    public static final int TYPE_SHOES = 5;
    public static final int TYPE_ACCESSORIES = 6;
    public static final int TYPE_OTHER = 7;

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
    private boolean isSelected = false;
    //コーデの登録の際の、アイテムのセレクトの時にしか使わない

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

            case TYPE_OUTER:
                return R.drawable.outer_icon;

            case TYPE_DRESS:
                return R.drawable.dress_icon;

            case TYPE_BAG:
                return R.drawable.bag_icon;

            case TYPE_SHOES:
                return R.drawable.shoes_icon;

            case TYPE_ACCESSORIES:
                return R.drawable.accessories_icon;

            default:
                return R.drawable.other_icon;
        }
    }

    public static String getTypeName(int type) {
        switch (type) {
            case TYPE_TOPS:
                return "TOP";

            case TYPE_BOTTOMS:
                return "BOTTOM";

            case TYPE_OUTER:
                return "OUTER";

            case TYPE_DRESS:
               return "DRESS";

            case TYPE_BAG:
               return "BAG";

            case TYPE_SHOES:
                return "SHOES";

            case TYPE_ACCESSORIES:
                return "ACCESSORY";

            default:
                return "OTHERS";
        }
    }
}
