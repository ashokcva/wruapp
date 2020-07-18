package com.webbrowser.wruapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
    SharedPreferences sharedpreferences;
    EditText uname,pword;
    Button login;
    TextView signup;
    public static final String MyPREFERENCES = "MyPrefs" ;
    String suname,spword;
    boolean bresult;
    public static final String Name = "nameKey";
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );




        uname = findViewById( R.id.uname );
        pword = findViewById( R.id.pword );
        login = findViewById( R.id.login );
        signup = findViewById( R.id.signup );
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
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
                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    editor.putString(Name, suname);
                    //  editor.putString(Password, spword);
                    //  editor.putString(Email, e);
                    editor.commit();
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
