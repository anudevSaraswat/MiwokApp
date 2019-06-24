package com.example.anudev.miwokapp;

public class word {

  private String english;
  private String miwok;
  private int image = -1;
  private int audio;


   public word(String miwokword, String englishword, int id1, int id2){
       miwok = miwokword;
       english = englishword;
       image = id1;
       audio = id2;
   }

   public word(String miwokword, String englishword, int id2){
       miwok = miwokword;
       english = englishword;
       audio = id2;
   }

   public String geteng() {
       return english;
   }

   public String getmwk() {
       return miwok;
   }

   public int getid() {
       return image;
   }

   public int getAudio(){
       return audio;
   }
}
