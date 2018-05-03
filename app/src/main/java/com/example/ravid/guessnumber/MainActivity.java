package com.example.ravid.guessnumber;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MainActivity extends ActionBarActivity {

    private Button createGameButton;
    private Button joinGameButton;
    private Firebase firebaseGamesRef;
    private boolean gameExists = false;
    private List<String> gameCodes = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Firebase.setAndroidContext(this);
        firebaseGamesRef = new Firebase("https://guessnumber-e84ea.firebaseio.com/Games");

        createGameButton = (Button) findViewById(R.id.createGameButton);
        createGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CreateGameActivity.class);
                startActivity(intent);
            }
        });

        joinGameButton = (Button) findViewById(R.id.joinGameButton);
        joinGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JoinGame();
            }
        });

        firebaseGamesRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                gameCodes.add(dataSnapshot.getKey());
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

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
    }

    public void JoinGame() {
        AlertDialog.Builder getGameCodeAlert = new AlertDialog.Builder(this);
        final EditText codeInput = new EditText(this);
        getGameCodeAlert.setMessage("Enter game code");
        getGameCodeAlert.setView(codeInput);
        getGameCodeAlert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
           @Override
        public void onClick(DialogInterface dialog, int which) {
               if (isGameCodeExist(codeInput.getText().toString())) {
                   // TODO : enter the game
                   Intent intent = new Intent(MainActivity.this, GameZone.class);
                   intent.putExtra(Constants.intentParameterGameCodeKey, codeInput.getText().toString());
                   startActivity(intent);
               } else {
                   // TODO : Alert for wrong game code
               }
           }
        });
        getGameCodeAlert.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        getGameCodeAlert.show();
    }

    public boolean isGameCodeExist(final String gameCode) {
        gameExists = false;
        for (String currentGameCode : gameCodes) {
            if (currentGameCode.equals(gameCode)) {
                gameExists = true;
            }
        }
        return gameExists;
    }


    //******************************************************
    //        Default android studio functions
    //******************************************************
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
