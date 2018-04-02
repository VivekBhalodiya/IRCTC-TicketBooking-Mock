package com.example.vivekbhalodiya.railticket.firebase;

import android.support.annotation.NonNull;
import android.util.Log;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.concurrent.Executor;

/**
 * Created by vivekbhalodiya on 2/8/18.
 */

public class FirebaseManager {
  private final DatabaseReference mFirebase;
  private final FirebaseAuth mAuth;
  private static final String PATH_USER = "User";
  private String TAG="status";

  public FirebaseManager() {
    mFirebase = FirebaseDatabase.getInstance().getReference();
    mAuth = FirebaseAuth.getInstance();
  }

  public void createFirebaseUser(String email, String password) {
    mAuth.createUserWithEmailAndPassword(email, password)
        .addOnCompleteListener((Executor) this, task -> {
          if (task.isSuccessful()) {
            Log.d(TAG, "Authentication successful");
          } else {
            Log.d(TAG,"Failed");
          }
        });
  }

  public FirebaseUser getFireBaseUser(){
    return mAuth.getCurrentUser();
  }
}
