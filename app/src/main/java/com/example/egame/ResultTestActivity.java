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

public class ResultTestActivity extends AppCompatActivity {
    int counter, numberRightAnswer, numberWrongAnswer;
    MediaPlayer click;
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
        Intent intent = getIntent();
        counter = intent.getIntExtra("counter", 0);
        numberRightAnswer = intent.getIntExtra("numberRightAnswer", 0);
        numberWrongAnswer = intent.getIntExtra("numberWrongAnswer", 0);
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
    public void okOnClick(){
        Intent intent = new Intent(ResultTestActivity.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}