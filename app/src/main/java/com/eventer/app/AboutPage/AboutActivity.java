package com.eventer.app.AboutPage;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.util.Pair;
import android.support.v4.view.LayoutInflaterCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.eventer.app.R;
import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.mikepenz.fontawesome_typeface_library.FontAwesome;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.iconics.context.IconicsContextWrapper;
import com.mikepenz.iconics.context.IconicsLayoutInflater;

import org.parceler.Parcels;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AboutActivity extends AppCompatActivity {

    @BindView(R.id.email)
    ImageView email;
    @BindView(R.id.email2)
    ImageView email2;
    @BindView(R.id.email3)
    ImageView email3;
    @BindView(R.id.github)
    ImageView github;
    @BindView(R.id.github2)
    ImageView github2;
    @BindView(R.id.github3)
    ImageView github3;
    @BindView(R.id.whatsapp)
    ImageView whatsapp;
    @BindView(R.id.whatsapp2)
    ImageView whatsapp2;
    @BindView(R.id.whatsapp3)
    ImageView whatsapp3;
    @BindView(R.id.linkedIn)
    ImageView linkedIn;
    @BindView(R.id.linkedIn2)
    ImageView linkedIn2;
    @BindView(R.id.linkedIn3)
    ImageView linkedIn3;

    @BindView(R.id.profile_photo)
    ImageView profile_photo;
    @BindView(R.id.profile_photo2)
    ImageView profile_photo2;
    @BindView(R.id.profile_photo3)
    ImageView profile_photo3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        LayoutInflaterCompat.setFactory(getLayoutInflater(), new IconicsLayoutInflater(getDelegate()));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_final);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("About");

        setUpHardCoded();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void setUpHardCoded() {
        GradientDrawable backgroundGradient = (GradientDrawable)email.getBackground();
        backgroundGradient.setColor(Color.parseColor("#ea4335"));
        email.setImageDrawable(new IconicsDrawable(email.getContext(), FontAwesome.Icon.faw_envelope_o).actionBar().color(Color.WHITE));
        GradientDrawable backgroundGradient2 = (GradientDrawable)email2.getBackground();
        backgroundGradient2.setColor(Color.parseColor("#ea4335"));
        email2.setImageDrawable(new IconicsDrawable(email2.getContext(), FontAwesome.Icon.faw_envelope_o).actionBar().color(Color.WHITE));
        GradientDrawable backgroundGradient3 = (GradientDrawable)email3.getBackground();
        backgroundGradient3.setColor(Color.parseColor("#ea4335"));
        email3.setImageDrawable(new IconicsDrawable(email3.getContext(), FontAwesome.Icon.faw_envelope_o).actionBar().color(Color.WHITE));


        GradientDrawable backgroundGradientgithub = (GradientDrawable)github.getBackground();
        backgroundGradientgithub.setColor(Color.BLACK);
        github.setImageDrawable(new IconicsDrawable(github.getContext(), FontAwesome.Icon.faw_github).actionBar().color(Color.WHITE));
        GradientDrawable backgroundGradientgithub2 = (GradientDrawable)github2.getBackground();
        backgroundGradientgithub2.setColor(Color.BLACK);
        github2.setImageDrawable(new IconicsDrawable(github2.getContext(), FontAwesome.Icon.faw_github).actionBar().color(Color.WHITE));
        GradientDrawable backgroundGradientgithub3 = (GradientDrawable)github3.getBackground();
        backgroundGradientgithub3.setColor(Color.parseColor("#1769ff"));
        github3.setImageDrawable(new IconicsDrawable(github3.getContext(), FontAwesome.Icon.faw_behance).actionBar().color(Color.WHITE));

        GradientDrawable backgroundGradientlinkedin = (GradientDrawable)linkedIn.getBackground();
        backgroundGradientlinkedin.setColor(Color.parseColor("#0077b5"));
        linkedIn.setImageDrawable(new IconicsDrawable(linkedIn.getContext(), FontAwesome.Icon.faw_linkedin).actionBar().color(Color.WHITE));
        GradientDrawable backgroundGradientlinkedin2 = (GradientDrawable)linkedIn2.getBackground();
        backgroundGradientlinkedin2.setColor(Color.parseColor("#0077b5"));
        linkedIn2.setImageDrawable(new IconicsDrawable(linkedIn2.getContext(), FontAwesome.Icon.faw_linkedin).actionBar().color(Color.WHITE));
        GradientDrawable backgroundGradientlinkedin3 = (GradientDrawable)linkedIn3.getBackground();
        backgroundGradientlinkedin3.setColor(Color.parseColor("#0077b5"));
        linkedIn3.setImageDrawable(new IconicsDrawable(linkedIn3.getContext(), FontAwesome.Icon.faw_linkedin).actionBar().color(Color.WHITE));

        GradientDrawable backgroundGradientlwhatsapp = (GradientDrawable)whatsapp.getBackground();
        backgroundGradientlwhatsapp.setColor(Color.parseColor("#25d366"));
        whatsapp.setImageDrawable(new IconicsDrawable(whatsapp.getContext(), FontAwesome.Icon.faw_whatsapp).actionBar().color(Color.WHITE));

        GradientDrawable backgroundGradientlwhatsapp2 = (GradientDrawable)whatsapp2.getBackground();
        backgroundGradientlwhatsapp2.setColor(Color.parseColor("#25d366"));
        whatsapp2.setImageDrawable(new IconicsDrawable(whatsapp2.getContext(), FontAwesome.Icon.faw_whatsapp).actionBar().color(Color.WHITE));
        GradientDrawable backgroundGradientlwhatsapp3 = (GradientDrawable)whatsapp3.getBackground();
        backgroundGradientlwhatsapp3.setColor(Color.parseColor("#25d366"));
        whatsapp3.setImageDrawable(new IconicsDrawable(whatsapp3.getContext(), FontAwesome.Icon.faw_whatsapp).actionBar().color(Color.WHITE));


        //Set up Profile Image
        Glide.with(this)
                .load(R.drawable.gulzar_a)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(profile_photo);
        Glide.with(this)
                .load("https://firebasestorage.googleapis.com/v0/b/magnovite-app.appspot.com/o/AAEAAQAAAAAAAAjfAAAAJGExYWIwZjUwLWMyNDYtNDJhYS05YjIzLTk4ZmMyZDNiNGQ1NA.jpg?alt=media&token=fd26bd7b-e6e6-49d0-a494-9de1eaa6aa65")
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(profile_photo2);
        Glide.with(this)
                .load("https://firebasestorage.googleapis.com/v0/b/magnovite-app.appspot.com/o/rahul.jpg?alt=media&token=088f0cf0-ad92-4fe5-99cc-3a89048bd6c0")
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(profile_photo3);
        //Set Up Click Listener
        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                        "mailto","gulzar.ahmed@btech.christuniversity.in", null));
                startActivity(Intent.createChooser(emailIntent, "Send email..."));
            }
        });
        //Email Intent
        email2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                        "mailto","dggs222@gmail.com", null));
                startActivity(Intent.createChooser(emailIntent, "Send email..."));
            }
        });
        email3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                        "mailto","a.v.rahul11@gmail.com", null));
                startActivity(Intent.createChooser(emailIntent, "Send email..."));
            }
        });

        github.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse("https://github.com/gulzar1996"));
                startActivity(i);
            }
        });
        github2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse("https://github.com/dggs123"));
                startActivity(i);
            }
        });
        github3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse("https://www.behance.net/avrahul11dbf4"));
                startActivity(i);
            }
        });

        linkedIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse("https://in.linkedin.com/in/gulzar-ahmed-17a544115"));
                startActivity(i);
            }
        });
        linkedIn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse("https://in.linkedin.com/in/gaurav-sehgal-79abb9112"));
                startActivity(i);
            }
        });
        linkedIn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse("https://in.linkedin.com/in/a-v-rahul-79a768125"));
                startActivity(i);
            }
        });
        whatsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ContactsContract.Intents.Insert.ACTION);
                intent.setType(ContactsContract.RawContacts.CONTENT_TYPE);
                intent.putExtra(ContactsContract.Intents.Insert.EMAIL,"gulzar.ahmed@btech.christuniversity.in")
                        .putExtra(ContactsContract.Intents.Insert.EMAIL_TYPE, ContactsContract.CommonDataKinds.Email.TYPE_WORK)
                        .putExtra(ContactsContract.Intents.Insert.PHONE, "8910167684")
                        .putExtra(ContactsContract.Intents.Insert.SECONDARY_PHONE, "9742634857")
                        .putExtra(ContactsContract.Intents.Insert.NAME,"Gulzar")
                        .putExtra(ContactsContract.Intents.Insert.PHONE_TYPE, ContactsContract.CommonDataKinds.Phone.TYPE_WORK);
                startActivity(intent);
            }
        });
        whatsapp2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ContactsContract.Intents.Insert.ACTION);
                intent.setType(ContactsContract.RawContacts.CONTENT_TYPE);
                intent.putExtra(ContactsContract.Intents.Insert.EMAIL,"gaurav.sehgal@btech.christuniversity.in")
                        .putExtra(ContactsContract.Intents.Insert.EMAIL_TYPE, ContactsContract.CommonDataKinds.Email.TYPE_WORK)
                        .putExtra(ContactsContract.Intents.Insert.PHONE, "7042601587")
                        .putExtra(ContactsContract.Intents.Insert.NAME,"Gaurav")
                        .putExtra(ContactsContract.Intents.Insert.PHONE_TYPE, ContactsContract.CommonDataKinds.Phone.TYPE_WORK);
                startActivity(intent);
            }
        });

        whatsapp3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ContactsContract.Intents.Insert.ACTION);
                intent.setType(ContactsContract.RawContacts.CONTENT_TYPE);
                intent.putExtra(ContactsContract.Intents.Insert.PHONE, "8147246469")
                        .putExtra(ContactsContract.Intents.Insert.NAME,"AV Rahul")
                        .putExtra(ContactsContract.Intents.Insert.PHONE_TYPE, ContactsContract.CommonDataKinds.Phone.TYPE_WORK);
                startActivity(intent);
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

