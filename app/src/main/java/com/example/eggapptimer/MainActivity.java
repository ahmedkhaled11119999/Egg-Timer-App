package com.example.eggapptimer;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView textView;
    SeekBar seekBar;
    Button timerButton;
    CountDownTimer myTimer;
    boolean flag_stopped = true;

    public void startCounting(View view){
        timerButton = findViewById(R.id.button);
        if(flag_stopped){
            flag_stopped = false;
            seekBar.setEnabled(false);
            timerButton.setText("Stop!");

            myTimer = new CountDownTimer(seekBar.getProgress()*1000,1000){
            @Override
            public void onTick(long millisUntilFinished) {

                textView.setText(formatTime((int) millisUntilFinished/1000));
            }

            @Override
            public void onFinish() {
                MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(),R.raw.airhorn);
                mediaPlayer.start();
                flag_stopped = true;
                seekBar.setEnabled(true);
                timerButton.setText("Start!");
                textView.setText("00:30");
                seekBar.setProgress(30);
            }
        }.start();
        }
        else{
            myTimer.cancel();
            flag_stopped = true;
            seekBar.setEnabled(true);
            timerButton.setText("Start!");
            textView.setText("00:30");
            seekBar.setProgress(30);
        }
    }

    public String formatTime(int seconds){
        int timeInMinInt = seconds/60;
        int timeInSecInt = seconds%60;
        String timeInMin = Integer.toString(timeInMinInt);
        String timeInSec = Integer.toString(timeInSecInt);
        if(timeInMinInt < 10){
            timeInMin = "0"+timeInMinInt;
        }
        if(timeInSecInt < 10){
            timeInSec = "0"+timeInSecInt;
        }

        return timeInMin+":"+timeInSec;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        seekBar = findViewById(R.id.seekBar);
        textView = findViewById(R.id.textView);

        int max = 5*60;
        seekBar.setMax(max);
        seekBar.setProgress(30);
        textView.setText(formatTime(30));
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                textView.setText(formatTime(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
}