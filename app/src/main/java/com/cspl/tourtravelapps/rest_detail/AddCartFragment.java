package com.cspl.tourtravelapps.rest_detail;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.cspl.tourtravelapps.MyApplication;
import com.cspl.tourtravelapps.RetrofitAPI.ApiClient;
import com.cspl.tourtravelapps.RetrofitAPI.ApiInterface;
import com.cspl.tourtravelapps.activity.CartActivity;
import com.cspl.tourtravelapps.adapter.CountryAdapter;
import com.cspl.tourtravelapps.adapter.SubPackageAdapter;
import com.cspl.tourtravelapps.helper.Common;
import com.cspl.tourtravelapps.helper.SessionManager;
import com.cspl.tourtravelapps.model.CountryDatum;
import com.cspl.tourtravelapps.model.PackageCategoryDatum;
import com.cspl.tourtravelapps.network.AddCartRequest;
import com.cspl.tourtravelapps.network.AddCartResponse;
import com.cspl.tourtravelapps.network.CountryResponse;
import com.cspl.tourtravelapps.network.PackageCategoryResponse;
import com.cspl.tourtravelapps.network.PackageRequest;
import com.cspl.tourtravelapps.receiver.ConnectivityReceiver;
import com.cspl.tourtravelapps.util.MyLinearLayoutManager;
import com.cspl.tourtravelapps.R;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.cspl.tourtravelapps.helper.Common.convertDateFormat_FrontToBack;
import static com.cspl.tourtravelapps.helper.Common.getDate;

public class AddCartFragment extends Fragment implements ConnectivityReceiver.ConnectivityReceiverListener {
    private RecyclerView packageRecycler;
    int noAdultTicket = 0;
    int noChildTicket = 0;
    int noInfantTicket = 0;

    private TextView adultValue;
    private TextView childValue;
    private TextView infantValue;
    private ImageView adultPlus;
    private ImageView adultMinus;
    private ImageView childPlus;
    private ImageView childMinus;
    private ImageView infantPlus;
    private ImageView infantMinus;
    private TextView addToCart;

    private static String EXTRA_PACKAGE_AGENT_ADULT_RATE = "agent_adult_rate";
    private static String EXTRA_PACKAGE_AGENT_CHILD_RATE = "agent_child_rate";
    private static String EXTRA_PACKAGE_AGENT_INFANT_RATE = "agent_infant_rate";
    private static String EXTRA_PACKAGE_CUSTOMER_ADULT_RATE = "customer_adult_rate";
    private static String EXTRA_PACKAGE_CUSTOMER_CHILD_RATE = "customer_child_rate";
    private static String EXTRA_PACKAGE_CUSTOMER_INFANT_RATE = "customer_infant_rate";
    private static String EXTRA_PACKAGE_ID = "package_id";
    private static String EXTRA_PACKAGE_NAME = "package_name";
    private static String EXTRA_PACKAGE_IMAGE="image_url";
    private static String EXTRA_HAS_SUB_PACKAGE = "is_sub_pakage";
    private static String EXTRA_USER_TYPE = "User_Type";

    private SessionManager sessionManeger;
    private int adultRate = 0;
    private int childRate = 0;
    private int infantRate = 0;
    private int total = 0;
    private Spinner addToCartcountry;
    private static ProgressDialog pDialog;
    private List<CountryDatum> countryData;
    private CountryAdapter countryAdapter;
    private MyLinearLayoutManager linearLayoutManager;
    private int pkgId;
    private String userType;
    private List<PackageCategoryDatum> subPackagesData;
    private SubPackageAdapter subPackageAdapter;
    private int flag = 0;
    private TextView datePicker;
    private int mYear;
    private int mMonth;
    private int mDay;
    private TextView selectedDate;
    private String travelDate;
    private String travelPickupTime;
    private final int flagId = 1;
    private Integer subPackageId;
    private ProgressDialog dialog;
    private int userId;
    private EditText custName;
    private EditText custEmail;
    private EditText custPhone;
    private EditText hotelName;
    private EditText hoteShortAddress;
    private String name;
    private String email;
    private String phone;
    private String hName;
    private String hAddress;
    private Integer countryPrice=0;
    private int SelectedIndex=0;
    private EditText flightNumber;
    private EditText flightArrivalTime;
    private EditText approxDepartureTime;
    private String appDepartTime;
    private String flightNo;
    private String flightArriveTime;
    private int totalPerson=0;
    private LinearLayout transferLayout;
    private Spinner vehicleType;
    private LinearLayout custPickupTimelayout;
    private TextView custPickupTime;
    private TextView pickupTime;
    private TextInputLayout flightNumberLayout;
    private TextInputLayout flightArrivalLayout;
    private TextInputLayout flightDepartureLayout;
    private ArrayList<String> vehicleList;
    private ArrayAdapter<String> vehicleTypeAdapter;
    private EditText noOfVehicle;
    private int noVehicle;


    public AddCartFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_cart, container, false);


        initializecontrols(view);

        getCountryList();

        datePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int dayOfMonth,
                                                  int monthOfYear, int year) {
                                selectedDate.setText(getDate(dayOfMonth, monthOfYear + 1, year));
                                travelDate = selectedDate.getText().toString();
//                                Log.d("SelectedDate:", selectedDate.getText().toString());
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                datePickerDialog.show();
            }
        });

        flightArrivalTime.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(b){
                    Calendar mcurrentTime = Calendar.getInstance();
                    int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                    int minute = mcurrentTime.get(Calendar.MINUTE);

                    TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                            flightArrivalTime.setText(selectedHour+":"+selectedMinute);
                        }
                    },hour,minute,true);
                    timePickerDialog.show();
                }
            }
        });


        custPickupTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        pickupTime.setText(selectedHour+":"+selectedMinute);
                        travelPickupTime = pickupTime.getText().toString();
//                        Log.d("Pickup Time",travelPickupTime);
                    }
                },hour,minute,true);
                timePickerDialog.show();
            }
        });

        
