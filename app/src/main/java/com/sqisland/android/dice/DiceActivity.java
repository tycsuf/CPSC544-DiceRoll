package com.sqisland.android.dice;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.Random;

public class DiceActivity extends Activity {
  private static final int REQUEST_CODE_RESULT = 2000;
  public static final String KEY_NUM_DICE = "num_dice";
  public static final String KEY_TOTAL = "total";
  public static final String KEY_SELECTED_NUM = "selected_num";
  public static final String KEY_USER_WINS = "user_wins";
  public static final String KEY_USER_LOSSES = "user_losses";
  public static final String KEY_CPU_WINS = "cpu_wins";
  public static final String KEY_CPU_LOSSES = "cpu_losses";
  public static final String KEY_DRAWS = "draws";
  private Random random = new Random();
  private int total;
  private int userTotal;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_dice);

    TextView userSelectedView = (TextView) findViewById(R.id.userSelected);
    TextView userTotalView = (TextView) findViewById(R.id.userTotal);
    TextView resultView = (TextView) findViewById(R.id.result);
    View nextButton = findViewById(R.id.next_button);

    int numDice = getIntent().getIntExtra(KEY_NUM_DICE, 1);
    int selectedNumber = getIntent().getIntExtra(KEY_SELECTED_NUM, 1);

    nextButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        next();
        //Intent data = new Intent();
        //data.putExtra(KEY_TOTAL, total);
        //setResult(Activity.RESULT_OK, data);
        //finish();
      }
    });

    userSelectedView.setText("User selected: '" + selectedNumber + "'");
    setUserTotal(userTotalView, selectedNumber);
    rollAll(resultView, numDice);

    //int result = rollOne();
    //String text = String.valueOf(result);
    //resultView.setText(text);
  }

  private void setUserTotal(TextView textView, int selectedNumber) {
    StringBuilder builder = new StringBuilder();
    builder.append("User: '");
    int rolledNumber = rollOne();
    userTotal = selectedNumber + rolledNumber;
    builder.append(selectedNumber).append("' + ").append(rolledNumber).append(" = ").append(userTotal);
    textView.setText(builder.toString());
  }

  private void rollAll(TextView textView, int numDice) {
    StringBuilder builder = new StringBuilder();
    builder.append("CPU: ");
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

  private void next() {
    Intent intent = new Intent(this, ResultActivity.class);
    intent.putExtra(ResultActivity.KEY_USER_TOTAL, userTotal);
    intent.putExtra(ResultActivity.KEY_CPU_TOTAL, total);
    intent.putExtra(ResultActivity.KEY_USER_WINS, getIntent().getIntExtra(KEY_USER_WINS, 0));
    intent.putExtra(ResultActivity.KEY_USER_LOSSES, getIntent().getIntExtra(KEY_USER_LOSSES, 0));
    intent.putExtra(ResultActivity.KEY_CPU_WINS, getIntent().getIntExtra(KEY_CPU_WINS, 0));
    intent.putExtra(ResultActivity.KEY_CPU_LOSSES, getIntent().getIntExtra(KEY_CPU_LOSSES, 0));
    intent.putExtra(ResultActivity.KEY_DRAWS, getIntent().getIntExtra(KEY_DRAWS, 0));
    //startActivity(intent);
    startActivityForResult(intent, REQUEST_CODE_RESULT);
  }
}
