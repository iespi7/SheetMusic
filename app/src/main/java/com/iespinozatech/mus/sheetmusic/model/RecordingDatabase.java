package com.iespinozatech.mus.sheetmusic.model;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverter;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;
import java.util.Date;

@Database(entities = {Recording.class, Midi.class}, version = 1, exportSchema = true)
@TypeConverters(Converters.class)
public abstract class RecordingDatabase extends RoomDatabase {

  private static final String DATABASE_NAME = "recording_db";

  private static RecordingDatabase instance = null;

  public static RecordingDatabase getInstance(Context context) {
    if (instance == null) {
      instance = Room
          .databaseBuilder(context.getApplicationContext(), RecordingDatabase.class, DATABASE_NAME)
          .build();
    }
    return instance;
  }

  public abstract RecordingDao getRecordingDao();

  public void forgetInstance(Context context) {
    instance = null;
  }
}
  class Converters {

    @TypeConverter
    public static Date datefromTimestamp(Long timestamp) {
      return (timestamp != null) ? new Date(timestamp) : null;
    }

    @TypeConverter
    public static Long timestampFromDate(Date date) {
      return (date != null) ? date.getTime() : null;
    }

}
