package com.manyquiz.android.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.manyquiz.R;
import com.manyquiz.android.application.QuizApplication;
import com.manyquiz.android.db.QuizSQLiteOpenHelper;
import com.manyquiz.common.quiz.impl.Category;
import com.manyquiz.common.quiz.impl.GameMode;
import com.manyquiz.common.quiz.impl.Level;
import com.manyquiz.common.quiz.impl.QuestionsNumChoice;
import com.manyquiz.android.util.EmailTools;
import com.manyquiz.common.util.IMultiChoiceControl;
import com.manyquiz.common.util.IPreferenceEditor;
import com.manyquiz.common.util.ISingleChoiceControl;
import com.manyquiz.common.util.MultiChoiceControl;
import com.manyquiz.android.util.SimpleSharedPreferenceEditor;
import com.manyquiz.common.util.SingleChoiceControl;

import java.util.ArrayList;
import java.util.List;

public abstract class QuizActivityBase extends Activity {

    private QuizSQLiteOpenHelper helper;

    protected QuizSQLiteOpenHelper getHelper() {
        return helper;
    }

    protected void setHelper(QuizSQLiteOpenHelper helper) {
        this.helper = helper;
    }

    protected boolean isLiteVersion() {
        return ((QuizApplication) this.getApplication()).isLiteVersion();
    }

    protected void checkAndSetupForLiteVersion() {
        if (isLiteVersion()) {
            findViewById(R.id.lite_watermark).setVisibility(View.VISIBLE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.common, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.menu_settings) {
            Intent intent = new Intent(getApplicationContext(), SettingsActivity.class);
            startActivity(intent);
            return true;
        }
        if (itemId == R.id.menu_contact) {
            EmailTools.send(this, R.string.subject_contact);
            return true;
        }
        if (itemId == R.id.menu_rate) {
            openURL(getString(R.string.url_rate));
            return true;
        }
        return false;
    }

    private void openURL(String url) {
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
    }

    @Override
    protected void onDestroy() {
        if (helper != null) {
            helper.close();
        }
        super.onDestroy();
    }

    protected SharedPreferences getSharedPreferences() {
        return PreferenceManager.getDefaultSharedPreferences(this);
    }

    protected ISingleChoiceControl createLevelChoiceControl() {
        String levelPrefKey = getString(R.string.pref_level);
        IPreferenceEditor levelPreferenceEditor = new SimpleSharedPreferenceEditor(getSharedPreferences(), levelPrefKey, "0");
        List<Level> levels = getHelper().getLevels();
        return new SingleChoiceControl(levelPreferenceEditor, levels);
    }

    protected ISingleChoiceControl createModeChoiceControl() {
        String modePrefKey = getString(R.string.pref_mode);
        GameMode defaultGameMode = new GameMode.ScoreAsYouGo(getString(R.string.mode_score_as_you_go));
        IPreferenceEditor modePreferenceEditor =
                new SimpleSharedPreferenceEditor(getSharedPreferences(), modePrefKey, defaultGameMode.value);
        List<GameMode> modes = new ArrayList<GameMode>();
        modes.add(defaultGameMode);
        modes.add(new GameMode.ScoreInTheEnd(getString(R.string.mode_score_in_the_end)));
        modes.add(new GameMode.SuddenDeath(getString(R.string.mode_suddendeath)));
        return new SingleChoiceControl(modePreferenceEditor, modes);
    }

    protected ISingleChoiceControl createQuestionsNumChoiceControl() {
        String numPrefKey = getString(R.string.pref_questions_num);
        IPreferenceEditor questionsNumPreferenceEditor =
                new SimpleSharedPreferenceEditor(getSharedPreferences(), numPrefKey, "15");
        List<QuestionsNumChoice> questionsNumChoices = new ArrayList<QuestionsNumChoice>();
        questionsNumChoices.add(new QuestionsNumChoice(100));
        questionsNumChoices.add(new QuestionsNumChoice(50));
        questionsNumChoices.add(new QuestionsNumChoice(15));
        questionsNumChoices.add(new QuestionsNumChoice(10));
        questionsNumChoices.add(new QuestionsNumChoice(5));
        questionsNumChoices.add(new QuestionsNumChoice(3));
        return new SingleChoiceControl(questionsNumPreferenceEditor, questionsNumChoices);
    }

    protected IMultiChoiceControl createCategoryFilterControl() {
        String categoriesPrefKey = getString(R.string.pref_categories);
        IPreferenceEditor preferenceEditor =
                new SimpleSharedPreferenceEditor(getSharedPreferences(), categoriesPrefKey, "");
        List<Category> categories = helper.getCategories();
        return new MultiChoiceControl(preferenceEditor, categories, true);
    }
}