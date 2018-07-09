package com.iespinozatech.mus.sheetmusic.model;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;

@Dao
public interface MidiDao {

  @Insert
  long insert(Midi midi);

}
