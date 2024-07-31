package com.cspl.tourtravelapps.activity;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.cspl.tourtravelapps.R;
import com.cspl.tourtravelapps.adapter.AdminOrderApadpter;

public class AdminMainActivity extends AppCompatActivity {

    private RecyclerView recyclerOrders;
    private AdminOrderApadpter ordersAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //toolbar.setNavigationIcon(R.drawable.ic_keyboard_arrow_left_white_24dp);
        /*toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });*/
        recyclerOrders = findViewById(R.id.recyclerOrders);
        setupReviewsRecycler();
    }

    private void setupReviewsRecycler() {
        recyclerOrders.setLayoutManager(new LinearLayoutManager(this));
        ordersAdapter = new AdminOrderApadpter(this);
        recyclerOrders.setAdapter(ordersAdapter);
        ordersAdapter.SetOnItemClickListener(new AdminOrderApadpter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                RecyclerView.ViewHolder v = recyclerOrders.getChildViewHolder(recyclerOrders.getChildAt(position));
                TextView orderStatus = v.itemView.findViewById(R.id.orderPkgStatus);
                if(orderStatus.getText().toString().equals("Driver Assigned")){
                    Snackbar.make(findViewById(R.id.admin_main),"Driver already assigned",Snackbar.LENGTH_LONG).show();
                }
                else{

                }
            }
        });
    }
}
