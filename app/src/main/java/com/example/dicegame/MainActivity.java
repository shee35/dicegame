package com.example.dicegame;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private int usersScore=0;
    private int usersTurn=0;
    private int computersTurn=0;
    private int computersScore=0;
    private Button roll;
    private Button hold;
    private TextView textView1;
    private TextView textView2;
    private ImageView imageX ;
    private TextView message;
    Random random = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView1 = findViewById(R.id.TV2);
        textView2 = findViewById(R.id.TV4);
        roll = findViewById(R.id.roll);
        imageX = findViewById(R.id.imageX);
        message = findViewById(R.id.displayTurn);

        roll.setOnClickListener(new View.OnClickListener() {
           // @SuppressLint("SetTextI18n")
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {
                int x = turnRoll();
                if(x!=1)
                {
                    usersTurn+=x;
                    message.setText("your turn score: "+ usersTurn);
                }
                else{
                    usersTurn = 0;
                    message.setText("computer's turn!");
                    compTurn();
                }
            }
        });

        Button reset = findViewById(R.id.reset);
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                usersTurn = 0;
                usersScore = 0;
                textView1.setText(String.valueOf(usersScore));
                computersScore = 0;
                textView2.setText(String.valueOf(computersScore));
                computersTurn = 0;
                hold.setEnabled(true);
                roll.setEnabled(true);
                message.setText("your turn!");
            }
        });

        hold = findViewById(R.id.hold);
        hold.setOnClickListener(new View.OnClickListener() {
            //@SuppressLint("SetTextI18n")
            @Override
            public void onClick(View view) {
                usersScore += usersTurn;
                textView1.setText(String.valueOf(usersScore));
                usersTurn=0;
                if(check()) {
                    message.setText("computer's turn!");
                    compTurn();
                }
                else{
                    hold.setEnabled(false);
                    roll.setEnabled(false);
                }
            }
        });
    }

    //@SuppressLint("SetTextI18n")
    private boolean check() {

        if(usersScore> 100){
            message.setText("you win!!! " + "press reset to play again");
            return false;
        }
        else if(computersScore> 100){
            message.setText("you lost!! " + "press reset to play again");
            return false;
        }
        return true;
    }

    //@SuppressLint("SetTextI18n")
    private void compTurn() {
        hold.setEnabled(false);
        roll.setEnabled(false);

        int wish =1;
        while(wish!=0){
            wish = random.nextInt(2);
            int x = turnRoll();
            if(x!=1)
            {
                computersTurn +=x;
                message.setText("computer's turn score: "+ usersTurn);
            }
            else{
                computersTurn = 0;
                message.setText("your turn!");
                hold.setEnabled(true);
                roll.setEnabled(true);
                return;
            }
        }

        computersScore += computersTurn;
        textView2.setText(String.valueOf(computersScore));
        computersTurn = 0;
        if (check()) {
            message.setText("your turn!");
            hold.setEnabled(true);
            roll.setEnabled(true);
        }
    }

    int id;
    private int turnRoll(){
        String[] dice = { "dice1", "dice2", "dice3","dice4", "dice5", "dice6"};
        final int[] x = new int[1];
        x[0] = random.nextInt(6);
        String choseDice = dice[x[0]];
        id = getResources().getIdentifier("com.example.dicegame:drawable/" + choseDice, null, null);
        imageX.setImageResource(id);
        return x[0]+1;
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("textView1", usersScore);
        outState.putInt("textView2", computersScore);
        outState.putInt("userTurn", usersTurn);
        outState.putInt("compTurn", computersTurn);
        outState.putInt("id", id);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        usersScore = savedInstanceState.getInt("textView1");
        computersScore = savedInstanceState.getInt("textView2");
        usersTurn = savedInstanceState.getInt("userTurn");
        computersTurn = savedInstanceState.getInt("compTurn");
        imageX.setImageResource(savedInstanceState.getInt("id"));
    }
}