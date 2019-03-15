package com.example.chris.assignment02_csci_4020;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.InputType;
import android.widget.EditText;

import com.example.chris.assignment02_csci_4020.Original_Game;

public class Player {
    private String name;
    private int highScore;
    private int sequenceCount;
    private Context context;
    private double multiplier;

    public Player(){

    }
    public Player(String name){
        this.name = name;
    }

    public Player(Context context){
        this.context = context;
        this.name = dialogName();
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
        return sequenceCount;
    }

    public void setScore(int sequenceCount) {
        this.sequenceCount = sequenceCount;
    }

    private String dialogName() {

        final String[] name = {"Didn't save"};

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Player Name");

// Set up the input
        final EditText input = new EditText(context);
// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        builder.setView(input);

// Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                name[0] = input.getText().toString();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
        return name[0];

    }
}
