package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView playerOneScore, playerTwoScore , winningMessage;
    private Button buttons[]= new Button[9];
    private Button reset;

    private int playerOneScoreCount, playerTwoScoreCount , roundCount;
    boolean activePlayer;
     //p1=0
     //p2=1
    //empty = 2
    int gameState[] ={2,2,2,2,2,2,2,2,2};
    int winningPosition[][]={{0,1,2},{3,4,5},{6,7,8},  //rows
                              {0,3,6},{1,4,7},{2,5,8},  //columns
                               {0,4,8},{2,4,6}};     // cross

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        playerOneScore=(TextView)findViewById(R.id.playerOneScore);
        playerTwoScore=(TextView)findViewById(R.id.playerTwoScore);
        winningMessage=(TextView)findViewById(R.id.winnerMessage);

        reset=(Button)findViewById(R.id.reset);
        for(int i=0;i< buttons.length;i++){
                String buttonID = "button_"+i;
                int rID = getResources().getIdentifier(buttonID,"id",getPackageName());
                buttons[i]=(Button)findViewById(rID);
                buttons[i].setOnClickListener(this);
            }

        roundCount=0;
        playerOneScoreCount=0;
        playerTwoScoreCount=0;
        activePlayer = true;

    }

    @Override
    public void onClick(View v) {
        if(!((Button)v).getText().toString().equals("")){
            return;
        }
       String buttonID = v.getResources().getResourceEntryName(v.getId());
       int gameStatePointer = Integer.parseInt(buttonID.substring(buttonID.length()-1 , buttonID.length()));

       if(activePlayer){
           ((Button)v).setText("X");
           gameState[gameStatePointer] = 0;
       }else{
           ((Button)v).setText("O");
           gameState[gameStatePointer] = 1;
       }
       roundCount++;
       if(checkWinner()){
           if(activePlayer){
               playerOneScoreCount++;
               updatePlayerScore();
               Toast.makeText(this,"Player One Won ",Toast.LENGTH_SHORT).show();
               playAgain();
           }else{
               playerTwoScoreCount++;
               updatePlayerScore();
               Toast.makeText(this,"Player Two Won ",Toast.LENGTH_SHORT).show();
               playAgain();
           }

       }else if(roundCount == 9){
           playAgain();
           Toast.makeText(this,"No Winner",Toast.LENGTH_SHORT).show();
       }else{
           activePlayer = !activePlayer;
       }
       if(playerOneScoreCount > playerTwoScoreCount){
           winningMessage.setText("Player one is winning!");
       }else if(playerTwoScoreCount > playerOneScoreCount){
           winningMessage.setText("Player two is winning!");
       }else{
           winningMessage.setText("");
       }

       reset.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               playAgain();
               playerOneScoreCount=0;
               playerTwoScoreCount=0;
               updatePlayerScore();
           }
       });
    }

    public boolean checkWinner(){
        boolean winnerResult = false;
        for(int winningPosition [] : winningPosition){
           if((gameState[winningPosition[0]] == gameState[winningPosition[1]])
                   && (gameState[winningPosition[1]] == gameState[winningPosition[2]])
                 && (gameState[winningPosition[0]] != 2) ){
                    winnerResult = true;
                    break;
           }
        }
        return winnerResult;
    }
    public void updatePlayerScore(){
        playerOneScore.setText(Integer.toString(playerOneScoreCount));
        playerTwoScore.setText(Integer.toString(playerTwoScoreCount));
    }
    public void playAgain(){
        roundCount = 0;
        activePlayer= true;

        for(int i=0;i<buttons.length;i++){
            gameState[i] = 2;
            buttons[i].setText("");
        }
    }


}