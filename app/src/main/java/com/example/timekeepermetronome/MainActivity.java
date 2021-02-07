package com.example.timekeepermetronome;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.flexbox.FlexboxLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {
    private MetronomeThread metronomeThread;
    private TextView bpmTextView;

    private Sequencer sequencer;
    FlexboxLayout sequencerLayout;

    //INIT SETTINGS
    int nButtons;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // START INITIALIZATION OF SEQUENCER
        init();

        //BUTTON AND VIEW INIT
        //Start button
        Button startButton = (Button) findViewById(R.id.startButton);
        startButton.setOnClickListener(startButtonListener);
        //Stop button
        Button stopButton = (Button) findViewById(R.id.stopButton);
        stopButton.setOnClickListener(stopButtonListener);
        //BPM textView
        bpmTextView = (TextView) findViewById(R.id.bpmTextView);
        //BPM increase button
        FloatingActionButton bpmPlusButton = (FloatingActionButton) findViewById(R.id.bpmPlusButton);
        bpmPlusButton.setOnClickListener(bpmPlusButtonListener);
        //BPM decrease button
        FloatingActionButton bpmMinusButton = (FloatingActionButton) findViewById(R.id.bmpMinusButton);
        bpmMinusButton.setOnClickListener(bpmMinusButtonListener);
        //Increase number of buttons button
        Button increaseNBtn = (Button) findViewById(R.id.increaseBtn);
        increaseNBtn.setOnClickListener(increaseNBtnButtonListener);
        //Decrease number of buttons button
        Button decreaseBtn = (Button) findViewById(R.id.decreaseBtn);
        decreaseBtn.setOnClickListener(decreaseNBtnButtonListener);

    }
    //----------------------------------------------ON CREATE INITIALIZATION------------------------------------------------------------
    public void init(){
        nButtons = 4;
        sequencerLayout = findViewById(R.id.flexboxContainer);
        sequencer = new Sequencer(this, sequencerLayout, nButtons);
        for (int i = 0; i < nButtons; i++) {
            sequencer.attachOnClickListener(i, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    sequencer.changeStateOnClick((Button) v);
                }
            });
        }
    }

    //----------------------------------------------ONCLICK START BUTTON------------------------------------------------------------
    final private View.OnClickListener startButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (metronomeThread == null) {
                metronomeThread = new MetronomeThread(MainActivity.this,
                        (double) Integer.parseInt(bpmTextView.getText().toString()),
                        sequencer.getButtonStates());
                //initAnimator();
                metronomeThread.start();
            }
        }
    };
    //----------------------------------------------ONCLICK STOP BUTTON-------------------------------------------------------------
    final private View.OnClickListener stopButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (metronomeThread != null) {
                metronomeThread.end();
                metronomeThread = null;
            }
        }
    };
    //----------------------------------------------ONCLICK BPM INCREASE-------------------------------------------------------------
    final private View.OnClickListener bpmPlusButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int bpm = Integer.parseInt(bpmTextView.getText().toString());
            bpm++;
            Log.d("plus", "onClick: " + bpm);
            bpmTextView.setText(String.valueOf(bpm));
            if (metronomeThread != null) {
                metronomeThread.setBpm((double) bpm);
                metronomeThread.calcSilence();
            }
        }
    };
    //----------------------------------------------ONCLICK BPM DECREASE-------------------------------------------------------------
    final private View.OnClickListener bpmMinusButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int bpm = Integer.parseInt(bpmTextView.getText().toString());
            bpm--;
            Log.d("plus", "onClick: " + bpm);
            bpmTextView.setText(String.valueOf(bpm));
            if (metronomeThread != null) {
                metronomeThread.setBpm((double) bpm);
                metronomeThread.calcSilence();
            }
        }
    };

    //----------------------------------------------ONCLICK INCREASE NUM OF BUTTONS-------------------------------------------------------------
    final private View.OnClickListener increaseNBtnButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(sequencer != null && (sequencer.getnButtons() < 8)) {
                sequencer.addButton();
                reattachOnClickListeners();
                if(metronomeThread != null) {
                    metronomeThread.updateBtnStateArray(sequencer.getButtonStates());
                };
            }
        }
    };
    //----------------------------------------------ONCLICK DECREASE NUM OF BUTTONS-------------------------------------------------------------
    final private View.OnClickListener decreaseNBtnButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(sequencer != null && (sequencer.getnButtons() > 1)) {
                sequencer.removeButton();
                reattachOnClickListeners();
                if(metronomeThread != null) {
                    metronomeThread.updateBtnStateArray(sequencer.getButtonStates());
                }
            }
        }
    };
    //----------------------------------------------REMOVE OLD BTNS AND REATTACH NEW-------------------------------------------------------------
    //----------------------------------------------SAVES OLD STATES-------------------------------------------------------------
    public void reattachOnClickListeners(){
        for (int i = 0; i < sequencer.getnButtons(); i++) {
            sequencer.attachOnClickListener(i, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    sequencer.changeStateOnClick((Button) v);
                }
            });
        }
    }
}