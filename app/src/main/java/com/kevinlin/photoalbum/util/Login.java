package com.kevinlin.photoalbum.util;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.kevinlin.photoalbum.R;
import com.kevinlin.photoalbum.control.Control;
import com.kevinlin.photoalbum.model.User;


public class Login extends ActionBarActivity {

    final Context context = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final Control control = new Control();
        if (!control.backend.getUsers().contains(new User(null, "admin"))){
            control.addUser("admin", "admin");
        }

        final Button login = (Button) findViewById(R.id.button_Login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText userField = (EditText) findViewById(R.id.User);
                String user = userField.getText().toString();
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("This is very stupid")
                        .setTitle("Error")
                        .setPositiveButton("Close", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.dismiss();
                            }
                        })
                ;
                builder.create();
                builder.show();
                /*
                if(control.login(user) && !user.equalsIgnoreCase("admin")){
                    new ErrorMsg("The user " + user + " does not exist!");
                }else{
                    if(user.equalsIgnoreCase("admin"))
                    {
                        //TODO admin activity
                    }
                    else
                    {
                        //TODO user activity
                    }
                }
                */
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            // Handle action bar item clicks here. The action bar will
            // automatically handle clicks on the Home/Up button, so long
            // as you specify a parent activity in AndroidManifest.xml.
            int id = item.getItemId();

            //noinspection SimplifiableIfStatement
            if (id == R.id.action_settings) {
                return true;
            }

            return super.onOptionsItemSelected(item);
    }
}
