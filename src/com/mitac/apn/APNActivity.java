package com.mitac.apn;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.view.View;
import android.widget.Toast;
import android.util.Log;

import android.content.Context;
import android.telephony.TelephonyManager;
import android.telephony.SubscriptionManager;
import android.telephony.SubscriptionInfo;

import java.util.List;




public class APNActivity extends Activity {

    private static String TAG = "APNActivity";

    //TextView tip_old_apn;
    //TextView tip_new_apn;
    //LinearLayout tipBack;


    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        //tip_old_apn = (TextView) findViewById(R.id.old_apn);
        //tip_new_apn = (TextView) findViewById(R.id.new_apn);
        //tipBack = (LinearLayout) findViewById(R.id.background);
        Log.d(TAG, "onCreate");
        TelephonyManager tm = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
        String imei = tm.getDeviceId();
        Log.d(TAG, "imei: "+imei);
        String tel = tm.getLine1Number();
        Log.d(TAG, "Line1Number: "+tel);
        String iccid =tm.getSimSerialNumber();
        Log.d(TAG, "iccid: "+iccid);
        String subId =tm.getSubscriberId();
        Log.d(TAG, "subId: "+subId);
        //for AOS10
        Log.d(TAG, "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
        String simSerialNo="";
        SubscriptionManager subsManager = (SubscriptionManager) this.getSystemService(Context.TELEPHONY_SUBSCRIPTION_SERVICE); 
        List<SubscriptionInfo> subsList = subsManager.getActiveSubscriptionInfoList();
        if (subsList!=null) {
            for (SubscriptionInfo subsInfo : subsList) {
                if (subsInfo != null) {
                    simSerialNo  = subsInfo.getIccId();
                    Log.d(TAG, "ICCID: "+simSerialNo);
                }
            }
        }
        Log.d(TAG, "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop");
    }

    public void onSetAPN(View v) {
        String name = "Astrata";
        String apn = "inetd.vodafone.iot";
        int apn_id = APNUtil.getAPN(APNActivity.this, apn);
        if(apn_id == -1){
            apn_id = APNUtil.addAPN(APNActivity.this, apn, name);
            if(apn_id == -1){
                Log.e(TAG, "faile to set APN");
            }
        }

        if(apn_id != -1){
            APNUtil.setAPN(APNActivity.this, apn_id);
        }

        //Toast
        Toast.makeText(getApplication(),
                    getApplication().getString(R.string.toast_new_apn),
                    Toast.LENGTH_LONG).show();
    }

}
