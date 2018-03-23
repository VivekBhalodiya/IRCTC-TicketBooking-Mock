package com.example.vivekbhalodiya.railticket.feature.usersignup;

import android.view.View;
import com.example.vivekbhalodiya.railticket.feature.base.BaseViewModel;

/**
 * Created by vivekbhalodiya on 2/5/18.
 */

public class UserSignUpViewModel extends BaseViewModel<UserSignUpView> {

  private String email = "";
  private String password = "";
  private String reenterPass = "";
  private String fullName = "";
  private String phoneNumber = "";

  UserSignUpViewModel(){

  }

  public UserSignUpViewModel(String email, String password, String fullName, String phoneNumber) {
    this.email = email;
    this.password = password;
    this.fullName = fullName;
    this.phoneNumber = phoneNumber;
  }

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

  public String getReenterPass() {
    return reenterPass;
  }

  public void setReenterPass(String reenterPass) {
    this.reenterPass = reenterPass;
  }

  public String getFullName() {
    return fullName;
  }

  public void setFullName(String fullName) {
    this.fullName = fullName;
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }

  public void setPhoneNumber(String mobileNumber) {
    this.phoneNumber = mobileNumber;
  }

  /*public void onRadioButtonClicked(View view){
    boolean checked  = ((RadioButton) view).isChecked();

    switch (view.getId()){
      case R.id.male_radio : if(checked) gender = "Male";
      break;
      case R.id.Female_radio : if(checked) gender = "Female";
      break;
    }
  }*/
  public void createUser(View view) {
    getView().createFireBaseUser(getEmail(),getPassword(),getFullName(),getPhoneNumber(),getReenterPass());
  }

}
