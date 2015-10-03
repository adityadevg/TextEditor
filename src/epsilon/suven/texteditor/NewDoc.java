package epsilon.suven.texteditor;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

public class NewDoc extends Activity {
	EditText textBox;
    String textcontent=null;
    String filename=null;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newdoc);
        textBox = (EditText) findViewById(R.id.newtextedit);
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu){
		super.onCreateOptionsMenu(menu);
		menu.add(0,1,1,"Save").setIcon(R.drawable.ic_save);;
        menu.add(1,2,2,"Cancel").setIcon(R.drawable.ic_cancel);;
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item){
			switch (item.getItemId()){
	        case 1:
	    		   //---Calls a Dialog Window ---
	        	final EditText filenametext = new EditText(this);
	        	final AlertDialog.Builder alert = new AlertDialog.Builder(this);
				alert.setView(filenametext);
				alert.setIcon(R.drawable.ic_browse);
		        alert.setTitle("Type File Name"); 
		        alert.setMessage("Enter File Name"); 
		        alert.setNegativeButton("Cancel",
		        		new DialogInterface.OnClickListener() {
		        		public void onClick(DialogInterface dialog, int whichButton) {
		        		dialog.cancel();
		        		}
		        		});
		        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
		            public void onClick(DialogInterface dialog, int whichbutton) {
		            	filename = filenametext.getText().toString();
		            	textcontent = textBox.getText().toString();
		            	if((filename.isEmpty())||(filename.contains("/n"))||isValidName(filename))
		                {
		                    CharSequence error = "Please enter a valid file name";
		                    Toast toast = Toast.makeText(getApplicationContext(), error,Toast.LENGTH_LONG);
		                    toast.show();
		                }
		        
		            	else{
		             		FileOutputStream fos;
							try {
								fos = openFileOutput(filename, Context.MODE_PRIVATE);
								fos.write(textcontent.getBytes());
								fos.close();
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
		             		
		    		    //---display file saved message---
		    		    Toast.makeText(getBaseContext(), "File saved successfully!", Toast.LENGTH_SHORT).show();
		    		    //public abstract File getFileStreamPath (String name)
		    		    Toast.makeText(getBaseContext(),"File Path: "+getFileStreamPath(filename),Toast.LENGTH_LONG).show();
		            	}
		    			}
	    			});
		           	alert.create(); // this returns the activity the Dialog object
		        	alert.show();
		        	Intent i = new Intent("epsilon.suven.texteditor.OpenDoc");
					Bundle extras = new Bundle();
					extras.putString(filename,textcontent); // key - value pair
					i.putExtras(extras);
		    		break;
	        case 2:
	        	startActivity(new Intent(this, TextEditorActivity.class)
	        	.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
	        	break;
	        }
			return false; 
        }
	public boolean isValidName(String testfilename)
	{
		Pattern p = Pattern.compile("^[a-zA-z0-9 ]*$");
	    Matcher matcher = p.matcher(testfilename);
	    
	    if(matcher.matches())
	    	return false;
	    else
	    	return true;
	}

}
