package com.sqisland.android.dice;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.io.IOException;

import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;

public class ResultActivity extends Activity {
  public static final String KEY_USER_TOTAL = "user_total";
  public static final String KEY_CPU_TOTAL = "cpu_total";
  public static final String KEY_USER_WINS = "user_wins";
  public static final String KEY_USER_LOSSES = "user_losses";
  public static final String KEY_CPU_WINS = "cpu_wins";
  public static final String KEY_CPU_LOSSES = "cpu_losses";
  public static final String KEY_DRAWS = "draws";
  private int userTotal;
  private int cpuTotal;
  private int whoWon;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_result);

    TextView userResultView = (TextView) findViewById(R.id.moddedUser);
    TextView cpuResultView = (TextView) findViewById(R.id.moddedCPU);
    TextView gameResultView = (TextView) findViewById(R.id.gameResult);
    TextView playAgainView = (TextView) findViewById(R.id.playAgain);
    View nextButton = findViewById(R.id.next_button);
    View quitButton = findViewById(R.id.quit_button);

    GifImageView gifView = (GifImageView)findViewById(R.id.gifView);

    userTotal = getIntent().getIntExtra(KEY_USER_TOTAL, 1);
    cpuTotal = getIntent().getIntExtra(KEY_CPU_TOTAL, 1);
    int moddedUserTotal = userTotal % 6;
    int moddedCpuTotal = cpuTotal % 6;

    userResultView.setText("Your final value: " + moddedUserTotal);
    cpuResultView.setText("CPU's final value: " + moddedCpuTotal);

    if (moddedUserTotal > moddedCpuTotal) {
      whoWon = 1;
      gameResultView.setText("YOU WIN!!");
      gifView.setImageResource(R.drawable.win);
    } else if (moddedUserTotal < moddedCpuTotal) {
      whoWon = 2;
      gameResultView.setText("YOU LOSE, BUMMER..");
      gifView.setImageResource(R.drawable.loss);
    } else {
      whoWon = 3;
      gameResultView.setText("IT'S A DRAW!");
      gifView.setImageResource(R.drawable.draw);
    }

    nextButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        returnToMain();
      }
    });

    quitButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("Exit me", true);
        startActivity(intent);
        finish();
      }
    });


  }

  private void returnToMain () {
    Intent intent = new Intent(this, MainActivity.class);
    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    intent.putExtra(MainActivity.KEY_GAME_RESULT, whoWon);
    intent.putExtra(MainActivity.KEY_USER_WINS, getIntent().getIntExtra(KEY_USER_WINS, 0));
    intent.putExtra(MainActivity.KEY_USER_LOSSES, getIntent().getIntExtra(KEY_USER_LOSSES, 0));
    intent.putExtra(MainActivity.KEY_CPU_WINS, getIntent().getIntExtra(KEY_CPU_WINS, 0));
    intent.putExtra(MainActivity.KEY_CPU_LOSSES, getIntent().getIntExtra(KEY_CPU_LOSSES, 0));
    intent.putExtra(MainActivity.KEY_DRAWS, getIntent().getIntExtra(KEY_DRAWS, 0));
    startActivity(intent);
  }
}
