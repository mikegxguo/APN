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




public class APNActivity extends Activity {

    private static String TAG = "APNActivity";


    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        Log.d(TAG, "onCreate");
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
        String name = "EROAD";
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
