package com.example.vivekbhalodiya.railticket.feature.userlogin;

import android.app.Activity;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import com.example.vivekbhalodiya.railticket.constants.AppConstant;
import com.example.vivekbhalodiya.railticket.feature.base.BaseViewModel;
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
    } else if (email.equals("")) {
      getView().showView("Invalid EmailID");
    } else if(password.length() < 6){
      getView().showView("Password length should be minimum 6 character long.");
    }
    else {
      getView().showProgress("Signing In", true);
      FirebaseAuth.getInstance().signInWithEmailAndPassword(getEmail(), getPassword())
          .addOnCompleteListener((Activity) getView(), task -> {
            if (task.isSuccessful()) {
              getView().triggerActivity(3);
              Log.i(AppConstant.TAG, "Yey");
              getView().showProgress("", false);
              getView().showToast("Successfully Signed In");
              ((Activity) getView()).finish();
            } else {
              getView().showView("Invalid Email or Password");
              getView().showProgress("", false);
            }
          });
    }
  }

  public void onClickCreateAccount(View view) {
    getView().triggerActivity(1);
  }

  public void onClickTrainLiveStatus(View view) {
    getView().triggerActivity(2);
  }
}
