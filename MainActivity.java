package com.example.tictactoe_game;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button[][] buttons = new Button[3][3];
    private boolean p1Turn = true;
    private int roundCount;
    private int p1Score;
    private int drawCount;
    private int p2Score;
    private TextView p1;
    private TextView draw;
    private TextView p2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("MainActivity", "onCreate(Bundle?) called");
        setContentView(R.layout.activity_main);
        p1 = findViewById(R.id.p1);
        draw = findViewById(R.id.draw_txt);
        p2 = findViewById(R.id.p2);
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                String buttonId = "button_" + i + j;
                int resID = getResources().getIdentifier(buttonId, "id", getPackageName());
                buttons[i][j] = findViewById(resID);
                buttons[i][j].setOnClickListener(this);
            }
        }
        Button buttonReset = findViewById(R.id.reset_button);
        buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetGame();
            }
        });
    }

    @Override
    protected void onStart() { super.onStart(); Log.d("MainActivity", "onStart() called"); }

    @Override
    protected void onResume() { super.onResume(); Log.d("MainActivity", "onResume() called"); }

    @Override
    protected void onPause() { super.onPause(); Log.d("MainActivity", "onPause() called"); }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        Log.i("MainActivity", "onSaveInstanceState");
        savedInstanceState.putInt("roundCount", roundCount);
        savedInstanceState.putInt("player1Points", p1Score);
        savedInstanceState.putInt("player2Points", p2Score);
        savedInstanceState.putBoolean("player1Turn", p1Turn);
    }
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.i("MainActivity", "onRestoreInstanceState");
        roundCount = savedInstanceState.getInt("roundCount");
        p1Score = savedInstanceState.getInt("player1Points");
        p2Score = savedInstanceState.getInt("player2Points");
        p1Turn = savedInstanceState.getBoolean("player1Turn");
    }

    @Override
    protected void onStop() { super.onStop(); Log.d("MainActivity", "onStop() called"); }

    @Override
    protected void onDestroy() { super.onDestroy(); Log.d("MainActivity", "onDestroy() called"); }

    @Override
    public void onClick(View v) {
        if (!((Button) v).getText().toString().equals("")) {
            return;
        }
        if (p1Turn) {
            ((Button) v).setText("X");
        } else {
            ((Button) v).setText("O");
        }
        roundCount++;
        if (checkForWin()) {
            if (p1Turn) {
                p1Wins();
            } else {
                p2Wins();
            }
        } else if (roundCount == 9) {
            draw();
        } else {
            p1Turn = !p1Turn;
        }
    }
    private boolean checkForWin() {
        String[][] field = new String[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                field[i][j] = buttons[i][j].getText().toString();
            }
        }
        for (int i = 0; i < 3; i++) {
            if (field[i][0].equals(field[i][1])
                    && field[i][0].equals(field[i][2])
                    && !field[i][0].equals("")) {
                return true;
            }
        }
        for (int i = 0; i < 3; i++) {
            if (field[0][i].equals(field[1][i])
                    && field[0][i].equals(field[2][i])
                    && !field[0][i].equals("")) {
                return true;
            }
        }
        if (field[0][0].equals(field[1][1])
                && field[0][0].equals(field[2][2])
                && !field[0][0].equals("")) {
            return true;
        }
        if (field[0][2].equals(field[1][1])
                && field[0][2].equals(field[2][0])
                && !field[0][2].equals("")) {
            return true;
        }
        return false;
    }
    private void p1Wins() {
        p1Score++;
        Toast.makeText(this, "Player 1 wins!", Toast.LENGTH_SHORT).show();
        updateScore();
        resetBoard();
    }
    private void p2Wins() {
        p2Score++;
        Toast.makeText(this, "Player 2 wins!", Toast.LENGTH_SHORT).show();
        updateScore();
        resetBoard();
    }
    private void draw() {
        drawCount++;
        Toast.makeText(this, "Draw!", Toast.LENGTH_SHORT).show();
        updateScore();
        resetBoard();
    }
    private void updateScore() {
        p1.setText("Player 1: " + p1Score);
        draw.setText("Draw: " + drawCount);
        p2.setText("Player 2: " + p2Score);
    }
    private void resetBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setText("");
            }
        }
        roundCount = 0;
        p1Turn = true;
    }
    private void resetGame() {
        p1Score = 0;
        drawCount = 0;
        p2Score = 0;
        updateScore();
        resetBoard();
    }
}