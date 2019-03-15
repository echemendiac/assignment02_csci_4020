package com.example.chris.assignment02_csci_4020;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class Original_Game extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game);


        Player player = new Player(this);

        TextView playerName_tv = findViewById(R.id.playerName_tv);
        playerName_tv.setText(player.getName());

        findViewById(R.id.start_b).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                randomSequence sequence = new randomSequence();


            }
        });
    }



}
