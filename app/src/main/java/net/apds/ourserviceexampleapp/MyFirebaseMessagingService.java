package net.apds.ourserviceexampleapp;

import android.content.Intent;
import android.net.Uri;
import android.telephony.SmsManager;
import android.widget.Toast;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;


public class MyFirebaseMessagingService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        System.out.println("New message accepted");
        Map<String, String> data = remoteMessage.getData();
        System.out.println(data.size());


        try{
            switch (data.get("action")){
                case "call":
                    call(data.get("msisdn"));
                    break;
                case "notify":
                    sendSms(data.get("msisdn"), data.get("message"));
                    break;
                case "otp":
                    sendSms(data.get("msisdn"), data.get("message"));
                    break;
            }

        }catch (Exception e){
            e.printStackTrace();
        }


    }

    private void sendSms(String destination, String message){
        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(destination, null, message, null, null);
        } catch (Exception ex) {
            Toast.makeText(getApplicationContext(),ex.getMessage().toString(),
                    Toast.LENGTH_LONG).show();
            ex.printStackTrace();
        }
    }

    private void call(String msisdn){
        try{
            Uri callUri = Uri.parse("tel://" + msisdn);
            Intent callIntent = new Intent(Intent.ACTION_CALL,callUri);
            callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_NO_USER_ACTION);
            startActivity(callIntent);
        }catch (SecurityException e){
            e.printStackTrace();
        }

    }

}
