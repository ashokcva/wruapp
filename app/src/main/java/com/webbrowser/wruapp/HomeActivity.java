package com.webbrowser.wruapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;
import java.util.Random;

public class HomeActivity extends AppCompatActivity {
    final Context context = this;
    public static String sphone;
    public static String otp;
    TextView ctv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_home);
        ctv = findViewById( R.id.contacts );

        FloatingActionButton fab = findViewById(R.id.fab);
        final DBHandler dbHandler = new DBHandler(this);
        //dbHandler.deleteAll();
        List<Contact> contacts = dbHandler.getAllContacts();
        ctv.setText( "" );
        for (Contact cn : contacts) {
            String log = "NAME: "+cn.getName()+"\nPHONE:"+cn.getPhone()+"\n\n";
            System.out.println(log);
            ctv.append( log );
        }

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( getApplicationContext(), MainActivity.class );
                startActivity( intent );

                // Click action
               /* Intent intent = new Intent(HomeActivity.this, AddContactActivity.class);
                startActivity(intent);*/

//                Toast.makeText( getApplicationContext(),"Your carrier may charge for SMS Messages used to add trusted contacts",Toast.LENGTH_LONG ).show();
//                // get prompts.xml view
//                LayoutInflater li = LayoutInflater.from(context);
//                View promptsView = li.inflate(R.layout.prompts, null);
//
//                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
//                        context);
//
//                // set prompts.xml to alertdialog builder
//                alertDialogBuilder.setView(promptsView);
//
//                final EditText phone =  promptsView
//                        .findViewById(R.id.phone );
//
//                // set dialog message
//                alertDialogBuilder
//                        .setCancelable(false)
//                        .setPositiveButton("OK",
//                                new DialogInterface.OnClickListener() {
//                                    public void onClick(DialogInterface dialog,int id) {
//                                        // get user input and set it to result
//                                        // edit text
//                                        if((!phone.getText().toString().equals( "" )))
//                                        {
//                                            sphone = phone.getText().toString().trim();
//                                            sendOtp();
//                                            Intent intent = new Intent(getApplicationContext(),AddContactActivity.class );
//                                            startActivity( intent );
//                                        }
//                                      //  result.setText(userInput.getText());
//                                    }
//                                })
//                        .setNegativeButton("Cancel",
//                                new DialogInterface.OnClickListener() {
//                                    public void onClick(DialogInterface dialog,int id) {
//                                        dialog.cancel();
//                                    }
//                                });
//
//                // create alert dialog
//                AlertDialog alertDialog = alertDialogBuilder.create();
//
//                // show it
//                alertDialog.show();

            }
        });
    }
    public void sendOtp()
    {
        otp = String.format("%04d",new Random().nextInt(10000)) ;
        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage( sphone, null, "WRUApp\nYour OTP is "+otp, null, null );
    }

    @Override
    protected void onResume() {
        super.onResume();
        final DBHandler dbHandler = new DBHandler(this);
        //dbHandler.deleteAll();
        List<Contact> contacts = dbHandler.getAllContacts();
        ctv.setText( "" );
        for (Contact cn : contacts) {
            String log = "NAME: "+cn.getName()+"\nPHONE:"+cn.getPhone()+"\n\n";
            System.out.println(log);
            ctv.append( log );
        }
    }
}
