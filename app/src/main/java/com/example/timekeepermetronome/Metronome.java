package com.example.timekeepermetronome;

import android.app.Activity;
import android.content.Context;

public class Metronome {

    //State
    int[] btnState;

    //Constants
    final private int sampleRate = 44100;

    //Num of Samples
    private int nTickSamples;
    private int nTockSamples;

    //Sound Arrays
    private byte[] tickSound;
    private byte[] tockSound;
    //Silence Arrays
    private byte[] tickSilenceArray;
    private byte[] tockSilenceArray;
    private byte[] fullSilenceArray;

    //OBJ
    private final AudioPlayer audioPlayer;
    private final DataLoader dataLoader;
    Activity activity;

    //Is metronome playing?
    private boolean play = true;

    //Beats per minute
    private double bpm;



    Metronome(Context context, double bpm, int[] buttonStates){
        this.bpm = bpm;
        this.btnState = buttonStates;
        int bufferSize = 320;
        audioPlayer = new AudioPlayer(sampleRate, bufferSize);
        dataLoader = new DataLoader(context);
    }

    public void calcSilence(){
        int tickSilenceLen = (int) (((60 / bpm) * sampleRate * 2) - nTickSamples);
        int tockSilenceLen = (int) (((60 / bpm) * sampleRate * 2) - nTockSamples);
        int fullSilenceLen = (int) ((60 / bpm) * sampleRate * 2);

        tickSilenceArray = new byte[tickSilenceLen];
        for (int i = 0; i < tickSilenceLen; i++){
            tickSilenceArray[i] = 0b00000000;
        }
        tockSilenceArray = new byte[tockSilenceLen];
        for (int i = 0; i < tockSilenceLen; i++){
            tockSilenceArray[i] = 0b00000000;
        }
        fullSilenceArray = new byte[fullSilenceLen];
        for (int i = 0; i < fullSilenceLen; i++){
            fullSilenceArray[i] = 0b00000000;
        }
    }


    public void play(){
        int i = 0;
        do {
            switch (btnState[i]){
                case 0:
                    audioPlayer.writeToPlayer(fullSilenceArray);
                    break;
                case 1:
                    audioPlayer.writeToPlayer(tockSound);
                    audioPlayer.writeToPlayer(tockSilenceArray);
                    break;
                case 2:
                    audioPlayer.writeToPlayer(tickSound);
                    audioPlayer.writeToPlayer(tickSilenceArray);
                    break;
            }
            if(i < btnState.length - 1){
                i++;
            } else {
                i = 0;
            }
        } while (play);
        audioPlayer.destroyPlayer();
    }

    public void stop() {
        play = false;
    }

    public void loadSound(){
        nTickSamples = dataLoader.loadSoundIntoBuffer("ticktwo");
        tickSound = new byte[nTickSamples];
        tickSound = dataLoader.copyToArray(nTickSamples);

        nTockSamples = dataLoader.loadSoundIntoBuffer("tocktwo");
        tockSound = new byte[nTockSamples];
        tockSound = dataLoader.copyToArray(nTockSamples);
        calcSilence();
    }

    public void updateBtnStateArray(int[] buttonStates){
        btnState = buttonStates;
    }

    public void setBpm(double bpm){ this.bpm = bpm; }
}
