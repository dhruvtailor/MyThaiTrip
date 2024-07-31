package com.cspl.tourtravelapps.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.cspl.tourtravelapps.R;

public class ReviewsActivity extends AppCompatActivity {
    private RecyclerView recyclerReviews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reviews);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.drawable.ic_keyboard_arrow_left_white_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        recyclerReviews = findViewById(R.id.recyclerReviews);
        setupReviewsRecycler();
    }

    private void setupReviewsRecycler() {
        recyclerReviews.setLayoutManager(new LinearLayoutManager(this));
    }
}
