package com.mitac.apn;




import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.telephony.TelephonyManager;
import android.util.Log;
 

public class APNUtil {
    public static Uri APN_URI = Uri.parse("content://telephony/carriers");
    public static Uri CURRENT_APN_URI = Uri.parse("content://telephony/carriers/preferapn");



    public static int addAPN(Context context, String apn, String name) {
        int id = -1;
        String NUMERIC = getSIMInfo(context);
        if (NUMERIC == null) {
            return -1;
        }

        ContentResolver resolver = context.getContentResolver();
        ContentValues values = new ContentValues();
        values.put("name", name);                                     //APN name
        values.put("apn", apn);                                       //apn identity
        values.put("type", "default,ia");                             //apn type
        values.put("numeric", NUMERIC);
        values.put("mcc", NUMERIC.substring(0, 3));
        values.put("mnc", NUMERIC.substring(3, NUMERIC.length()));
        values.put("proxy", "");                                        //proxy
        values.put("port", "");                                         //port
        values.put("mmsproxy", "");                                     //MMS proxy
        values.put("mmsport", "");                                      //MMS port
        values.put("user", "");                                         //user name
        values.put("server", "");                                       //server
        values.put("password", "");                                     //password
        values.put("mmsc", "");                                         //MMSC
        Cursor c = null;
        Uri newRow = resolver.insert(APN_URI, values);
        if (newRow != null) {
            c = resolver.query(newRow, null, null, null, null);
            int idIndex = c.getColumnIndex("_id");
            c.moveToFirst();
            id = c.getShort(idIndex);
        }
        if (c != null)
            c.close();

        return id;
    }

 

    public static String getSIMInfo(Context context) {
        TelephonyManager iPhoneManager = (TelephonyManager)context
                .getSystemService(Context.TELEPHONY_SERVICE);
                return iPhoneManager.getSimOperator();
    }
 

    //Set APN
    public static void setAPN(Context context, int id) {
        ContentResolver resolver = context.getContentResolver();
        ContentValues values = new ContentValues();
        values.put("apn_id", id);
        resolver.update(CURRENT_APN_URI, values, null, null);
    }

 

    public static int getAPN(Context context, String apn){
        ContentResolver resolver = context.getContentResolver();
        String apn_condition = String.format("apn like '%%%s%%'", apn);
        Cursor c = resolver.query(APN_URI, null, apn_condition, null, null);

        //If APN exists
        if (c != null && c.moveToNext()) {
            int id = c.getShort(c.getColumnIndex("_id"));
            String name1 = c.getString(c.getColumnIndex("name"));
            String apn1 = c.getString(c.getColumnIndex("apn"));

            Log.e("APN has exist", id + name1 + apn1);
            return id;
        }

        return -1;
    }
}
