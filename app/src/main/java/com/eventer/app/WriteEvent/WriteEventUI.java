package com.eventer.app.WriteEvent;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;

import com.eventer.app.R;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.iconics.IconicsDrawable;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Gulzar on 19-07-2016.
 */
public abstract class WriteEventUI extends AppCompatActivity implements View.OnClickListener {
    //ToolBar Elements
    public   Toolbar mToolbar;
    public   Toolbar mBottomToolbar;
    public   TextView mToolBarTitle;
    //
    public EditText mEventTitle;
    public EditText mEventDescription;
    public EditText mEventcVenue;
    public EditText mMaxReg,mMinReg;


    //Initialize Date and Time Picker
    public   DatePicker mDatePicker;
    public   TimePicker mTimePicker;
    //Date time Picker Variable
    public int day,month,year,hour,minutes;
    //Contact Picker Variable
    public  ArrayList<String> contactNamePhone=new ArrayList<>();
    public String contactName;
    public String contactPhone;
    public static final int CONTACT_PICKER_RESULT = 1001;
    public int contactCounter=0;
    public int contactLimit=2;
    MaterialDialog contactDialog;
    //Tag Picker
    public ArrayList<String> tagName=new ArrayList<>();
    public  ArrayList<String> storeTagName=new ArrayList<>();
    public int tagCounter=0;
    public Integer arrTags[];
    //Image Picker
    public final int SELECT_PICTURE = 1;
    String selectedImagePath;
    private ImageView iaction_time,iaction_contact,iaction_label,iaction_photo;

