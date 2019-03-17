package com.example.chris.assignment02_csci_4020;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    Button buttons[] = new Button[4];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //---- Set Up OnclickListners ----//
        for(int j=0; j <= 3; j++){
            //Below creates a new string that matches the button id
            //Then a variable for resource id
            String buttonID = "modeb_" + (j+1);

            int resourceID = getResources().getIdentifier(buttonID, "id", getPackageName());
            buttons[j] = (Button)findViewById(resourceID);
            buttons[j].setOnClickListener(this);

            Log.i("MainActivity","Button modeb_"+j +": "+buttons[j]);
        }
    }

    @Override
    public void onClick(View v) {

        //---- Pack a new Intent ----//

        int mode;   //stores the game mode
        //Depending on the button clicked sets up the mode int.
        switch(v.getId()) {
            case R.id.modeb_1:
                mode = 1;
                break;
            case R.id.modeb_2:
                mode = 2;
                break;
            case R.id.modeb_3:
                mode = 3;
                break;
            case R.id.modeb_4:
                mode = 4;
                break;
            default:
                mode = -999;

        }

        //Create the bundle
        Bundle bundle = new Bundle();

        //Add mode int to the bundle
        bundle.putInt("MODE_KEY",mode);

        //---- Start New Activity ----//
        Intent i = new Intent(MainActivity.this, Original_Game.class);

        //Add the bundle to the intent
        i.putExtras(bundle);

        //Start the new activity
        startActivity(i);
    }
}
