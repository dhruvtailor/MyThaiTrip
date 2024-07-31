package com.cspl.tourtravelapps.network;

/**
 * Created by HP on 08/14/2018.
 */

public class AddCartRequest {

    private int FlagID;
    private int Cart_Package_ID;
    private int Cart_User_ID;
    private int NoOfChild;
    private int NoOfAdults;
    private int NoOfInFant;
    private String Travel_Date;
    private int Total_Amount;
    private String Cust_Name;
    private String Cust_Email;
    private String Cust_Phone;
    private String Hotel_Name;
    private String Hotel_Short_Address;
    private String Approx_Departure_Time;
    private String Flight_No;
    private String Flight_Arrival_Time;
    private String Vehicle_Type;
    private String No_of_Vehicle;
    private String Cust_Pickup_Time;

    public AddCartRequest(int flagID, int cart_Package_ID, int cart_User_ID, int noOfChild, int noOfAdult, int noOfInFant, String travel_Date, int total_Amount, String cust_Name, String cust_Email, String cust_Phone, String hotel_Name, String hotel_Short_Address, String approx_Departure_Time, String flight_No, String flight_Arrival_Time, String vehicle_Type, String no_of_Vehicle,String cust_Pickup_Time) {
        FlagID = flagID;
        Cart_Package_ID = cart_Package_ID;
        Cart_User_ID = cart_User_ID;
        NoOfChild = noOfChild;
        NoOfAdults = noOfAdult;
        NoOfInFant = noOfInFant;
        Travel_Date = travel_Date;
        Total_Amount = total_Amount;
        Cust_Name = cust_Name;
        Cust_Email = cust_Email;
        Cust_Phone = cust_Phone;
        Hotel_Name = hotel_Name;
        Hotel_Short_Address = hotel_Short_Address;
        Approx_Departure_Time = approx_Departure_Time;
        Flight_No = flight_No;
        Flight_Arrival_Time = flight_Arrival_Time;
        Vehicle_Type = vehicle_Type;
        No_of_Vehicle = no_of_Vehicle;
        Cust_Pickup_Time = cust_Pickup_Time;
    }

    public AddCartRequest(int flagID, int cart_User_ID) {
        FlagID = flagID;
        Cart_User_ID = cart_User_ID;
    }

    public AddCartRequest(int flagID, int cart_Package_ID,int cart_User_ID) {
        FlagID = flagID;
        Cart_Package_ID = cart_Package_ID;
        Cart_User_ID = cart_User_ID;
    }
}
