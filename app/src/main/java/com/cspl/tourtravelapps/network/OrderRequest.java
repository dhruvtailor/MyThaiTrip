package com.cspl.tourtravelapps.network;

/**
 * Created by HP on 08/22/2018.
 */

public class OrderRequest {
    private int FlagID;
    private int SrNo;
    private int Order_ID;
    private int Order_Package_ID;
    private int Order_User_ID;
    private int NoOfChild;
    private int NoOfAdult;
    private int NoOfInFant;
    private String Travel_Date;
    private double Total_Amount;
    private double Service_Charge;
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

    public OrderRequest(int flagID,int srNo, int order_Package_ID, int order_User_ID, int noOfChild, int noOfAdult, int noOfInFant, String travel_Date, double total_Amount, double service_Charge, String cust_Name, String cust_Email, String cust_Phone, String hotel_Name, String hotel_Short_Address,  String flight_No,String approx_Departure_Time, String flight_Arrival_Time, String vehicle_Type, String no_of_Vehicle,String cust_Pickup_Time) {
        FlagID = flagID;
        SrNo = srNo;
//        Order_ID = order_ID;
        Order_Package_ID = order_Package_ID;
        Order_User_ID = order_User_ID;
        NoOfChild = noOfChild;
        NoOfAdult = noOfAdult;
        NoOfInFant = noOfInFant;
        Travel_Date = travel_Date;
        Total_Amount = total_Amount;
        Service_Charge = service_Charge;
        Cust_Name = cust_Name;
        Cust_Email = cust_Email;
        Cust_Phone = cust_Phone;
        Hotel_Name = hotel_Name;
        Hotel_Short_Address = hotel_Short_Address;
        Flight_No = flight_No;
        Approx_Departure_Time = approx_Departure_Time;
        Flight_Arrival_Time = flight_Arrival_Time;
        Vehicle_Type = vehicle_Type;
        No_of_Vehicle = no_of_Vehicle;
        Cust_Pickup_Time = cust_Pickup_Time;
    }

    public OrderRequest(int flagID, int order_User_ID) {
        FlagID = flagID;
        Order_User_ID = order_User_ID;
    }
}
