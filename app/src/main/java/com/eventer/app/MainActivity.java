package com.eventer.app;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
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

import com.eventer.app.AboutPage.AboutActivity;
import com.eventer.app.RecyclerEvent.AllEvents;
import com.eventer.app.RecyclerEvent.MyEvents;
import com.eventer.app.SignIn.SignInSignUp;
import com.eventer.app.model.Story;
import com.eventer.app.util.FirebaseUtils;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    private static final String TAG ="MainActivity" ;

    @BindView(R.id.containervp)
    ViewPager mViewPager;
    @BindView(R.id.tabs)
    TabLayout tabLayout;
    @BindView(R.id.toolbar)
    Toolbar mtoolbar;
    @BindView(R.id.displayAlls)
    CoordinatorLayout coordinatorLayout;
    private FragmentPagerAdapter mPagerAdapter;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Log.d("ActTransact","Oncreate");
        setSupportActionBar(mtoolbar);
        mAuth = FirebaseAuth.getInstance();

//        DatabaseReference mdatabaseRef= FirebaseUtils.getDatabase().getReference();
//
//        for(int i=1;i<=3;i++)
//        {
//            String key= mdatabaseRef.child("stories").push().getKey();
//            Story s=new Story("Story Name"+1,"https://firebasestorage.googleapis.com/v0/b/eventer-app-b1654.appspot.com/o/SourceCode%202017%2FRm-gn68K_400x400.jpg?alt=media&token=fbdc0a32-c130-4ae7-ac4a-f833fb1d9ba8","https://firebasestorage.googleapis.com/v0/b/eventer-app-b1654.appspot.com/o/SourceCode%202017%2FHAPPY%20CHRISTITES-PHARELL%20WILLIAMS%20(CHRIST%20UNIVERSITY%2CBANGALORE).3gp?alt=media&token=61f00eb7-2b03-4953-9c5f-46bacc99d74a",key,"BLAH BLAH"+i,"Gulzar",1459109);
//            mdatabaseRef.child("stories").child(key).setValue(s);
//        }

        createTabs();
    }

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
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.logooutMenu).setTitle("Logout ("+FirebaseAuth.getInstance().getCurrentUser().getDisplayName().toString()+")");
        return super.onPrepareOptionsMenu(menu);

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
               // showNotification("hhhhhhhhhhhhhhhhhhhhh");
                startActivity(new Intent(this, AboutActivity.class));
                return true;
            default:
            return super.onOptionsItemSelected(item);
        }

    }
}