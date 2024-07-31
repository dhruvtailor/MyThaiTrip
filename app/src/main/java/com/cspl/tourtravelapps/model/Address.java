package com.cspl.tourtravelapps.model;

/**
 * Created by a_man on 24-01-2018.
 */

public class Address {
    private String title, address;
    private boolean selected;

    public Address(String title, String address) {
        this.title = title;
        this.address = address;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public String getTitle() {
        return title;
    }

    public String getAddress() {
        return address;
    }

    public boolean isSelected() {
        return selected;
    }
}
