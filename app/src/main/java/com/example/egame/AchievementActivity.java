package com.example.egame;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentTransaction;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.SQLException;
import java.util.List;

import model.Achievements;
import model.Result;
import model.HelperFactory;

public class AchievementActivity extends AppCompatActivity {
    long timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_achievement);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        HelperFactory.setHelper(getApplicationContext());

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width = displayMetrics.widthPixels / 4;
        int height = width * 16 / 10;
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width, height);
        params.width = width;
        params.height = height;
        params.setMarginEnd(5);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        UnknownAchievementFragment unknownAchievementFragment = new UnknownAchievementFragment();
        FrameLayout hardWorker = findViewById(R.id.hardWorker);
        hardWorker.setLayoutParams(params);
        ft.replace(R.id.hardWorker, unknownAchievementFragment);
        UnknownAchievementFragment unknownAchievementFragment1 = new UnknownAchievementFragment();
        FrameLayout sapper = findViewById(R.id.sapper);
        sapper.setLayoutParams(params);
        ft.replace(R.id.sapper, unknownAchievementFragment1);
        UnknownAchievementFragment unknownAchievementFragment2 = new UnknownAchievementFragment();
        FrameLayout flint = findViewById(R.id.flint);
        flint.setLayoutParams(params);
        ft.replace(R.id.flint, unknownAchievementFragment2);
        UnknownAchievementFragment unknownAchievementFragment3 = new UnknownAchievementFragment();
        FrameLayout lasVegas = findViewById(R.id.lasVegas);
        lasVegas.setLayoutParams(params);
        ft.replace(R.id.lasVegas, unknownAchievementFragment3);
        UnknownAchievementFragment unknownAchievementFragment4 = new UnknownAchievementFragment();
        FrameLayout takeTheTest = findViewById(R.id.takeTheTest);
        takeTheTest.setLayoutParams(params);
        ft.replace(R.id.takeTheTest, unknownAchievementFragment4);

        try {
            List<Achievements> achievementsList = HelperFactory.getHelper().getAchievementsDAO().queryForEq("is_unlocked", true);
            if (!achievementsList.isEmpty()) {
                HardWorkerFragment hardWorkerFragment = new HardWorkerFragment();
                SapperFragment sapperFragment = new SapperFragment();
                FlintFragment flintFragment = new FlintFragment();
                LasVegasFragment lasVegasFragment = new LasVegasFragment();
                TakeTheTestFragment takeTheTestFragment = new TakeTheTestFragment();
                for (int i = 0; i < achievementsList.size(); i++) {
                    switch (achievementsList.get(i).getName()) {
                        case ("РАБОТЯГА"): {
                            ft.replace(R.id.hardWorker, hardWorkerFragment);
                        }
                        break;
                        case ("САПЕРОМ БУДЕШЬ"): {
                            ft.replace(R.id.sapper, sapperFragment);
                        }
                        break;
                        case ("КРЕМЕНЬ!"): {
                            ft.replace(R.id.flint, flintFragment);
                        }
                        break;
                        case ("ЛАС-ВЕГАС"): {
                            ft.replace(R.id.lasVegas, lasVegasFragment);
                        }
                        break;
                        case ("ПРОСТО СДАЙ ЕГЭ"): {
                            ft.replace(R.id.takeTheTest, takeTheTestFragment);
                        }
                        break;
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        ft.commit();

        MediaPlayer ach = MediaPlayer.create(this, R.raw.ach);
        Result result;
        try {
            result = HelperFactory.getHelper().getResultDAO().queryForFirst();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        Intent intent = getIntent();
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
        TextView seriesTest = findViewById(R.id.seriesTest);
        TextView percentage = findViewById(R.id.percentage);
        seriesTest.setText(String.valueOf(result.getSeriesTest()));
        double perc;
        if (result.getNumberOfCorrectAnswers() == 0 && result.getNumberOfWrongAnswers() == 0)
            perc = 0;
        else
            perc = (double) result.getNumberOfCorrectAnswers() / (result.getNumberOfCorrectAnswers() + result.getNumberOfWrongAnswers()) * 100;
        percentage.setText(String.valueOf(new BigDecimal(perc).setScale(1, RoundingMode.HALF_UP).doubleValue()).concat("%"));
        Button back = findViewById(R.id.back);
        MediaPlayer click = MediaPlayer.create(this, R.raw.click);
        back.setOnClickListener(v -> {
            click.start();
            Intent intentBack = new Intent(AchievementActivity.this, MainActivity.class);
            intentBack.putExtra("timer", timer);
            intentBack.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intentBack);
        });
    }

    @Override
    protected void onStop() {
        HelperFactory.releaseHelper();
        super.onStop();
    }
}