//        flightArrivalTime.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Calendar mcurrentTime = Calendar.getInstance();
//                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
//                int minute = mcurrentTime.get(Calendar.MINUTE);
//
//                TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
//                    @Override
//                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
//                        flightArrivalTime.setText(selectedHour+" : "+selectedMinute);
//                    }
//                },hour,minute,true);
//                timePickerDialog.show();
//            }
//        });

        approxDepartureTime.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    Calendar mcurrentTime = Calendar.getInstance();
                    int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                    int minute = mcurrentTime.get(Calendar.MINUTE);

                    TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                            approxDepartureTime.setText(selectedHour+":"+selectedMinute);
                        }
                    },hour,minute,true);
                    timePickerDialog.show();
                }
            }
        });
//        approxDepartureTime.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Calendar mcurrentTime = Calendar.getInstance();
//                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
//                int minute = mcurrentTime.get(Calendar.MINUTE);
//
//                TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
//                    @Override
//                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
//                        approxDepartureTime.setText(selectedHour+" : "+selectedMinute);
//                    }
//                },hour,minute,true);
//                timePickerDialog.show();
//            }
//        });

        addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                hideKeyboard(view);
                name = custName.getText().toString();
                email = custEmail.getText().toString();
                phone = custPhone.getText().toString();
                hName = hotelName.getText().toString();
                hAddress = hoteShortAddress.getText().toString();
                noVehicle = Integer.parseInt(noOfVehicle.getText().toString());


                countryPrice = countryData.get(addToCartcountry.getSelectedItemPosition()).getCountryRate();
