package com.cspl.tourtravelapps.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.cspl.tourtravelapps.R;
import com.cspl.tourtravelapps.helper.SessionManager;
import com.cspl.tourtravelapps.model.PackageCategoryDatum;


import java.util.List;

import static com.cspl.tourtravelapps.helper.Common.IMG_PATH;

/**
 * Created by HP on 08/11/2018.
 */

public class SubPackageAdapter extends RecyclerView.Adapter<SubPackageAdapter.MyViewHolder> {

    private SessionManager sessionManager;
    private Context context;
    private List<PackageCategoryDatum> subPackagesData;
    private OnItemClickListener mItemClickListener;

    public SubPackageAdapter(Context context, List<PackageCategoryDatum> subPackagesData) {
        this.context = context;
        this.subPackagesData = subPackagesData;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_sub_package,parent,false));
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.setData(subPackagesData.get(position));
    }

    @Override
    public int getItemCount() {
        return subPackagesData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private final ImageView subPkgAction;
        private final TextView subPkgInfo;
        private TextView subPkgName, subPkgRate;
        private ImageView subPkgImg;

        public MyViewHolder(View itemView) {
            super(itemView);
            subPkgName = itemView.findViewById(R.id.subPackageName);
            subPkgInfo = itemView.findViewById(R.id.subPackageInfo);
            subPkgRate = itemView.findViewById(R.id.subPackageRate);
            subPkgAction = itemView.findViewById(R.id.subPackageAction);
            subPkgImg = itemView.findViewById(R.id.subPackageImage);
            subPkgAction.setOnClickListener(this);
            sessionManager = new SessionManager(context);
//            subPkgAction.setOnClickListener(this);

//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    //context.startActivity(PackageDetailActivity.newIntent(context, packagesData.get(getAdapterPosition())));
//                }
//            });
        }

        public void setData(PackageCategoryDatum subPackageDatum) {
            subPkgName.setText(subPackageDatum.getCategoryName());
            subPkgInfo.setText(subPackageDatum.getCategoryDescription());
//            if(sessionManager.getKeyUserType().equals("1")){
//                subPkgRate.setText("\u0E3F"+" "+String.valueOf(subPackageDatum.getAgentCategoryAdultRate()));
//            }
//            else {
//                subPkgRate.setText("\u0E3F"+" "+String.valueOf(subPackageDatum.getCustCategoryAdultRate()));
//            }
            if(subPackageDatum.getPackageTypeName().equals("N")){
                if(sessionManager.getKeyUserType().equals("1") || sessionManager.getKeyUserType().equals("2")){
                    subPkgRate.setText("\u0E3F"+" "+String.valueOf(subPackageDatum.getAgentCategoryAdultRate()));
                }
                else {
                    subPkgRate.setText("\u0E3F"+" "+String.valueOf(subPackageDatum.getCustCategoryAdultRate()));
                }
            } else {
                subPkgRate.setText(String.valueOf(subPackageDatum.getCarRate()));
            }

//            if(subPackageDatum.getCategoryName().toLowerCase().contains("safari")){
//                subPkgImg.setImageResource(R.drawable.rest_res_1);
//            }
//            else if(subPackageDatum.getCategoryName().toLowerCase().contains("coral")){
//                subPkgImg.setImageResource(R.drawable.rest_res_2);
//            }
//            else if(subPackageDatum.getCategoryName().toLowerCase().contains("chao")){
//                subPkgImg.setImageResource(R.drawable.charo_phraya);
//            }
//            else if(subPackageDatum.getCategoryName().toLowerCase().contains("old city")){
//                subPkgImg.setImageResource(R.drawable.bangkok_city_tour);
//            }
//            else if(subPackageDatum.getCategoryName().toLowerCase().contains("alcazar")){
//                subPkgImg.setImageResource(R.drawable.alcazar);
//            }
//            else if(subPackageDatum.getCategoryName().toLowerCase().contains("sriracha")){
//                subPkgImg.setImageResource(R.drawable.sriracha);
//            }
//            else if(subPackageDatum.getCategoryName().toLowerCase().contains("phi")){
//                subPkgImg.setImageResource(R.drawable.phi_phi);
//            }
            Glide.with(context).load(IMG_PATH+subPackageDatum.getPackageImg()).into(subPkgImg);
        }

        @Override
        public void onClick(View view) {
            if (mItemClickListener!=null)
            {
                mItemClickListener.onItemClick(view,getLayoutPosition());
            }
        }
    }

    public interface OnItemClickListener {
        public void onItemClick(View view, int position);
    }

    public void SetOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }
}
