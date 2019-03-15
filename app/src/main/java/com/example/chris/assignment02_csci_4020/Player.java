package com.example.chris.assignment02_csci_4020;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.InputType;
import android.util.Log;
import android.widget.EditText;

import com.example.chris.assignment02_csci_4020.Original_Game;

import java.util.Vector;

public class Player {
    private String name;
    private int highScore;
    private int sequenceCount;
    private Context context;
    private double multiplier;


    public Player(String name) {
        this.name = name;
        this.highScore=0;
    }

    public Player(Context context) {
        this.context = context;
        this.highScore = 0;
        dialogName();
    }

    public Player(String name, int highScore) {
        this.name = name;
        this.highScore = highScore;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return highScore;
    }

    public void setScore(int sequenceCount) {
        this.sequenceCount = sequenceCount;
    }

    private void dialogName() {

        final String[] name = {""};

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Player Name");

// Set up the input
        final EditText input = new EditText(context);
// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        builder.setView(input);

// Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.i("Player Class", input.getText().toString());
                String playerName = input.getText().toString();
                Log.i("Player Class", playerName);
                pullNameOut(playerName);
            }
        });

        builder.show();

    }

    private void pullNameOut(String aName) {
        Log.i("Pull Name", aName);
        this.name = aName;
        Log.i("Pull Name", name);
    }
}
