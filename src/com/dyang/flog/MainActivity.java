/**
 * 
 */
package com.dyang.flog;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * @author Dokie
 * 
 */
public class MainActivity extends Activity {

	private static final String TAG = "OutputStreamWriter";

	private Button mRunBtn;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_activity);

		mRunBtn = (Button) findViewById(R.id.button1);
		mRunBtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				FLog.getInstance().v(TAG, "verbose");
				FLog.getInstance().d(TAG, "debug");
				FLog.getInstance().i(TAG, "info");
				FLog.getInstance().w(TAG, "warn");
				FLog.getInstance().e(TAG, "error");

				FLog.getInstance().d(TAG, "----------");

				FLog.getInstance().setLogLevel(FLog.WARN);
				;
				FLog.getInstance().i(TAG, "info");
				FLog.getInstance().w(TAG, "warn");
				FLog.getInstance().e(TAG, "error");

				FLog.getInstance().setLogFolderPath("/sdcard/folderPath/");
				FLog.getInstance().v(TAG, "verbose");
				FLog.getInstance().d(TAG, "debug");
				FLog.getInstance().i(TAG, "info");
				FLog.getInstance().w(TAG, "warn");
				FLog.getInstance().e(TAG, "error");

			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		return super.onCreateOptionsMenu(menu);
	}

}
