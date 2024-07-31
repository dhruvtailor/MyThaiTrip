package com.cspl.tourtravelapps.rest_detail;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cspl.tourtravelapps.R;

public class InfoFragment extends Fragment {

    private static String EXTRA_PACKAGE_INFO="package_info";
    private TextView packageDesc;
    private String packageInfo;

    public InfoFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_info, container, false);

        packageDesc = (TextView) view.findViewById(R.id.packageDesc);
        packageInfo = getActivity().getIntent().getStringExtra(EXTRA_PACKAGE_INFO).toString();

        StringBuffer pkgInfo = new StringBuffer();

        String tempPackageInfo[] = packageInfo.split("[.]");

        for (int i = 0; i<tempPackageInfo.length ; i++) {
            pkgInfo.append("<text>&bull "+tempPackageInfo[i]+"</text><br><br>");
        }

        packageDesc.setText(Html.fromHtml(pkgInfo.toString()));
//        Log.d("Package Info: ",getActivity().getIntent().getStringExtra(EXTRA_PACKAGE_INFO).toString());
        return view;
    }

}
