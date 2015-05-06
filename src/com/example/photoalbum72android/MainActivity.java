package com.example.photoalbum72android;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import cs213.photoAlbum.control.Control;
import cs213.photoAlbum.model.Album;

public class MainActivity extends Activity {
	private static final int CREATE_ALBUM_ACTIVITY = 0;
	private static final int OPEN_ALBUM_ACTIVITY = 1;
	static Control control = new Control();
	static ArrayList<String> albumNames;
	int count;
	
	static final int OPEN = 0;
	static final int RENAME = 1;
	static final int DELETE = 2;
	static final int CANCEL = 3;
	static int selectedItem=0;
	
	final Context context = this;
	private static ListView ListView;  
	private static ArrayAdapter<String> listAdapter; 
	
	public static void updateList(){
		albumNames.clear();
		for (String str:control.listAlbumNames()){
			albumNames.add(str);
		}
		listAdapter.notifyDataSetChanged();
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		control.addUser("1", "edward");
		
		final Button createAlbumButton = (Button) findViewById(R.id.createAlbumBtn);
		createAlbumButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(context,CreateAlbum.class);
				intent.putExtra("control", control);
				startActivityForResult(intent,CREATE_ALBUM_ACTIVITY);
			}
		});
		
		ListView = (ListView) findViewById( R.id.albumList );
		albumNames = control.listAlbumNames();
		listAdapter = new ArrayAdapter<String>(this, R.layout.simplerow, albumNames); 
		ListView.setAdapter( listAdapter );
    	ListView.setOnItemClickListener(new OnItemClickListener() {
    	    public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
    	      selectedItem = position;	      
    	      optionDialog(); // When clicked, show the option dialog
    	    }
    	});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
    	if (resultCode != 1){
    		return;
    	}
    	updateList();
    }
    
    private void optionDialog() {
    	final String[] items = {"Open", "Rename", "Delete", "Cancel"};

    	AlertDialog.Builder builder = new AlertDialog.Builder(this);
    	builder.setTitle("Options:");
    	builder.setItems(items, new DialogInterface.OnClickListener() {
    	    public void onClick(DialogInterface dialog, int item) {
    	    	
    	    	switch(item){
    	    	case OPEN: display(selectedItem);
    	    					break;
    	    	case RENAME: 	editAlbum(selectedItem);
    	    					break;
    	    	case DELETE: deleteAlbum(selectedItem);
    	    					break;
    	    	case CANCEL: dialog.cancel(); 
    	    					break;
    	    	default: return;
    	    	}
    	    }
    	});
    	AlertDialog alert = builder.create();
    	alert.show();
		
	}
    
    private void display(int index){
    	Intent intent = new Intent(context, OpenAlbum.class);
		intent.putExtra("control", control);
		startActivityForResult(intent,OPEN_ALBUM_ACTIVITY);
		
    }
    
    private void editAlbum(final int item){
    	
    	AlertDialog.Builder rename = new AlertDialog.Builder(context);
    	rename.setTitle("Enter new album name");
    	final EditText input = new EditText(context);
    	rename.setView(input);
    	
    	rename.setPositiveButton("Ok", new DialogInterface.OnClickListener() {	
			@Override
			public void onClick(DialogInterface dialog, int which) {
				String value = input.getText().toString();
				if(value==null || value.equals("")){
					new ErrorMsg(context,"Please enter a name");
				}
				if (control.rename(item, value)){
					updateList();
				}
				else{
					new ErrorMsg(context,"Album with that name already exists");
				}
			}
		});
    	rename.setNegativeButton("Cancel",new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// Canceled
			}
		});
    	rename.show();
    	
    }
    
    private void deleteAlbum(int index){
    	String name = albumNames.get(index);
    	control.deleteAlbum(name);
    	updateList();
    }
}
