package com.example.chris.assignment02_csci_4020;

import android.media.AudioAttributes;
import android.media.SoundPool;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.HashSet;
import java.util.Set;

public class MainActivity extends AppCompatActivity {
    private SoundPool soundPool;
    private Set<Integer> soundsLoaded;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        soundsLoaded = new HashSet<Integer>();
    }

    @Override
    protected void onResume(){
        super.onResume();

        AudioAttributes.Builder attrBuilder = new AudioAttributes.Builder();
        attrBuilder.setUsage(AudioAttributes.USAGE_GAME);

        SoundPool.Builder spBuilder = new SoundPool.Builder();
        spBuilder.setAudioAttributes(attrBuilder.build());
        spBuilder.setMaxStreams(2);
        soundPool = spBuilder.build();

        soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                if(status == 0){
                    //all good
                    soundsLoaded.add(sampleId);
                    Log.i("Sound", "Sound loaded " + sampleId);
                }
                else {
                    Log.i("Sound", "Failed load sound " + sampleId);
                }
            }
        });

        final int b1Sound = soundPool.load(this, R.raw.menegassbd, 1);

        if(soundsLoaded.contains(b1Sound)){
            soundPool.play(b1Sound,1.0f, 1.0f, 0, 0, 1.0f );
        }
    }
}
