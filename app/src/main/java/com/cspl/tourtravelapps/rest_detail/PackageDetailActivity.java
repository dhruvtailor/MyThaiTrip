package com.cspl.tourtravelapps.rest_detail;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.cspl.tourtravelapps.MyApplication;
import com.cspl.tourtravelapps.R;
import com.cspl.tourtravelapps.helper.SessionManager;
import com.cspl.tourtravelapps.model.PackageDatum;
import com.cspl.tourtravelapps.adapter.ViewPagerStateAdapter;
import com.cspl.tourtravelapps.receiver.ConnectivityReceiver;

import static com.cspl.tourtravelapps.helper.Common.IMG_PATH;

public class PackageDetailActivity extends AppCompatActivity implements ConnectivityReceiver.ConnectivityReceiverListener {

    private static String EXTRA_USER_TYPE="User_Type";
    private static String EXTRA_PACKAGE_ID="package_id";
    private static String EXTRA_PACKAGE_IMAGE="image_url";
    private static String EXTRA_PACKAGE_NAME="package_name";
    private static String EXTRA_PACKAGE_TIME="package_time";
    private static String EXTRA_PACKAGE_PRICE="package_price";
    private static String EXTRA_PACKAGE_PAYMENT_MODE="package_payment_mode";
    private static String EXTRA_HAS_SUB_PACKAGE="is_sub_pakage";
    private static String EXTRA_PACKAGE_INFO="package_info";
    private static String EXTRA_PACKAGE_AGENT_ADULT_RATE="agent_adult_rate";
    private static String EXTRA_PACKAGE_AGENT_CHILD_RATE="agent_child_rate";
    private static String EXTRA_PACKAGE_AGENT_INFANT_RATE="agent_infant_rate";
    private static String EXTRA_PACKAGE_CUSTOMER_ADULT_RATE="customer_adult_rate";
    private static String EXTRA_PACKAGE_CUSTOMER_CHILD_RATE="customer_child_rate";
    private static String EXTRA_PACKAGE_CUSTOMER_INFANT_RATE="customer_infant_rate";


