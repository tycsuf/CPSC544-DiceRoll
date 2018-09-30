package com.sqisland.android.dice;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ResultActivity extends Activity {
  public static final String KEY_USER_TOTAL = "user_total";
  public static final String KEY_CPU_TOTAL = "cpu_total";
  private int cpuTotal;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_result);

    View nextButton = findViewById(R.id.next_button);

    cpuTotal = getIntent().getIntExtra(KEY_CPU_TOTAL, 1);

    nextButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        returnToMain();
        //Intent data = new Intent();
        //data.putExtra(KEY_CPU_TOTAL, cpuTotal);
        //setResult(Activity.RESULT_OK, data);
        //finish();
      }
    });
  }

  private void returnToMain () {
    Intent intent = new Intent(this, MainActivity.class);
    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    intent.putExtra(MainActivity.KEY_GAME_RESULT, 100);
    startActivity(intent);
  }
}
