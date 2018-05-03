package com.example.ravid.guessnumber;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.ravid.guessnumber.GamePackage.ChoicesNumbers;
import com.example.ravid.guessnumber.GamePackage.DBAccess;
import com.example.ravid.guessnumber.GamePackage.EnterGame;
import com.example.ravid.guessnumber.GamePackage.GameSession;
import com.example.ravid.guessnumber.UI.Alerts;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class GameZone extends ActionBarActivity {

    private EditText choice;
    private Button choose;
    private TextView result;
    private TextView playerNumberOutput;
    private TextView winnerPlayerOutput;
    private TextView playersLeftOutput;
    private TextView gameCodeTextFeild;
    private Firebase firebaseRefLastPlayerIndex;
    private Firebase firebaseRefChoices;
    private Firebase firebaseRefNumberOfPlayers;
    private GameSession gameSession;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_zone);

        Firebase.setAndroidContext(this);

        gameSession = new GameSession();
        choice = (EditText) findViewById(R.id.choiceFeild);
        choose = (Button) findViewById(R.id.chooseButton);
        result = (TextView) findViewById(R.id.resultFeild);
        winnerPlayerOutput = (TextView) findViewById(R.id.winnerPlayerFeild);
        playerNumberOutput = (TextView) findViewById(R.id.playerNumberFeild);
        playersLeftOutput = (TextView) findViewById(R.id.PlayersLeftFeild);
        gameCodeTextFeild = (TextView) findViewById(R.id.gameCodeTextFeild);

        // Get the current game code and relate to the matching game
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String currentGameCode = extras.getString(Constants.intentParameterGameCodeKey);
            firebaseRefNumberOfPlayers = new Firebase("https://guessnumber-e84ea.firebaseio.com/Games/" + currentGameCode + "/NumberOfPlayers");
            firebaseRefLastPlayerIndex = new Firebase("https://guessnumber-e84ea.firebaseio.com/Games/" + currentGameCode + "/LastPlayerIndex");
            firebaseRefChoices = new Firebase("https://guessnumber-e84ea.firebaseio.com/Games/" + currentGameCode + "/Choices");
            gameCodeTextFeild.setText("Game Code: " + currentGameCode);
        } else {
            new Alerts().badAlert(GameZone.this, "problem loading game");
        }


        firebaseRefLastPlayerIndex.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String lastPlayerIndex = dataSnapshot.getValue(String.class);
                try {
                    gameSession.setPlayerNumber(EnterGame.getPlayerNumber(Integer.parseInt(lastPlayerIndex), firebaseRefLastPlayerIndex));
                    playerNumberOutput.setText("PLAYER " + gameSession.getPlayerNumber());
                } catch (Exception e) {
                    dbErrorAlert();
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                dbErrorAlert();
            }
        });


        firebaseRefChoices.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                gameSession.addChoiceToAllChoices(dataSnapshot);

                if (gameSession.isGameFinished()) {
                    result.setText(Integer.toString(gameSession.getWinnerChoice()));
                    winnerPlayerOutput.setText("WINNER : PLAYER " + gameSession.getWinner());
                } else {
                    playersLeftOutput.setText(Integer.toString(gameSession.getRemainingPlayers()));
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                gameSession.addChoiceToAllChoices(dataSnapshot);
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        firebaseRefNumberOfPlayers.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                gameSession.setNumberOfPlayers(Integer.parseInt(dataSnapshot.getValue(String.class)));
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });


        choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    DBAccess.getInstance().chooseNumber(choice.getText().toString(), firebaseRefChoices);
                    okInputAlert();
                } catch (IOException e) {
                    InvalidInputAlert();
                } catch (Exception ex) {
                    dbErrorAlert();
                }
            }
        });
    }



    // ************* ALERT functions *******************
    public void InvalidInputAlert() {
        new Alerts().badAlert(GameZone.this, "You can only enter a number between 1 - 9");
    }

    public void okInputAlert() {
        new Alerts().okAlert(GameZone.this, "We got your choice");
    }

    public void dbErrorAlert() {
        new Alerts().badAlert(GameZone.this, "Problem accessing server");
    }




    // ****************** Default android studio functions ********************
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_game_zone, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
