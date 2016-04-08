package hackathon.spicejet.com.spicecare;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.PhoneStateListener;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class CallBroadcastReciver extends BroadcastReceiver {

    private static Map<String,String> requestMap = new HashMap<>();
    private static Map<Integer,String> messages = new HashMap<>();
    private static String message = "Thanks For Contacting Spice Care.  Itinerary for your flight with PNR No ZYN4XG to Srinagar is available at http://bit.ly/23oclh6 and your flight is on time";
    private static String message1 = "Thanks For Contacting Spice Care. Itinerary for your flight with PNR No ZYN4XG to Srinagar is available at http://bit.ly/1RIXOIQ and your flight is on time";
    private static String message2 = "Thanks For Contacting Spice Care. Itinerary for your flight with PNR No ZYN4XG to Srinagar is available at http://bit.ly/20eignc and your flight is on time";
    private static String message3 = "Thanks For Contacting Spice Care. Itinerary for your flight with PNR No ZYN4XG to Srinagar is available at http://bit.ly/1SUUr0J and your flight is on time";

    private static String failureMessage = "No flight is booked with us for you. Visit spicejet.com to book for your future journey";

    public CallBroadcastReciver() {
        messages.put(0,message);
        messages.put(1,message1);
        messages.put(2,message2);
        messages.put(3,message3);
    }
    @Override
    public void onReceive(Context context, Intent intent) {

        try{
            String state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);
            if(state.equals(TelephonyManager.EXTRA_STATE_RINGING)) {
                String callerPhoneNumber = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);
                if (requestMap.get(callerPhoneNumber) == null) {
                    SmsManager sms = SmsManager.getDefault();
                    requestMap.put(callerPhoneNumber,message);
                    if (new Random().nextInt(10) != 6) {
                        sms.sendTextMessage(callerPhoneNumber, null, messages.get(new Random().nextInt(4)), null, null);
                    } else {
                        sms.sendTextMessage(callerPhoneNumber, null, failureMessage, null, null);
                    }
                }
                Toast.makeText(context, "Phone Is Ringing " + callerPhoneNumber, Toast.LENGTH_LONG).show();

            }
        }
        catch(Exception e){e.printStackTrace();}
    }
}
