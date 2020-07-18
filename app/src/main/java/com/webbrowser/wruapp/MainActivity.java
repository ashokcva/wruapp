package com.webbrowser.wruapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    EditText uname,pword;
    Button login;
    TextView signup;

    String suname,spword;
    boolean bresult;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );

        uname = findViewById( R.id.uname );
        pword = findViewById( R.id.pword );
        login = findViewById( R.id.login );
        signup = findViewById( R.id.signup );

        login.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                suname = uname.getText().toString().trim();
                spword = pword.getText().toString().trim();

                if(validateSignin( suname,spword ))
                {
                    checkBase( suname, spword ) ;
                }
                else{
                    Toast.makeText( getApplicationContext(),"Check username and password, then Retry Login!",Toast.LENGTH_LONG ).show();
                    uname.requestFocus();
                }
            }
        } );

        signup.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( getApplicationContext(), SignupActivity.class);
                startActivity( intent );
            }
        } );
    }

    public boolean validateSignin(String suname,String spword)
    {
        if(suname.equals("")||spword.equals( "" )||spword.length()<8)
        {
         return false;
        }
        else
            return  true;
    }

    public void checkBase(final String suname, final String spword)
    {


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference userRef = database.getReference( "USER/"+suname );
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                System.out.println( dataSnapshot.getValue() );
                if((dataSnapshot.child("uname").getValue(String.class)).equals( suname )&&(dataSnapshot.child( "pword" ).getValue(String.class)).equals( spword ))
                {
                    Intent intent = new Intent( getApplicationContext(), HomeActivity.class);
                    startActivity( intent );
                    finish();
                }
                else
                {
                    clearAlert();
                }
            }
            @Override
            public void onCancelled(DatabaseError error) {
                System.out.println( error.toException() );

            }
        });

    }

    public void clearAlert()
    {
        uname.setText("");
        pword.setText("");
        Toast.makeText( getApplicationContext(),"Login Error! Enter correct Username and password!",Toast.LENGTH_LONG ).show();
        uname.requestFocus();
    }


}