//                Log.d("Country",String.valueOf(countryPrice));

                adultRate += countryPrice;
                childRate += countryPrice;

                if (isValidName(name) && isValidEmail(email) && isValidHotelName(hName) && isValidDate(travelDate) && isValidPersons(noAdultTicket, noChildTicket, noInfantTicket)) {
                    travelDate = selectedDate.getText().toString();
                    try {
                        travelDate = convertDateFormat_FrontToBack(travelDate);
//                        Log.d("Travel_Date", travelDate);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    if(subPackagesData.get(SelectedIndex).getPackageTypeName().equals("HT"))
                    {
                        if (vehicleType.getSelectedItem().toString().equalsIgnoreCase("car")){
                            total = subPackagesData.get(SelectedIndex).getCarRate()*noVehicle;
//                            Log.d("Total Amount:", String.valueOf(total));
                        }
                        if (vehicleType.getSelectedItem().toString().equalsIgnoreCase("innova")){
                            total = subPackagesData.get(SelectedIndex).getInnovaRate()*noVehicle;
//                            Log.d("Total Amount:", String.valueOf(total));
                        }
                        if (vehicleType.getSelectedItem().toString().equalsIgnoreCase("van")){
                            total = subPackagesData.get(SelectedIndex).getVanRate()*noVehicle;
//                            Log.d("Total Amount:", String.valueOf(total));
                        }

//                        Log.d("vehicle type",vehicleType.getSelectedItem().toString());
                        //total = subPackagesData.get(SelectedIndex).getCategoryRate();
//                        Log.d("Total Amount:", String.valueOf(total));
                        appDepartTime=approxDepartureTime.getText().toString();
                        if (isValidPackage(adultRate,total) && isValidName(appDepartTime) && isValidNoVehicle(noVehicle) && isValidVehicle(totalPerson,vehicleType.getSelectedItem().toString(),noVehicle)){
                           addToCart(flagId, subPackageId, userId, noChildTicket, noAdultTicket, noInfantTicket, travelDate, total, name, email, phone, hName, hAddress,appDepartTime,null,null,vehicleType.getSelectedItem().toString(), String.valueOf(noVehicle),null);
                        }
                        else {
                            if (!isValidVehicle(totalPerson,vehicleType.getSelectedItem().toString(),noVehicle)){
                                Snackbar.make(getActivity().findViewById(R.id.frag_add_cart), "Please enter valid number of vehicles or choose another vehicle", Snackbar.LENGTH_LONG)
                                        .show();
                            }
                            if (!isValidNoVehicle(noVehicle)){
                                Snackbar.make(getActivity().findViewById(R.id.frag_add_cart), "Please enter valid number of vehicles", Snackbar.LENGTH_LONG)
                                        .show();
                            }
                            if (!isValidPackage(adultRate, total)) {
                                Snackbar.make(getActivity().findViewById(R.id.frag_add_cart), "Please select package", Snackbar.LENGTH_LONG)
                                        .show();
                            }
                            if (!isValidName(appDepartTime)) {
                                Snackbar.make(getActivity().findViewById(R.id.frag_add_cart), "Please enter approx departure time", Snackbar.LENGTH_LONG)
                                        .show();
                            }
                        }
//                        Log.d("Time:", String.valueOf(appDepartTime));
                    }
//                    if(subPackagesData.get(SelectedIndex).getCategoryName().toLowerCase().startsWith("hotel")){
//                        total = subPackagesData.get(SelectedIndex).getCategoryRate();
//                        Log.d("Total Amount:", String.valueOf(total));
//                        appDepartTime=approxDepartureTime.getText().toString();
//                        if (isValidName(appDepartTime)){
//                            addToCart(flagId, subPackageId, userId, noChildTicket, noAdultTicket, noInfantTicket, travelDate, total, name, email, phone, hName, hAddress,appDepartTime,null,null);
//                        }
//                        else {
//                            if (!isValidName(appDepartTime)) {
//                                Snackbar.make(getActivity().findViewById(R.id.frag_add_cart), "Please enter approx departure time", Snackbar.LENGTH_LONG)
//                                        .show();
//                            }
//                        }
//                        Log.d("Time:", String.valueOf(appDepartTime));
//                        //addToCart(flagId, subPackageId, userId, noChildTicket, noAdultTicket, noInfantTicket, travelDate, total, name, email, phone, hName, hAddress);
//                    }
                    else if (subPackagesData.get(SelectedIndex).getPackageTypeName().equals("AT")){

                        totalPerson=noAdultTicket+noChildTicket+noInfantTicket;

                        if (vehicleType.getSelectedItem().toString().equalsIgnoreCase("car")){
                            total = subPackagesData.get(SelectedIndex).getCarRate()*noVehicle;
//                            Log.d("Total Amount:", String.valueOf(total));
                        }
                        if (vehicleType.getSelectedItem().toString().equalsIgnoreCase("innova")){
                            total = subPackagesData.get(SelectedIndex).getInnovaRate()*noVehicle;
//                            Log.d("Total Amount:", String.valueOf(total));
                        }
                        if (vehicleType.getSelectedItem().toString().equalsIgnoreCase("van")){
                            total = subPackagesData.get(SelectedIndex).getVanRate()*noVehicle;
//                            Log.d("Total Amount:", String.valueOf(total));
                        }
//                        Log.d("vehicle type",vehicleType.getSelectedItem().toString());
//                        total = subPackagesData.get(SelectedIndex).getCategoryRate();
//                        Log.d("Total Amount:", String.valueOf(total));
                        flightNo = flightNumber.getText().toString();
                        flightArriveTime = flightArrivalTime.getText().toString();

                        if (isValidPackage(adultRate,total) && isValidName(flightNo) && isValidHotelName(flightArriveTime) && isValidNoVehicle(noVehicle) && isValidVehicle(totalPerson,vehicleType.getSelectedItem().toString(),noVehicle)) {
                            addToCart(flagId, subPackageId, userId, noChildTicket, noAdultTicket, noInfantTicket, travelDate, total, name, email, phone, hName, hAddress,null,flightNo,flightArriveTime,vehicleType.getSelectedItem().toString(), String.valueOf(noVehicle),null);
                        } else {
                            if (!isValidVehicle(totalPerson,vehicleType.getSelectedItem().toString(),noVehicle)){
                                Snackbar.make(getActivity().findViewById(R.id.frag_add_cart), "Please enter valid number of vehicles or choose another vehicle", Snackbar.LENGTH_LONG)
                                        .show();
                            }
                            if (!isValidNoVehicle(noVehicle)){
                                Snackbar.make(getActivity().findViewById(R.id.frag_add_cart), "Please enter valid number of vehicles", Snackbar.LENGTH_LONG)
                                        .show();
                            }
                            if (!isValidPackage(adultRate, total)) {
                                Snackbar.make(getActivity().findViewById(R.id.frag_add_cart), "Please select package", Snackbar.LENGTH_LONG)
                                        .show();
                            }
                            if (!isValidName(flightNo)) {
                                Snackbar.make(getActivity().findViewById(R.id.frag_add_cart), "Please enter approx departure time", Snackbar.LENGTH_LONG)
                                        .show();
                            }
                            if (!isValidHotelName(flightArriveTime)) {
                                Snackbar.make(getActivity().findViewById(R.id.frag_add_cart), "Please enter valid flight arrival time", Snackbar.LENGTH_LONG)
                                        .show();
                            }
                        }
                    }
//                    else if(subPackagesData.get(SelectedIndex).getCategoryName().toLowerCase().endsWith("hotel")){
//                        total = subPackagesData.get(SelectedIndex).getCategoryRate();
//                        Log.d("Total Amount:", String.valueOf(total));
//                        flightNo = flightNumber.getText().toString();
//                        flightArriveTime = flightArrivalTime.getText().toString();
//                        if (isValidName(flightNo) && isValidHotelName(flightArriveTime)) {
//                            addToCart(flagId, subPackageId, userId, noChildTicket, noAdultTicket, noInfantTicket, travelDate, total, name, email, phone, hName, hAddress,null,flightNo,flightArriveTime);
//                        } else {
//                            if (!isValidName(flightNo)) {
//                                Snackbar.make(getActivity().findViewById(R.id.frag_add_cart), "Please enter approx departure time", Snackbar.LENGTH_LONG)
//                                        .show();
//                            }
//                            if (!isValidHotelName(flightArriveTime)) {
//                                Snackbar.make(getActivity().findViewById(R.id.frag_add_cart), "Please enter valid flight arrival time", Snackbar.LENGTH_LONG)
//                                        .show();
//                            }
//                        }
//                        //addToCart(flagId, subPackageId, userId, noChildTicket, noAdultTicket, noInfantTicket, travelDate, total, name, email, phone, hName, hAddress);
//                    }
                    else {

                        if (subPackagesData.get(SelectedIndex).getPackageTypeName().equals("T")){

                            totalPerson=noAdultTicket+noChildTicket+noInfantTicket;

                            if (vehicleType.getSelectedItem().toString().equalsIgnoreCase("car")){
                                total = subPackagesData.get(SelectedIndex).getCarRate()*noVehicle;
//                                Log.d("Total Amount:", String.valueOf(total));
                            }
                            if (vehicleType.getSelectedItem().toString().equalsIgnoreCase("innova")){
                                total = subPackagesData.get(SelectedIndex).getInnovaRate()*noVehicle;
//                                Log.d("Total Amount:", String.valueOf(total));
                            }
                            if (vehicleType.getSelectedItem().toString().equalsIgnoreCase("van")){
                                total = subPackagesData.get(SelectedIndex).getVanRate()*noVehicle;
//                                Log.d("Total Amount:", String.valueOf(total));
                            }
//                            total = subPackagesData.get(SelectedIndex).getCategoryRate();

//                            Log.d("vehicle type",vehicleType.getSelectedItem().toString());
                            //Log.d("Total Amount:", String.valueOf(total));
                            if (isValidPackage(adultRate,total) && isValidHotelName(travelPickupTime) && isValidNoVehicle(noVehicle) && isValidVehicle(totalPerson,vehicleType.getSelectedItem().toString(),noVehicle)){
                                addToCart(flagId, subPackageId, userId, noChildTicket, noAdultTicket, noInfantTicket, travelDate, total, name, email, phone, hName, hAddress,null,null,null,vehicleType.getSelectedItem().toString(), String.valueOf(noVehicle),travelPickupTime);
                            } else {
                                if (!isValidVehicle(totalPerson,vehicleType.getSelectedItem().toString(),noVehicle)){
                                    Snackbar.make(getActivity().findViewById(R.id.frag_add_cart), "Please enter valid number of vehicles or choose another vehicle", Snackbar.LENGTH_LONG)
                                            .show();
                                }
                                if (!isValidNoVehicle(noVehicle)){
                                    Snackbar.make(getActivity().findViewById(R.id.frag_add_cart), "Please enter valid number of vehicles", Snackbar.LENGTH_LONG)
                                            .show();
                                }
                                if (!isValidHotelName(travelPickupTime)){
                                    Snackbar.make(getActivity().findViewById(R.id.frag_add_cart), "Please select pickup time", Snackbar.LENGTH_LONG)
                                            .show();
                                }
                                if (!isValidPackage(adultRate, total)) {
                                    Snackbar.make(getActivity().findViewById(R.id.frag_add_cart), "Please select package", Snackbar.LENGTH_LONG)
                                            .show();
                                }

                            }
                        }

//                        if(subPackagesData.get(SelectedIndex).getCategoryName().toLowerCase().contains("Pattaya to Bangkok") || subPackagesData.get(SelectedIndex).getCategoryName().toLowerCase().contains("Bangkok to Pattaya")){
//                            total = subPackagesData.get(SelectedIndex).getCategoryRate();
//                            Log.d("Total Amount:", String.valueOf(total));
//                            addToCart(flagId, subPackageId, userId, noChildTicket, noAdultTicket, noInfantTicket, travelDate, total, name, email, phone, hName, hAddress,null,null,null);
//                        }
                        else {
                            total = (noAdultTicket * adultRate) + (noChildTicket * childRate) + (noInfantTicket * infantRate);
//                            Log.d("Total Amount:", String.valueOf(total));
                            if(isValidPackage(adultRate, childRate)) {
                                addToCart(flagId, subPackageId, userId, noChildTicket, noAdultTicket, noInfantTicket, travelDate, total, name, email, phone, hName, hAddress,null,null,null,null,null,null);
                            } else {
                                if (!isValidPackage(adultRate, childRate)) {
                                    Snackbar.make(getActivity().findViewById(R.id.frag_add_cart), "Please select package", Snackbar.LENGTH_LONG)
                                            .show();
                                }
                            }

                        }
//                        total = (noAdultTicket * adultRate) + (noChildTicket * childRate) + (noInfantTicket * infantRate);
//                        Log.d("Total Amount:", String.valueOf(total));
//                        addToCart(flagId, subPackageId, userId, noChildTicket, noAdultTicket, noInfantTicket, travelDate, total, name, email, phone, hName, hAddress,null,null,null);
                    }
//                    Snackbar.make(getActivity().findViewById(R.id.frag_add_cart), "Total: " + String.valueOf(total), Snackbar.LENGTH_LONG)
//                            .show();
                } else {

                    if (!isValidHotelName(hName)) {
                        Snackbar.make(getActivity().findViewById(R.id.frag_add_cart), "Please enter valid hotel name", Snackbar.LENGTH_LONG)
                                .show();
                    }

                    if (!isValidEmail(email)) {
                        Snackbar.make(getActivity().findViewById(R.id.frag_add_cart), "Please enter valid email", Snackbar.LENGTH_LONG)
                                .show();
                    }

                    if (!isValidName(name)) {
                        Snackbar.make(getActivity().findViewById(R.id.frag_add_cart), "Please select valid name", Snackbar.LENGTH_LONG)
                                .show();
                    }

                    if (!isValidDate(travelDate)) {
                        Snackbar.make(getActivity().findViewById(R.id.frag_add_cart), "Please select valid travel date", Snackbar.LENGTH_LONG)
                                .show();
                    }

                    if (!isValidPersons(noAdultTicket, noChildTicket, noInfantTicket)) {
                        Snackbar.make(getActivity().findViewById(R.id.frag_add_cart), "Please select valid number of persons", Snackbar.LENGTH_LONG)
                                .show();
                    }

//                        Snackbar.make(getActivity().findViewById(R.id.frag_add_cart), "Please select valid options", Snackbar.LENGTH_LONG)
//                                .show();
                }
            }



        });

        adultMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                decreaseAdultValue();
                adultValue.setText(String.valueOf(noAdultTicket));
                totalPerson = noAdultTicket+noChildTicket+noInfantTicket;