    //MAx and Minimum Reg
    Integer maxReg=null,minReg=null;
    ToggleButton groupToggleButton;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_event);
        //Initialize toolbar
        mToolbar= (Toolbar) findViewById(R.id.toolbar);
        mToolBarTitle= (TextView) findViewById(R.id.toolbar_title);
        mToolbar.setTitle("");
        mToolbar.inflateMenu(R.menu.write_event_menu);
        mToolBarTitle.setVisibility(View.VISIBLE);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //  getSupportActionBar().setHomeButtonEnabled(true);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        //initialize bottom toolbar
        mBottomToolbar=(Toolbar)findViewById(R.id.bottom_toolbar);
        setSupportActionBar(mBottomToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        //Initialize date time and month
        day=month=year=hour=minutes=-1;
        //Initialize Edittext
        mEventTitle= (EditText) findViewById(R.id.event_title);
        mEventDescription=(EditText)findViewById(R.id.eventDescription);
        mEventcVenue=(EditText)findViewById(R.id.eventcvenue);
        mMaxReg= (EditText) findViewById(R.id.maxReg);
        mMinReg= (EditText) findViewById(R.id.minReg);
        //SetonClick listner
        findViewById(R.id.action_time).setOnClickListener(this);
        findViewById(R.id.action_contact).setOnClickListener(this);
        findViewById(R.id.action_label).setOnClickListener(this);
        findViewById(R.id.action_photo).setOnClickListener(this);
        //Initializing ImageView
        iaction_contact= (ImageView) findViewById(R.id.action_contact);
        iaction_label= (ImageView) findViewById(R.id.action_label);
        iaction_photo=(ImageView)findViewById(R.id.action_photo);
        iaction_time= (ImageView) findViewById(R.id.action_time);



    }

    public void groupToggleButtonListner() {
        groupToggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    MaterialDialog d=new MaterialDialog.Builder(WriteEventUI.this)
                            .title(R.string.title)
                            .content(R.string.content)
                            .inputType(InputType.TYPE_NUMBER_FLAG_SIGNED )
                            .input(R.string.input_hint, R.string.input_prefills, new MaterialDialog.InputCallback() {
                                        @Override
                                        public void onInput(MaterialDialog dialog, CharSequence input) {
                                            // Do something
                                            String max=input.toString().toLowerCase().trim();
                                        }

                                    }

                            )
                            .content(R.string.content)
                            .inputType(InputType.TYPE_NUMBER_FLAG_SIGNED )
                            .input(R.string.input_hint_min, R.string.input_prefills, new MaterialDialog.InputCallback() {
                                        @Override
                                        public void onInput(MaterialDialog dialog, CharSequence input) {
                                            // Do something
                                            String min=input.toString().toLowerCase().trim();
                                        }

                                    }

                            )
                            .autoDismiss(true)
                            .show();
                }
            }
        });
    }

    /**
     Arguments holding the value
     day,month,year,hour and minutes
     default value is -1
     */
    public void DateTImePicker()
    {
        final Dialog dialogdatetime = new Dialog(this);
        dialogdatetime.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogdatetime.setContentView(R.layout.date_time_dialog);
        //Initialize Date and Time Picker
        mDatePicker= (DatePicker) dialogdatetime.findViewById(R.id.datePicker);
        //Setting minimum date
        mDatePicker.setMinDate(System.currentTimeMillis() - 1000);

        mTimePicker=(TimePicker)dialogdatetime.findViewById(R.id.timePicker);
        if(day!=-1 && month!=-1 && year!=-1)
        {
            MaterialDialog dialog1 = new MaterialDialog.Builder(this)
                    .title("Event Date")
                    .content(day+"/"+(month+1)+"/"+year)
                    .positiveText("Yes")
                    .negativeText("No")
                    .onNegative(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            Log.i("XOXO","POSITIVE");
                            dialogdatetime.show();

                        }
                    })
                    .show()
                    ;


        }
        else
            dialogdatetime.show();
        dialogdatetime.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                updateUI();
            }
        });
        dialogdatetime.setTitle("Pick Date and Time ");

        dialogdatetime.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                //Get day month year
                day=mDatePicker.getDayOfMonth();
                month=mDatePicker.getMonth();
                year=mDatePicker.getYear();
                //Get time
                hour=mTimePicker.getCurrentHour();
                minutes=mTimePicker.getCurrentMinute();
                updateUI();


            }
        });

    }
    //ArrayLIst contactArray Stores the name and contact details
    public void ContactPicker() {

        contactDialog =   new MaterialDialog.Builder(this)
                .title("Organizers")
                .items(getcontactArray())
                .negativeText(R.string.add)
                .positiveText("Remove")
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        Log.i("XOXO","Contact size :"+contactNamePhone.size()+1);
                        if(contactNamePhone.size()+1<=contactLimit) {
                            Intent i = new Intent(Intent.ACTION_PICK,
                                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
                            startActivityForResult(i, CONTACT_PICKER_RESULT);
                        }
                        else
                            Toast.makeText(getBaseContext(), contactLimit+" Only", Toast.LENGTH_SHORT).show();
                    }
                })
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        contactNamePhone.clear();
                        updateUI();
                    }
                })
                .dismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        updateUI();
                    }
                })
                .autoDismiss(true)
                .show();

    }

    //After Contact activity is opened
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        contactName=contactPhone=null;
        if (resultCode == RESULT_OK)
        {
            switch (requestCode)
            {
                case CONTACT_PICKER_RESULT:
                    Cursor cursor = null;

                    try {
                        Uri result = data.getData();
                        String id = result.getLastPathSegment();

                        //Get Name
                        cursor = getContentResolver().query(result, null, null, null, null);
                        if (cursor.moveToFirst()) {
                            contactName = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));//Display Name
                            contactPhone=cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                            setContactNamePhone(contactName,contactPhone);
                            updateUI();

                        }} catch (Exception e)
                    { }
                    break;
                case SELECT_PICTURE:
                    selectedImagePath=null;
                    Uri selectedImage = data.getData();
                    String[] filePath = {MediaStore.Images.Media.DATA};
                    Cursor c = getContentResolver().query(selectedImage, filePath, null, null, null);
                    c.moveToFirst();
                    int columnIndex = c.getColumnIndex(filePath[0]);
                    String picturePath = c.getString(columnIndex);
                    updateUI();
                    c.close();
                    selectedImagePath=picturePath;

                    Log.e("XOXO","path +"+ picturePath + "");

                    //    selectedImagePath = getPath(selectedImageUri);
                    // Log.i("XOXO","IMAGE PATH :"+selectedImagePath);
                    break;
            }
        }
    }
    public void LabelPicker()
    {
        String[] tagArray = new String[tagName.size()];
        tagArray = tagName.toArray(tagArray);
        storeTagName.clear();
        MaterialDialog tagDialog =   new MaterialDialog.Builder(this)
                .title("Tags")
                .items(tagArray)
                .itemsCallbackMultiChoice(null, new MaterialDialog.ListCallbackMultiChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog dialog, Integer[] which, CharSequence[] text) {
                        /**
                         * If you use alwaysCallMultiChoiceCallback(), which is discussed below,
                         * returning false here won't allow the newly selected check box to actually be selected.
                         * See the limited multi choice dialog example in the sample project for details.
                         **/
                        storeTagName.clear();
                        for(CharSequence c:text){
                            storeTagName.add(c.toString());
                            Log.i("XOXO","CharSequence"+c.toString());}
                        for(int a:which)
                            Log.i("XOXO","Which"+a);
                        setTags(which);
                        updateUI();
                        return true;
                    }
                })
                .dismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        updateUI();
                    }
                })
                .positiveText(R.string.add)
                .alwaysCallMultiChoiceCallback()
                .autoDismiss(true)
                .show();
        if(getTags()!=null)
            tagDialog.setSelectedIndices(getTags());

    }
    public void ImagePicker()
    {
        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, SELECT_PICTURE);
        updateUI();
    }
    /**
     * helper to retrieve the path of an image URI
     */

    //Save tags
    public Integer[] getTags()
    {
        return arrTags;
    }
    public void setTags(Integer arrTags[])
    {
        this.arrTags= Arrays.copyOf(arrTags,arrTags.length);
    }
    /**
     * Getters Yeah!!
     */
    public int getDay()
    {
        return day;
    }
    public int getMonth()
    {
        return (month+1);
    }
    public int getYear()
    {
        return year;
    }
    public int getHour()
    {
        return hour;
    }
    public int getMinutes()
    {
        return minutes;
    }
    public String[] getstoreTagName()
    {
        if (storeTagName==null)
            return null;
        else
        {
            String[] stockArr = new String[storeTagName.size()];
            stockArr = storeTagName.toArray(stockArr);
            return stockArr;
        }
    }
    public String[] getcontactArray()
    {
        if(contactNamePhone.size()==0)
            return null;
        String[] contactArray = new String[contactNamePhone.size()];
        contactArray = contactNamePhone.toArray(contactArray);
        return contactArray;
    }
    public String getEventTitle()
    {
        if(mEventTitle.getText()==null)
            return null;
        return mEventTitle.getText().toString();

    }
    public String getSelectedImagePath()
    {
        return selectedImagePath;
    }
    public String getEventDescription()
    {
        if(mEventDescription.getText()==null)
            return null;
        return mEventDescription.getText().toString();
    }
    public String getEventVenue()
    {
        return mEventcVenue.getText().toString();
    }
    public Integer getMaxReg(){
        if(mMaxReg.getText()==null)
            return null;
        return Integer.parseInt(mMaxReg.getText().toString());}
    public Integer getMinReg(){
        if(mMinReg.getText()==null)
            return null;
        return Integer.parseInt(mMinReg.getText().toString());}
    /**
     * Setters Oh YESS!
     */
    public void setTagName(String tag[])
    {
        tagName.clear();
        for(String t:tag)
        {
            tagName.add(t);
        }
    }
    public void setContactNamePhone(String name,String phone)
    {
        contactNamePhone.add(name+" "+phone);
    }
    public void updateUI()
    {
        if(getMonth()!=-1)
            iaction_time.setImageDrawable(new IconicsDrawable(this, GoogleMaterial.Icon.gmd_alarm).actionBar().color(Color.BLACK));
        else
            iaction_time.setImageDrawable(new IconicsDrawable(this, GoogleMaterial.Icon.gmd_alarm).actionBar().color(Color.GRAY));

        if(getcontactArray()==null)
            iaction_contact.setImageDrawable(new IconicsDrawable(this, GoogleMaterial.Icon.gmd_perm_contact_calendar).actionBar().color(Color.GRAY));
        else
            iaction_contact.setImageDrawable(new IconicsDrawable(this, GoogleMaterial.Icon.gmd_perm_contact_calendar).actionBar().color(Color.BLACK));

        if(getstoreTagName().length==0)
            iaction_label.setImageDrawable(new IconicsDrawable(this, GoogleMaterial.Icon.gmd_label).actionBar().color(Color.GRAY));
        else
            iaction_label.setImageDrawable(new IconicsDrawable(this, GoogleMaterial.Icon.gmd_label).actionBar().color(Color.BLACK));
        if(getSelectedImagePath()==null)
            iaction_photo.setImageDrawable(new IconicsDrawable(this, GoogleMaterial.Icon.gmd_add_a_photo).actionBar().color(Color.GRAY));
        else
            iaction_photo.setImageDrawable(new IconicsDrawable(this, GoogleMaterial.Icon.gmd_add_a_photo).actionBar().color(Color.BLACK));



    }
}
