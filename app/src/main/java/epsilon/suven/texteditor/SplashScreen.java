package epsilon.suven.texteditor;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

public class SplashScreen extends Activity{
	private static long SLEEP_TIME = 3;	// Sleep for some time

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash_screen);

		// Start timer and launch main activity
		IntentLauncher launcher = new IntentLauncher();
		launcher.start();
	}
	//To disable Back Button
	public void onBackPressed() {}

	private class IntentLauncher extends Thread {
	@Override
   	//Sleep for some time and then return to Main Activity
	public void run() {
		try {
        // Sleeping
	    Thread.sleep(SLEEP_TIME*1000);
	    }
	    catch (Exception e) {
	    	 Toast.makeText(getApplicationContext(), "Splash Exception Found", Toast.LENGTH_LONG).show();
	    }
	    
		// Go back to main activity
	    Intent intent = new Intent(SplashScreen.this, TextEditorActivity.class);
	    /*This line initiates an Intent to "go" (not return) to the main screen and clear 
	     * the "back" history, thereby making it seem that you have returned to 
	     * the main screen. This can be confirmed by clicking the back button.
	     * The Application should exit.
	    */
	    startActivity(intent);
	    SplashScreen.this.finish();
	    }
	}
}