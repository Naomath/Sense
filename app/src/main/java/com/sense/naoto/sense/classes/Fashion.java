package com.sense.naoto.sense.classes;

import android.os.Parcel;
import android.os.Parcelable;

import org.apache.commons.lang.RandomStringUtils;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

public class Fashion implements Parcelable {

    @Getter
    @Setter
    private String localDeviceUri;

    @Getter
    @Setter
    private String strDate;

    @Getter
    @Setter
    private String prefKey;

    @Getter
    @Setter
    private List<String> itemPrefKey = new ArrayList<>();

    @Getter
    @Setter
    boolean isFav;

    @Getter
    @Setter
    private String imageCode;
    //allなんちゃらで使うよう

    

    public Fashion(){}


    public Fashion(String date, String localDeviceUri, String prefKey){
        this.strDate = date;
        this.localDeviceUri = localDeviceUri;
        this.prefKey = prefKey;
    }

    public Fashion(String date, String localDeviceUri, String prefKey, List<String> itemPrefKeys){
        this.strDate = date;
        this.localDeviceUri = localDeviceUri;
        this.prefKey = prefKey;
        this.itemPrefKey = itemPrefKeys;
    }


    public static String newPreferenceKey(){
        return RandomStringUtils.randomAlphabetic(16);
    }


    //ここからParcelableの継承したメソッド

    protected Fashion(Parcel in) {
        localDeviceUri = in.readString();
        strDate = in.readString();
        prefKey = in.readString();
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
        parcel.writeString(localDeviceUri);
        parcel.writeString(strDate);
        parcel.writeString(prefKey);

    }

}
