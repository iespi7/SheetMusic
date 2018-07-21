package com.iespinozatech.mus.sheetmusic;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import com.iespinozatech.mus.sheetmusic.view.ConvertFragment;
import com.iespinozatech.mus.sheetmusic.view.HomeFragment;
import com.iespinozatech.mus.sheetmusic.view.RecordingFragment;
import retrofit2.Retrofit;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;


public class MainActivity extends AppCompatActivity
    implements NavigationView.OnNavigationItemSelectedListener {

  private boolean permissionToRecordAccepted = false;

  private static final int REQUEST_ALL_PERMISSION = 1000;
  private String [] permissions ={
      Manifest.permission.RECORD_AUDIO,
      Manifest.permission.WRITE_EXTERNAL_STORAGE,
      Manifest.permission.READ_EXTERNAL_STORAGE,
      Manifest.permission.INTERNET
  };

  Retrofit retrofit = new Retrofit.Builder()
      .baseUrl("https://api.sonicAPI.com")
      .addConverterFactory(SimpleXmlConverterFactory.create())
      .build();
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

    checkPermissions();

    displaySelectedScreen(R.id.nav_home);

  }

  private void checkPermissions() {
    boolean needPermission = false;
    boolean needRationale = false;
    for (String permission : permissions) {
      needPermission |= ContextCompat.checkSelfPermission(
          this, permission) != PackageManager.PERMISSION_GRANTED;
      needRationale |= ActivityCompat.shouldShowRequestPermissionRationale(
          this, permission);
    }
    if (needPermission) {
      if (needRationale) {
        // Show an explanation to the user *asynchronously* -- don't block
        // this thread waiting for the user's response! After the user
        // sees the explanation, try again to request the permission.
      } else {
        // No explanation needed; request the permission
        ActivityCompat.requestPermissions(this, permissions, REQUEST_ALL_PERMISSION);
      }
    }
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

    DrawerLayout drawer =  findViewById(R.id.drawer_layout);
    drawer.closeDrawer(GravityCompat.START);
  }

  @Override
  public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
      @NonNull int [] grantResults) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    if (requestCode == REQUEST_ALL_PERMISSION) {
      if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
        finish();
      }
    }
  }


  @Override
  public void onSaveInstanceState(@NonNull Bundle outState) {
    super.onSaveInstanceState(outState);
  }


}
