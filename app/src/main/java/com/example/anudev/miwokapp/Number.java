package com.example.anudev.miwokapp;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class Number extends AppCompatActivity {

    MediaPlayer mp = new MediaPlayer();
    AudioManager am;
    AudioManager.OnAudioFocusChangeListener changeListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_number);

        am = (AudioManager) getSystemService(AUDIO_SERVICE);

        final ArrayList words = new ArrayList<>();
        words.add(new word("Lutti","One", R.drawable.number_one, R.raw.number_one));
        words.add(new word("Otiiko","Two", R.drawable.number_two, R.raw.number_two));
        words.add(new word("Tolookosu","Three", R.drawable.number_three, R.raw.number_three));
        words.add(new word("Oyissa","Four", R.drawable.number_four, R.raw.number_four));
        words.add(new word("Massoka","Five", R.drawable.number_five, R.raw.number_five));
        words.add(new word("Temmokka","Six", R.drawable.number_six, R.raw.number_six));
        words.add(new word("Kenekaku","Seven", R.drawable.number_seven, R.raw.number_seven));
        words.add(new word("Kawinta","Eight", R.drawable.number_eight, R.raw.number_eight));
        words.add(new word("Wo'e","Nine", R.drawable.number_nine, R.raw.number_nine));
        words.add(new word("Na'accha","Ten", R.drawable.number_ten, R.raw.number_ten));

        wordAdapter wa = new wordAdapter(this,words);
        ListView lv = findViewById(R.id.numberview);
        lv.setAdapter(wa);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                word w = (word) words.get(position);

                int a = am.requestAudioFocus(changeListener, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

                if(a == AudioManager.AUDIOFOCUS_REQUEST_GRANTED){

                    if(mp!=null){
                        mp.stop();
                        mp.release();
                        mp = null;
                    }

                    mp = MediaPlayer.create(Number.this, w.getAudio());
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
