package com.mcon521.othello.lib;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.content.ContextCompat;
import androidx.preference.PreferenceManager;

import com.mcon521.othello.R;

import static androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM;
import static androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_NO;
import static androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_YES;

public class Utils
{
    /*
    Night Mode-related helper methods
     */
    public static void setNightModeOnOffFromPreferenceValue(Context context, String keyNightMode) {
        setNightModeOnOrOff(isNightModePrefOn(context, keyNightMode));
    }

    public static void setNightModeOnOrOff(boolean setToOn) {
        int onMode  =  Build.VERSION.SDK_INT < 28 ? MODE_NIGHT_YES : MODE_NIGHT_FOLLOW_SYSTEM;
        AppCompatDelegate.setDefaultNightMode(setToOn ? onMode : MODE_NIGHT_NO);
    }

    private static boolean isNightModePrefOn(Context context, String keyNightMode) {
        SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return defaultSharedPreferences.getBoolean(keyNightMode, true);
    }
    // AlertDialog
    // -----------
    /**
     * Shows an Android (nicer) equivalent to JOptionPane
     *
     * @param strTitle Title of the Dialog box
     * @param strMsg   Message (body) of the Dialog box
     */
    private static void showAlertDialog (Context context, String strTitle, String strMsg,
                                         DialogInterface.OnClickListener okListener,
                                         DialogInterface.OnClickListener cancelListener)
    {
        AlertDialog.Builder alertDialogBuilder = getDialogBasicsADB (context, strTitle, strMsg);

        if (okListener == null) {
            // create an OK button only and use a dummy listener for that button and the dialog
            alertDialogBuilder.setNeutralButton (context.getString (android.R.string.ok),
                                                 getNewEmptyOnClickListener ());
        }
        else {
            alertDialogBuilder.setPositiveButton (context.getString (android.R.string.ok),
                                                  okListener);
            alertDialogBuilder.setNegativeButton (context.getString (android.R.string.cancel),
                                                  cancelListener);
        }

        // Create and Show the Dialog
        alertDialogBuilder.show ();
    }

    @NonNull
    private static AlertDialog.Builder getDialogBasicsADB (Context context, String strTitle,
                                                           String strMsg)
    {
        // Create the AlertDialog.Builder object
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder (context);

        // Use the AlertDialog's Builder Class methods to set the title, icon, message, et al.
        // These could all be chained as one long statement, if desired
        alertDialogBuilder.setTitle (strTitle);
        alertDialogBuilder.setMessage (strMsg);
        alertDialogBuilder.setIcon (ContextCompat.getDrawable (context, R.mipmap.ic_launcher));
        alertDialogBuilder.setCancelable (true);
        return alertDialogBuilder;
    }

    @SuppressWarnings("WeakerAccess") @NonNull
    public static DialogInterface.OnClickListener getNewEmptyOnClickListener ()
    {
        return new DialogInterface.OnClickListener ()
        {
            @Override
            public void onClick (DialogInterface dialog, int which)
            {

            }
        };
    }

    public static void showInfoDialog (Context context, int titleID, int msgID)
    {
        showInfoDialog (context, context.getString (titleID), context.getString (msgID));
    }

    @SuppressWarnings("WeakerAccess")
    public static void showInfoDialog (Context context, String strTitle, String strMsg)
    {
        showAlertDialog (context, strTitle, strMsg);
    }

    @SuppressWarnings("WeakerAccess")
    public static void showOkCancelDialog (Context context, String strTitle, String strMsg,
                                           DialogInterface.OnClickListener okListener,
                                           DialogInterface.OnClickListener cancelListener)
    {
        showAlertDialog (context, strTitle, strMsg, okListener, cancelListener);
    }

    public static void showYesNoDialog (Context context, String strTitle, String strMsg,
                                        DialogInterface.OnClickListener okListener,
                                        DialogInterface.OnClickListener cancelListener)
    {
        showAlertDialog (context, strTitle, strMsg, okListener, cancelListener);
    }

    private static void showAlertDialog (Context context, String strTitle, String strMsg)
    {
        showAlertDialog (context, strTitle, strMsg, null, null);
    }
}