package com.example.egame;

import android.content.Intent;
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
import java.util.Calendar;
import java.util.List;
import model.Achievements;
import model.HelperFactory;
import model.Result;

public class ResultTestActivity extends AppCompatActivity {
    int counter, numberRightAnswer, numberWrongAnswer;
    MediaPlayer click;
    long timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_result_test);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Toast toast;
        MediaPlayer ach = MediaPlayer.create(this, R.raw.ach);
        HelperFactory.setHelper(getApplicationContext());

        Intent intent = getIntent();
        counter = intent.getIntExtra("counter", 0);
        numberRightAnswer = intent.getIntExtra("numberRightAnswer", 0);
        numberWrongAnswer = intent.getIntExtra("numberWrongAnswer", 0);

        try {
            if (numberRightAnswer == 10 && counter == 10) {
                List<Achievements> achievementsList = HelperFactory.getHelper().getAchievementsDAO().queryForEq("name", "САПЕРОМ БУДЕШЬ");
                Achievements achievement = achievementsList.get(0);
                if (!achievement.isUnlocked()) {
                    achievement.setUnlocked(true);
                    HelperFactory.getHelper().getAchievementsDAO().update(achievement);
                    toast = Toast.makeText(this, "Разблокировано достижение ".concat(achievement.getName()), Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                    ach.start();
                }
            }
            Result result = HelperFactory.getHelper().getResultDAO().queryForFirst();
            result.setNumberOfCorrectAnswers(result.getNumberOfCorrectAnswers() + numberRightAnswer);
            result.setNumberOfWrongAnswers(result.getNumberOfWrongAnswers() + numberWrongAnswer);

            if (result.getSeriesTest() == 0) result.setSeriesTest(1);
            else {
                BigDecimal now = new BigDecimal(System.currentTimeMillis());
                BigDecimal lastDate = new BigDecimal(result.getDate().getTime());
                if (now.subtract(lastDate).compareTo(new BigDecimal(64800000)) >= 0) {
                    result.setSeriesTest(result.getSeriesTest() + 1);
                    result.setDate(Calendar.getInstance().getTime());
                }
            }
            HelperFactory.getHelper().getResultDAO().update(result);

            if (result.getSeriesTest() >= 5) {
                List<Achievements> achievementsList = HelperFactory.getHelper().getAchievementsDAO().queryForEq("name", "РАБОТЯГА");
                Achievements achievement = achievementsList.get(0);
                if (!achievement.isUnlocked()) {
                    achievement.setUnlocked(true);
                    HelperFactory.getHelper().getAchievementsDAO().update(achievement);
                    toast = Toast.makeText(this, "Разблокировано достижение ".concat(achievement.getName()), Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                    ach.start();
                }
            }
            if (result.getNumberOfCorrectAnswers() > 0 || result.getNumberOfWrongAnswers() > 0) {
                double perc = (double) result.getNumberOfCorrectAnswers() / (result.getNumberOfCorrectAnswers() + result.getNumberOfWrongAnswers()) * 100;
                if (new BigDecimal(perc).compareTo(new BigDecimal(90)) >= 0) {
                    List<Achievements> achievementsList = HelperFactory.getHelper().getAchievementsDAO().queryForEq("name", "ПРОСТО СДАЙ ЕГЭ");
                    Achievements achievement = achievementsList.get(0);
                    if (!achievement.isUnlocked()) {
                        achievement.setUnlocked(true);
                        HelperFactory.getHelper().getAchievementsDAO().update(achievement);
                        toast = Toast.makeText(this, "Разблокировано достижение ".concat(achievement.getName()), Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                        ach.start();
                    }
                }
            }
            timer = intent.getLongExtra("timer", 0);
            if (timer == 0) timer = System.currentTimeMillis();
            BigDecimal bg = new BigDecimal(timer);
            if (new BigDecimal(System.currentTimeMillis()).subtract(bg).compareTo(new BigDecimal(3600000)) >= 0) {
                List<Achievements> achievementsList = HelperFactory.getHelper().getAchievementsDAO().queryForEq("name", "КРЕМЕНЬ!");
                Achievements achievement = achievementsList.get(0);
                if (!achievement.isUnlocked()) {
                    achievement.setUnlocked(true);
                    HelperFactory.getHelper().getAchievementsDAO().update(achievement);
                    toast = Toast.makeText(this, "Разблокировано достижение ".concat(achievement.getName()), Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                    ach.start();
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        click = MediaPlayer.create(this, R.raw.click);
        TextView counterTextView = findViewById(R.id.counter);
        counterTextView.setText(String.valueOf(counter));
        TextView numberRightAnswerTextView = findViewById(R.id.numberRightAnswer);
        numberRightAnswerTextView.setText(String.valueOf(numberRightAnswer));
        TextView numberWrongAnswerTextView = findViewById(R.id.numberWrongAnswer);
        numberWrongAnswerTextView.setText(String.valueOf(numberWrongAnswer));
        Button ok = findViewById(R.id.ok);
        ok.setOnClickListener(v -> {
            click.start();
            okOnClick();
        });
    }

    @Override
    public void onBackPressed() {
    }

    public void okOnClick() {
        Intent intent = new Intent(ResultTestActivity.this, MainActivity.class);
        intent.putExtra("timer", timer);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    @Override
    protected void onStop() {
        HelperFactory.releaseHelper();
        super.onStop();
    }
}