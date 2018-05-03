package com.example.ravid.guessnumber.GamePackage;

import com.example.ravid.guessnumber.Constants;
import com.firebase.client.Firebase;

import java.io.IOException;
import java.util.Random;

/**
 * Created by Ravid on 01/12/2017.
 */
public class createGame {

    public static String createTheGame(Firebase firebaseRef, String numberOfPlayers) throws IOException {
        if (isNumberOfPlayersValid(numberOfPlayers)) {
            String randomGameCode = Integer.toString(generateRandomGameCode());
            Firebase firebaseGameRef = firebaseRef.child(randomGameCode);
            Firebase firebaseGameChoicesRef = firebaseGameRef.child(Constants.firebaseChoicesKey);
            firebaseGameChoicesRef.setValue("");
            Firebase firebaseGameLastPlayerIndexRef = firebaseGameRef.child(Constants.firebaseLastPlayerIndexKey);
            firebaseGameLastPlayerIndexRef.setValue("0");
            Firebase firebaseGameNumberOfPlayersRef = firebaseGameRef.child(Constants.firebaseNumberOfPlayersKey);
            firebaseGameNumberOfPlayersRef.setValue(numberOfPlayers);
            return randomGameCode;
        } else {
            throw new IOException();
        }
    }

    private static int generateRandomGameCode() {
        Random random = new Random();
        return (random.nextInt(Constants.maximumGameCodeNumber) + Constants.minimumGameCodeNumber);
    }

    private static boolean isNumberOfPlayersValid(String numberStr) {
        if (numberStr.matches("\\d+")) {
            int value = Integer.parseInt(numberStr);
            if (value >= Constants.minimumNumberOfPlayers && value <= Constants.maximumNumberOfPlayers) {
                return true;
            }
        }
        return false;
    }
}
