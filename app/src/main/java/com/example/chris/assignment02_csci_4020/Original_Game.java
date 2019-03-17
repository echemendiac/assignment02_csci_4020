package com.example.chris.assignment02_csci_4020;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.AudioAttributes;
import android.media.Image;
import android.media.SoundPool;
import android.os.AsyncTask;
import android.os.Bundle;
//import android.support.design.widget.FloatingActionButton;
//import android.support.design.widget.Snackbar;
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
import android.widget.Switch;
import android.widget.TextView;
import android.view.KeyEvent;


import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.Vector;

import com.example.chris.assignment02_csci_4020.correctSequence;


public class Original_Game extends AppCompatActivity{
    //---- Get Mode Key ----//

    //create a bundle from the intent started earlier in MainActivity
    Bundle bundle;

    //pull key out and store the mode information
    int mode_key;

    Player player;                  //This object represents the player
    Game_Engine game = new Game_Engine(mode_key);           //This object controls the game
    TextView    highScore_tv,  //Some of the textviews for the game
                playerScore_tv,
                title_tv;

    Button      green_b,
                red_b,
                yellow_b,
                blue_b;
    Vector<Integer> buttonmap;
    final int original = 1; //mode for the game

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
        HighScore = prefs.getInt("OriginalHighScore", 0);

    }

    @Override
    protected void onResume() {
        super.onResume();

        SharedPreferences prefs = this.getSharedPreferences("myPrefsKey", Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = prefs.edit();
        HighScore = prefs.getInt("OriginalHighScore", 0);

        Log.i("highscore", "highscore is currently " + HighScore);

        title_tv = findViewById(R.id.title_tv);

        //---- Create InGame Objects ----//
        player = new Player(this);


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

        //Start Button Onclick listener
        findViewById(R.id.start_b).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //playerName_tv.setText(player.getName());
                //playerScore_tv.setText(player.getScore() +" pts.");

                //---- Start new Game Objects ----//
                game = new Game_Engine(original);


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

            }
        });

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

        findViewById(R.id.gameb1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playSound(1);
                if (game.isStarted()) {
                    game.addPlayerInput(1);
                    onInputChecks(editor);
                }
            }
        });

        findViewById(R.id.gameb2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playSound(2);
                if (game.isStarted()) {
                    game.addPlayerInput(2);
                    onInputChecks(editor);
                }
            }
        });
        findViewById(R.id.gameb3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playSound(3);
                if (game.isStarted()) {
                    game.addPlayerInput(3);
                    onInputChecks(editor);
                }
            }
        });

        findViewById(R.id.gameb4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playSound(4);
                if (game.isStarted()) {
                    game.addPlayerInput(4);
                    onInputChecks(editor);
                }
            }
        });

        findViewById(R.id.start_b).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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
        if(game.getPlayerISize() == game.getGameInputSize()) {
            if (correctSequence.check(game.getGameInput(), game.getPlayerInput())) {
                game.addGameInput(sequence.getSequence());
                game.updateScore(game.getScore()+1);
                playerScore_tv.setText("Current Score: " + game.getScore());

                if(game.getScore() > HighScore){
                    e.putInt("OriginalHighScore", game.getScore());
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

    @Override
    protected void onPause() {
        super.onPause();
        if (soundPool != null) {
            soundPool.release();
            soundPool = null;

            soundsLoaded.clear();
        }
    }

    private void playSound(int soundId) {
        if (soundsLoaded.contains(soundId)) {
            soundPool.play(soundIds.elementAt((soundId-1)), 1.0f, 1.0f, 0, 0, 1.0f);
            switch(soundId){
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
        }
    }

    private void playSequence(Vector<Integer> gInput, final Vector<Integer> sounds){
        findViewById(R.id.start_b).setEnabled(false);
        findViewById(R.id.gameb1).setEnabled(false);
        findViewById(R.id.gameb2).setEnabled(false);
        findViewById(R.id.gameb3).setEnabled(false);
        findViewById(R.id.gameb4).setEnabled(false);
        findViewById(R.id.instructions_b).setEnabled(false);

        for(int i = 0; i < gInput.size(); i++){
            try {
                Thread.sleep(375);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if(gInput.elementAt(i) == 1){
                Log.i("game", "hit green");
                //findViewById(R.id.gameb1).startAnimation(animation);
                playSound(gInput.elementAt(i));
                try {
                    Thread.sleep(250);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            else if(gInput.elementAt(i) == 2){
                Log.i("game", "hit red");
                //findViewById(R.id.gameb2).startAnimation(animation);
                playSound(gInput.elementAt(i));
                try {
                    Thread.sleep(250);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            else if(gInput.elementAt(i) == 3){
                Log.i("game", "hit yellow");
                //findViewById(R.id.gameb3).startAnimation(animation);
                playSound(gInput.elementAt(i));
                try {
                    Thread.sleep(250);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            else if(gInput.elementAt(i) == 4){
                Log.i("game", "hit blue");
                //findViewById(R.id.gameb4).startAnimation(animation);
                playSound(gInput.elementAt(i));
                try {
                    Thread.sleep(250);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        findViewById(R.id.start_b).setEnabled(true);
        findViewById(R.id.gameb1).setEnabled(true);
        findViewById(R.id.gameb2).setEnabled(true);
        findViewById(R.id.gameb3).setEnabled(true);
        findViewById(R.id.gameb4).setEnabled(true);
        findViewById(R.id.instructions_b).setEnabled(true);
    }
}
