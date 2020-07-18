package com.webbrowser.wruapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.provider.Telephony;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class SmsListener extends BroadcastReceiver {
    double lat, longi;
    int flag=0;
    List<Address> addresses;
    String fulladdress;
    Geocoder geocoder;
    String from, msg;

    @Override
    public void onReceive(Context context, Intent intent) {
        final DBHandler dbHandler = new DBHandler( context );
        Contact contact = new Contact();
        geocoder =new Geocoder( context, Locale.getDefault() );
        //Toast.makeText( context,"messageBody",Toast.LENGTH_LONG ).show();
        if (intent.getAction().equals( "android.provider.Telephony.SMS_RECEIVED" )) {
            Bundle bundle = intent.getExtras();
            SmsMessage[] msgs = null;

            if (bundle != null) {
                try {
                    Object[] pdus = (Object[]) bundle.get( "pdus" );
                    msgs = new SmsMessage[pdus.length];
                    for (int i = 0; i < msgs.length; i++) {
                        msgs[i] = SmsMessage.createFromPdu( (byte[]) pdus[i] );
                        from = msgs[i].getOriginatingAddress();
                        msg = msgs[i].getMessageBody();
                        contact.setPhone( from );
                        System.out.println( from );
                        System.out.println( contact.getPhone() );

                        int rxcount = dbHandler.getRecordCount( contact );
                        System.out.println( rxcount );
                        if (rxcount >= 1) {
                            LocationTrack loc = new LocationTrack( context );
                            do {
                                lat = loc.getLatitude();
                                longi = loc.getLongitude();
                            }while((lat==0)&&(longi==0));
                            System.out.println( lat+" "+longi );
                            try
                            {
                                addresses = geocoder.getFromLocation( lat, longi, 1 );
                                if (addresses != null && addresses.size() > 0)
                                {
                                    String address = addresses.get( 0 ).getAddressLine( 0 );
                                    String city = addresses.get( 0 ).getLocality();
                                    String state = addresses.get( 0 ).getAdminArea();
                                    String country = addresses.get( 0 ).getCountryName();
                                    String postalcode = addresses.get( 0 ).getPostalCode();
                                    String knownName = addresses.get( 0 ).getFeatureName();
                                    fulladdress = address + "," + city + "," + state + "," + country + "," + postalcode+","+knownName;
                                    System.out.println( fulladdress  );
                                    if(msg.equalsIgnoreCase( "wru" ))
                                    sendSMS( from,fulladdress );
                                }
                                }
                            catch (Exception e)
                            {
                                e.printStackTrace();
                            }
                        }
                        //Toast.makeText( context,"Record exists!"+rxcount,Toast.LENGTH_LONG ).show();
                    }
                } catch (Exception e) {
                    System.out.println( e.toString() );
                }
            }
        }
/*        if(flag==1)
        {
            sendSMS(from,fulladdress);
            flag=0;
        }*/
    }

    private void sendSMS(String from,String fulladdress)
    {
        SmsManager sms = SmsManager.getDefault();

        sms.sendTextMessage( from, null, fulladdress, null, null );
    }


   /* private void sendMyLoc(double latitude, double longitude, Context context) {

        geocoder = new Geocoder( context, Locale.getDefault() );
        try {
            addresses = geocoder.getFromLocation( lat, longi, 1 );

            String address = addresses.get( 0 ).getAddressLine( 0 );
            String area = addresses.get( 0 ).getLocality();
            String city = addresses.get( 0 ).getAdminArea();
            String country = addresses.get( 0 ).getCountryName();
            String postalcode = addresses.get( 0 ).getPostalCode();
            //Toast.makeText( Main2Activity.this, "Sent successfully!", Toast.LENGTH_LONG ).show();
            fulladdress = address + "," + area + "," + city + "," + country + "," + postalcode;
            //textview.setText(fulladdress);
            // Toast.makeText( Main2Activity.this, fulladdress, Toast.LENGTH_LONG ).show();
            System.out.println( fulladdress );
        } catch (IOException e) {
           System.out.println( "Cant convert "+e );
        }

}*/
}