package epsilon.suven.texteditor;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.backup.BackupManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NewDoc extends Activity {
	EditText textBox;
	String textcontent=null;
	String filename=null;
	FileOutputStream fos;
	String defaultName="";
	static final String TAG = "BRActivity";
	static final Object[] sDataLock = new Object[0];
	String addContent;
	File folder;
	File FilenameDirectory;


	/** Also supply a global standard file name for everyone to use */
	static final String DATA_FILE_NAME = "";

	/** Cache a reference to our persistent data file */
	File mDataFile;

	/** Also cache a reference to the Backup Manager */
	BackupManager mBackupManager;

	/** Set up the activity and populate its UI from the persistent data. */

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.newdoc);
		textBox = (EditText) findViewById(R.id.newtextedit);
		textBox.setHint("Enter your data here...");

		try{
			Intent i = getIntent();
			Bundle extras = i.getExtras();
			if (extras!=null)
			{
				//defaultName = extras.getString("B1");

			}
			else{
				//Toast.makeText(getBaseContext(), "ERROR", 1000).show();
			}
			defaultName=extras.getString("Name");
			Toast.makeText(getBaseContext(), defaultName, Toast.LENGTH_SHORT).show();
			textBox.setText(defaultName);
		}
		catch(Exception e){}
		/** Set up our file bookkeeping */
		mDataFile = new File(getFilesDir(), NewDoc.DATA_FILE_NAME);

		/** It is handy to keep a BackupManager cached */
		mBackupManager = new BackupManager(this);

		/**
		 * Finally, build the UI from the persistent store
		 */
		File sdCard = Environment.getExternalStorageDirectory();
		folder = new File(sdCard.getAbsolutePath(), "/TextEditor");
		if(!folder.exists()){
			folder.mkdirs();
		}
		FilenameDirectory = new File(folder,"FileNameDir.txt");
		try {
			FilenameDirectory.createNewFile();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}


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
				//---Calls a Dialog Window ---//
				final EditText filenametext = new EditText(this);
				filenametext.setSingleLine();
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
						if(!filename.contains(".txt")){
							filename = filename + ".txt";
						}
						textcontent = textBox.getText().toString();


						if((filename.isEmpty()))
						{
							CharSequence error = "Please enter a valid file name";
							Toast toast = Toast.makeText(getApplicationContext(), error,Toast.LENGTH_LONG);
							toast.show();
						}

						else{

							File saveAs = new File(folder, filename);
							if(saveAs.exists())
							{
								Toast.makeText(getBaseContext(), "File name Already exists!", Toast.LENGTH_LONG).show();
								Toast.makeText(getBaseContext(), "Save using different File Name", Toast.LENGTH_LONG).show();
							}
							else{

								try{
									saveAs.createNewFile();
									FileWriter fWriter = new FileWriter(saveAs);
									fWriter.write(textcontent);

									defaultName=textcontent;
									Toast.makeText(getBaseContext(), "Default Name : "+defaultName , Toast.LENGTH_LONG).show();
									fWriter.flush();
									fWriter.close();
									FileInputStream fis = new FileInputStream(FilenameDirectory);
									DataInputStream in = new DataInputStream(fis);
									String fileData="";
									String line="";
									while((line = in.readLine()) != null){
										fileData += line;
									}
									fileData += filename + ";";

									fWriter = new FileWriter(FilenameDirectory);
									fWriter.write(fileData);
									fWriter.flush();
									fWriter.close();
									//---display file saved message---
									Toast.makeText(getBaseContext(), "File saved successfully!", Toast.LENGTH_SHORT).show();
									//public abstract File getFileStreamPath (String name)
									Toast.makeText(getBaseContext(),"File Path: " + saveAs.getAbsolutePath(),Toast.LENGTH_LONG).show();
									addContent=defaultName;
								}
								catch(Exception e){

								}
							}
						}}
				});
				alert.create(); // this returns the activity the Dialog object
				alert.show();
				mBackupManager.dataChanged();
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

	/** Now that we've processed the file, build the UI outside the lock */
	//meducation.check(whichEducation);
	//mandroid.setChecked(addAndroid);
	//mphp.setChecked(addPHP);
	/**
	 * Handy helper routine to write the UI data to a file.
	 */
	void writeDataToFileLocked(RandomAccessFile file,
							   String addContent)
			throws IOException {
		file.setLength(0L);
		file.writeChars(addContent);
		Log.v(TAG, "File content Here: "+addContent);
	}
	/**
	 * Another helper; this one reads the current UI state and writes that
	 * to the persistent store, then tells the backup manager that we need
	 * a backup.
	 */
	void recordNewUIState() {
//        boolean addAndroid = mandroid.isChecked();
		//      boolean addPHP = mphp.isChecked();
		//    int whichEducation = meducation.getCheckedRadioButtonId();
		try {
			synchronized (NewDoc.sDataLock) {
				RandomAccessFile file = new RandomAccessFile(mDataFile, "rw");
				writeDataToFileLocked(file,addContent);
			}
		} catch (IOException e) {
			Log.e(TAG, "Unable to record new UI state");
		}

		mBackupManager.dataChanged();
	}
}