package com.cspl.tourtravelapps.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cspl.tourtravelapps.fragment.AuthFragment;
import com.cspl.tourtravelapps.R;
import com.cspl.tourtravelapps.interactor.AuthMainInteractor;
import com.cspl.tourtravelapps.location.LocationActivity;

public class SplashActivity extends AppCompatActivity {
    private TextView splashMessage;
    private ImageView splashLogo;
    private CardView cardView;
    private FrameLayout frameLayout;

    private Handler mHandler;
    private final String FRAG_TAG_SIGN_IN_UP = "SIGN_IN";
    private ImageView background;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        splashLogo = findViewById(R.id.splashLogo);
        splashMessage = findViewById(R.id.splashMessage);
        cardView = findViewById(R.id.cardView);
        frameLayout = findViewById(R.id.frameLayout);
        background = findViewById(R.id.background);

        Glide.with(this).load(R.drawable.chef_logo).into(splashLogo);
        Glide.with(this).load(R.drawable.back1).into(background);

        setupAuthLayout();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                endSplash();
            }
        }, 1800);
    }

    private void setupAuthLayout() {
        mHandler = new Handler();
        loadFragment(FRAG_TAG_SIGN_IN_UP);
    }

    private void loadFragment(final String fragTag) {
        Fragment fragment = null;
        switch (fragTag) {
            case FRAG_TAG_SIGN_IN_UP:
                fragment = AuthFragment.newInstance(new AuthMainInteractor() {
                    @Override
                    public void moveToMain() {
                        startActivity(new Intent(SplashActivity.this, LocationActivity.class));
                        finish();
                    }
                });
                break;
        }
        // Sometimes, when fragment has huge data, screen seems hanging
        // when switching between navigation menus
        // So using runnable, the fragment is loaded with cross fade effect
        // This effect can be seen in GMail app
        final Fragment finalFragment = fragment;
        Runnable mPendingRunnable = new Runnable() {
            @Override
            public void run() {
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                //fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
                fragmentTransaction.replace(R.id.splashFrame, finalFragment, fragTag);
                fragmentTransaction.commit();
            }
        };

        mHandler.post(mPendingRunnable);

    }

    private void endSplash() {
        splashLogo.animate().translationY(-1 * getResources().getDimension(R.dimen.splashLogoMarginTop)).setDuration(600).start();
        AlphaAnimation animation1 = new AlphaAnimation(1.0f, 0.0f);
        animation1.setDuration(600);
        animation1.setFillAfter(true);
        AlphaAnimation animation2 = new AlphaAnimation(0.0f, 1.0f);
        animation2.setDuration(600);
        animation2.setFillAfter(true);

        animation2.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                ViewGroup.LayoutParams params = frameLayout.getLayoutParams();

                params.height = ViewGroup.LayoutParams.MATCH_PARENT;
                frameLayout.setLayoutParams(params);
                splashMessage.setVisibility(View.GONE);
                cardView.setVisibility(View.VISIBLE);

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        splashMessage.startAnimation(animation1);
        //cardView.startAnimation(animation2);
        cardView.setVisibility(View.VISIBLE);
        Glide.with(SplashActivity.this).load(R.drawable.back3).into(background);
    }
}
