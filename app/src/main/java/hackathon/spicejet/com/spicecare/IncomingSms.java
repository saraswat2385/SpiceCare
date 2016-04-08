package hackathon.spicejet.com.spicecare;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

public class IncomingSms extends BroadcastReceiver {
     
    // Get the object of SmsManager
    final SmsManager sms = SmsManager.getDefault();

    private static Map<String,String> requests  = new HashMap<>();
     
    public void onReceive(Context context, Intent intent) {
        final Bundle bundle = intent.getExtras();
        try {
            if (bundle != null) {
                 
                final Object[] pdusObj = (Object[]) bundle.get("pdus");
                 
                for (int i = 0; i < pdusObj.length; i++) {
                     
                    SmsMessage currentMessage = SmsMessage.createFromPdu((byte[]) pdusObj[i]);
                    String phoneNumber = currentMessage.getDisplayOriginatingAddress();
                     
                    String senderNum = phoneNumber;
                    String message = currentMessage.getDisplayMessageBody();
 
                    Log.i("SmsReceiver", "senderNum: " + senderNum + "; message: " + message);

                    sendMessage(message.toLowerCase(),senderNum);
                   // Show Alert
                    int duration = Toast.LENGTH_LONG;
                    Toast toast = Toast.makeText(context, 
                                 "senderNum: "+ senderNum + ", message: " + message, duration);

                    toast.show();
                     
                } // end for loop
              } // bundle is null
 
        } catch (Exception e) {
            Log.e("SmsReceiver", "Exception smsReceiver" +e);
             
        }
    }

    private void sendMessage(String command,String number)
    {
        String message = "Thanks For Contacting Spice Care. ";

        if (command.equalsIgnoreCase("send itinerary") || command.equalsIgnoreCase("send ticket")){
            message += "Itinerary for your Delhi-Srinagar flight is available at http://bit.ly/23oclh6";
        }else if (command.equalsIgnoreCase("any delay")){
            message += "Your Flight between Delhi-Srinagar is on Schedule at 8:50AM on Wed 02 Mar 16.";
        }else if (command.contains("meal")){
            if (command.contains("veg")){
                message += "Veg meal is booked for your journey. Please pay INR 250 to flight crew for same.";
            }else if (command.contains("nonveg")){
                message += "NonVeg meal is booked for your journey. Please pay INR 250 to flight crew for same.";
            }
        }else if (command.contains("flight detail")){
            message += "Your flight SG-160@8:50 on Wed 02 Mar 16. Waiting for see you on Board";
        } else if (command.contains("need")) {
            if (command.contains("wheelchair")) {
                message += "We got your request for wheelchair.Contact frontdesk on airport for same";
            } else if (command.contains("stroller")) {
                message += "We got your request for stroller.Contact frontdesk on airport for same";
            }
        }
        if (requests.get(number+command)==null) {
            SmsManager sms = SmsManager.getDefault();
            sms.sendTextMessage(number, null, message, null, null);
            requests.put(number+command,message);
        }
    }
}