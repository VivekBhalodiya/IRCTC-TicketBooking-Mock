package com.example.vivekbhalodiya.railticket.feature.userlogin;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import com.example.vivekbhalodiya.railticket.constants.AppConstant;
import com.example.vivekbhalodiya.railticket.feature.base.BaseViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import timber.log.Timber;

/**
 * Created by vivekbhalodiya on 2/5/18.
 */

public class UserLoginViewModel extends BaseViewModel<UserLoginView> {

  private String email = "";
  private String password = "";

  public void setEmail(String email) {
    this.email = email;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getEmail() {
    return email;
  }

  public String getPassword() {
    return password;
  }

  public void onClickLoginUser(View view) {
    Timber.i("onClick");
    if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
        getView().showView("Invalid EmailID");
      }
     else {
      FirebaseAuth.getInstance().signInWithEmailAndPassword(getEmail(),getPassword())
          .addOnCompleteListener((Activity) getView(), new OnCompleteListener<AuthResult>() {
            @Override public void onComplete(@NonNull Task<AuthResult> task) {
              if(task.isSuccessful()) {
                getView().triggerActivity(3);
                Log.i(AppConstant.TAG, "Yey");
              }
              else
                Log.i(AppConstant.TAG,"Dengey " + task.getException());
            }
          });
    }
  }
  public void onClickCreateAccount(View view){
    getView().triggerActivity(1);
  }

  public void onClickAdminLogin(View view){
    getView().triggerActivity(2);
  }
}
