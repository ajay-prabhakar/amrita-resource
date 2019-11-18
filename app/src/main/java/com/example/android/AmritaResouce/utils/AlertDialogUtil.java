package com.example.android.AmritaResouce.utils;

import android.content.Context;
import android.content.DialogInterface;
import androidx.appcompat.app.AlertDialog;

public class AlertDialogUtil {

    public static void showAlertDialog(
            Context context, String message, final DialogInterface.OnClickListener action) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder
                .setMessage(message)
                .setNegativeButton(
                        "Close",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                if (action != null) {
                                    action.onClick(dialog, 1);
                                }
                            }
                        })
                .setCancelable(false)
                .create()
                .show();
    }
}
