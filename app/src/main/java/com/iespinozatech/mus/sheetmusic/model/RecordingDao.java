package com.iespinozatech.mus.sheetmusic.model;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import java.util.List;

@Dao
public interface RecordingDao {

  @Insert
  long insert(Recording recording);

  @Query("SELECT * FROM Recording ORDER BY timestamp DESC")
  List<Recording> select();

}
