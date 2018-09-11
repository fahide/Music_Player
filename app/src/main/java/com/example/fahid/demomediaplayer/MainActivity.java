package com.example.fahid.demomediaplayer;

import java.util.concurrent.TimeUnit;

import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private SeekBar seekBar;
    private Button button;
    private TextView text,text1;
    int totaltime;
    MediaPlayer media;
    private double starttime=0;
    private double finaltime=0;
    Handler handler=new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        seekBar= (SeekBar) findViewById(R.id.seek);
        button= (Button) findViewById(R.id.playbtn);
        text= (TextView) findViewById(R.id.text1);
        text1= (TextView) findViewById(R.id.text2);


        media=MediaPlayer.create(this,R.raw.song);
        media.setLooping(true);
        media.seekTo(0);
        totaltime=media.getDuration();
        seekBar.setMax(totaltime);
        starttime=media.getCurrentPosition();
        finaltime=media.getDuration();


        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser)
                    media.seekTo(progress);
                    seekBar.setProgress(progress);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        text.setText(String.format("%d min,%d sec",
                TimeUnit.MILLISECONDS.toMinutes((long)starttime),
                TimeUnit.MILLISECONDS.toSeconds((long)starttime)-
                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long)starttime
                )))
        );
        seekBar.setProgress((int)starttime);
        handler.postDelayed(updatetime,100);
        text1.setText(String.format("%d min, %d sec",
                TimeUnit.MILLISECONDS.toMinutes((long)finaltime),
                TimeUnit.MILLISECONDS.toSeconds((long)finaltime)-
                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long)finaltime)
                )));

    }


    private Runnable updatetime=new Runnable() {
        @Override
        public void run() {
            starttime=media.getCurrentPosition();
            text.setText(String.format("%d min,%d sec",
                    TimeUnit.MILLISECONDS.toMinutes((long)starttime),
                    TimeUnit.MILLISECONDS.toSeconds((long)starttime)-
                            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long)starttime
                            )))
            );
            seekBar.setProgress((int)starttime);
           handler.postDelayed(this,100);

        }
    };

    public void play(View view) {
        if (!media.isPlaying()){
            media.start();
            button.setBackgroundResource(R.drawable.ic_pause_black_24dp);
        }else {
            media.pause();
            button.setBackgroundResource(R.drawable.ic_play_arrow_black_24dp);
        }
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("Exit:")
                .setMessage("Do You Want To Exit..")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                        media.stop();
                    }
                }).setNegativeButton("No",null)
                .show();
    }
}
