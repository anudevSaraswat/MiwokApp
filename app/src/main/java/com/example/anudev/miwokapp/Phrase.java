package com.example.anudev.miwokapp;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class Phrase extends AppCompatActivity {

    MediaPlayer mp;
    AudioManager am;
    AudioManager.OnAudioFocusChangeListener changeListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phrase);

        am = (AudioManager) getSystemService(AUDIO_SERVICE);

        final ArrayList al = new ArrayList();
        al.add(new word("minto wuksus","Where are you going?", R.raw.phrase_where_are_you_going));
        al.add(new word("tinna oyasinna","What is your name?", R.raw.phrase_what_is_your_name));
        al.add(new word("oyaaset","My name is", R.raw.phrase_my_name_is));
        al.add(new word("michaksas","How are you feeling?", R.raw.phrase_how_are_you_feeling));
        al.add(new word("kuchi achit","I'm feeling good", R.raw.phrase_im_feeling_good));
        al.add(new word("aanas'aa","Are you coming?", R.raw.phrase_are_you_coming));
        al.add(new word("haa'anaam","Yes, i'm coming", R.raw.phrase_yes_im_coming));

        wordAdapter wa = new wordAdapter(this,al);
        ListView lv = findViewById(R.id.phraseview);
        lv.setAdapter(wa);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                word w = (word) al.get(position);

                int a = am.requestAudioFocus(changeListener, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

                if(a == AudioManager.AUDIOFOCUS_REQUEST_GRANTED){

                    if(mp!=null){
                        mp.stop();
                        mp.release();
                        mp = null;
                    }

                    mp = MediaPlayer.create(Phrase.this, w.getAudio());
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
