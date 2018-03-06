package com.example.vivekbhalodiya.railticket.feature.usersignup;

import android.os.Bundle;
import android.util.Log;
import com.example.vivekbhalodiya.railticket.R;
import com.example.vivekbhalodiya.railticket.databinding.ActivityUserSignUpBinding;
import com.example.vivekbhalodiya.railticket.feature.base.BaseActivity;
import com.example.vivekbhalodiya.railticket.firebase.FirebaseManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.irozon.sneaker.Sneaker;

public class UserSignUpActivity extends BaseActivity<ActivityUserSignUpBinding, UserSignUpViewModel, UserSignUpView> implements UserSignUpView {
  FirebaseAuth firebaseAuth;
  FirebaseManager firebaseManager;
  UserSignUpViewModel userSignUpViewModel;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    firebaseAuth = FirebaseAuth.getInstance();
    firebaseManager = new FirebaseManager();
  }

  @Override protected int getLayoutId() {
    return R.layout.activity_user_sign_up;
  }

  @Override protected void onComponentCreated() {
    viewModel = new UserSignUpViewModel();
  }

  @Override public void showSneaker(String title, String message, boolean error) {
    if (error) {
      Sneaker.with(this)
          .setTitle(title)
          .setMessage(message)
          .sneakError();
    } else {
      Sneaker.with(this)
          .setTitle(title)
          .setMessage(message)
          .sneak(R.color.success_green);
    }
  }

  @Override public void createFireBaseUser(final String email, final String password, final String fullName, final String phoneNum) {
    firebaseAuth.createUserWithEmailAndPassword(email, password)
        .addOnCompleteListener(this, task -> {
          if (task.isSuccessful()) {
            pushUserDataToDB(email, password, fullName, phoneNum);
            Log.d("Status", "Authentication successful");
          } else {
            Log.i("Status", "Failed" + task.getException());
          }
        });
  }

  private void pushUserDataToDB(final String email, String password, String fullName, String phoneNum) {
    UserSignUpViewModel user = new UserSignUpViewModel(email, password, fullName, phoneNum);
    FirebaseDatabase.getInstance()
        .getReference()
        .child("user")
        .child(firebaseManager.getFireBaseUser().getUid())
        .setValue(user)
        .addOnCompleteListener(this, task -> {
          if (task.isSuccessful()) {
            Log.i("Status", "Data added");
          } else {
            Log.i("Status", "Data Added failed");
          }
        });
  }
}