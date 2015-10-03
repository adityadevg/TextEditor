package epsilon.suven.texteditor;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class OpenDoc extends Activity {
	
	protected static final int READ_BLOCK_SIZE = 0;
	String[] SavedFiles = { "Sample File 1", "Sample File 2", "Sample File 3" };
	int itemsChecked,item;
	ListView listSavedFiles;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.opendoc);
        Button btn = (Button) findViewById(R.id.retrievebutton);
		btn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// calls onCreateDialog() method
				showDialog(0);
			}
		});
	}
	@Override  // onCreate dialog is a CallBack method. It makes the calling Activity the owner of the dialog  
	protected Dialog onCreateDialog(int id) { // overriding the onCreateDialog Method 
		switch (id) {
		case 0:
			return new AlertDialog.Builder(this)	// dot . operator is a short cut to invoke methods to an object
			.setIcon(R.drawable.ic_browse)
			.setTitle("Files present on Cloud")
			.setSingleChoiceItems(SavedFiles, -1, new DialogInterface.OnClickListener() {
			    public void onClick(DialogInterface dialog, int itemsChecked) {
			        Toast.makeText(getApplicationContext(), SavedFiles[itemsChecked]+" selected", Toast.LENGTH_SHORT).show();
			        item=itemsChecked;
				}
			}
					)
			.setPositiveButton("OK", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog,int whichButton){
					Toast.makeText(getBaseContext(),
							"OK clicked!", Toast.LENGTH_SHORT).show();     // toast message to show instant msg.
					//showText(item);
					TextView view = (TextView) findViewById(R.id.textView1);
			        Intent intent = getIntent();
			        view.setText("Hello");

					}
			})
			.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog,int whichButton){
					Toast.makeText(getBaseContext(),
							"Cancel clicked!", Toast.LENGTH_LONG).show(); // Toast.LENGTH_SHORT means short time
				}
			})
				.create(); // this returns the activity the Dialog object
		}
		
		return null;
	}
/*	void showText(int temp)
	{
		FileInputStream fIn = null;
		InputStreamReader isr = null;
		try{
			char[] inputBuffer = new char[1024];
			String data = null;
			fIn = openFileInput(SavedFiles[temp]);
			isr = new InputStreamReader(fIn);
			isr.read(inputBuffer);
			data = new String(inputBuffer);
			TextView tv =(TextView) findViewById(R.id.newtextedit);
			tv.setText(data);
			isr.close();
			fIn.close();
			
		}catch(IOException e){
			e.printStackTrace(System.err);
			}
	}*/
}
