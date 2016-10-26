package com.eventer.app;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;

import com.eventer.app.Chat.ChatFragment;
import com.eventer.app.RecyclerEvent.AllEvents;
import com.eventer.app.SignIn.SignInSignUp;
import com.eventer.app.Write.WriteActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.iconics.IconicsDrawable;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
    private static final String TAG ="authxxx" ;
    @BindView(R.id.container)
    View mContainer;
    @BindView(R.id.ic_stack)
    ImageView mic_stack;
    @BindView(R.id.ic_user) ImageView mic_user;
    @BindView(R.id.stack) View mstack;
    @BindView(R.id.user) View muser;
    public  @BindView(R.id.abottom_bar)View mbottom_bar;
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

        //getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
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

        mFragments.put(TAG_DECK_FRAGMENT, AllEvents.newInstance());

        mFragments.put(TAG_USER_FRAGMENT, ChatFragment.newInstance());

        swapFragment(TAG_DECK_FRAGMENT);
        bottomBarIcons(mic_stack,mic_user,1);

    }


    @OnClick(R.id.user) public void onUserClicked() {
        startActivity(new Intent(this, WriteActivity.class));
        //Temp
        //(TAG_USER_FRAGMENT);
//        mAuth.signOut();
//        startActivity(new Intent(this, SignInSignUp.class));
        bottomBarIcons(mic_stack,mic_user,2);
    }


    public static void bottomBarIcons(ImageView deck, ImageView user, int highlight) {
        switch(highlight)
        {
            case 1:
                user.setImageDrawable(new IconicsDrawable(user.getContext(), GoogleMaterial.Icon.gmd_account_box).actionBar().color(Color.LTGRAY));
                deck.setImageDrawable(new IconicsDrawable(deck.getContext(), GoogleMaterial.Icon.gmd_reorder).actionBar().color(Color.BLACK));
                break;
            case 2:
                user.setImageDrawable(new IconicsDrawable(user.getContext(), GoogleMaterial.Icon.gmd_account_box).actionBar().color(Color.BLACK));
                deck.setImageDrawable(new IconicsDrawable(deck.getContext(), GoogleMaterial.Icon.gmd_reorder).actionBar().color(Color.LTGRAY));
                break;
        }
    }
    private void swapFragment(String tag) {
        FragmentManager manager = getSupportFragmentManager();
        Fragment targetFragment = manager.findFragmentByTag(tag);
        if (targetFragment != null && targetFragment.isVisible()) {
            return;
        }

        mContainer.setAnimation(mAnimation);
        mAnimation.start();

        // String otherTag = tag.equals(TAG_DECK_FRAGMENT) ? TAG_ARCHIVE_FRAGMENT : TAG_DECK_FRAGMENT;
        String otherTag = tag.equals(TAG_DECK_FRAGMENT) ? TAG_USER_FRAGMENT : TAG_DECK_FRAGMENT;
        if (manager.findFragmentByTag(tag) != null) {
            manager.beginTransaction()
                    .show(manager.findFragmentByTag(tag))
                    .commit();
            bottomBarIcons(mic_stack,mic_user,1);
        } else {
            manager.beginTransaction()
                    .add(R.id.container, mFragments.get(tag), tag)
                    .commit();
        }
        if (manager.findFragmentByTag(otherTag) != null) {
            manager.beginTransaction()
                    .hide(manager.findFragmentByTag(otherTag))
                    .commit();
        }
        //if (tag.equals(TAG_DECK_FRAGMENT)) showBottomBar(true);
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
}
