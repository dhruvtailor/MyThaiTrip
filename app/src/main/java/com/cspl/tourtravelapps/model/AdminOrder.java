package com.cspl.tourtravelapps.model;

/**
 * Created by HP on 08/18/2018.
 */

public class AdminOrder {
    private String travelDate, packageName;
    private double orderTotal;
    private int noOfAdult,noOfChild,noOfInfant,isDriverAssigned;

    public AdminOrder(String travelDate, String packageName, double orderTotal, int noOfAdult, int noOfChild, int noOfInfant, int isDriverAssigned) {
        this.travelDate = travelDate;
        this.packageName = packageName;
        this.orderTotal = orderTotal;
        this.noOfAdult = noOfAdult;
        this.noOfChild = noOfChild;
        this.noOfInfant = noOfInfant;
        this.isDriverAssigned = isDriverAssigned;
    }

    public String getTravelDate() {
        return travelDate;
    }

    public void setTravelDate(String travelDate) {
        this.travelDate = travelDate;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public double getOrderTotal() {
        return orderTotal;
    }

    public void setOrderTotal(double orderTotal) {
        this.orderTotal = orderTotal;
    }

    public int getNoOfAdult() {
        return noOfAdult;
    }

    public void setNoOfAdult(int noOfAdult) {
        this.noOfAdult = noOfAdult;
    }

    public int getNoOfChild() {
        return noOfChild;
    }

    public void setNoOfChild(int noOfChild) {
        this.noOfChild = noOfChild;
    }

    public int getNoOfInfant() {
        return noOfInfant;
    }

    public void setNoOfInfant(int noOfInfant) {
        this.noOfInfant = noOfInfant;
    }

    public int getIsDriverAssigned() {
        return isDriverAssigned;
    }

    public void setIsDriverAssigned(int isDriverAssigned) {
        this.isDriverAssigned = isDriverAssigned;
    }
}
