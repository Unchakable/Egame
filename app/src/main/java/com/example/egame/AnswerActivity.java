package com.example.egame;

import android.content.Intent;
import android.icu.util.Calendar;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import model.Achievements;
import model.Result;
import model.HelperFactory;

public class AnswerActivity extends AppCompatActivity {
    int counter, numberRightAnswer, numberWrongAnswer, numberRightAnswerAchievement;
    long timer, timerTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_answer);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        HelperFactory.setHelper(getApplicationContext());
        MediaPlayer click = MediaPlayer.create(this, R.raw.click);
        MediaPlayer ach = MediaPlayer.create(this, R.raw.ach);
        TextView decision = findViewById(R.id.decision);
        TextView rightAnswer = findViewById(R.id.rightAnswer);
        Button endTest = findViewById(R.id.endTest);
        Button farther = findViewById(R.id.farther);
        Intent intent = getIntent();
        decision.setText(intent.getStringExtra("decision"));
        rightAnswer.setText(intent.getStringExtra("rightAnswer"));
        counter = intent.getIntExtra("counter", 0);
        numberRightAnswer = intent.getIntExtra("numberRightAnswer", 0);
        numberWrongAnswer = intent.getIntExtra("numberWrongAnswer", 0);
        timer = intent.getLongExtra("timer", 0);
        if (timer == 0) timer = System.currentTimeMillis();
        BigDecimal bg = new BigDecimal(timer);
        if (new BigDecimal(System.currentTimeMillis()).subtract(bg).compareTo(new BigDecimal(3600000)) >= 0) {
            try {
                List<Achievements> achievementsList = HelperFactory.getHelper().getAchievementsDAO().queryForEq("name", "КРЕМЕНЬ!");
                Achievements achievement = achievementsList.get(0);
                if (!achievement.isUnlocked()) {
                    achievement.setUnlocked(true);
                    HelperFactory.getHelper().getAchievementsDAO().update(achievement);
                    Toast toast = Toast.makeText(this, "Разблокировано достижение ".concat(achievement.getName()), Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                    ach.start();
                }

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        timerTest = intent.getLongExtra("timerTest", 0);
        numberRightAnswerAchievement = intent.getIntExtra("numberRightAnswerAchievement", 0);
        if (timerTest == 0) timerTest = System.currentTimeMillis();
        bg = new BigDecimal(timerTest);
        if (numberRightAnswerAchievement == 3 && new BigDecimal(System.currentTimeMillis()).subtract(bg).compareTo(new BigDecimal(10000)) <= 0) {
            try {
                List<Achievements> achievementsList = HelperFactory.getHelper().getAchievementsDAO().queryForEq("name", "ЛАС-ВЕГАС");
                Achievements achievement = achievementsList.get(0);
                if (!achievement.isUnlocked()) {
                    achievement.setUnlocked(true);
                    HelperFactory.getHelper().getAchievementsDAO().update(achievement);
                    Toast toast = Toast.makeText(this, "Разблокировано достижение ".concat(achievement.getName()), Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                    ach.start();
                }

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        TextView counterTextView = findViewById(R.id.counterAnswer);
        counterTextView.setText(String.valueOf(counter).concat("/10"));
        endTest.setOnClickListener(v -> {
            try {
                HelperFactory.setHelper(getApplicationContext());
                Result result = HelperFactory.getHelper().getResultDAO().queryForFirst();
                if (result.getSeriesTest() == 0) result.setSeriesTest(1);
                else {
                    Date lastDate = result.getDate();
                    Date now = Calendar.getInstance().getTime();
                    int diff = (int) (now.getTime() - lastDate.getTime()) / 86400000;
                    if (diff == 1) result.setSeriesTest(result.getSeriesTest() + 1);
                    if (diff > 1) result.setSeriesTest(1);
                }
                HelperFactory.getHelper().getResultDAO().update(result);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            click.start();
            endTestOnclick();
        });

        farther.setOnClickListener(v -> {
            click.start();
            if (counter == 10) {
                try {
                    HelperFactory.setHelper(getApplicationContext());
                    Result result = HelperFactory.getHelper().getResultDAO().queryForFirst();
                    if (result.getSeriesTest() == 0) result.setSeriesTest(1);
                    else {
                        Date lastDate = result.getDate();
                        Date now = Calendar.getInstance().getTime();
                        int diff = (int) (now.getTime() - lastDate.getTime()) / 86400000;
                        if (diff == 1) result.setSeriesTest(result.getSeriesTest() + 1);
                        if (diff > 1) result.setSeriesTest(1);
                    }
                    HelperFactory.getHelper().getResultDAO().update(result);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                endTestOnclick();
            } else fartherOnClick();
        });
    }

    private void endTestOnclick() {
        Intent intent = new Intent(AnswerActivity.this, ResultTestActivity.class);
        intent.putExtra("counter", counter);
        intent.putExtra("numberRightAnswer", numberRightAnswer);
        intent.putExtra("numberWrongAnswer", numberWrongAnswer);
        intent.putExtra("timer", timer);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    private void fartherOnClick() {
        Intent intent = new Intent(AnswerActivity.this, QuestionActivity.class);
        intent.putExtra("counter", counter);
        intent.putExtra("numberRightAnswer", numberRightAnswer);
        intent.putExtra("numberWrongAnswer", numberWrongAnswer);
        intent.putExtra("timer", timer);
        intent.putExtra("timerTest", timerTest);
        intent.putExtra("numberRightAnswerAchievement", numberRightAnswerAchievement);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
    }

    @Override
    protected void onStop() {
        HelperFactory.releaseHelper();
        super.onStop();
    }
}