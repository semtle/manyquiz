package com.manyquiz;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.Toast;

public class QuestionSuggestionActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_submit_question);

		findViewById(R.id.btnSubmitQuestion).setOnClickListener(
				new SubmitButtonOnClickListener());
	}

	class SubmitButtonOnClickListener implements OnClickListener {
		@Override
		public void onClick(View arg0) {
			sendQuestionSuggestion();
		}
	}

	public void sendQuestionSuggestion() {
		EditText etSuggestedQuestion = (EditText) findViewById(R.id.etSuggestedQuestion);
		String emailBody = "Question: " + etSuggestedQuestion.getText();

		Intent intent = new Intent(Intent.ACTION_SEND);
		intent.setType("message/rfc822");
		intent.putExtra(Intent.EXTRA_EMAIL, new String[] {
				getResources().getString(R.string.question_email_address) });
		intent.putExtra(Intent.EXTRA_SUBJECT,
				getResources().getString(R.string.question_email_title));
		intent.putExtra(Intent.EXTRA_TEXT, emailBody);

		try {
			startActivity(Intent.createChooser(intent, "Send mail..."));
		} catch (android.content.ActivityNotFoundException ex) {
			Toast.makeText(QuestionSuggestionActivity.this,
					"There are no email clients installed.", Toast.LENGTH_SHORT)
					.show();
		}
	}
}