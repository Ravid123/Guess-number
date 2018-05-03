package com.example.ravid.guessnumber.GamePackage;

import com.firebase.client.DataSnapshot;

import java.util.HashMap;

/**
 * Created by Ravid on 23/11/2017.
 */
public abstract class ChoicesNumbers {
    private HashMap<String, Integer> allChoices = new HashMap<String, Integer>();

    public ChoicesNumbers() {

    }

    public void addChoiceToAllChoices(DataSnapshot dataSnapshot) {
        String newChooser = dataSnapshot.getKey();
        String newChoice = dataSnapshot.getValue(String.class);
        allChoices.put(newChooser, Integer.parseInt(newChoice));
    }

    public HashMap<String, Integer> getChoices() {
        return this.allChoices;
    }
}
