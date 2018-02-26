package com.example.vivekbhalodiya.railticket.feature.adminlogin;

import android.view.View;
import com.example.vivekbhalodiya.railticket.feature.base.BaseViewModel;
import com.example.vivekbhalodiya.railticket.feature.usersignup.UserSignUpView;

/**
 * Created by vivekbhalodiya on 2/5/18.
 */

public class AdminViewModel extends BaseViewModel<AdminView> {
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

  }
  public void onClickCreateAccount(View view){

  }
}
