package com.example.ravid.guessnumber.GamePackage;

import com.firebase.client.DataSnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Created by Ravid on 01/12/2017.
 */
public class GameSession extends ChoicesNumbers {

    private int playerNumber;
    private int[] theNumbers; // Array that contains in every cell, the amount of participants that chose the number in this cell's index.
    private int numberOfPlayers;
    private int winnerChoice;
    private String winner;
    private boolean isGameFinished;

    public GameSession() {
        this.theNumbers = new int[10];
        for (int i = 0; i < this.theNumbers.length; i++) {
            theNumbers[i] = 0;
        }
        this.isGameFinished = false;
    }

    @Override
    public void addChoiceToAllChoices(DataSnapshot dataSnapshot) {
        super.addChoiceToAllChoices(dataSnapshot);
        if (this.getChoices().size() == this.getNumberOfPlayers()) {
            this.isGameFinished = true;
            handleResults();
        }
    }

    private void handleResults() {
        copyNumbersFromDB();
        this.setWinnerChoice(this.findWinnerChoice());
        this.setWinner(this.findWinner(this.getWinnerChoice()));
    }

    private void copyNumbersFromDB() {
        for (int currentNumber : this.getChoices().values()) {
            this.getTheNumbers()[currentNumber]++;
        }
    }

    private int findWinnerChoice() {
//        int winnerChoiceFound;
//        int winnerIndex;
//        if (this.getNumberOfPlayers() == 1) {
//            winnerChoiceFound = this.getTheNumbers().get(0);
//        } else {
//            winnerIndex = this.getTheNumbers().size() - 1;
//            while ((winnerIndex > 0) && (this.getTheNumbers().get(winnerIndex) == this.getTheNumbers().get(winnerIndex - 1))) {
//                winnerIndex--;
//            }
//            // If there is no winner
//            if (winnerIndex == 0) {
//                winnerChoiceFound = -1;
//            } else if ((winnerIndex != this.getTheNumbers().size() - 1) && (this.getTheNumbers().get(winnerIndex) == this.getTheNumbers().get(winnerIndex - 1))) {
//                winnerChoiceFound = -1;
//            } else {
//                winnerChoiceFound = this.getTheNumbers().get(winnerIndex);
//            }
//
//        }
//        return winnerChoiceFound;
        int winnerChoiceFound = -1;
        boolean isWinnerChoiceFound = false;
        for (int i = this.getTheNumbers().length - 1; (i >= 0) && (!isWinnerChoiceFound); i--) {
            if (this.getTheNumbers()[i] == 1) {
                isWinnerChoiceFound = true;
                winnerChoiceFound = i;
            }
        }
        return winnerChoiceFound;
    }

    private String findWinner(int winnerChoiceFound) {
        for (String currentKey : this.getChoices().keySet()) {
            if (this.getChoices().get(currentKey) == winnerChoiceFound) {
                return currentKey;
            }
        }
        return "PROBLEM: No winner found with the winner choice given";
    }

    public int getRemainingPlayers() {
        return this.getNumberOfPlayers() - this.getChoices().size();
    }

    // Getters Setters

    public int getPlayerNumber() {
        return playerNumber;
    }

    public void setPlayerNumber(int playerNumber) {
        this.playerNumber = playerNumber;
    }

    public int[] getTheNumbers() {
        return theNumbers;
    }

    public void setTheNumbers(int[] theNumbers) {
        this.theNumbers = theNumbers;
    }

    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }

    public void setNumberOfPlayers(int numberOfPlayers) {
        this.numberOfPlayers = numberOfPlayers;
    }

    public int getWinnerChoice() {
        return winnerChoice;
    }

    public void setWinnerChoice(int winnerChoice) {
        this.winnerChoice = winnerChoice;
    }

    public boolean isGameFinished() {
        return this.isGameFinished;
    }

    public String getWinner() {
        return this.winner;
    }

    public void setWinner(String theWinner) {
        this.winner = theWinner;
    }
}
