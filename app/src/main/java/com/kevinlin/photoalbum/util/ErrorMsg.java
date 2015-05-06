package com.kevinlin.photoalbum.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

/**
 * Created by Hongjie Lin on 5/6/2015.
 */
public class ErrorMsg{
    //this is use for copy and pasting for errors
    public ErrorMsg(Context context, String msg){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(msg)
                .setTitle("Error")
                .setPositiveButton("Close", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                })
        ;
        builder.create();
        builder.show();
    }
}