//                if (totalPerson>0 && totalPerson<=3){
//                    vehicleType.setText("Car");
//                }
//                if (totalPerson>3 && totalPerson<=6) {
//                    vehicleType.setText("Innova");
//                }
//                if (totalPerson>6 && totalPerson<=10) {
//                    vehicleType.setText("Van");
//                }
            }
        });

        adultPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                increaseAdultValue();
                adultValue.setText(String.valueOf(noAdultTicket));
                totalPerson = noAdultTicket+noChildTicket+noInfantTicket;
//                if (totalPerson>0 && totalPerson<=3){
//                    vehicleType.setText("Car");
//                }
//                if (totalPerson>3 && totalPerson<=4) {
//                    vehicleType.setText("Innova");
//                }
//                if (totalPerson>4 && totalPerson<=10) {
//                    vehicleType.setText("Van");
//                }
            }
        });

        childMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                decreaseChildValue();
                childValue.setText(String.valueOf(noChildTicket));
                totalPerson = noAdultTicket+noChildTicket+noInfantTicket;
//                if (totalPerson>0 && totalPerson<=3){
//                    vehicleType.setText("Car");
//                }
//                if (totalPerson>3 && totalPerson<=6) {
//                    vehicleType.setText("Innova");
//                }
//                if (totalPerson>6 && totalPerson<=10) {
//                    vehicleType.setText("Van");
//                }
            }
        });

        childPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                increaseChildValue();
                childValue.setText(String.valueOf(noChildTicket));
                totalPerson = noAdultTicket+noChildTicket+noInfantTicket;
//                if (totalPerson>0 && totalPerson<=3){
//                    vehicleType.setText("Car");
//                }
//                if (totalPerson>3 && totalPerson<=6) {
//                    vehicleType.setText("Innova");
//                }
//                if (totalPerson>6 && totalPerson<=10) {
//                    vehicleType.setText("Van");
//                }
            }
        });

        infantMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                decreaseInfantValue();
                infantValue.setText(String.valueOf(noInfantTicket));
                totalPerson = noAdultTicket+noChildTicket+noInfantTicket;
