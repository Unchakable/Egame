package com.example.egame;

import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.util.Calendar;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.j256.ormlite.table.TableUtils;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

import controllers.MainController;
import model.Achievements;
import model.Result;
import model.HelperFactory;
import model.Questions;

public class MainActivity extends AppCompatActivity {
    long timer;
    SharedPreferences prefs = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        prefs = getSharedPreferences("com.example.egame", MODE_PRIVATE);
        if (prefs.getBoolean("firstRun", true)) {
            getApplicationContext().deleteDatabase("Egame.db");
            prefs.edit().putBoolean("firstRun", false).apply();
        }
        MediaPlayer ach = MediaPlayer.create(this, R.raw.ach);
        HelperFactory.setHelper(getApplicationContext());
        try {
            List<Questions> notUsedQuestions = HelperFactory.getHelper().getQuestionsDAO().queryForEq("in_used", false);
            if (notUsedQuestions.isEmpty()) {
                notUsedQuestions = MainController.getQuestions();
                TableUtils.clearTable(HelperFactory.getHelper().getConnectionSource(), Questions.class);
                for (int i = 0; i < notUsedQuestions.size(); i++) {
                    HelperFactory.getHelper().getQuestionsDAO().create(notUsedQuestions.get(i));
                }
            }

            Result result = HelperFactory.getHelper().getResultDAO().queryForFirst();
            if (result == null) {
                result = new Result(Calendar.getInstance().getTime(), 0, 0, 0);
                TableUtils.clearTable(HelperFactory.getHelper().getConnectionSource(), Result.class);
                HelperFactory.getHelper().getResultDAO().create(result);
            }

            List<Achievements> achievementsList = HelperFactory.getHelper().getAchievementsDAO().queryForAll();
            if (achievementsList.isEmpty()) {
                TableUtils.clearTable(HelperFactory.getHelper().getConnectionSource(), Achievements.class);
                achievementsList = MainController.getAchievements();
                for (int i = 0; i < achievementsList.size(); i++)
                    HelperFactory.getHelper().getAchievementsDAO().create(achievementsList.get(i));
            }

            Intent intent = getIntent();

            timer = intent.getLongExtra("timer", 0);
            if (timer == 0) timer = System.currentTimeMillis();
            BigDecimal bg = new BigDecimal(timer);
            if (new BigDecimal(System.currentTimeMillis()).subtract(bg).compareTo(new BigDecimal(3600000)) >= 0) {
                achievementsList = HelperFactory.getHelper().getAchievementsDAO().queryForEq("name", "КРЕМЕНЬ!");
                Achievements achievement = achievementsList.get(0);
                if (!achievement.isUnlocked()) {
                    achievement.setUnlocked(true);
                    HelperFactory.getHelper().getAchievementsDAO().update(achievement);
                    Toast toast = Toast.makeText(this, "Разблокировано достижение ".concat(achievement.getName()), Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                    ach.start();
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        MediaPlayer click = MediaPlayer.create(this, R.raw.click);
        Button startTest = findViewById(R.id.startTest);
        Button achievements = findViewById(R.id.achievements);
        Button exit = findViewById(R.id.exit);
        startTest.setSoundEffectsEnabled(false);
        achievements.setSoundEffectsEnabled(false);
        exit.setSoundEffectsEnabled(false);

        startTest.setOnClickListener(v ->
        {
            click.start();
            Intent intentStart = new Intent(MainActivity.this, QuestionActivity.class);
            intentStart.putExtra("timer", timer);
            intentStart.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intentStart);
        });

        achievements.setOnClickListener(v ->

        {
            click.start();
            Intent intentAchievements = new Intent(MainActivity.this, AchievementActivity.class);
            intentAchievements.putExtra("timer", timer);
            intentAchievements.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intentAchievements);
        });

        exit.setOnClickListener(v ->
        {
            click.start();
            finishAffinity();
            System.exit(0);
        });
    }

    @Override
    protected void onStop() {
        HelperFactory.releaseHelper();
        super.onStop();
    }

}

