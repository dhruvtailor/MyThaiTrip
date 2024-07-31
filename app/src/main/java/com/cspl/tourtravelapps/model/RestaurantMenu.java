package com.cspl.tourtravelapps.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by a_man on 24-01-2018.
 */

public class RestaurantMenu implements Parcelable{
    private String name, description;
    private boolean added;
    private int price;
    private int imageRes;

    public RestaurantMenu(String name, String description, int price,int res) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.imageRes=res;
    }

    protected RestaurantMenu(Parcel in) {
        name = in.readString();
        description = in.readString();
        added = in.readByte() != 0;
        price = in.readInt();
        imageRes = in.readInt();
    }

    public static final Creator<RestaurantMenu> CREATOR = new Creator<RestaurantMenu>() {
        @Override
        public RestaurantMenu createFromParcel(Parcel in) {
            return new RestaurantMenu(in);
        }

        @Override
        public RestaurantMenu[] newArray(int size) {
            return new RestaurantMenu[size];
        }
    };

    public int getImageRes() {
        return imageRes;
    }

    public void setAdded(boolean added) {
        this.added = added;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public boolean isAdded() {
        return added;
    }

    public int getPrice() {
        return price;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(description);
        parcel.writeByte((byte) (added ? 1 : 0));
        parcel.writeFloat(price);
        parcel.writeInt(imageRes);
    }
}
