package com.example.egame;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import java.sql.SQLException;
import model.Achievement;
import model.HelperFactory;

public class AchievementActivity extends AppCompatActivity {

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
        Achievement achievement;
        try {
            achievement = HelperFactory.getHelper().getAchievementDAO().queryForFirst();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        TextView seriesTest = findViewById(R.id.seriesTest);
        TextView percentage = findViewById(R.id.percentage);
        seriesTest.setText(String.valueOf(achievement.getSeriesTest()));
        double perc;
        if (achievement.getNumberOfCorrectAnswers() == 0 && achievement.getNumberOfWrongAnswers() == 0) perc = 0;
        else perc = (double) achievement.getNumberOfCorrectAnswers() / (achievement.getNumberOfCorrectAnswers() + achievement.getNumberOfWrongAnswers()) * 100;
        percentage.setText(String.valueOf(perc).substring(0, 4).concat("%"));
        Button back = findViewById(R.id.back);
        MediaPlayer click = MediaPlayer.create(this, R.raw.click);
        back.setOnClickListener(v -> {
            click.start();
            Intent intent = new Intent(AchievementActivity.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        });
    }

    @Override
    protected void onStop() {
        HelperFactory.releaseHelper();
        super.onStop();
    }
}