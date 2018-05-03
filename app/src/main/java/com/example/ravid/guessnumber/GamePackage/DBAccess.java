package com.example.ravid.guessnumber.GamePackage;

import com.firebase.client.Firebase;

import java.io.IOException;

/**
 * Created by Ravid on 23/11/2017.
 */
public class DBAccess {
    private static DBAccess instance = null;
    private Firebase firebaseRef;

    private DBAccess() {
        firebaseRef = new Firebase("https://guessnumber-e84ea.firebaseio.com/");
    }

    public static DBAccess getInstance() {
        if (instance == null) {
            instance = new DBAccess();
        }
        return instance;
    }

    public void chooseNumber(String choice, Firebase firebaseRefChoices) throws Exception {
        // Validate the input
        if (ValidateChoice(choice)) {
            Firebase firebaseCurrentChoice = firebaseRefChoices.child("Choice " + Integer.toString(EnterGame.getPlayerNumber(0, null)));
            firebaseCurrentChoice.setValue(choice);
        } else {
            throw new IOException("Invalid input");
        }
    }

    private boolean ValidateChoice (String choiceString) {
        if (choiceString.matches("\\d")) {
            return true;
        }
        return false;
    }
}
