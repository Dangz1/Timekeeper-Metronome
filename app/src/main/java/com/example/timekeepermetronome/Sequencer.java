package com.example.timekeepermetronome;

import android.content.Context;
import android.view.View;
import android.widget.Button;

import com.google.android.flexbox.FlexboxLayout;

import java.util.Arrays;

public class Sequencer {

    Button[] buttonArray;
    int[] buttonStates;
    int[] previousStates;
    int nButtons;
    int buttonWidth;

    Context context;
    FlexboxLayout flexboxLayout;

    Sequencer(Context context, FlexboxLayout flexboxLayout, int nButtons) {
        this.nButtons = nButtons;
        this.context = context;
        this.flexboxLayout = flexboxLayout;
        buttonWidth = 100;
        initStates();
        generateButtons();
    }

    private void initStates(){
        buttonStates = new int[nButtons];
        Arrays.fill(buttonStates, 1);
        buttonStates[0] = 2;
    }

    public int[] getButtonStates() {
        return buttonStates;
    }
    public int getnButtons(){return nButtons;}

    public void attachOnClickListener(int i, View.OnClickListener onClickListener) {
        buttonArray[i].setOnClickListener(onClickListener);
    }

    public void generateButtons() {
        buttonArray = new Button[nButtons];
        for (int i = 0; i < buttonArray.length; i++) {
            FlexboxLayout.LayoutParams params = new FlexboxLayout.LayoutParams(buttonWidth, 266);
            Button btn = new Button(context);
            btn.setId(i);
            btn.setText("");
            setBackgroundOnGenerate(btn, i);
            flexboxLayout.addView(btn, params);
            buttonArray[i] = (Button) btn;
        }
    }

    public void addButton(){
        previousStates = new int[nButtons];
        //Save states
        if (nButtons >= 0) System.arraycopy(buttonStates, 0,
                previousStates, 0, nButtons);
        nButtons++;
        buttonStates = new int[nButtons];
        reGenerateStates();
        flexboxLayout.removeAllViews();
        generateButtons();
    }

    public void removeButton(){
        previousStates = new int[nButtons];
        //Save states
        if (nButtons >= 0) System.arraycopy(buttonStates, 0,
                previousStates, 0, nButtons);
        nButtons--;
        buttonStates = new int[nButtons];
        reGenerateStates();
        flexboxLayout.removeAllViews();
        generateButtons();
    }

    private void reGenerateStates(){
        if (nButtons > previousStates.length){
            System.arraycopy(previousStates, 0,
                    buttonStates, 0, nButtons - 1);
            buttonStates[nButtons-1] = 1;
        }else {
            if (nButtons >= 0) System.arraycopy(previousStates, 0,
                    buttonStates, 0, nButtons);
        }
    }

    public void setBackgroundOnGenerate(Button btn, int n) {
        switch (buttonStates[n]) {
            case 0:
                btn.setBackgroundResource(R.drawable.btn_state_zero);
                break;
            case 1:
                btn.setBackgroundResource(R.drawable.btn_state_one);
                break;
            case 2:
                btn.setBackgroundResource(R.drawable.btn_state_two);
                break;
        }
    }

    public void changeStateOnClick(Button v) {
        int id = v.getId();
        if (buttonStates[id] < 2) {
            buttonStates[id] = buttonStates[id] + 1;
        } else {
            buttonStates[id] = 0;
        }
        setBackgroundOnGenerate(v, id);
    }
}