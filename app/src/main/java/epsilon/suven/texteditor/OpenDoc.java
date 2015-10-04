package epsilon.suven.texteditor;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class OpenDoc extends ListActivity {
	protected static final int READ_BLOCK_SIZE = 0;
	int itemsChecked,item;
	ListView listSavedFiles;
	File[] filenames;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ListView lv = getListView();
		lv.setChoiceMode(0);
		File sdCard = Environment.getExternalStorageDirectory();
		File folder = new File(sdCard.getAbsolutePath(), "/TextEditor");
		if(!folder.exists()){
			Toast.makeText(getBaseContext(), "No Folder Exists", Toast.LENGTH_SHORT).show();
		}
		else{
			filenames = folder.listFiles();
			setListAdapter(new ArrayAdapter<File>(this, android.R.layout.simple_list_item_1, filenames));
		}

		lv.setOnItemClickListener(new  OnItemClickListener() {
									  public void onItemClick(AdapterView<?> arg0, View arg1,
															  int arg2, long arg3) {
										  String aData="";
										  File sdCard = Environment.getExternalStorageDirectory();
										  File folder = new File(sdCard.getAbsolutePath(), "/TextEditor");
										  File[] filelist = folder.listFiles();
										  File toOpen = filelist[arg2];
										  try{
											  String str="";
											  BufferedReader reader = new BufferedReader(new FileReader(toOpen));
											  while ((str = reader.readLine()) != null)
											  {
												  aData=aData + str + "\n";
											  }
											  Toast.makeText(getBaseContext(), aData, Toast.LENGTH_SHORT).show();
										  }
										  catch(Exception e){}
										  Bundle extras = new Bundle();
										  extras.putString("Name", aData);
										  Intent i = new Intent(OpenDoc.this,NewDoc.class);
										  i.putExtras(extras);
										  startActivity(i);
									  }
								  }
		);
	}
}