package com.webbrowser.wruapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

public class AddContactActivity extends AppCompatActivity {

    EditText name,phone,otp;
    Button save;
    String sname,sotp,sphone;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_add_contact );



        if (HomeActivity.sphone.equals( "" )) {
            wentWrong();
        }

        name = findViewById( R.id.name );
        phone = findViewById( R.id.phone );
        otp = findViewById( R.id.otp );
        save = findViewById( R.id.save );

        phone.setText(HomeActivity.sphone);
        phone.setEnabled( false );

        save.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sname = name.getText().toString().trim();
                //sphone = phone.getText().toString().trim();
                sotp = otp.getText().toString().trim();
                verifyOtp();
            }
        } );
    }
    public void verifyOtp ()
    {
        if(sotp.equals( HomeActivity.otp ))
        {
            Contact c = new Contact( sname,HomeActivity.sphone.replace( "+91 ","+91" ) );
            final DBHandler dbHandler = new DBHandler(this);
            dbHandler.addContact( c );
            Toast.makeText( getApplicationContext(), "Contact saved", Toast.LENGTH_LONG ).show();
            Intent intent = new Intent( getApplicationContext(), HomeActivity.class );
            startActivity( intent );
            finish();
        }
        else
        {
            wentWrong();
        }
    }
    public void wentWrong()
    {
        Toast.makeText( getApplicationContext(), "Something went wrong!", Toast.LENGTH_LONG ).show();
        Intent intent = new Intent( getApplicationContext(), HomeActivity.class );
        startActivity( intent );
        finish();
    }
}
