package com.sqisland.android.dice;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends Activity {
  private static final int REQUEST_CODE_DICE = 1000;
  private TextView resultView;
  private int selectedNumber;
  public static final String KEY_GAME_RESULT = "game_result";
  public static final String KEY_USER_WINS = "user_wins";
  public static final String KEY_USER_LOSSES = "user_losses";
  public static final String KEY_CPU_WINS = "cpu_wins";
  public static final String KEY_CPU_LOSSES = "cpu_losses";
  public static final String KEY_DRAWS = "draws";
  private int userWins;
  private int userLosses;
  private int cpuWins;
  private int cpuLosses;
  private int draws;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    if (getIntent().getBooleanExtra("Exit me", false)) {
      finishAndRemoveTask();
    }

    resultView = (TextView) findViewById(R.id.result);
    View button = findViewById(R.id.roll_button);
    TextView userScoresView = (TextView) findViewById(R.id.result);
    TextView cpuScoresView = (TextView) findViewById(R.id.resultCPU);
    TextView drawCountView = (TextView) findViewById(R.id.drawCount);

    userWins = getIntent().getIntExtra(KEY_USER_WINS, 0);
    userLosses = getIntent().getIntExtra(KEY_USER_LOSSES, 0);
    cpuWins = getIntent().getIntExtra(KEY_CPU_WINS, 0);
    cpuLosses = getIntent().getIntExtra(KEY_CPU_LOSSES, 0);
    draws = getIntent().getIntExtra(KEY_DRAWS, 0);

    int gameResult = getIntent().getIntExtra(KEY_GAME_RESULT, 0);
    if (gameResult == 1) {
      userWins += 1; cpuLosses += 1;
    } else if (gameResult == 2) {
      userLosses += 1; cpuWins += 1;
    } else if (gameResult == 3) {
      draws += 1;
    }

    userScoresView.setText("YOU:       " + userWins + "          " + userLosses);
    cpuScoresView.setText("CPU:       " + cpuWins + "          " + cpuLosses);
    drawCountView.setText("DRAWS:  " + draws);

    final Spinner dropdown = (Spinner)findViewById(R.id.spinner1);
    String[] items = new String[]{"1", "2", "3", "4", "5", "6"};
    ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
    dropdown.setAdapter(adapter);

    button.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        selectedNumber = Integer.parseInt(dropdown.getSelectedItem().toString());
        roll(selectedNumber);
      }
    });

  }

  private void roll(int selectedNumber) {
    Intent intent = new Intent(this, DiceActivity.class);
    intent.putExtra(DiceActivity.KEY_NUM_DICE, 2);
    intent.putExtra(DiceActivity.KEY_SELECTED_NUM, selectedNumber);
    intent.putExtra(DiceActivity.KEY_USER_WINS, userWins);
    intent.putExtra(DiceActivity.KEY_USER_LOSSES, userLosses);
    intent.putExtra(DiceActivity.KEY_CPU_WINS, cpuWins);
    intent.putExtra(DiceActivity.KEY_CPU_LOSSES, cpuLosses);
    intent.putExtra(DiceActivity.KEY_DRAWS, draws);
    startActivityForResult(intent, REQUEST_CODE_DICE);
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    if (requestCode == REQUEST_CODE_DICE && resultCode == Activity.RESULT_OK) {
      int total = data.getIntExtra(DiceActivity.KEY_TOTAL, 0);
      resultView.setText("CPU Total: " + String.valueOf(total));
      resultView.setVisibility(View.VISIBLE);
      return;
    }
    super.onActivityResult(requestCode, resultCode, data);
  }
}