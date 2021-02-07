package com.example.timekeepermetronome;

import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;

public class AudioPlayer {

    private AudioTrack audioTrack;
    final private int sampleRate;
    private final int BUFFER_SIZE;



    AudioPlayer(int sampleRate, int BUFFER_SIZE){
        this.sampleRate = sampleRate;
        this.BUFFER_SIZE = BUFFER_SIZE;
        createPlayer();
    }


    public void createPlayer(){
        audioTrack = new AudioTrack(AudioManager.STREAM_MUSIC,
                sampleRate, AudioFormat.CHANNEL_OUT_MONO,
                AudioFormat.ENCODING_PCM_16BIT, BUFFER_SIZE,
                AudioTrack.MODE_STREAM);
        audioTrack.play();
    }

    public void writeToPlayer(byte[] data){
        audioTrack.write(data, 0, data.length);
    }

    public void destroyPlayer(){
        audioTrack.stop();
        audioTrack.release();
    }

}
