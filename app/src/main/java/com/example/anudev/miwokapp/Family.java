package com.example.anudev.miwokapp;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class Family extends AppCompatActivity {

    MediaPlayer mp;
    AudioManager am;
    AudioManager.OnAudioFocusChangeListener changeListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_family);

        am = (AudioManager)getSystemService(AUDIO_SERVICE);

        final ArrayList al = new ArrayList();
        al.add(new word("father","apa", R.drawable.family_father, R.raw.family_father));
        al.add(new word("mother","ata", R.drawable.family_mother, R.raw.family_mother));
        al.add(new word("son","angsi", R.drawable.family_son, R.raw.family_son));
        al.add(new word("daughter","tune", R.drawable.family_daughter, R.raw.family_daughter));
        al.add(new word("older brother","taachi", R.drawable.family_older_brother, R.raw.family_older_brother));
        al.add(new word("younger brother","chalitti", R.drawable.family_younger_brother, R.raw.family_younger_brother));
        al.add(new word("older sister","tete", R.drawable.family_older_sister, R.raw.family_older_sister));
        al.add(new word("younger sister","kolitti", R.drawable.family_younger_sister, R.raw.family_younger_sister));
        al.add(new word("grandmother","ama", R.drawable.family_grandmother, R.raw.family_grandmother));
        al.add(new word("grandfather","paapa", R.drawable.family_grandfather, R.raw.family_grandfather));

        wordAdapter wa = new wordAdapter(this,al);
        ListView lv = findViewById(R.id.familyview);
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

                    mp = MediaPlayer.create(Family.this,w.getAudio());
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
