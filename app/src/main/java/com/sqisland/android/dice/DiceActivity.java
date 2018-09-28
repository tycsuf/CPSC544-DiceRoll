package com.sqisland.android.dice;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.Random;

public class DiceActivity extends Activity {
  public static final String KEY_NUM_DICE = "num_dice";
  public static final String KEY_TOTAL = "total";
  private Random random = new Random();
  private int total;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_dice);

    TextView resultView = (TextView) findViewById(R.id.result);
    View doneButton = findViewById(R.id.done_button);

    int numDice = getIntent().getIntExtra(KEY_NUM_DICE, 1);

    doneButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent data = new Intent();
        data.putExtra(KEY_TOTAL, total);
        setResult(Activity.RESULT_OK, data);
        finish();
      }
    });

    rollAll(resultView, numDice);

    //int result = rollOne();
    //String text = String.valueOf(result);
    //resultView.setText(text);
  }

  private void rollAll(TextView textView, int numDice) {
    StringBuilder builder = new StringBuilder();
    total = 0;
    for (int i = 0; i < numDice; i++) {
      int result = rollOne();
      total += result;

      if (i > 0) {
        builder.append(" + ");
      }

      builder.append(result);
    }

    if (numDice > 1) {
      builder.append(" = ");
      builder.append(total);
    }

    textView.setText(builder.toString());
  }

  private int rollOne() {
    return random.nextInt(6) + 1;
  }
}
