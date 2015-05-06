package com.kevinlin.photoalbum.util;

import android.app.DialogFragment;
import android.os.Bundle;

/**
 * Created by Hongjie Lin on 5/6/2015.
 */
public class ErrorMsg {

    public ErrorMsg(String msg){
        DialogFragment dialog = new ErrorDialog();
        Bundle args = new Bundle();
        args.putString("message", msg);
        dialog.setArguments(args);
    }
}
