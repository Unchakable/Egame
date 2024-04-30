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
import com.j256.ormlite.table.TableUtils;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import controllers.MainController;
import model.Achievement;
import model.HelperFactory;
import model.Questions;

public class QuestionActivity extends AppCompatActivity {

    MediaPlayer click, right, wrong;
    Questions questions;
    Achievement achievement;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_question);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        HelperFactory.setHelper(getApplicationContext());
        List<Questions> notUsedQuestions;
        try {
            achievement = HelperFactory.getHelper().getAchievementDAO().queryForFirst();
            notUsedQuestions = HelperFactory.getHelper().getQuestionsDAO().queryForEq("in_used", false);
            if (notUsedQuestions.isEmpty()) {
                notUsedQuestions = MainController.getQuestions();
                TableUtils.clearTable(HelperFactory.getHelper().getConnectionSource(), Questions.class);
                for (int i = 0; i < notUsedQuestions.size(); i++) {
                    HelperFactory.getHelper().getQuestionsDAO().create(notUsedQuestions.get(i));
                }
            }
            questions = notUsedQuestions.get(getRandomInt(0, notUsedQuestions.size() - 1));
            questions.setInUsed(true);
            HelperFactory.getHelper().getQuestionsDAO().update(questions);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        TextView question = findViewById(R.id.question);
        Button answer1 = findViewById(R.id.answer1);
        Button answer2 = findViewById(R.id.answer2);
        Button answer3 = findViewById(R.id.answer3);
        Button answer4 = findViewById(R.id.answer4);
        answer1.setSoundEffectsEnabled(false);
        answer2.setSoundEffectsEnabled(false);
        answer3.setSoundEffectsEnabled(false);
        answer4.setSoundEffectsEnabled(false);
        click = MediaPlayer.create(this, R.raw.click);
        right = MediaPlayer.create(this, R.raw.right);
        wrong = MediaPlayer.create(this, R.raw.wrong);
        question.setText(questions.getQuestion());
        List<String> listAnswer = Arrays.asList(questions.getRightAnswer(), questions.getRowAnswer1(), questions.getRowAnswer2(), questions.getRowAnswer3());
        Set<Integer> idSet = new HashSet<>();
        for (int i = 0; i < listAnswer.size(); i++) {
            do {
                int id = getRandomInt(1, 4);
                if (idSet.contains(id)) continue;
                switch (id) {
                    case (1): {
                        answer1.setText(listAnswer.get(i));
                        idSet.add(id);
                    }
                    break;
                    case (2): {
                        answer2.setText(listAnswer.get(i));
                        idSet.add(id);
                    }
                    break;
                    case (3): {
                        answer3.setText(listAnswer.get(i));
                        idSet.add(id);
                    }
                    break;
                    case (4): {
                        answer4.setText(listAnswer.get(i));
                        idSet.add(id);
                    }
                    break;
                }
            } while (i != idSet.size() - 1);
        }
        answer1.setOnClickListener(v -> answerOnClick(String.valueOf(answer1.getText())));

        answer2.setOnClickListener(v -> answerOnClick(String.valueOf(answer2.getText())));

        answer3.setOnClickListener(v -> answerOnClick(String.valueOf(answer3.getText())));

        answer4.setOnClickListener(v -> answerOnClick(String.valueOf(answer4.getText())));

    }

    private void answerOnClick(String answer) {
        if (answer.equals(questions.getRightAnswer())) {
            right.start();
            achievement.setNumberOfCorrectAnswers(achievement.getNumberOfCorrectAnswers() + 1);
        } else {
            achievement.setNumberOfWrongAnswers(achievement.getNumberOfWrongAnswers() + 1);
            wrong.start();
        }
        try {
            HelperFactory.setHelper(getApplicationContext());
            HelperFactory.getHelper().getAchievementDAO().update(achievement);
            HelperFactory.releaseHelper();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        Intent intent = new Intent(QuestionActivity.this, AnswerActivity.class);
        intent.putExtra("decision", questions.getDecision());
        intent.putExtra("rightAnswer", questions.getRightAnswer());
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    private int getRandomInt(int min, int max) {
        int randomId;
        do {
            randomId = min + (int) (Math.random() * max);
        } while (randomId < min || randomId > max);
        return randomId;
    }

    @Override
    protected void onStop() {
        HelperFactory.releaseHelper();
        super.onStop();
    }
}