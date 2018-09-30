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

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    resultView = (TextView) findViewById(R.id.result);
    View button = findViewById(R.id.roll_button);

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

    int gameResult = getIntent().getIntExtra(KEY_GAME_RESULT, 1);
/**
    try {
      resultView.setText(Integer.toString(gameResult));
    } catch (RuntimeException e) {
      System.out.println(e);
    }
**/
  }

  private void roll(int selectedNumber) {
    Intent intent = new Intent(this, DiceActivity.class);
    intent.putExtra(DiceActivity.KEY_NUM_DICE, 2);
    intent.putExtra(DiceActivity.KEY_SELECTED_NUM, selectedNumber);
    //startActivity(intent);
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