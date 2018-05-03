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

import com.example.ravid.guessnumber.GamePackage.createGame;
import com.example.ravid.guessnumber.UI.Alerts;
import com.firebase.client.Firebase;

import java.io.IOException;


// This activity will initialize the game with: LastPlayerIndex route, and choices route.


public class CreateGameActivity extends ActionBarActivity {

    private Firebase firebaseGamesRef;
    private EditText numberOfPlayersInputFeild;
    private Button createGameButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_game);

        Firebase.setAndroidContext(this);

        createGameButton = (Button) findViewById(R.id.CreateGameButton);
        numberOfPlayersInputFeild = (EditText) findViewById(R.id.numberOfPlayersInputFeild);
        firebaseGamesRef = new Firebase(Constants.firebaseRefGamesRoute);

        createGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String gameCode = createGame.createTheGame(firebaseGamesRef, numberOfPlayersInputFeild.getText().toString());
                    Intent intent = new Intent(CreateGameActivity.this, GameZone.class);
                    intent.putExtra(Constants.intentParameterGameCodeKey, gameCode);
                    startActivity(intent);
                } catch (IOException e) {
                    InvalidInputAlert();
                }
            }
        });
    }

    public void InvalidInputAlert() {
        new Alerts().badAlert(CreateGameActivity.this, "Number of players must be between 1 - 50");
    }








    // ****************** Default android studio functions ********************
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_create_game, menu);
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
