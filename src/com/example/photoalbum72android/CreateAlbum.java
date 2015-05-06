package com.example.photoalbum72android;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import cs213.photoAlbum.control.Control;
import cs213.photoAlbum.model.Album;


public class CreateAlbum extends Activity {

	final Context context = this;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_album);
		Intent i = getIntent();
		final Control control = (Control)i.getSerializableExtra("control");

		final Button createAlbum = (Button) findViewById(R.id.button_Create);
		createAlbum.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				final EditText userField = (EditText) findViewById(R.id.albumName);
				String albumName = userField.getText().toString();
				if (albumName == null || albumName.equals("")){
					new ErrorMsg(context,"Please enter a name");
				}
				else{
					boolean test = Control.makeAlbum(albumName);
					if (test == false){
						new ErrorMsg(context,"Album not added");
					}
					else{
						Intent intent = new Intent();
						setResult(1, intent);
						//intent.putExtra("control", control);

						finish();
					}
				}
			}
		});
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.create_album, menu);
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