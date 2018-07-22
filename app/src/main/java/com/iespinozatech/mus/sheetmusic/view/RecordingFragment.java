package com.iespinozatech.mus.sheetmusic.view;


import android.media.AudioAttributes;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.media.MediaRecorder.AudioSource;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import com.iespinozatech.mus.sheetmusic.R;
import com.iespinozatech.mus.sheetmusic.model.Recording;
import com.iespinozatech.mus.sheetmusic.model.RecordingDatabase;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.List;


public class RecordingFragment extends Fragment {

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    View root = inflater.inflate(R.layout.fragment_record, container, false);
    return root;
  }

  private static final int SAMPLE_RATE = 44100;         // Set as appropriate
  private static final int RECORD_BUFFER_MULTIPLIER = 4;
  private static final int READ_BUFFER_SIZE = 4096;
  private static final int NUM_CHANNELS = 1;            // Change this to 1 for mono.
  private static final int BITS_PER_SAMPLE_PER_CHANNEL = 16;
  private static final int AUDIO_ENCODING_FORMAT = 1;   // Corresponds to PCM.
  private static final int[] AUDIO_FORMAT_CHANNELS = {AudioFormat.CHANNEL_IN_MONO};


  private File file = null;
  private boolean recording = false;
  private ListView recordingList;
  private RecordingDatabase database;
  private ArrayAdapter<Recording> adapter;


  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    Button record = view.findViewById(R.id.record_button);
    Button stop = view.findViewById(R.id.stop_button);
    Button erase = view.findViewById(R.id.delete_button);
    recordingList = view.findViewById(R.id.record_list);
    adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_checked);
    recordingList.setAdapter(adapter);
    recordingList.setOnItemClickListener(new OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(getContext(), "Converting", Toast.LENGTH_SHORT).show();
      }
    });
    record.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        Toast.makeText(getContext(), "Recording", Toast.LENGTH_SHORT).show();
        startRecording();
      }
    });

    stop.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        Toast.makeText(getContext(), "Stop Recording", Toast.LENGTH_SHORT).show();
        stopRecording();

      }
    });

    erase.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        Toast.makeText(getContext(), "Deleting", Toast.LENGTH_SHORT).show();
        erase();
      }
    });
    new GetRecordings().execute();

  }


  private void startRecording() {
    recording = true;
    new Recorder().start();

  }

  private void stopRecording() {
    recording = false;


  }


  private void erase() {
    if (file != null && file.exists()) {
      file.delete();
      file = null;
    }
  }


  private class Recorder extends Thread {

    @Override
    public void run() {
      try {
        File external = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC);
        File rawFile = new File(external, getString(R.string.raw_filename_format, new Date()));
        // If you don't want wavFile to be public, then use internal in the next line.
        File wavFile = new File(external, getString(R.string.wav_filename_format, new Date()));
        long startTime = System.currentTimeMillis();
        recordRawAudio(rawFile);
        long finishTime = System.currentTimeMillis();
        writeWavFile(rawFile, wavFile);
        MediaScannerConnection.scanFile(
            getContext(), new String[]{wavFile.toString()}, null, null);
        file = wavFile;
        rawFile.delete();
        Recording recording = new Recording();
        recording.setRecordingName(wavFile.getName());
        recording.setRecordingLength(finishTime - startTime);
        RecordingDatabase.getInstance(getContext()).getRecordingDao().insert(recording);
        new GetRecordings().execute();
       } catch (IOException e) {
        throw new RuntimeException(e);
      }
    }

    private void recordRawAudio(File rawFile) throws IOException {
      AudioRecord record = null;
      try (
          FileOutputStream os = new FileOutputStream(rawFile);
          BufferedOutputStream output = new BufferedOutputStream(os);
      ) {
        short[] readBuffer = new short[READ_BUFFER_SIZE];
        byte[] writeBuffer = new byte[READ_BUFFER_SIZE * 2];

        record = new AudioRecord(MediaRecorder.AudioSource.MIC, SAMPLE_RATE,
            AUDIO_FORMAT_CHANNELS[NUM_CHANNELS - 1], AudioFormat.ENCODING_PCM_16BIT,
            RECORD_BUFFER_MULTIPLIER * AudioRecord.getMinBufferSize(SAMPLE_RATE,
                AudioFormat.CHANNEL_IN_STEREO, AudioFormat.ENCODING_PCM_16BIT));
        while (record.getState() != AudioRecord.STATE_INITIALIZED) {
        }
        record.startRecording();
        int readLength = 0;
        while (recording || readLength > 0) {
          if (!recording) {
            record.stop();
          }
          readLength = record.read(readBuffer, 0, readBuffer.length);
          if (readLength > 0) {
            shortArrayToLEByteArray(readBuffer, 0, readLength, writeBuffer, 0);
            output.write(writeBuffer, 0, 2 * readLength);
          }
        }
        output.flush();
      }
    }

    private void writeWavFile(File rawFile, File wavFile) throws IOException {
      try (
          FileInputStream is = new FileInputStream(rawFile);
          BufferedInputStream input = new BufferedInputStream(is);
          FileOutputStream os = new FileOutputStream(wavFile);
          BufferedOutputStream output = new BufferedOutputStream(os)
      ) {
        byte[] xferBuffer = new byte[READ_BUFFER_SIZE * 2];
        writeWavHeader(output,
            is.getChannel().size(),         // Number of bytes in raw data
            AUDIO_ENCODING_FORMAT,          // = 1 for PCM
            NUM_CHANNELS,                   // Number of channels
            SAMPLE_RATE,                    // Samples per second
            BITS_PER_SAMPLE_PER_CHANNEL
        );
        while (true) {
          int readLength = input
              .read(xferBuffer, 0, Math.min(input.available(), xferBuffer.length));
          if (readLength <= 0) {
            break;
          } else {
            output.write(xferBuffer, 0, readLength);
          }
        }
        output.flush();
      }
    }


    private void shortArrayToLEByteArray(short[] input, int readOffset, int readLength,
        byte[] output, int writeOffset) {
      for (int i = readOffset, j = writeOffset; i < readOffset + readLength; i++, j += 2) {
        output[j] = (byte) (input[i] & 0xff);
        output[j + 1] = (byte) ((input[i] >> 8) & 0xff);
      }
    }


    private void writeWavHeader(OutputStream output, long rawDataLength, int format,
        int channels, int sampleRate, int bitsPerSamplePerChannel) throws IOException {
      long allDataLength = rawDataLength + 36;
      short bytesPerSample = (short) (channels * bitsPerSamplePerChannel / 8);
      int byteRate = sampleRate * bytesPerSample;
      byte[] header = {
          'R', 'I', 'F', 'F',                      // [0, 4)
          (byte) (allDataLength & 0xff),           // [4, 8)
          (byte) ((allDataLength >> 8) & 0xff),
          (byte) ((allDataLength >> 16) & 0xff),
          (byte) ((allDataLength >> 24) & 0xff),
          'W', 'A', 'V', 'E',                      // [8, 12)
          'f', 'm', 't', ' ',                      // [12, 16)
          16, 0, 0, 0,                             // [16, 20)
          (byte) (format & 0xff),                  // [20, 22)
          (byte) ((format >> 8) & 0xff),
          (byte) (channels & 0xff),                // [22, 24)
          (byte) ((channels >> 8) & 0xff),
          (byte) (sampleRate & 0xff),              // [24, 28)
          (byte) ((sampleRate >> 8) & 0xff),
          (byte) ((sampleRate >> 16) & 0xff),
          (byte) ((sampleRate >> 24) & 0xff),
          (byte) (byteRate & 0xff),                // [28, 32)
          (byte) ((byteRate >> 8) & 0xff),
          (byte) ((byteRate >> 16) & 0xff),
          (byte) ((byteRate >> 24) & 0xff),
          (byte) (bytesPerSample & 0xff),          // [32, 34)
          (byte) ((bytesPerSample >> 8) & 0xff),
          (byte) (bitsPerSamplePerChannel & 0xff), // [34, 36)
          (byte) ((bitsPerSamplePerChannel >> 8) & 0xff),
          'd', 'a', 't', 'a',                      // [36, 40)
          (byte) (rawDataLength & 0xff),           // [40, 44)
          (byte) ((rawDataLength >> 8) & 0xff),
          (byte) ((rawDataLength >> 16) & 0xff),
          (byte) ((rawDataLength >> 24) & 0xff)
      };
      output.write(header);
    }

  }

  private class GetRecordings extends AsyncTask<Void, Void, List<Recording>> {

    @Override
    protected void onPostExecute(List<Recording> recordings) {
      ArrayAdapter<Recording> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, recordings);
      recordingList.setAdapter(adapter);
    }

    @Override
    protected List<Recording> doInBackground(Void... voids) {
      return RecordingDatabase.getInstance(getContext()).getRecordingDao().select();
    }
  }

}

