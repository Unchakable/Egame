package com.example.egame;
import android.content.Intent;
import android.icu.util.Calendar;
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
import java.util.Date;
import model.Achievement;
import model.HelperFactory;

public class AnswerActivity extends AppCompatActivity {

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
        MediaPlayer click = MediaPlayer.create(this, R.raw.click);
        TextView decision = findViewById(R.id.decision);
        TextView rightAnswer = findViewById(R.id.rightAnswer);
        Button endTest = findViewById(R.id.endTest);
        Button farther = findViewById(R.id.farther);
        Intent intent = getIntent();
        decision.setText(intent.getStringExtra("decision"));
        rightAnswer.setText(intent.getStringExtra("rightAnswer"));
        endTest.setOnClickListener(v -> {
            try {
                HelperFactory.setHelper(getApplicationContext());
                Achievement achievement = HelperFactory.getHelper().getAchievementDAO().queryForFirst();
                if (achievement.getSeriesTest() == 0) achievement.setSeriesTest(1);
                else {
                    Date lastDate = achievement.getDate();
                    Date now = Calendar.getInstance().getTime();
                    int diff = (int) (now.getTime() - lastDate.getTime())/ 86400000;
                    if (diff == 1) achievement.setSeriesTest(achievement.getSeriesTest() + 1);
                    if (diff > 1) achievement.setSeriesTest(1);
                }
                HelperFactory.getHelper().getAchievementDAO().update(achievement);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            click.start();
            Intent intentEndTest = new Intent(AnswerActivity.this, MainActivity.class);
            intentEndTest.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intentEndTest);
        });

        farther.setOnClickListener(v -> {
            click.start();
            Intent intentFarther = new Intent(AnswerActivity.this, QuestionActivity.class);
            intentFarther.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intentFarther);
        });
    }

    @Override
    protected void onStop() {
        HelperFactory.releaseHelper();
        super.onStop();
    }
}