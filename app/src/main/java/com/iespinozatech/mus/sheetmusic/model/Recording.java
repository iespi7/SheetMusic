package com.iespinozatech.mus.sheetmusic.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;
import java.util.Date;

@Entity
public class Recording {

  @PrimaryKey(autoGenerate = true)
  @ColumnInfo(name = "recording_id")
  private long recordingId;

  @ColumnInfo(name = "recording_name", index = true)
  private String recordingName;

  @ColumnInfo(name = "recording_length", index = false)
  private long recordingLength;

  @NonNull
  private Date timestamp;

  public long getRecordingId() {
    return recordingId;
  }

  public void setRecordingId(long recordingId) {
    this.recordingId = recordingId;
  }

  public String getRecordingName() {
    return recordingName;
  }

  public void setRecordingName(String recordingName) {
    this.recordingName = recordingName;
  }

  public long getRecordingLength() {
    return recordingLength;
  }

  public void setRecordingLength(long recordingLength) {
    this.recordingLength = recordingLength;
  }

  @NonNull
  public Date getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(@NonNull Date timestamp) {
    this.timestamp = timestamp;
  }
}
