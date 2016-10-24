package com.geteventer.eventer.SignIn;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.geteventer.eventer.R;
import com.pixelcan.inkpageindicator.InkPageIndicator;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Gulzar on 24-10-2016.
 */
public class SignInSignUp extends AppCompatActivity {
    @BindView(R.id.container) ViewPager mViewPager;
    @BindView(R.id.indicator) InkPageIndicator mindicator;
    @BindView(R.id.button_sign_in) Button mbutton_sign_in;
    @BindView(R.id.button_sign_up) Button mbutton_sign_up;
    @BindView(R.id.field_email) EditText mRegField;
    @BindView(R.id.field_password) EditText mPasswordField;
    @BindView(R.id.email_password_fields) View memail_password_fields;
    @BindView(R.id.email_password_buttons) View memail_password_buttons;
    @BindView(R.id.progressbar) ProgressBar mProgressBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signin_activity);
        ButterKnife.bind(this);

        //Setting Up page adapter
        MyPagerAdapter adapter = new MyPagerAdapter();
        mViewPager.setAdapter(adapter);
        mindicator.setViewPager(mViewPager);
    }


    @OnClick(R.id.button_sign_up)void signupButton()
    {
        if(!validateForm())
            return;
        toggleProgressVisibility();
    }
    @OnClick(R.id.button_sign_in)void signinButton()
    {
        if(!validateForm())
            return;
    }


    //Page Adapter
    private class MyPagerAdapter extends PagerAdapter {

        public int getCount() {
            return 3;
        }
        public Object instantiateItem(View collection, int position) {

            LayoutInflater inflater = (LayoutInflater) collection.getContext()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            int resId = 0;
            switch (position) {
                case 0:
                    resId = R.layout.eventer_logo;
                    break;
                case 1:
                    resId = R.layout.eventer_logo;
                    break;
                case 2:
                    resId = R.layout.eventer_logo;
                    break;
            }

            View view = inflater.inflate(resId, null);

            ((ViewPager) collection).addView(view, 0);

            return view;
        }
        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == ((View) arg1);

        }
        @Override
        public Parcelable saveState() {
            return null;
        }
        @Override
        public void destroyItem(View arg0, int arg1, Object arg2) {
            ((ViewPager) arg0).removeView((View) arg2);

        }
    }
    //Validate TextFields
    boolean validateForm() {
        boolean result = true;
        if (TextUtils.isEmpty(mRegField.getText().toString())) {
            mRegField.setError("Required");
            result = false;
        } else {
            mRegField.setError(null);
        }

        if (mRegField.length()!=7) {
            mRegField.setError("Enter Valid Reg.");
            result = false;
        } else {
            mRegField.setError(null);
        }
        if (TextUtils.isEmpty(mPasswordField.getText().toString())) {
            mPasswordField.setError("Required");
            result = false;
        } else {
            mPasswordField.setError(null);}
        return result;
    }
    public void toggleProgressVisibility() {
        if(mProgressBar.getVisibility()==View.INVISIBLE)
        {
            mProgressBar.setVisibility(View.VISIBLE);
            memail_password_buttons.setVisibility(View.INVISIBLE);
            memail_password_fields.setVisibility(View.INVISIBLE);
        }
        else
        {
            mProgressBar.setVisibility(View.INVISIBLE);
            memail_password_buttons.setVisibility(View.VISIBLE);
            memail_password_fields.setVisibility(View.VISIBLE);
        }

    }

}
