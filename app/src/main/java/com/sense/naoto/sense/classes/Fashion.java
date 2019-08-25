package com.sense.naoto.sense.classes;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

public class Fashion implements Parcelable {

    @Getter
    @Setter
    private int fashionResId;


    @Getter
    @Setter
    private String externalPathName;

    @Getter
    @Setter
    private String title;

    @Getter
    @Setter
    private String description;

    @Getter
    @Setter
    private String makerName;

    @Getter
    @Setter
    private String makerId;

    @Getter
    @Setter
    private String makerImageCode;
    //これはbase64FA

    @Getter
    @Setter
    private String [] imageIds;



    public Fashion(){}

    //これは中で使う
    public Fashion(int fashionResId, String title, String description, String makerName, String makerImageCode){
        this.fashionResId = fashionResId;
        this.title = title;
        this.description = description;
        this.makerName = makerName;
        this.makerImageCode = makerImageCode;
    }

    //これは外との通信で使う
    public Fashion(String externalPathName, String title, String description,
                   String makerName, String makerId){
        this.externalPathName = externalPathName;
        this.title = title;
        this.description = description;
        this.makerName = makerName;
        this.makerId = makerId;

    }

    //ここからParcelableの継承したメソッド

    protected Fashion(Parcel in) {
        fashionResId = in.readInt();
        title = in.readString();
        description = in.readString();
        makerName = in.readString();
        makerImageCode = in.readString();
    }


    public static final Creator<Fashion> CREATOR = new Creator<Fashion>() {
        @Override
        public Fashion createFromParcel(Parcel in) {
            return new Fashion(in);
        }

        @Override
        public Fashion[] newArray(int size) {
            return new Fashion[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(fashionResId);
        parcel.writeString(title);
        parcel.writeString(description);
        parcel.writeString(makerName);
        parcel.writeString(makerImageCode);
    }

}
