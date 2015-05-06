package com.kevinlin.photoalbum.util;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

import com.kevinlin.photoalbum.R;

/**
 * Created by Hongjie Lin on 5/5/2015.
 */
public class ErrorDialog extends DialogFragment{

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        String msg = getArguments().getString("message", "");
        builder.setMessage("This is very stupid")
                .setTitle("Error")
                .setPositiveButton("Close", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                })
        ;
        AlertDialog sth = builder.create();
        builder.show();
    }
}
