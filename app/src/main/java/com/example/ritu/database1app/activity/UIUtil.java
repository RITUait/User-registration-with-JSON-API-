package com.example.ritu.database1app.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;

/**
 * Created by ritu on 1/9/2018.
 */
//it has reusable code (class UIUtil )
public class UIUtil {
    private static ProgressDialog mProgressDialog;
    private static Object mObject = new Object();

    public static void startProgressDialog(Context context, String message) {
        try {
            synchronized (mObject) {
                if (mProgressDialog == null) {
                    mProgressDialog = ProgressDialog.show(context, "", message);
                    mProgressDialog.setIndeterminate(true);
                }
            }
        } catch (Exception e) {
            Log.e("Exception", "startProgressDialog ------------------------------: " + e.toString());
            e.printStackTrace();
        }
    }

    public static void stopProgressDialog(Context context) {
        try {
            synchronized (mObject) {
                if (mProgressDialog != null && mProgressDialog.isShowing()) {
                    mProgressDialog.dismiss();
                    mProgressDialog = null;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
