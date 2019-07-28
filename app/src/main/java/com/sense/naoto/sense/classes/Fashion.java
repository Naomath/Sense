package com.sense.naoto.sense.classes;

import android.os.Parcel;
import android.os.Parcelable;

import lombok.Getter;
import lombok.Setter;

public class Fashion implements Parcelable {


    @Getter
    @Setter
    private int imageCode;

    @Getter
    @Setter
    private String title;

    @Getter
    @Setter
    private String description;


    public Fashion(){}

    public Fashion(int imageCode, String title, String description){
        this.imageCode = imageCode;
        this.title = title;
        this.description = description;
    }


    //ここからParcelableの継承したメソッド

    protected Fashion(Parcel in) {
        imageCode = in.readInt();
        title = in.readString();
        description = in.readString();
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
        parcel.writeInt(imageCode);
        parcel.writeString(title);
        parcel.writeString(description);
    }
}
