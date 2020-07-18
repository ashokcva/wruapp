package com.webbrowser.wruapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.FirebaseError;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignupActivity extends AppCompatActivity {

    EditText uname,fname,lname,pword,email;
    Button signup;
    String suname,sfname,slname,spword,semail;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    //DatabaseReference userRef = database.getReference( "USER/" + suname  );


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_signup );

        uname = findViewById( R.id.uname );
        fname = findViewById( R.id.fname );
        lname = findViewById( R.id.lname );
        pword = findViewById( R.id.pword );
        email = findViewById( R.id.email );
        signup = findViewById( R.id.signup );

        signup.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rxData();
                if(validateSignup()){
                    saveToBase();
                    Intent intent = new Intent( getApplicationContext(), MainActivity.class );
                    startActivity( intent );
                }

                }
        } );




    }


    private boolean validateSignup()
    {
        if(suname.equals( "" )||sfname.equals( "" )||slname.equals( "" ))
        {
            toast("User name, First name and Last name are required");
            uname.requestFocus();
            return false;
        }
        else
        {
            if(spword.length()>=8)
            {
                if(semail.contains( "@" )&&semail.contains( "." ))
                {
                    return true;
                }
                else
                {
                    toast( "Enter correct email id" );
                    email.requestFocus();
                    return false;
                }
            }
            else
            {
                toast( "Required password length is 8" );
                pword.requestFocus();
                return false;
            }
        }
    }

    private void saveToBase()
    {
        try {
            // Write a message to the database
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference userRef = database.getReference( "USER/" );
            final DatabaseReference unameRef = database.getReference( "USER/" + suname + "/uname" );
            final DatabaseReference nameRef = database.getReference( "USER/" + suname + "/name" );
            final DatabaseReference pwordRef = database.getReference( "USER/" + suname + "/pword" );
            final DatabaseReference emailRef = database.getReference( "USER/" + suname + "/email" );

/*
            userRef = FirebaseDatabase.getInstance().getReference().child("BusNumber");

            userRef.orderByChild("busNum").equalTo(su)
                    .addListenerForSingleValueEvent(new ValueEventListener() {

                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if(dataSnapshot.exists()){
                                //bus number exists in Database
                            } else {
                                //bus number doesn't exists.
                            }

                            @Override
                            public void onCancelled(FirebaseError firebaseError) {

                            }
                        });*/



            userRef.orderByChild("uname").equalTo(suname).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                   if( dataSnapshot.exists() )
                   {
                       toast( "USER EXISTS!" );

                   }
                   else
                   {
                       unameRef.setValue( suname );
                       nameRef.setValue( sfname + " " + slname );
                       pwordRef.setValue( spword );
                       emailRef.setValue( semail );
                       clearText();
                       toast( "SUCCESS! now Try Login" );
//                       Intent intent = new Intent( getApplicationContext(),MainActivity.class );
//                       startActivity( intent );
                       finish();
                   }

                }
                @Override
                public void onCancelled(DatabaseError error) {
                    toast("Something went wrong, Retry signup!");
                }
            });
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    private void showError()
    {
        toast("Something went wrong, Retry signup!");
        uname.requestFocus();
    }

    private void rxData()
    {
        suname=uname.getText().toString().trim();
        sfname=fname.getText().toString().trim();
        slname=lname.getText().toString().trim();
        spword=pword.getText().toString().trim();
        semail=email.getText().toString().trim();
    }

    private void clearText()
    {
        uname.setText("");
        fname.setText("");
        lname.setText("");
        pword.setText("");
        email.setText("");
    }

    private void toast(String s)
    {
        Toast.makeText( getApplicationContext(),s,Toast.LENGTH_LONG ).show();
    }



}
