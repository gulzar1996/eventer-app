package com.eventer.app.Chat;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.eventer.app.R;
import com.eventer.app.SignIn.SignInSignUp;
import com.eventer.app.util.FirebaseUtils;
import com.eventer.app.util.ViewUtils;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Gulzar on 26-10-2016.
 */
public class ChatFragment extends Fragment implements ChildEventListener, ChatContract.View {

    @BindView(R.id.input_message)
    EditText messageInput;
    @BindView(R.id.list_chat)
    RecyclerView chatList;
    @BindView(R.id.query_name) TextView mquery_name;
    private DatabaseReference firebase;
    private List<Chat> chats;
    private String idUser;
    private ChatAdapter adapter;
    private String senderName;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private ChatContract.UserActionListener presenter;

    public ChatFragment() {
    }
    public static Fragment newInstance() {
        return new ChatFragment();
    }

    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        //Change Sender name
        senderName="Eventer User";

       // idUser = getUId();
        //idUser= String.valueOf(FirebaseAuth.getInstance().getCurrentUser());
        mAuthListener = new FirebaseAuth.AuthStateListener() {@Override
        public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
            FirebaseUser user = firebaseAuth.getCurrentUser();
            if (user != null) {
                idUser=user.getUid();
                senderName=user.getDisplayName();

            } else {
               startActivity(new Intent(getActivity(), SignInSignUp.class));
            }
        }
        };


    }

    @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                       Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat, container, false);
        ButterKnife.bind(this, view);
        ChatActivity ca=(ChatActivity)getActivity();
        Log.d("Gulxx",ca.mEvent.title);
        mquery_name.setText(ca.mEvent.title+" Discussion");
        initializeFirebase(ca.mEvent.eventID);
        initializePresenter();
        setupAdapter();
        setupList();

        return view;
    }



    @OnClick(R.id.button_sent) public void submit() {
        presenter.send();
    }

    private void initializeFirebase(String eventId) {
        firebase = FirebaseUtils.getDatabase().getReference().child("event-chat").child(eventId);
        firebase.keepSynced(true);
        firebase.addChildEventListener(this);
    }

    private void setupAdapter() {
        chats = new ArrayList<>();
        adapter = new ChatAdapter(chats, idUser);
    }

    private void setupList() {
        chatList.setLayoutManager(new LinearLayoutManager(chatList.getContext()));
        chatList.setAdapter(adapter);
    }

//    private String getUId() {
//        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//        return user+"";
//    }

    private void initializePresenter() {
        if (presenter == null) presenter = new ChatPresenter(this);
    }

    @Override public void onChildAdded(DataSnapshot dataSnapshot, String s) {
        presenter.childAdded(dataSnapshot, s);
    }

    @Override public void onChildChanged(DataSnapshot dataSnapshot, String s) {

    }

    @Override public void onChildRemoved(DataSnapshot dataSnapshot) {

    }

    @Override public void onChildMoved(DataSnapshot dataSnapshot, String s) {

    }

    @Override
    public void onCancelled(DatabaseError databaseError) {

    }

    @Override public void sendMessage() {
        String message = messageInput.getText().toString();
        if (!message.trim().isEmpty()) firebase.push().setValue(new Chat(message, idUser,senderName));

        messageInput.setText("");
    }

    @Override public void fireBaseOnChildAdded(DataSnapshot dataSnapshot, String s) {
        if (dataSnapshot != null && dataSnapshot.getValue() != null) {
            Chat model = dataSnapshot.getValue(Chat.class);
            chats.add(model);
            chatList.scrollToPosition(chats.size() - 1);
            adapter.notifyItemInserted(chats.size() - 1);
        }
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