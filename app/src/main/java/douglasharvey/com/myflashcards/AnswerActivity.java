package douglasharvey.com.myflashcards;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class AnswerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer);
        String question = getIntent().getStringExtra(Intent.EXTRA_TITLE);
        String answer = getIntent().getStringExtra(Intent.EXTRA_TEXT);
        TextView tvQuestion = (TextView) findViewById(R.id.tvQuestion);
        TextView tvAnswer = (TextView) findViewById(R.id.tvAnswer);

        tvQuestion.setText(question);
        tvAnswer.setText(answer);

    }
}
