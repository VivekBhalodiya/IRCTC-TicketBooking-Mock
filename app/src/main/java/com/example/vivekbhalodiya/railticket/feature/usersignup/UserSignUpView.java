package com.example.vivekbhalodiya.railticket.feature.usersignup;

import com.example.vivekbhalodiya.railticket.feature.base.MvvmView;

/**
 * Created by vivekbhalodiya on 2/6/18.
 */

public interface UserSignUpView extends MvvmView {
  void showSneaker(String title, String message,boolean error);
  void createFireBaseUser(String email,String password,String fullName,String phoneNum);
}
