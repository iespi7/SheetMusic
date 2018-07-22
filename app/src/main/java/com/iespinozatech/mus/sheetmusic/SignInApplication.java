package com.iespinozatech.mus.sheetmusic;

import android.app.Application;
import com.facebook.stetho.Stetho;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

public class SignInApplication extends Application {

  private GoogleSignInClient signInClient;
  private GoogleSignInAccount signInAccount;

  @Override
  public void onCreate() {
    super.onCreate();
    GoogleSignInOptions options = new GoogleSignInOptions.Builder()
        .requestEmail()
        .requestId()
        .requestIdToken("726960436780-u3ioqjcof39jjj29pflr2vbilvm28mh4.apps.googleusercontent.com")
        .build();
    signInClient = GoogleSignIn.getClient(this, options);
    Stetho.initializeWithDefaults(this);
  }

  public GoogleSignInClient getSignInClient() {
    return signInClient;
  }

  public void setSignInClient(GoogleSignInClient signInClient) {
    this.signInClient = signInClient;
  }

  public GoogleSignInAccount getSignInAccount() {
    return signInAccount;
  }

  public void setSignInAccount(GoogleSignInAccount signInAccount) {
    this.signInAccount = signInAccount;
  }
}
