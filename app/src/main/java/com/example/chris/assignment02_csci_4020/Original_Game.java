package com.example.chris.assignment02_csci_4020;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.AudioAttributes;
import android.media.Image;
import android.media.SoundPool;
import android.os.AsyncTask;
import android.os.Bundle;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Switch;
import android.widget.TextView;
import android.view.KeyEvent;


import java.util.Collections;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.Vector;

import com.example.chris.assignment02_csci_4020.correctSequence;


public class Original_Game extends AppCompatActivity{

    //---- Get Mode Key ----//

    //create a bundle from the intent started earlier in MainActivity
    Bundle bundle;

    //pull key out and store the mode information
    int mode_key;

    public void setMode_key(int mode_key){ this.mode_key=mode_key; }

    Game_Engine game;           //This object controls the game
    TextView    highScore_tv,  //Some of the textviews for the game
                playerScore_tv,
                title_tv;

    Button      green_b,
                red_b,
                yellow_b,
                blue_b;
    Vector<Integer> buttonmap;

    private SoundPool soundPool;
    private Set<Integer> soundsLoaded;

    private int HighScore;

    final randomSequence sequence = new randomSequence();
    final Vector<Integer> soundIds = new Vector<Integer>();

    final Animation animation = new AlphaAnimation(1, 0); // Change alpha from fully visible to invisible


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game);

        bundle = getIntent().getExtras();
        mode_key = bundle.getInt("MODE_KEY");

        soundsLoaded = new HashSet<Integer>();

        SharedPreferences prefs = this.getSharedPreferences("myPrefsKey", Context.MODE_PRIVATE);

        switch (mode_key) {
            case 1:
                HighScore = prefs.getInt("OriginalHighScore", 0);
                break;
            case 2:
                HighScore = prefs.getInt("OppositeHighScore", 0);
                break;
            case 3:
                HighScore = prefs.getInt("TricksterHighScore", 0);
                break;
            case 4:
                HighScore = prefs.getInt("SurpriseHighScore", 0);
                break;
            case -999:
                Log.i("game_load", "error getting highscore");
            default:
                Log.i("game_load", "error getting highscore");
        }

    }

    @Override
    protected void onResume() {
        super.onResume();

        SharedPreferences prefs = this.getSharedPreferences("myPrefsKey", Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = prefs.edit();
            switch (mode_key) {
                case 1:
                    HighScore = prefs.getInt("OriginalHighScore", 0);
                    break;
                case 2:
                    HighScore = prefs.getInt("OppositeHighScore", 0);
                    break;
                case 3:
                    HighScore = prefs.getInt("TricksterHighScore", 0);
                    break;
                case 4:
                    HighScore = prefs.getInt("SurpriseHighScore", 0);
                    break;
                case -999:
                    Log.i("game_load", "error getting highscore");
                default:
                    Log.i("game_load", "error getting highscore");
            }

        Log.i("highscore", "highscore is currently " + HighScore);

        //---- Set Up Title ----//
        title_tv = findViewById(R.id.title_tv);

        Log.i("mode","Mode: " +mode_key);

        switch (mode_key){
            case 1:
                title_tv.setText("Original");
                break;
            case 2:
                title_tv.setText("Opposite");
                break;
            case 3:
                title_tv.setText("Trickster");
                break;
            case 4:
                title_tv.setText("Surprise!");
                break;
            case -999:
                title_tv.setText("Error: Wrong Button");
            default:
                title_tv.append("\nERROR");
        }





        Log.i("o game", "am I working");

        //---- Set Up TextViews ----//
        highScore_tv = findViewById(R.id.high_score_tv);
        highScore_tv.setText("High Score: " + HighScore);
        playerScore_tv = findViewById(R.id.playerScore_tv);

        //---- Create Java GameGrid Buttons ----//


        //---- SetUp GameGrid Button Onclick ----//
        green_b = findViewById(R.id.gameb1);
        red_b = findViewById(R.id.gameb2);
        yellow_b = findViewById(R.id.gameb3);
        blue_b = findViewById(R.id.gameb4);


        AudioAttributes.Builder attrBuilder = new AudioAttributes.Builder();
        attrBuilder.setUsage(AudioAttributes.USAGE_GAME);

        SoundPool.Builder spBuilder = new SoundPool.Builder();
        spBuilder.setAudioAttributes(attrBuilder.build());
        spBuilder.setMaxStreams(4);
        soundPool = spBuilder.build();

        soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                if (status == 0) { // success
                    soundsLoaded.add(sampleId);
                    Log.i("SOUND", "Sound loaded " + sampleId);
                } else {
                    Log.i("SOUND", "Error cannot load sound status = " + status);
                }
            }
        });

        final int gb1Sound = soundPool.load(this, R.raw.resofactorcblip, 1);
        final int gb2Sound = soundPool.load(this, R.raw.jnvrbsse, 1);
        final int gb3Sound = soundPool.load(this, R.raw.modularsamplesdsitetrashortsfshorts, 1);
        final int gb4Sound = soundPool.load(this, R.raw.menegassbd, 1);

        soundIds.add(gb1Sound);
        soundIds.add(gb2Sound);
        soundIds.add(gb3Sound);
        soundIds.add(gb4Sound);

        animation.setDuration(50);
        animation.setStartOffset(20);
        animation.setRepeatMode(Animation.REVERSE);
        animation.setRepeatCount(1);

        findViewById(R.id.gameb1).setSoundEffectsEnabled(false);
        findViewById(R.id.gameb2).setSoundEffectsEnabled(false);
        findViewById(R.id.gameb3).setSoundEffectsEnabled(false);
        findViewById(R.id.gameb4).setSoundEffectsEnabled(false);

        for(int j=1; j <= 4; j++) {
            //Below creates a new string that matches the button id
            //Then a variable for resource id
            String buttonID = "gameb" + j;

            Log.i("Game Button", "gameb" +j);

            if (mode_key == 4) {
                findViewById(R.id.gameb1).setBackgroundColor(Color.GRAY);
                findViewById(R.id.gameb2).setBackgroundColor(Color.GRAY);
                findViewById(R.id.gameb3).setBackgroundColor(Color.GRAY);
                findViewById(R.id.gameb4).setBackgroundColor(Color.GRAY);
            }

            int resourceID = getResources().getIdentifier(buttonID, "id", getPackageName());
            Button gameb = (Button) findViewById(resourceID);
            gameb.setOnClickListener(new View.OnClickListener() {
                //Simplified code by using id instead multiple onclicklistners
                int id;
                @Override
                public void onClick(View v) {
                    switch(v.getId()){
                        case R.id.gameb1:
                            id=1;break;
                        case R.id.gameb2:
                            id=2;break;
                        case R.id.gameb3:
                            id=3;break;
                        case R.id.gameb4:
                            id=4;break;
                        default:
                            id=-999;
                    }
                    playSound(id, id);
                    if(game != null) {
                        if (game.isStarted()) {
                            game.addPlayerInput(id);
                            onInputChecks(editor);
                        }
                    }
                }});

        }


        findViewById(R.id.start_b).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                //---- Start new Game Objects ----//
                game = new Game_Engine(mode_key);


                buttonmap = new Vector<Integer>();
                //buttonmap.addAll(sequence.getSequence());
                Log.i("O Game", buttonmap+"");

                for(int i=0; i<buttonmap.size();i++){

                    //read in value from map Reader
                    switch(buttonmap.get(i)){
                        case 1:
                            green_b.setBackgroundColor(Color.rgb(255, 35, 35));
                            break;
                        case 2:
                            red_b.setBackgroundColor(Color.RED);
                            break;
                        case 3:
                            yellow_b.setBackgroundColor(Color.YELLOW);
                            break;
                        case 4:
                            blue_b.setBackgroundColor(Color.BLUE);
                            break;
                        default:
                            Log.i("O Game", "Error: randomsequence off track" + buttonmap.get(i));

                    }

                }

                //startCounter();
                if(game.getPlayerISize() > 0 || game.isStarted()){
                    game.reset();
                }
                sequence.clearSequence();

                game.addGameInput(sequence.getSequence());
                playSequence(game.getGameInput(), soundIds);

                playerScore_tv.setText("Current Score: " + game.getScore());

            }
        });

    }

    private void onInputChecks(SharedPreferences.Editor e){
        if(mode_key != 2) {
            if (game.getPlayerISize() == game.getGameInputSize()) {
                if (correctSequence.check(game.getGameInput(), game.getPlayerInput())) {
                    game.addGameInput(sequence.getSequence());
                    game.updateScore(game.getScore() + 1);
                    playerScore_tv.setText("Current Score: " + game.getScore());

                    if (game.getScore() > HighScore) {
                        switch (mode_key) {
                            case 1:
                                e.putInt("OriginalHighScore", game.getScore());
                                break;
                            case 2:
                                e.putInt("OppositeHighScore", game.getScore());

                                break;
                            case 3:
                                e.putInt("TricksterHighScore", game.getScore());
                                break;
                            case 4:
                                e.putInt("SurpriseHighScore", game.getScore());
                                break;
                            case -999:
                                Log.i("game_checks", "error pushing highscore");
                            default:
                                Log.i("game_checks", "error pushing highscore");
                        }
                        e.commit();
                        HighScore = game.getScore();
                        highScore_tv.setText("High Score: " + HighScore);
                    }

                    playSequence(game.getGameInput(), soundIds);
                    game.clearPlayerInput();
                } else if (!correctSequence.check(game.getGameInput(), game.getPlayerInput())) {
                    game.reset();
                    sequence.clearSequence();
                }

            }
        }
        else if(mode_key == 2){
            if (game.getPlayerISize() == game.getGameInputSize()) {
                if (correctSequence.oppositeCheck(game.getGameInput(), game.getPlayerInput())) {
                    game.addGameInput(sequence.getSequence());
                    game.updateScore(game.getScore() + 1);
                    playerScore_tv.setText("Current Score: " + game.getScore());

                    if (game.getScore() > HighScore) {
                        switch (mode_key) {
                            case 1:
                                e.putInt("OriginalHighScore", game.getScore());
                                break;
                            case 2:
                                e.putInt("OppositeHighScore", game.getScore());

                                break;
                            case 3:
                                e.putInt("TricksterHighScore", game.getScore());
                                break;
                            case 4:
                                e.putInt("SurpriseHighScore", game.getScore());
                                break;
                            case -999:
                                Log.i("game_checks", "error pushing highscore");
                            default:
                                Log.i("game_checks", "error pushing highscore");
                        }
                        e.commit();
                        HighScore = game.getScore();
                        highScore_tv.setText("High Score: " + HighScore);
                    }

                    playSequence(game.getGameInput(), soundIds);
                    game.clearPlayerInput();
                } else if (!correctSequence.oppositeCheck(game.getGameInput(), game.getPlayerInput())) {
                    game.reset();
                    sequence.clearSequence();
                }

            }
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        if (soundPool != null) {
            soundPool.release();
            soundPool = null;

            soundsLoaded.clear();
        }
    }

    private void playSound(int soundId, int flash) {
        switch (flash) {
            case 1:
                findViewById(R.id.gameb1).startAnimation(animation);
                break;
            case 2:
                findViewById(R.id.gameb2).startAnimation(animation);
                break;
            case 3:
                findViewById(R.id.gameb3).startAnimation(animation);
                break;
            case 4:
                findViewById(R.id.gameb4).startAnimation(animation);
                break;
        }
        if(mode_key != 4) {
            if (soundsLoaded.contains(soundId)) {
                soundPool.play(soundIds.elementAt((soundId - 1)), 1.0f, 1.0f, 0, 0, 1.0f);
            }
        }
        else if (mode_key == 4) {
            soundPool.play(soundIds.elementAt(0), 1.0f, 1.0f, 0, 0, 1.0f);
        }
    }

    private void playSequence(Vector<Integer> gInput, final Vector<Integer> sounds){
        Random trickster = new Random();

        findViewById(R.id.start_b).setEnabled(false);
        findViewById(R.id.gameb1).setEnabled(false);
        findViewById(R.id.gameb2).setEnabled(false);
        findViewById(R.id.gameb3).setEnabled(false);
        findViewById(R.id.gameb4).setEnabled(false);
        findViewById(R.id.instructions_b).setEnabled(false);


        UpdateTask updateTask = new UpdateTask();
        updateTask.execute();

        /*for(int i = 0; i < gInput.size(); i++){
            try {
                Thread.sleep(375);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if(gInput.elementAt(i) == 1){
                Log.i("game", "hit green");
                //findViewById(R.id.gameb1).startAnimation(animation);
                if(mode_key == 3 && (trickster.nextInt(4)+ 1) == 4 && game.getScore() > 1){
                    playSound((trickster.nextInt(4)+ 1), gInput.elementAt(i));
                }
                else {
                    playSound(gInput.elementAt(i), gInput.elementAt(i));
                }
                try {
                    Thread.sleep(250);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            else if(gInput.elementAt(i) == 2){
                Log.i("game", "hit red");
                //findViewById(R.id.gameb2).startAnimation(animation);
                if(mode_key == 3 && (trickster.nextInt(4)+ 1) == 4 && game.getScore() > 1){
                    playSound((trickster.nextInt(4)+ 1), gInput.elementAt(i));
                }
                else {
                    playSound(gInput.elementAt(i), gInput.elementAt(i));
                }
                try {
                    Thread.sleep(250);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            else if(gInput.elementAt(i) == 3){
                Log.i("game", "hit yellow");
                //findViewById(R.id.gameb3).startAnimation(animation);
                if(mode_key == 3 && (trickster.nextInt(4)+ 1) == 4 && game.getScore() > 1){
                    playSound((trickster.nextInt(4)+ 1), gInput.elementAt(i));
                }
                else {
                    playSound(gInput.elementAt(i), gInput.elementAt(i));
                }
                try {
                    Thread.sleep(250);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            else if(gInput.elementAt(i) == 4 && game.getScore() > 1){
                Log.i("game", "hit blue");
                //findViewById(R.id.gameb4).startAnimation(animation);
                if(mode_key == 3 && (trickster.nextInt(4)+ 1) == 4){
                    playSound((trickster.nextInt(4)+ 1), gInput.elementAt(i));
                }
                else {
                    playSound(gInput.elementAt(i), gInput.elementAt(i));
                }
                try {
                    Thread.sleep(250);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }*/

        findViewById(R.id.start_b).setEnabled(true);
        findViewById(R.id.gameb1).setEnabled(true);
        findViewById(R.id.gameb2).setEnabled(true);
        findViewById(R.id.gameb3).setEnabled(true);
        findViewById(R.id.gameb4).setEnabled(true);
        findViewById(R.id.instructions_b).setEnabled(true);
    }

    class UpdateTask extends AsyncTask<Void, Void, Void>{
        @Override
        protected Void doInBackground(Void... voids){
            final Vector<Integer> gi = game.getGameInput();
            final Random trickster = new Random();

            for(int j = 0; j < gi.size(); j++){
                try {
                    Thread.sleep(250);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                final int i = j;
            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                        if(gi.elementAt(i) == 1){
                            Log.i("game", "hit green");
                            findViewById(R.id.gameb1).startAnimation(animation);
                            if(mode_key == 3 && (trickster.nextInt(4)+ 1) == 4 && game.getScore() > 1){
                                playSound((trickster.nextInt(4)+ 1), gi.elementAt(i));
                            }
                            else {
                                playSound(gi.elementAt(i), gi.elementAt(i));
                            }
                        }
                        else if(gi.elementAt(i) == 2){
                            Log.i("game", "hit red");
                            findViewById(R.id.gameb2).startAnimation(animation);
                            if(mode_key == 3 && (trickster.nextInt(4)+ 1) == 4 && game.getScore() > 1){
                                playSound((trickster.nextInt(4)+ 1), gi.elementAt(i));
                            }
                            else {
                                playSound(gi.elementAt(i), gi.elementAt(i));
                            }
                        }
                        else if(gi.elementAt(i) == 3){
                            Log.i("game", "hit yellow");
                            findViewById(R.id.gameb3).startAnimation(animation);
                            if(mode_key == 3 && (trickster.nextInt(4)+ 1) == 4 && game.getScore() > 1){
                                playSound((trickster.nextInt(4)+ 1), gi.elementAt(i));
                            }
                            else {
                                playSound(gi.elementAt(i), gi.elementAt(i));
                            }
                        }
                        else if(gi.elementAt(i) == 4 && game.getScore() > 1){
                            Log.i("game", "hit blue");
                            findViewById(R.id.gameb4).startAnimation(animation);
                            if(mode_key == 3 && (trickster.nextInt(4)+ 1) == 4){
                                playSound((trickster.nextInt(4)+ 1), gi.elementAt(i));
                            }
                            else {
                                playSound(gi.elementAt(i), gi.elementAt(i));
                            }
                        }
                    }

            });

                try {
                    Thread.sleep(250);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }


            return null;
        }
    }


    /**
     * This function displays an alert dialog when the user clicks the 'instructions' button
     *
     * @param v View that is passed in for using onclick method
     *
     * @precondition mode_key must be declared
     * @postcondition none
     */

    public void display(View v){
        Log.i("Instructions Button","Instructions button was hit.");
        //setup a textView to be used inside the alert dialog
        String titleString = "Instructions/Rules";
        TextView textView = new TextView(v.getContext());
        //Determine the instructions to use based on what game mode app is in
        switch (mode_key){
            case 1:
                textView.setText(R.string.rules1);
                break;
            case 2:
                textView.setText(R.string.rules2);
                break;
            case 3:
                textView.setText(R.string.rules3);
                break;
            case 4:
                textView.setText(R.string.rules4);
                break;
            case 10:
                textView.setText(R.string.about);
                titleString = "About";
                break;
            case -999:
                textView.setText("Error: Invalid button pushed.\nContact developer.");
            default:
                textView.append("\n----ERRROR----");
        }
        //format the text view
        textView.setTextSize(20);
        textView.setTypeface(Typeface.DEFAULT_BOLD);
        textView.setTextColor(Color.BLACK);
        textView.setPadding(40,40,40,40);

        //Allow the text view to scroll by setting it as a child of a scrollview
        ScrollView scrollView = new ScrollView(v.getContext());
        scrollView.addView(textView);


        //Create alert dialog and set up attributes
        new AlertDialog.Builder(v.getContext())
                //set the message of the alert dialog to be a view
                .setView(scrollView)
                .setTitle(titleString)


                .setPositiveButton(android.R.string.ok
                        , new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Continue with delete operation
                    }
                })

                .show();
    }

}
