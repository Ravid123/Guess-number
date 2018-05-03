package com.example.ravid.guessnumber;

/**
 * Created by Ravid on 30/11/2017.
 */
public class Constants {
    public static final String intentParameterGameCodeKey = "GameCode";
    public static final int minimumNumberOfPlayers = 1;
    public static final int maximumNumberOfPlayers = 50;
    public static final int minimumGameCodeNumber = 1000;
    public static final int maximumGameCodeNumber = 100000;

    // Firebase consts
    public static final String firebaseChoicesKey = "Choices";
    public static final String firebaseLastPlayerIndexKey = "LastPlayerIndex";
    public static final String firebaseNumberOfPlayersKey = "NumberOfPlayers";
    public static final String firebaseRefGamesRoute = "https://guessnumber-e84ea.firebaseio.com/Games";
}