    private TabLayout tabLayout;
    private ViewPager viewPager;
    private AppBarLayout appBarLayout;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private Toolbar toolbar;
    private String title;
    private ImageView packageImage;
    private TextView packageName;
    private TextView packageAvgTime;
    private TextView packagePrice;
    private TextView packagePaymentMode;
    private SessionManager sessionManager;
    private static String User_Type;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_detail);
        sessionManager = new SessionManager(this);
        User_Type = sessionManager.getKeyUserType();

        initUi();
        setupViewPager();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_rest_detail, menu);
        return super.onCreateOptionsMenu(menu);
    }

    private void setupViewPager() {
        ViewPagerStateAdapter adapter = new ViewPagerStateAdapter(getSupportFragmentManager());
        adapter.addFrag(new AddCartFragment(), "Add to cart");
        adapter.addFrag(new InfoFragment(), "Info");
        adapter.addFrag(new ReviewFragment(), "Review");
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(3);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void initUi() {


        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);
        appBarLayout = findViewById(R.id.appBarLayout);
        collapsingToolbarLayout = findViewById(R.id.collapsingToolbar);

        packageImage = findViewById(R.id.packageImg);
        packageName = findViewById(R.id.packageName);
        packageAvgTime = findViewById(R.id.package_avg_time);
        packagePrice = findViewById(R.id.package_price);
        packagePaymentMode = findViewById(R.id.package_payment_mode);

        Glide.with(this).load(IMG_PATH+getIntent().getStringExtra(EXTRA_PACKAGE_IMAGE)).into(packageImage);
        packageName.setText(getIntent().getStringExtra(EXTRA_PACKAGE_NAME).toString());
        packageAvgTime.setText(getIntent().getStringExtra(EXTRA_PACKAGE_TIME).toString());
        packagePrice.append(" "+String.valueOf(getIntent().getIntExtra(EXTRA_PACKAGE_PRICE,0)));
        packagePaymentMode.setText(getIntent().getStringExtra(EXTRA_PACKAGE_PAYMENT_MODE).toString());

//        Log.d("Img",getIntent().getStringExtra(EXTRA_PACKAGE_IMAGE));

//        ArrayList<SubPackageDatum> subPackage = getIntent().getParcelableArrayListExtra(EXTRA_SUB_PACKAGE);
//        Log.d("EXTRA",subPackage.get(0).getCategoryName());

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.drawable.ic_keyboard_arrow_left_white_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        collapsingToolbarLayout.setTitle(" ");

        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    //userDetailsSummaryContainer.setVisibility(View.INVISIBLE);
                    collapsingToolbarLayout.setTitle(getIntent().getStringExtra(EXTRA_PACKAGE_NAME).toString());
                    collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.MyCollapsingTitleTextAppearance);
                    isShow = true;
                } else if (isShow) {
                    //userDetailsSummaryContainer.setVisibility(View.VISIBLE);
                    collapsingToolbarLayout.setTitle(" ");
                    isShow = false;
                }
            }
        });
    }

    private void checkConnection() {
        boolean isInternetConnected = ConnectivityReceiver.isConnected();
        showView(isInternetConnected);
    }

    private void showView(boolean isInternetConnected) {
        if (!isInternetConnected) {
            Snackbar.make(findViewById(R.id.pkgDetail), R.string.no_internet, Snackbar.LENGTH_LONG)
                    .show();
        }
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

    public static Intent newIntent(Context context, PackageDatum packageDatum) {
        Intent intent = new Intent(context, PackageDetailActivity.class);
        intent.putExtra(EXTRA_PACKAGE_ID, packageDatum.getPackageID());
        intent.putExtra(EXTRA_PACKAGE_IMAGE, packageDatum.getPackageImg());
        intent.putExtra(EXTRA_PACKAGE_NAME, packageDatum.getPackageName());
        intent.putExtra(EXTRA_PACKAGE_TIME, packageDatum.getAverageTime());
        intent.putExtra(EXTRA_PACKAGE_PRICE, packageDatum.getPackageRate());
        intent.putExtra(EXTRA_PACKAGE_PAYMENT_MODE, packageDatum.getPaymentMode());
        intent.putExtra(EXTRA_PACKAGE_INFO,packageDatum.getPackageInfo());
        intent.putExtra(EXTRA_HAS_SUB_PACKAGE,packageDatum.getIsAddedSubCategory());
        User_Type = context.getSharedPreferences("Amz_Thi_Pref",0).getString(EXTRA_USER_TYPE,null);
        intent.putExtra(EXTRA_USER_TYPE,User_Type);
//        Log.d("User_Type",context.getSharedPreferences("Amz_Thi_Pref",0).getString(EXTRA_USER_TYPE,null));
        //SessionManager sessionManager = new SessionManager();
        //Log.d("type",sessionManager.getKeyUserType());
        if(!packageDatum.getIsAddedSubCategory()){
            if(User_Type.equals("1") || User_Type.equals("2")){
                intent.putExtra(EXTRA_PACKAGE_AGENT_ADULT_RATE, packageDatum.getAgentAdultRate());
                intent.putExtra(EXTRA_PACKAGE_AGENT_CHILD_RATE, packageDatum.getAgentChildRate());
                intent.putExtra(EXTRA_PACKAGE_AGENT_INFANT_RATE, packageDatum.getAgentInFrontRate());
            }
            else {
                intent.putExtra(EXTRA_PACKAGE_CUSTOMER_ADULT_RATE, packageDatum.getCustomerAdultRate());
                intent.putExtra(EXTRA_PACKAGE_CUSTOMER_CHILD_RATE, packageDatum.getCustomerChildRate());
                intent.putExtra(EXTRA_PACKAGE_CUSTOMER_INFANT_RATE, packageDatum.getCustomerInFrontRate());
            }
        }
//
//        if(User_Type.equals("1")){
//            intent.putExtra(EXTRA_PACKAGE_AGENT_ADULT_RATE, packageDatum.getAgentAdultRate());
//            intent.putExtra(EXTRA_PACKAGE_AGENT_CHILD_RATE, packageDatum.getAgentChildRate());
//            intent.putExtra(EXTRA_PACKAGE_AGENT_INFANT_RATE, packageDatum.getAgentInFrontRate());
//        }
//        else {
//            intent.putExtra(EXTRA_PACKAGE_CUSTOMER_ADULT_RATE, packageDatum.getCustomerAdultRate());
//            intent.putExtra(EXTRA_PACKAGE_CUSTOMER_CHILD_RATE, packageDatum.getCustomerChildRate());
//            intent.putExtra(EXTRA_PACKAGE_CUSTOMER_INFANT_RATE, packageDatum.getCustomerInFrontRate());
//        }
        return intent;
    }
}