//                if (totalPerson>0 && totalPerson<=3){
//                    vehicleType.setText("Car");
//                }
//                if (totalPerson>3 && totalPerson<=6) {
//                    vehicleType.setText("Innova");
//                }
//                if (totalPerson>6 && totalPerson<=10) {
//                    vehicleType.setText("Van");
//                }
            }
        });

        infantPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                increaseInfantValue();
                infantValue.setText(String.valueOf(noInfantTicket));
                totalPerson = noAdultTicket+noChildTicket+noInfantTicket;
//                if (totalPerson>0 && totalPerson<=3){
//                    vehicleType.setText("Car");
//                }
//                if (totalPerson>3 && totalPerson<=6) {
//                    vehicleType.setText("Innova");
//                }
//                if (totalPerson>6 && totalPerson<=10) {
//                    vehicleType.setText("Van");
//                }
            }
        });
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupSubPackageRecycler();
    }

    private void setupSubPackageRecycler() {
        linearLayoutManager = new MyLinearLayoutManager(getContext());
        packageRecycler.setLayoutManager(linearLayoutManager);

//        packageRecycler.setAdapter(new CuisineAdapter(getContext(), new CuisineAdapter.CuisineListToggleListener() {
//            @Override
//            public void OnListExpanded(final boolean selected) {
//                if (selected) {
//                    packageRecycler.scrollToPosition(0);
//                }
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        linearLayoutManager.setScrollEnabled(!selected);
//                    }
//                }, 500);
//            }
//        }));
    }

    private void initializecontrols(View view) {
        addToCartcountry = (Spinner) view.findViewById(R.id.addtoCartCountry);
        adultValue = (TextView) view.findViewById(R.id.tvAdult);
        childValue = (TextView) view.findViewById(R.id.tvChild);
        infantValue = (TextView) view.findViewById(R.id.tvInfant);
        adultPlus = (ImageView) view.findViewById(R.id.adultPlus);
        adultMinus = (ImageView) view.findViewById(R.id.adultMinus);
        childPlus = (ImageView) view.findViewById(R.id.childPlus);
        childMinus = (ImageView) view.findViewById(R.id.childMinus);
        infantPlus = (ImageView) view.findViewById(R.id.infantPlus);
        infantMinus = (ImageView) view.findViewById(R.id.infantMinus);
        datePicker = (TextView) view.findViewById(R.id.date_picker);
        selectedDate = (TextView) view.findViewById(R.id.tvDate);
        addToCart = (TextView) view.findViewById(R.id.addtoCart);
        custName = (EditText) view.findViewById(R.id.customerName);
        custEmail = (EditText) view.findViewById(R.id.customerEmail);
        custPhone = (EditText) view.findViewById(R.id.custmerPhone);
        hotelName = (EditText) view.findViewById(R.id.hotelName);
        hoteShortAddress = (EditText) view.findViewById(R.id.hotelShortAddress);
        flightNumber = (EditText) view.findViewById(R.id.flightNumber);
        flightArrivalTime = (EditText) view.findViewById(R.id.flightArrivalTime);
        approxDepartureTime = (EditText) view.findViewById(R.id.flightDepartureApprox);
        flightNumberLayout = (TextInputLayout) view.findViewById(R.id.flightNumberLayout);
        flightArrivalLayout = (TextInputLayout) view.findViewById(R.id.flightArrivalLayout);
        flightDepartureLayout = (TextInputLayout) view.findViewById(R.id.flightDeparturelayout);
        transferLayout = (LinearLayout) view.findViewById(R.id.transferLayout);
        vehicleType = (Spinner) view.findViewById(R.id.vehicleType);
        custPickupTimelayout = (LinearLayout) view.findViewById(R.id.pickupTimeLayout);
        custPickupTime = (TextView) view.findViewById(R.id.custPickupTime);
        pickupTime = (TextView) view.findViewById(R.id.tvCustPickupTime);
        packageRecycler = view.findViewById(R.id.packageRecycler);
        sessionManeger = new SessionManager(getContext());
        pDialog = new ProgressDialog(getContext(),R.style.DialogBox);
        pDialog.setCancelable(false);
        userType = getActivity().getIntent().getStringExtra(EXTRA_USER_TYPE);
        userId = sessionManeger.getKeyUserId();

        noOfVehicle = (EditText) view.findViewById(R.id.noOfVehicle);
        noOfVehicle.setText("1");
        vehicleList = new ArrayList<String>();
        vehicleList.add("Car");
        vehicleList.add("Innova");
        vehicleList.add("Van");
        vehicleTypeAdapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_item,vehicleList);
        vehicleTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        vehicleType.setAdapter(vehicleTypeAdapter);
