package com.eventer.app;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;

import com.eventer.app.AboutPage.AboutActivity_PostLollipop;
import com.eventer.app.RecyclerEvent.AllEvents;
import com.eventer.app.RecyclerEvent.MyEvents;
import com.eventer.app.SignIn.SignInSignUp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    private static final String TAG ="authxxx" ;

    @BindView(R.id.containervp)
    ViewPager mViewPager;
    @BindView(R.id.tabs)
    TabLayout tabLayout;
    @BindView(R.id.toolbar)
    Toolbar mtoolbar;
    @BindView(R.id.displayAlls)
    CoordinatorLayout coordinatorLayout;
    private FragmentPagerAdapter mPagerAdapter;
    private final Map<String, Fragment> mFragments = new HashMap<>(2);
    private static final String TAG_DECK_FRAGMENT = "TAG_DECK_FRAGMENT";
    private static final String TAG_USER_FRAGMENT = "TAG_USER_FRAGMENT";
    private final Animation mAnimation = new AlphaAnimation(0, 1);{
        mAnimation.setDuration(200);
    }
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(mtoolbar);
        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {@Override
        public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
            FirebaseUser user = firebaseAuth.getCurrentUser();
            if (user != null) {
                Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
            } else {
                Log.d(TAG, "onAuthStateChanged:signed_out");
            }
        }
        };
        createTabs();




    }


//    @OnClick(R.id.user) public void onUserClicked() {
//        startActivity(new Intent(this, WriteActivity.class));
//        //Temp
//        //(TAG_USER_FRAGMENT);
////        mAuth.signOut();
////        startActivity(new Intent(this, SignInSignUp.class));
//    }

    private void createTabs() {
        mPagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            private final Fragment[] mFragments = new Fragment[] {
                    new AllEvents(),
                    new MyEvents()
            };
            private final String[] mFragmentNames = new String[] {
                    "All Events",
                    "My Events",
            };
            @Override
            public Fragment getItem(int position) {
                Log.d("GulXYZ",position+"");
                return mFragments[position];
            }
            @Override
            public int getCount() {
                return mFragments.length;
            }
            @Override
            public CharSequence getPageTitle(int position) {
                return mFragmentNames[position];
            }
        };
        // Set up the ViewPager with the sections adapter.
        mViewPager.setAdapter(mPagerAdapter);
        tabLayout.setupWithViewPager(mViewPager);
    }


    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);}
    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    @Override
    public void onBackPressed() {
        Snackbar snackbar = Snackbar
                .make(coordinatorLayout,"", Snackbar.LENGTH_LONG)
                .setAction("Exit", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                      finish();
                    }
                });
        snackbar.show();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_mainmenu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.logooutMenu:
                mAuth.signOut();
                startActivity(new Intent(this,SignInSignUp.class));
                finish();
                return true;
            case R.id.aboutPagemenu:
            if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP){
                startActivity(new Intent(this, AboutActivity_PostLollipop.class),
                        ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
            }
                else
            {

            }

                return true;
            default:
            return super.onOptionsItemSelected(item);
        }

    }
}