package com.example.vivekbhalodiya.railticket.feature.usersignup;

import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.widget.Toast;
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

  @Override public void createFireBaseUser(final String email, final String password, final String fullName, final String phoneNum,
      String reenterPass) {
    if(userDataIsValid(email, password, fullName, phoneNum,reenterPass)) {
      showPreogressDialog("Signing Up",true);
      firebaseAuth.createUserWithEmailAndPassword(email, password)
          .addOnCompleteListener(this, task -> {
            if (task.isSuccessful()) {
              pushUserDataToDB(email, password, fullName, phoneNum);
              Log.d("Status", "Authentication successful");
            } else {
              showPreogressDialog("",false);
              showSneakerError("Some error occurred. Please try again.");
              Log.i("Status", "Failed" + task.getException());
            }
          });
    }
  }

  private boolean userDataIsValid(String email, String password, String fullName, String phoneNum, String reenterPass) {
    if(email.isEmpty() || password.isEmpty() || fullName.isEmpty() || phoneNum.isEmpty() || reenterPass.isEmpty()){
      showSneakerError("Enter all the fields");
      return false;
    }
    else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
      showSneakerError("Invalid Email ID");
      return false;
    }
    else if(password.length()< 6 || reenterPass.length() < 6){
      showSneakerError("Password should be of minimum 6 character.");
      return false;
    }
    else if(!password.equals(reenterPass)){
      showSneakerError("Password entered doesn't matched.");
      return false;
    }
    else if(phoneNum.length() != 10){
      showSneakerError("Invalid Phone Number");
      return false;
    }
    else
      return true;
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
            Toast.makeText(getApplicationContext(),"Successfully Signed up.",Toast.LENGTH_SHORT).show();
            finish();

          } else {
            showPreogressDialog("",false);
            showSneakerError("User not created. Please try again.");
            Log.i("Status", "Data Added failed");
          }
        });
  }
}