//        if(getActivity().getIntent().getStringExtra(EXTRA_PACKAGE_NAME).toLowerCase().contains("airport")) {
//            flightNumber.setVisibility(View.VISIBLE);
//            flightArrivalTime.setVisibility(View.VISIBLE);
//            approxDepartureTime.setVisibility(View.VISIBLE);
//        } else {
//            flightNumber.setVisibility(View.GONE);
//            flightArrivalTime.setVisibility(View.GONE);
//            approxDepartureTime.setVisibility(View.GONE);
//        }

        if (getActivity().getIntent().getBooleanExtra(EXTRA_HAS_SUB_PACKAGE, false)) {
            pkgId = getActivity().getIntent().getIntExtra(EXTRA_PACKAGE_ID, 0);
            getSubPackages(pkgId, userType);

        } else {
            packageRecycler.setVisibility(View.GONE);
            if (userType.equals("1") || userType.equals("2")) {
                adultRate = getActivity().getIntent().getIntExtra(EXTRA_PACKAGE_AGENT_ADULT_RATE, 0);
                childRate = getActivity().getIntent().getIntExtra(EXTRA_PACKAGE_AGENT_CHILD_RATE, 0);
                infantRate = getActivity().getIntent().getIntExtra(EXTRA_PACKAGE_AGENT_INFANT_RATE, 0);
            } else {
                adultRate = getActivity().getIntent().getIntExtra(EXTRA_PACKAGE_CUSTOMER_ADULT_RATE, 0);
                childRate = getActivity().getIntent().getIntExtra(EXTRA_PACKAGE_CUSTOMER_CHILD_RATE, 0);
                infantRate = getActivity().getIntent().getIntExtra(EXTRA_PACKAGE_CUSTOMER_INFANT_RATE, 0);
            }
        }
    }

    public void increaseAdultValue() {
        noAdultTicket = noAdultTicket + 1;

    }

    public void increaseChildValue() {
        noChildTicket = noChildTicket + 1;

    }

    public void decreaseChildValue() {
        if (noChildTicket > 0) {
            noChildTicket = noChildTicket - 1;
        }
    }

    public void decreaseAdultValue() {
        if (noAdultTicket > 0) {
            noAdultTicket = noAdultTicket - 1;
        }
    }

    public void increaseInfantValue() {
        noInfantTicket = noInfantTicket + 1;
    }

    public void decreaseInfantValue() {
        if (noInfantTicket > 0) {
            noInfantTicket = noInfantTicket - 1;
        }
    }

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    private void getSubPackages(final int packageId, final String userType) {
        boolean isConnected = ConnectivityReceiver.isConnected();

        if (!isConnected) {
            Snackbar.make(getActivity().findViewById(R.id.frag_add_cart), R.string.no_internet, Snackbar.LENGTH_LONG)
                    .show();
        } else {
            ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
            pDialog.setMessage("Getting sub packages...");
            pDialog.setCancelable(false);
            showDialog();

            Call<PackageCategoryResponse> call = apiInterface.getPackageCategoryDetails(new PackageRequest(packageId, Integer.parseInt(userType)));

            call.enqueue(new Callback<PackageCategoryResponse>() {
                @Override
                public void onResponse(Call<PackageCategoryResponse> call, Response<PackageCategoryResponse> response) {
                    try {
//                        Log.d("Response", String.valueOf(response.code()));

                        if (response.isSuccessful()) {
//                            Log.d("Status", String.valueOf(response.body().getStatusCode()));

                            if (response.body().getStatusCode() == 1) {
                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    public void run() {
                                        hideDialog();
                                    }
                                }, 1000);

                                subPackagesData = response.body().getPackageCategoryData();
//                                Log.d("package count", String.valueOf(subPackagesData.size()));

                                //set adapater
                                subPackageAdapter = new SubPackageAdapter(getContext(), subPackagesData);

                                packageRecycler.setAdapter(subPackageAdapter);
                                subPackageAdapter.notifyDataSetChanged();

                                subPackageAdapter.SetOnItemClickListener(new SubPackageAdapter.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(View view, int position) {
//                                        RecyclerView.ViewHolder v = packageRecycler.getChildViewHolder(packageRecycler.getChildAt(position));
//                                        ImageView iv = v.itemView.findViewById(R.id.subPackageAction);
//                                        TextView rate = v.itemView.findViewById(R.id.subPackageRate);

//                                        if(position==SelectedIndex){
//                                            ImageView image = packageRecycler.getChildAt(position).findViewById(R.id.subPackageImage);
//                                            image.setImageResource(R.drawable.ic_add_circle_primary_24dp);
//                                            SelectedIndex=0;
//                                        }
//                                        else {
                                            for (int i = 0; i < packageRecycler.getChildCount(); i++) {
                                                if (position == i) {
                                                    ImageView image= packageRecycler.getChildAt(i).findViewById(R.id.subPackageAction);
                                                    image.setImageResource(R.drawable.ic_done_primary_24dp);

                                                    if (subPackagesData.get(i).getPackageTypeName().equals("HT")){
                                                        approxDepartureTime.setVisibility(View.VISIBLE);
                                                        flightDepartureLayout.setVisibility(View.VISIBLE);
                                                        flightNumber.setVisibility(View.GONE);
                                                        flightNumberLayout.setVisibility(View.GONE);
                                                        flightArrivalTime.setVisibility(View.GONE);
                                                        flightArrivalLayout.setVisibility(View.GONE);
                                                        transferLayout.setVisibility(View.VISIBLE);
                                                    }
                                                    else if (subPackagesData.get(i).getPackageTypeName().equals("AT")){
                                                        flightNumber.setVisibility(View.VISIBLE);
                                                        flightNumberLayout.setVisibility(View.VISIBLE);
                                                        flightArrivalTime.setVisibility(View.VISIBLE);
                                                        flightArrivalLayout.setVisibility(View.VISIBLE);
                                                        approxDepartureTime.setVisibility(View.GONE);
                                                        flightDepartureLayout.setVisibility(View.GONE);
                                                        transferLayout.setVisibility(View.VISIBLE);
                                                    }
                                                    else if (subPackagesData.get(i).getPackageTypeName().equals("T")) {
                                                        approxDepartureTime.setVisibility(View.GONE);
                                                        flightDepartureLayout.setVisibility(View.GONE);
                                                        flightNumber.setVisibility(View.GONE);
                                                        flightNumberLayout.setVisibility(View.GONE);
                                                        flightArrivalTime.setVisibility(View.GONE);
                                                        flightArrivalLayout.setVisibility(View.GONE);
                                                        transferLayout.setVisibility(View.VISIBLE);
                                                        custPickupTimelayout.setVisibility(View.VISIBLE);
                                                    }

//                                                    if(subPackagesData.get(i).getCategoryName().toLowerCase().startsWith("hotel")){
//                                                        approxDepartureTime.setVisibility(View.VISIBLE);
//                                                        flightNumber.setVisibility(View.GONE);
//                                                        flightArrivalTime.setVisibility(View.GONE);
//                                                    }
//
//                                                    else if( subPackagesData.get(i).getCategoryName().toLowerCase().endsWith("hotel")){
//                                                        flightNumber.setVisibility(View.VISIBLE);
//                                                        flightArrivalTime.setVisibility(View.VISIBLE);
//                                                        approxDepartureTime.setVisibility(View.GONE);
//                                                    }

                                                    subPackageId = subPackagesData.get(i).getCategorySubPackageID();
                                                    if (userType.equals("1") || userType.equals("2")) {
                                                        adultRate = subPackagesData.get(i).getAgentCategoryAdultRate();
                                                        childRate = subPackagesData.get(i).getAgentCategoryChildRate();
                                                        infantRate = subPackagesData.get(i).getAgentCategoryInfantRate();
//                                                        Log.d("adult", String.valueOf(adultRate));
//                                                        Log.d("child", String.valueOf(childRate));
//                                                        Log.d("infant", String.valueOf(infantRate));
                                                    } else {
                                                        adultRate = subPackagesData.get(i).getCustCategoryAdultRate();
                                                        childRate = subPackagesData.get(i).getCustCategoryChildRate();
                                                        infantRate = subPackagesData.get(i).getCustCategoryInfantRate();
//                                                        Log.d("adult", String.valueOf(adultRate));
//                                                        Log.d("child", String.valueOf(childRate));
//                                                        Log.d("infant", String.valueOf(infantRate));
                                                    }
//                                                    Log.d("Package name", subPackagesData.get(position).getCategoryName().toString());
//                                                    Log.d("cityname", cityName.toString());
                                                    SelectedIndex = i;

//                                                    Log.d("SelectedIndex", String.valueOf(SelectedIndex));
                                                } else {
                                                    ImageView image = packageRecycler.getChildAt(i).findViewById(R.id.subPackageAction);
                                                    image.setImageResource(R.drawable.ic_add_circle_primary_24dp);
                                                }
//                                            }
                                            }

//                                        if (flag == 0) {
//                                            flag++;
//
//                                            iv.setImageResource(R.drawable.ic_done_primary_24dp);
//                                            subPackageId = subPackagesData.get(position).getSrNo();
//
//                                            if (userType.equals("1")) {
//                                                adultRate = subPackagesData.get(position).getAgentCategoryAdultRate();
//                                                childRate = subPackagesData.get(position).getAgentCategoryChildRate();
//                                                infantRate = subPackagesData.get(position).getAgentCategoryInfantRate();
//                                            } else {
//                                                adultRate = subPackagesData.get(position).getCustCategoryAdultRate();
//                                                childRate = subPackagesData.get(position).getCustCategoryChildRate();
//                                                infantRate = subPackagesData.get(position).getCustCategoryInfantRate();
//                                            }
//                                        } else if (flag == 1) {
//                                            flag--;
//                                            iv.setImageResource(R.drawable.ic_add_circle_primary_24dp);
//                                            adultRate = 0;
//                                            childRate = 0;
//                                            infantRate = 0;
//                                        }
//                                        Log.d("Rate",String.valueOf(adultRate));
//                                        Log.d("Action", "clicked");
                                    }
                                });


                            } else {
                                if (pDialog.isShowing())
                                    hideDialog();
                                Snackbar.make(getActivity().findViewById(R.id.frag_add_cart), response.body().getMessage(), Snackbar.LENGTH_LONG)
                                        .show();
                            }
                        } else {
                            if (pDialog.isShowing())
                                hideDialog();
                            Snackbar.make(getActivity().findViewById(R.id.frag_add_cart), response.message(), Snackbar.LENGTH_LONG)
                                    .show();
                        }
                    } catch (Exception e) {
                        if (pDialog.isShowing())
                            hideDialog();
                    }
                }

                @Override
                public void onFailure(Call<PackageCategoryResponse> call, Throwable t) {
                    if (pDialog.isShowing())
                        hideDialog();
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setTitle(getResources().getString(R.string.NetworkErrorTitle));
                    builder.setMessage(getResources().getString(R.string.NetworkErrorMsg));
                    builder.setPositiveButton(getResources().getString(R.string.NetworkErrorBtnTxt), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            getSubPackages(pkgId, userType);
                        }
                    });
                    builder.setCancelable(false);
                    builder.show();
                }
            });

        }


    }

    private void addToCart(final int flagId, final int subPackageId, final int userId, final int noChildTicket, final int noAdultTicket, final int noInfantTicket, final String travelDate, final int total, final String name, final String email, final String phone,final String hName, final String hAddress, final String appDepartTime,final String flightNo,final String flightArriveTime, final String vehicle,final String numberVehicle,final String travelPickupTime) {
        boolean isConnected = ConnectivityReceiver.isConnected();

        if (!isConnected) {
            Snackbar.make(getActivity().findViewById(R.id.frag_add_cart), R.string.no_internet, Snackbar.LENGTH_LONG)
                    .show();
        } else {
            ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

//            final int subpackage = subPackageId;
//            final int userid = userId;
//            final int noChilds = noChildTicket;
//            final int noAdults = noAdultTicket;
//            final int noInfants = noInfantTicket;
//            final String traveldate = travelDate;
//            final int totalAmount = total;

            dialog = Common.showProgressDialog(getContext());
            dialog.show();

            Call<AddCartResponse> call = apiInterface.addtoCart(new AddCartRequest(flagId, subPackageId, userId, noChildTicket, noAdultTicket, noInfantTicket, travelDate, total,name,email,phone,hName,hAddress,appDepartTime,flightNo,flightArriveTime,vehicle,numberVehicle,travelPickupTime));

            call.enqueue(new Callback<AddCartResponse>() {
                @Override
                public void onResponse(Call<AddCartResponse> call, Response<AddCartResponse> response) {
                    try {
                        if (response.isSuccessful()) {
                            if (response.body().getStatusCode() == 0) {
//                                Log.d("status", String.valueOf(response.body().getStatusCode()));
                                closeProgressDialog();
//                                Log.d("message", String.valueOf(response.body().getMessage()));
                                startActivity(new Intent(getContext(), CartActivity.class));
                                getActivity().finish();

                            } else {
                                if (dialog.isShowing()) {
                                    closeProgressDialog();
//                                    Log.d("response", response.body().getMessage());
                                    Snackbar.make(getActivity().findViewById(R.id.frag_add_cart), response.body().getMessage(), Snackbar.LENGTH_LONG)
                                            .show();
                                }
                            }
                        } else {
                            if (dialog.isShowing()) {
                                closeProgressDialog();
//                                Log.d("response", response.body().getMessage());
                                Snackbar.make(getActivity().findViewById(R.id.frag_add_cart), response.message(), Snackbar.LENGTH_LONG)
                                        .show();
                            }
                        }
                    } catch (Exception e) {
                        if (dialog.isShowing()) {
                            closeProgressDialog();
                        }
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<AddCartResponse> call, Throwable t) {
                    closeProgressDialog();
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setTitle(getResources().getString(R.string.NetworkErrorTitle));
                    builder.setMessage(getResources().getString(R.string.NetworkErrorMsg));
                    builder.setPositiveButton(getResources().getString(R.string.NetworkErrorBtnTxt), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            addToCart(flagId, subPackageId, userId, noChildTicket, noAdultTicket, noInfantTicket, travelDate, total,name,email,phone,hName,hAddress,appDepartTime,flightNo,flightArriveTime,vehicle,numberVehicle,travelPickupTime);
                        }
                    });
                    builder.setCancelable(false);
                    builder.show();
                }
            });

        }
    }

    private void getCountryList() {
        boolean isConnected = ConnectivityReceiver.isConnected();

        if (!isConnected) {
            Snackbar.make(getActivity().findViewById(R.id.frag_add_cart), R.string.no_internet, Snackbar.LENGTH_LONG)
                    .show();
        } else {
            ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

            pDialog.setMessage("Getting country list...");
            pDialog.setCancelable(false);
            showDialog();

            Call<CountryResponse> call = apiInterface.getCountry();
//            Log.d("url", "" + call.request().url().toString());

            call.enqueue(new Callback<CountryResponse>() {
                @Override
                public void onResponse(Call<CountryResponse> call, Response<CountryResponse> response) {
                    try {
//                        Log.d("Response", String.valueOf(response.code()));
                        if (response.isSuccessful()) {
//                            Log.d("Status", String.valueOf(response.body().getStatusCode()));

                            if (response.body().getStatusCode() == 1) {

                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    public void run() {
                                        hideDialog();
                                    }
                                }, 1000);

                                countryData = response.body().getCountryData();
//                                Log.d("country count", String.valueOf(countryData.size()));
                                countryAdapter = new CountryAdapter(getContext(), countryData);
                                addToCartcountry.setAdapter(countryAdapter);
                                countryAdapter.notifyDataSetChanged();
                            } else {
                                if (pDialog.isShowing())
                                    hideDialog();
                                Snackbar.make(getActivity().findViewById(R.id.frag_add_cart), response.body().getMessage(), Snackbar.LENGTH_LONG)
                                        .show();
                            }
                        } else {
                            if (pDialog.isShowing())
                                hideDialog();
                            Snackbar.make(getActivity().findViewById(R.id.frag_add_cart), response.message(), Snackbar.LENGTH_LONG)
                                    .show();
                        }
                    } catch (Exception e) {
                        if (pDialog.isShowing())
                            hideDialog();
                    }
                }

                @Override
                public void onFailure(Call<CountryResponse> call, Throwable t) {
                    if (pDialog.isShowing())
                        hideDialog();
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setTitle(getResources().getString(R.string.NetworkErrorTitle));
                    builder.setMessage(getResources().getString(R.string.NetworkErrorMsg));
                    builder.setPositiveButton(getResources().getString(R.string.NetworkErrorBtnTxt), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            getCountryList();
                        }
                    });
                    builder.setCancelable(false);
                    builder.show();
                }
            });
        }
    }

    private static void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

    private void checkConnection() {
        boolean isConnected = ConnectivityReceiver.isConnected();
        if (!isConnected) {
            Snackbar.make(getActivity().findViewById(R.id.frag_add_cart), R.string.no_internet, Snackbar.LENGTH_LONG)
                    .show();
        }
    }

    private boolean isValidEmail(String email) {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    private boolean isValidPhone(String phone) {
        if (phone != null && phone.length() > 3) {
            return true;
        }
        return false;
    }

    private boolean isValidName(String name) {
        if (name != null && name.length() > 3) {
            return true;
        }
        return false;
    }

    private boolean isValidHotelName(String hname) {
        if (hname != null && hname.length() > 3) {
            return true;
        }
        return false;
    }

    private boolean isValidPackage(int adultRate, int childRate) {
        if (adultRate != 0 && childRate != 0 && subPackageId != null) {
            return true;
        }
        return false;
    }

    private boolean isValidNoVehicle(int noVehicle) {
        if (noVehicle != 0) {
            return true;
        }
        return false;
    }

    private boolean isValidPersons(int noAdultTicket, int noChildTicket, int noInfantTicket) {
        if (noAdultTicket != 0 && noChildTicket != 0) {
            return true;
        }
        if (noAdultTicket != 0) {
            return true;
        }
        if (noInfantTicket != 0 && noAdultTicket == 0 && noChildTicket == 0) {
            return false;
        }
        return false;
    }

    private boolean isValidVehicle(int person,String vehicle,int numberVehicles){
        if(vehicle.equalsIgnoreCase("Car")){
            int maxcap = 3;
            int totalfit = maxcap * numberVehicles;
            if(person <= totalfit){
                return true;
            }
        }
        if(vehicle.equalsIgnoreCase("Innova")){
            int maxcap = 4;
            int totalfit = maxcap * numberVehicles;
            if(person <= totalfit){
                return true;
            }
        }
        if(vehicle.equalsIgnoreCase("Van")){
            int maxcap = 10;
            int totalfit = maxcap * numberVehicles;
            if(person <= totalfit){
                return true;
            }
        }
        return false;
    }

    private void closeProgressDialog() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    private boolean isValidDate(String selectedDate) {
        if (selectedDate != null) {
            return true;
        }
        return false;
    }

    @Override
    public void onResume() {
        super.onResume();
        MyApplication.getInstance().setConnectivityListener(this);
    }


    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        checkConnection();
    }
}
