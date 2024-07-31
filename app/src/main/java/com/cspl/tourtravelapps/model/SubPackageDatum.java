package com.cspl.tourtravelapps.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by HP on 08/10/2018.
 */

public class SubPackageDatum implements Parcelable {


    private String categoryName;
    private String categoryDescription;
    private Integer categoryRate;


    public SubPackageDatum(Parcel in) {
        categoryName = in.readString();
        categoryDescription = in.readString();
        if (in.readByte() == 0) {
            categoryRate = null;
        } else {
            categoryRate = in.readInt();
        }
    }

    public SubPackageDatum(String categoryName, String categoryDescription, Integer categoryRate) {
        this.categoryName = categoryName;
        this.categoryDescription = categoryDescription;
        this.categoryRate = categoryRate;
    }

    public static final Creator<SubPackageDatum> CREATOR = new Creator<SubPackageDatum>() {
        @Override
        public SubPackageDatum createFromParcel(Parcel in) {
            return new SubPackageDatum(in);
        }

        @Override
        public SubPackageDatum[] newArray(int size) {
            return new SubPackageDatum[size];
        }
    };

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryDescription() {
        return categoryDescription;
    }

    public void setCategoryDescription(String categoryDescription) {
        this.categoryDescription = categoryDescription;
    }

    public Integer getCategoryRate() {
        return categoryRate;
    }

    public void setCategoryRate(Integer categoryRate) {
        this.categoryRate = categoryRate;
    }


    @Override
    public int describeContents() {
        return getClass().hashCode();
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(categoryName);
        parcel.writeString(categoryDescription);
        if (categoryRate == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(categoryRate);
        }
    }
}
