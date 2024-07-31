package com.cspl.tourtravelapps.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.cspl.tourtravelapps.R;
import com.cspl.tourtravelapps.model.PackageReview;

import java.util.List;

/**
 * Created by a_man on 24-01-2018.
 */

public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.MyViewHolder> {
    private Context context;
    private List<PackageReview> dataReview;

    public ReviewsAdapter(Context context,List<PackageReview> dataReview) {
        this.context = context;
        this.dataReview = dataReview;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_review, parent, false));
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.setData(dataReview.get(position));
    }

    @Override
    public int getItemCount() {
        return dataReview.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView reviewName,reviewText,reviewCount;
        private RatingBar reviewRating;

        public MyViewHolder(View itemView) {
            super(itemView);
            reviewName = itemView.findViewById(R.id.reviewName);
            reviewRating = itemView.findViewById(R.id.reviewRating);
            reviewCount = itemView.findViewById(R.id.reviewCount);
            reviewText = itemView.findViewById(R.id.reviewText);
        }

        public void setData(PackageReview packageReview) {
            reviewName.setText(packageReview.getName());
            reviewRating.setProgress(packageReview.getFeedbackCount());
            reviewCount.setText(String.valueOf(packageReview.getFeedbackCount()));
            reviewText.setText(packageReview.getMessage());
        }
    }
}
