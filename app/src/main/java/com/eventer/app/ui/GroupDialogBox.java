package com.eventer.app.ui;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.InputType;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.eventer.app.UiCallBack.GroupInputDetails;

/**
 * Created by Gulzar on 22-08-2016.
 */
public class GroupDialogBox {
    public EditText[] mRegNoEditext,mPasswordEdit;
    public void  createGroupDialogBox(final int n, final Context context, final GroupInputDetails GroupInputDetails)
    {
        ScrollView scrl=new ScrollView(context);
        LinearLayout parent = new LinearLayout(context);
        LinearLayout horizontal=new LinearLayout(context);

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        layoutParams.setMargins(30, 20, 30, 0);


        parent.setOrientation(LinearLayout.VERTICAL);


        horizontal.setOrientation(LinearLayout.VERTICAL);
        scrl.addView(parent);

        mRegNoEditext=new EditText[n];
        mPasswordEdit=new EditText[n];
        EditText myself=new EditText(context);
        myself.setText("Me");
        myself.setFocusable(false);
        horizontal.addView(myself);

        for(int i=0;i<n;i++)
        {
            mRegNoEditext[i]=new EditText(context);
            mPasswordEdit[i]=new EditText(context);
            mRegNoEditext[i].setHint("Reg no "+(i+1));
            mPasswordEdit[i].setHint("Password "+(i+1));
            mPasswordEdit[i].setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            mRegNoEditext[i].setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_NUMBER_FLAG_SIGNED);
            horizontal.addView(mRegNoEditext[i]
            );
            // horizontal.addView(mPasswordEdit[i]);
        }
        horizontal.setLayoutParams(layoutParams);
        parent.addView(horizontal);



        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Group Member Details");



        builder.setView(scrl);
        builder.setCancelable(false);

        // Set up the buttons
        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
//                boolean flag=true;
//                for(int i=0;i<n;i++) {
//                    if (mRegNoEditext[i].getText() == null)
//                        flag = false;
//                    else if (mRegNoEditext[i].getText().toString().length() != 7)
//                        flag = false;
//                    else if(!isNumeric(mRegNoEditext[i].getText().toString()))
//                        flag=false;
//                }
//                if(flag==false)
//                {
//                    Toast.makeText(context, "Enter valid Reg No", Toast.LENGTH_SHORT).show();
//                    flag=true;
//
//                }
//                else {
                String Reg[]=new String[n];
                String Pswd[]=new String[n];

                for(int i=0;i<n;i++)
                {
                    if(mRegNoEditext[i].getText()==null)
                        Reg[i]=null;
                    else
                        Reg[i]=mRegNoEditext[i].getText().toString();
                    if(mPasswordEdit[i].getText()==null)
                        Pswd[i]=null;
                    else
                        Pswd[i]=mPasswordEdit[i].getText().toString();
                }
                GroupInputDetails.onSuccess(Reg, Pswd, n);
                dialog.dismiss();

            }
        });
        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }
    Boolean isNumeric(String reg)
    {
        for(int i=0;i<reg.length();i++)
            if(!Character.isDigit(reg.charAt(i)))
                return false;
        return true;
    }
}
