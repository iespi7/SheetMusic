package com.iespinozatech.mus.sheetmusic.model;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

//@Database(entities = {Midi.class, Recording.class}, version = 1, exportSchema = true)
public abstract class MidiDatabase extends RoomDatabase {

  private static final String DATABASE_NAME = "midi_db";

  private static MidiDatabase instance = null;

  public static MidiDatabase getInstance(Context context) {
    if (instance == null) {
      instance = Room
          .databaseBuilder(context.getApplicationContext(), MidiDatabase.class, DATABASE_NAME)
          .build();
    }
    return instance;
  }

  public abstract MidiDao getMidiDao();

  public void forgetInstance(Context context) {
    instance = null;
  }
}