package com.manyquiz; 

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;

import com.google.ads.AdRequest;
import com.google.ads.AdSize;
import com.google.ads.AdView;

public class IntroActivity extends Activity {

	private static final String TAG = IntroActivity.class.getSimpleName();
	
	private int gameMode = 0; //difficulty/mode

	private Button btnStartQuiz;
	private Button btnExit;
	private AdView adView;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d(TAG, "++onCreate");
		setContentView(R.layout.activity_intro);

		if (((QuizApplication)this.getApplication()).isLiteVersion()) {
			findViewById(R.id.lite_watermark).setVisibility(View.VISIBLE);

			// TODO
			// ... impose limitations of the lite version ...
			
			displayIntroAds();
		}

		btnStartQuiz = (Button) findViewById(R.id.btn_startQuiz);
		btnStartQuiz.setOnClickListener(new NextClickListener());
		btnExit = (Button) findViewById(R.id.btn_exit);
		btnExit.setOnClickListener(new ExitClickListener());
	}

	class ExitClickListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			finish();	
		}
	}

	class NextClickListener implements OnClickListener {
		@Override
		public void onClick(View arg0) {
			gameMode = getSelectedDifficulty();

			Bundle bundle = new Bundle();
			bundle.putInt(QuizActivity.GAME_MODE, gameMode);

			Intent intent = new Intent(IntroActivity.this, QuizActivity.class);
			intent.putExtras(bundle);
			startActivity(intent);
		}
	}

	private int getSelectedDifficulty() {
		RadioButton option2 = (RadioButton) findViewById(R.id.radio_difficulty2);
		RadioButton option3 = (RadioButton) findViewById(R.id.radio_difficulty3);
		RadioButton option4 = (RadioButton) findViewById(R.id.radio_difficulty4);
		RadioButton option5 = (RadioButton) findViewById(R.id.radio_difficulty5);

		//Check which difficulty radio button was selected
		if (option2.isChecked() == true) {
			gameMode = 2;
		}
		else if (option3.isChecked() == true) {
			gameMode = 3;
		}
		else if (option4.isChecked() == true) {
			gameMode = 4;
		}
		else if (option5.isChecked() == true) {
			gameMode = 5;
		}
		return gameMode;
	}
	
	public void displayIntroAds() {
	    adView = new AdView(this, AdSize.BANNER, QuizActivity.ADMOB_UNIT_ID);
	    adView.setBackgroundColor(Color.BLACK);
	    
	    LinearLayout layout = (LinearLayout)findViewById(R.id.lowerIntroScreen);
	    layout.addView(adView);
	    adView.loadAd(new AdRequest());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	@Override  
	protected void onDestroy() {
		Log.d(TAG, "++onDestroy");
		super.onDestroy();
	}

} //end of activity class
