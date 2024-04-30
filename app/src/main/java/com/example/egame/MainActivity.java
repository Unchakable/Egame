package com.example.egame;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.Button;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.j256.ormlite.table.TableUtils;
import java.sql.SQLException;
import java.util.List;
import controllers.MainController;
import model.HelperFactory;
import model.Questions;

public class MainActivity extends AppCompatActivity {

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

        HelperFactory.setHelper(getApplicationContext());
        List<Questions> notUsedQuestions;
        try {
            notUsedQuestions = HelperFactory.getHelper().getQuestionsDAO().queryForEq("in_used", false);
            if (notUsedQuestions.isEmpty()) {
                notUsedQuestions = MainController.getQuestions();
                TableUtils.clearTable(HelperFactory.getHelper().getConnectionSource(), Questions.class);
                for (int i = 0; i < notUsedQuestions.size(); i++) {
                    HelperFactory.getHelper().getQuestionsDAO().create(notUsedQuestions.get(i));
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
        List<Questions> finalNotUsedQuestions = notUsedQuestions;
        startTest.setOnClickListener(v ->
        {
            click.start();
            Intent intent = new Intent(MainActivity.this, QuestionActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        });

        achievements.setOnClickListener(v ->

        {
            click.start();
            Intent intent = new Intent(MainActivity.this, AchievementActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
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

