package com.iespinozatech.mus.sheetmusic;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.media.MediaRecorder;
import android.media.MediaRecorder.AudioEncoder;
import android.media.MediaRecorder.OutputFormat;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import com.iespinozatech.mus.sheetmusic.view.ConvertFragment;
import com.iespinozatech.mus.sheetmusic.view.HomeFragment;
import com.iespinozatech.mus.sheetmusic.view.RecordingFragment;
import java.io.IOException;


public class MainActivity extends AppCompatActivity
    implements NavigationView.OnNavigationItemSelectedListener {

  private static final String LOG_TAG = "AudioRecord";
  private static final int REQUEST_RECORD_AUDIO_PERMISSION = 200;
  private static String FileName = null;


  private MediaRecorder Recorder = null;

  private boolean permissionToRecordAccepted = false;
  private String [] permissions ={Manifest.permission.RECORD_AUDIO};

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);


    DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
    ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
        this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
    drawer.addDrawerListener(toggle);
    toggle.syncState();

    NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
    navigationView.setNavigationItemSelectedListener(this);
    displaySelectedScreen(R.id.nav_home);
  }

  @Override
  public void onBackPressed() {
    DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
    if (drawer.isDrawerOpen(GravityCompat.START)) {
      drawer.closeDrawer(GravityCompat.START);
    } else {
      super.onBackPressed();
    }
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.main, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the HomeFragment/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    int id = item.getItemId();

    //noinspection SimplifiableIfStatement
    if (id == R.id.action_settings) {
      return true;
    }

    return super.onOptionsItemSelected(item);
  }

  @SuppressWarnings("StatementWithEmptyBody")
  @Override
  public boolean onNavigationItemSelected(MenuItem item) {
    displaySelectedScreen(item.getItemId());
    return true;
  }

  private void displaySelectedScreen(int itemId) {

    Fragment fragment = null;

    switch (itemId) {
      case R.id.nav_home:
        fragment = new HomeFragment();
        break;
      case R.id.nav_record:
        fragment = new RecordingFragment();
        break;
      case R.id.nav_convert:
        fragment = new ConvertFragment();
        break;
    }

    if (fragment != null) {
      FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
      ft.replace(R.id.content_frame, fragment);
      ft.commit();
    }

    DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
    drawer.closeDrawer(GravityCompat.START);
  }

  @Override
  public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
      @NonNull int [] grantResults) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    switch (requestCode){
      case REQUEST_RECORD_AUDIO_PERMISSION : permissionToRecordAccepted =
          grantResults[0] == PackageManager.PERMISSION_GRANTED;
        break;
    }
    if (!permissionToRecordAccepted) finish();

  }

  private void onRecord (boolean start) {
    if (start) {
      startRecording();
    } else {
      stopRecording();
    }
  }

  private void startRecording() {
    Recorder = new MediaRecorder();
    Recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
    Recorder.setOutputFormat(OutputFormat.THREE_GPP);
    Recorder.setOutputFile(FileName);
    Recorder.setAudioEncoder(AudioEncoder.AMR_NB);

    try {
      Recorder.prepare();
    }catch (IOException e) {
      Log.e(LOG_TAG, "prepare() failed");
    }
    Recorder.start();
  }

  private void stopRecording() {
    Recorder.stop();
    Recorder.release();
    Recorder = null;
  }

  class RecordButton extends AppCompatButton {
    boolean StartRecording = true;

    OnClickListener clicker = new OnClickListener() {
      public void onClick(View v) {
        onRecord(StartRecording);
        if(StartRecording) {

        } else {

        }
        StartRecording = !StartRecording;
      }
    };

    public RecordButton(Context context) {
      super (context);
      setOnClickListener(clicker);
    }
  }
}
