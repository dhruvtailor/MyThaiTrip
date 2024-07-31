package com.cspl.tourtravelapps.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cspl.tourtravelapps.R;
import com.cspl.tourtravelapps.helper.SessionManager;
import com.cspl.tourtravelapps.interactor.AuthInnerInteractor;

/**
 * Created by ANDROID-PC on 21/07/2018.
 */

public class UserTypeFragment extends Fragment {
    private AuthInnerInteractor innerInteractor;



    public UserTypeFragment() {
        // Required empty public constructor
    }

    public static UserTypeFragment newInstance(AuthInnerInteractor innerInteractor) {
        UserTypeFragment fragment = new UserTypeFragment();
        fragment.innerInteractor = innerInteractor;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_usertype, container, false);

        final SessionManager sessionManager = new SessionManager(getContext());

        view.findViewById(R.id.btnAgent).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sessionManager.setKeyUserType("1");
                innerInteractor.switchToSignIn();
            }
        });

        view.findViewById(R.id.btnCustomer).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sessionManager.setKeyUserType("3");
                innerInteractor.switchToSignIn();
            }
        });



//        view.findViewById(R.id.btnAdmin).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                sessionManager.setKeyUserType("3");
//                innerInteractor.switchToSignIn();
//            }
//        });

        return view;
    }
}
