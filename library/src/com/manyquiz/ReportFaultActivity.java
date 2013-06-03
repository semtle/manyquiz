package com.manyquiz;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.Toast;

public class ReportFaultActivity extends Activity {

	private static final String TAG = ReportFaultActivity.class.getSimpleName();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_report_fault);

		findViewById(R.id.btnSubmitFault).setOnClickListener(
				new SubmitButtonOnClickListener());
	}

	class SubmitButtonOnClickListener implements OnClickListener {
		@Override
		public void onClick(View arg0) {
			sendFaultReport();
		}
	}

	public void sendFaultReport() {
		EditText etFaultDescription = (EditText) findViewById(R.id.etFaultDescription);
		String emailBody = "Problem(s) or Suggestion(s): " + etFaultDescription.getText();

		String pkg = getApplicationContext().getPackageName();
		PackageManager manager = getApplicationContext().getPackageManager();
		try {
			PackageInfo info = manager.getPackageInfo(pkg, 0);
			emailBody += String.format("  [Version: %d/%s]", info.versionCode, info.versionName);
		} catch (NameNotFoundException e) {
			Log.e(TAG, "Could not get package info", e);
		}

		Intent intent = new Intent(Intent.ACTION_SEND);
		intent.setType("message/rfc822");
		intent.putExtra(Intent.EXTRA_EMAIL, new String[] {
				getResources().getString(R.string.fault_email_address) });
		intent.putExtra(Intent.EXTRA_SUBJECT,
				getResources().getString(R.string.fault_email_title));
		intent.putExtra(Intent.EXTRA_TEXT, emailBody);

		try {
			startActivity(Intent.createChooser(intent, "Send mail..."));
		} catch (android.content.ActivityNotFoundException ex) {
			Toast.makeText(ReportFaultActivity.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
		}
	}
}