package com.iespinozatech.mus.sheetmusic.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

@Entity(foreignKeys = @ForeignKey(entity = Recording.class,
    parentColumns = "recording_id", childColumns = "recording_id"))
public class Midi {

  @PrimaryKey(autoGenerate = true)
  @ColumnInfo(name = "midi_id")
  private long midiId;

  //Will need foreign key.
  @ColumnInfo(name = "recording_id")
  private long recordingId;

  @ColumnInfo(name = "midi_number")
  private int midiNumber;

  @ColumnInfo(name = "note_name")
  private String noteName;

  @ColumnInfo(name = "note_length")
  private double noteLength;

  public long getMidiId() {
    return midiId;
  }

  public void setMidiId(long midiId) {
    this.midiId = midiId;
  }

  public long getRecordingId() {
    return recordingId;
  }

  public void setRecordingId(long recordingId) {
    this.recordingId = recordingId;
  }

  public int getMidiNumber() {
    return midiNumber;
  }

  public void setMidiNumber(int midiNumber) {
    this.midiNumber = midiNumber;
  }

  public String getNoteName() {
    return noteName;
  }

  public void setNoteName(String noteName) {
    this.noteName = noteName;
  }

  public double getNoteLength() {
    return noteLength;
  }

  public void setNoteLength(double noteLength) {
    this.noteLength = noteLength;
  }
}
