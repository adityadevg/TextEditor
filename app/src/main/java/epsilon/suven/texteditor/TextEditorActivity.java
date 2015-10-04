package epsilon.suven.texteditor;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class TextEditorActivity extends Activity {
    
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
      	
	Button newdoc = (Button) findViewById(R.id.newdocbutton);
    newdoc.setOnClickListener(new View.OnClickListener() {
    	public void onClick(View v1) {
    		Intent newDocIntent = new Intent(TextEditorActivity.this, NewDoc.class);
    		startActivity(newDocIntent);
        }
      });
    
    Button opendoc = (Button) findViewById(R.id.opendocbutton);
    opendoc.setOnClickListener(new View.OnClickListener() {
    	public void onClick(View v2) {
    		Intent openDocIntent = new Intent(TextEditorActivity.this, OpenDoc.class);
    		startActivity(openDocIntent);
        }
      });
}
}
