package com.example.timekeepermetronome;

import android.content.Context;

public class MetronomeThread extends Thread{
    Metronome metronome;

    MetronomeThread(Context context, double bpm, int[] buttonStates){
        metronome = new Metronome(context, bpm, buttonStates);
        metronome.loadSound();
    }

    @Override
    public void run() {
        metronome.play();
    }

    public void end(){
        metronome.stop();
        metronome = null;
    }

    public void setBpm(double bpm){
        metronome.setBpm(bpm);
    }
    public void calcSilence(){
        metronome.calcSilence();
    }
    public void updateBtnStateArray(int[] buttonStates){metronome.updateBtnStateArray(buttonStates);}
}
