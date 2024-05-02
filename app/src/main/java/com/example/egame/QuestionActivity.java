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

import com.j256.ormlite.table.TableUtils;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import controllers.MainController;
import model.Achievements;
import model.Result;
import model.HelperFactory;
import model.Questions;

public class QuestionActivity extends AppCompatActivity {
    MediaPlayer click, right, wrong;
    Questions questions;
    Result result;
    int counter, numberRightAnswer, numberWrongAnswer, numberRightAnswerAchievement;
    long timer, timerTest;

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

        MediaPlayer ach = MediaPlayer.create(this, R.raw.ach);
        Intent intent = getIntent();
        counter = intent.getIntExtra("counter", 0) + 1;
        numberRightAnswer = intent.getIntExtra("numberRightAnswer", 0);
        numberWrongAnswer = intent.getIntExtra("numberWrongAnswer", 0);
        numberRightAnswerAchievement = intent.getIntExtra("numberRightAnswerAchievement", 0);
        timer = intent.getLongExtra("timer", 0);
        if (timer == 0) timer = System.currentTimeMillis();
        BigDecimal bg = new BigDecimal(timer);
        if (new BigDecimal(System.currentTimeMillis()).subtract(bg).compareTo(new BigDecimal(3600000)) >= 0)
        {
            try {
                List<Achievements> achievementsList = HelperFactory.getHelper().getAchievementsDAO().queryForEq("name", "КРЕМЕНЬ!");
                Achievements achievement = achievementsList.get(0);
                if (!achievement.isUnlocked()){
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
        if (timerTest == 0) timerTest = System.currentTimeMillis();
        bg = new BigDecimal(timerTest);
        if (new BigDecimal(System.currentTimeMillis()).subtract(bg).compareTo(new BigDecimal(10000)) >= 0) {
            timerTest = System.currentTimeMillis();
            numberRightAnswerAchievement = 0;
        }
        HelperFactory.setHelper(getApplicationContext());
        List<Questions> notUsedQuestions;
        try {
            result = HelperFactory.getHelper().getResultDAO().queryForFirst();
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
        TextView counterTextView = findViewById(R.id.counterQuestion);
        counterTextView.setText(String.valueOf(counter).concat("/10"));
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
            result.setNumberOfCorrectAnswers(result.getNumberOfCorrectAnswers() + 1);
            numberRightAnswer++;
            numberRightAnswerAchievement++;

        } else {
            wrong.start();
            result.setNumberOfWrongAnswers(result.getNumberOfWrongAnswers() + 1);
            numberWrongAnswer++;
            timerTest = System.currentTimeMillis();
            numberRightAnswerAchievement = 0;
        }
        try {
            HelperFactory.setHelper(getApplicationContext());
            HelperFactory.getHelper().getResultDAO().update(result);
            HelperFactory.releaseHelper();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        Intent intent = new Intent(QuestionActivity.this, AnswerActivity.class);
        intent.putExtra("decision", questions.getDecision());
        intent.putExtra("rightAnswer", questions.getRightAnswer());
        intent.putExtra("counter", counter);
        intent.putExtra("numberRightAnswer", numberRightAnswer);
        intent.putExtra("numberWrongAnswer", numberWrongAnswer);
        intent.putExtra("timer", timer);
        intent.putExtra("timerTest", timerTest);
        intent.putExtra("numberRightAnswerAchievement", numberRightAnswerAchievement);
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
    public void onBackPressed() {
    }

    @Override
    protected void onStop() {
        HelperFactory.releaseHelper();
        super.onStop();
    }
}