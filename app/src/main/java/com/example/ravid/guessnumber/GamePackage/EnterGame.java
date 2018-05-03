package com.example.ravid.guessnumber.GamePackage;

import com.firebase.client.Firebase;

/**
 * Created by Ravid on 23/11/2017.
 */
public class EnterGame {
    private static EnterGame instance = null;
    private static int playerNumber;

    private EnterGame(int playerNum) {
        playerNumber = playerNum;
    }

    public static int getPlayerNumber(int lastPlayerIndex, Firebase firebaseRefLastPlayerIndex) throws Exception {
        if (instance == null) {
            int newPlayerNumber = lastPlayerIndex + 1;

            // Update the last player's index in the DB
            String newLastIndex = Integer.toString(newPlayerNumber);
            firebaseRefLastPlayerIndex.setValue(newLastIndex);

            instance = new EnterGame(newPlayerNumber);
        }
        return playerNumber;
    }
}
