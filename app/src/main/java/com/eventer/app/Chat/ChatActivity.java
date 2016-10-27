package com.eventer.app.Chat;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.eventer.app.R;
import com.eventer.app.model.Event;

import org.parceler.Parcels;

import butterknife.BindView;

/**
 * Created by Gulzar on 26-10-2016.
 */
public class ChatActivity extends AppCompatActivity {
    @BindView(R.id.chat_frame)
    View mchat_frame;
    public Event mEvent=new Event();
    public ChatActivity(){}
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_activity);
        mEvent = Parcels.unwrap(getIntent().getParcelableExtra("EXTRA_EVENT"));
        //Temp add event key here

        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction()
                .add(R.id.chat_frame, new ChatFragment())
                .commit();
    }

}
