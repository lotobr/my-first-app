package com.lotobr.myfirstapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;

public class MyActivity extends AppCompatActivity
{
    private LinearLayout root =null;
    private TextView tvScore;
    private TextView tvBestScore;
    private Button btnNewGame;

    private int score = 0;
    private static final String BEST_SCORE = "bestScore";
    private static MyActivity myActivity = null;
    private AnimLayer animLayer = null;
    private GameView gameView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_my);

        root = (LinearLayout)findViewById(R.id.container);
        tvScore = (TextView) findViewById(R.id.tvScore);
        tvBestScore = (TextView) findViewById(R.id.tvBestScore);
        btnNewGame = (Button) findViewById(R.id.btnNewGame);
        gameView = (GameView) findViewById(R.id.gameView);
        animLayer = (AnimLayer) findViewById(R.id.animLayer);
        showBestScore(getBestScore());

        btnNewGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gameView.startGame();
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        myActivity = null;
    }

    public void clearScore() {
        score = 0;
        showScore();
    }

    public void showScore() {
        tvScore.setText(score + "");
    }

    public void addScore(int s) {
        score += s;
        showScore();

        if (score > getBestScore()) {
            //int maxScore = Math.max(score, getBestScore());
            saveBestScore(score);
            showBestScore(score);
        }
    }

    public void showBestScore(int maxScore) {
        tvBestScore.setText(maxScore + "");
    }

    public void saveBestScore(int maxScore) {
        SharedPreferences.Editor editor = getPreferences(MODE_PRIVATE).edit();
        editor.putInt(BEST_SCORE, maxScore);
        editor.apply();
    }

    public int getBestScore() {
        return getPreferences(MODE_PRIVATE).getInt(BEST_SCORE, 0);
    }

    public MyActivity() {
        myActivity = this;
    }
    public static MyActivity getMyActivity(){
        return myActivity;
    }

    public AnimLayer getAnimLayer() {
        return animLayer;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK) {
            new AlertDialog.Builder(this).setTitle("提示")
                    .setMessage("你要退出游戏吗？")
                    .setPositiveButton("退出游戏", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    })
                    .setNegativeButton("继续游戏", null)
            .show();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
