package com.example.vivekbhalodiya.railticket.feature.userlogin;

import android.content.Intent;
import android.os.Bundle;
import com.example.vivekbhalodiya.railticket.R;
import com.example.vivekbhalodiya.railticket.databinding.ActivitySigninBinding;
import com.example.vivekbhalodiya.railticket.feature.adminlogin.AdminLoginActivity;
import com.example.vivekbhalodiya.railticket.feature.base.BaseActivity;
import com.example.vivekbhalodiya.railticket.feature.trainsearch.TrainSearchActivity;
import com.example.vivekbhalodiya.railticket.feature.usersignup.UserSignUpActivity;
import com.irozon.sneaker.Sneaker;
import timber.log.Timber;

public class UserLoginActivity extends BaseActivity<ActivitySigninBinding, UserLoginViewModel, UserLoginView> implements UserLoginView {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Override protected int getLayoutId() {
    return R.layout.activity_signin;
  }

  @Override protected void onComponentCreated() {
    viewModel=new UserLoginViewModel();
  }

  @Override public void showView(String sneakerMessage) {
    Sneaker.with(this)
        .setTitle("Error !!")
        .setMessage(sneakerMessage)
        .sneakError();
    Timber.i(binding.emailInputLayout.getEditText().getText().toString());
  }

  @Override public void triggerActivity(int option) {
    if( option == 1) {
      Intent intent = new Intent(this, UserSignUpActivity.class);
      startActivity(intent);
    }
    else if( option == 2){
      Intent intent = new Intent(this, AdminLoginActivity.class);
      startActivity(intent);
    }
    else if(option ==3){
      startActivity(new Intent(this, TrainSearchActivity.class));
    }

  }
}