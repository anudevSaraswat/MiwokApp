package com.example.anudev.miwokapp;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class Color extends AppCompatActivity {

    MediaPlayer mp;
    AudioManager am;
    AudioManager.OnAudioFocusChangeListener changeListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color);

        am = (AudioManager)getSystemService(AUDIO_SERVICE);

        final ArrayList al = new ArrayList();
        al.add(new word("wetetti","red",R.drawable.color_red, R.raw.color_red));
        al.add(new word("chiwitta","mustard yellow",R.drawable.color_mustard_yellow, R.raw.color_mustard_yellow));
        al.add(new word("topissa","dusty yellow",R.drawable.color_dusty_yellow, R.raw.color_dusty_yellow));
        al.add(new word("chokokki","green",R.drawable.color_green, R.raw.color_green));
        al.add(new word("takaakki","brown",R.drawable.color_brown, R.raw.color_brown));
        al.add(new word("topoppi","gray",R.drawable.color_gray, R.raw.color_gray));
        al.add(new word("kululli","black",R.drawable.color_black, R.raw.color_black));
        al.add(new word("kelelli","white",R.drawable.color_white, R.raw.color_white));

        wordAdapter wa = new wordAdapter(this,al);
        ListView lv = findViewById(R.id.colorview);
        lv.setAdapter(wa);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                word w = (word) al.get(position);

                int a = am.requestAudioFocus(changeListener, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
                if(a == AudioManager.AUDIOFOCUS_REQUEST_GRANTED){

                    if (mp!=null) {
                        mp.stop();
                        mp.release();
                        mp = null;
                    }

                    mp = MediaPlayer.create(Color.this, w.getAudio());
                    mp.start();
                    mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mp) {
                            am.abandonAudioFocus(changeListener);
                            mp.release();
                            mp = null;
                        }
                    });
                    }

            }
        });
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        mp.release();
    }

    @Override
    protected void onPause(){
        super.onPause();
        mp.pause();
    }
}
