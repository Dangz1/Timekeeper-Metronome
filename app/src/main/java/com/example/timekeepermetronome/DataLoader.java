package com.example.timekeepermetronome;

import android.content.Context;
import android.util.Log;

import org.apache.commons.io.IOUtils;

import java.io.IOException;


public class DataLoader{

    //BUFFER
    final private int BUFFER_LENGHT = 44100;
    private byte[] BUFFER;

    Context context;

    DataLoader(Context context){
        this.context = context;
        BUFFER = new byte[BUFFER_LENGHT];
    }

    public int loadSoundIntoBuffer(String s){
        return loadData(s);

    }

    public int loadData(String s){
        int tickTockLen = 0;
        context.getResources().getIdentifier("ticktwo", "raw",  null);
        try {
            tickTockLen = IOUtils.read(
                    context.getResources().openRawResource(
                    context.getResources().getIdentifier(s, "raw",  context.getPackageName())),
                    BUFFER, 0, BUFFER_LENGHT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.d("AudioPlayer", "loadSound2: number of bytes read " + tickTockLen);
        return tickTockLen;
    }

    public byte[] copyToArray(int size){
        byte[] newArray = new byte[size];
        if (size >= 0) System.arraycopy(BUFFER, 0, newArray, 0, size);
        return newArray;
    }
}
