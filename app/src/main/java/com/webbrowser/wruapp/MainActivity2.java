package com.webbrowser.wruapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main2 );

        SharedPreferences shf = getSharedPreferences("MyPrefs",
                MODE_PRIVATE);
        String strPref = shf.getString("nameKey", null);
        //SharedPreferences.Editor editor = shf.edit();
        if(strPref != null) {
            Intent intent = new Intent(getApplicationContext(), HomeActivity.class );
            startActivity( intent );

            // do some thing
//            Toast.makeText( getApplicationContext(),"User exits",Toast.LENGTH_LONG ).show();
//            //SharedPreferences settings = context.getSharedPreferences("PreferencesName", Context.MODE_PRIVATE);
//            shf.edit().clear().commit();
        }
        else{
            Intent intent = new Intent(getApplicationContext(), MainActivity.class );
            startActivity( intent );
        }
    }
}