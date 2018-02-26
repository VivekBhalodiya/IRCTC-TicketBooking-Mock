package com.example.vivekbhalodiya.railticket.feature.userlogin;

import com.example.vivekbhalodiya.railticket.feature.base.MvvmView;

/**
 * Created by vivekbhalodiya on 2/6/18.
 */

public interface UserLoginView extends MvvmView {
  void showView(String sneakerMessage);
  void triggerActivity(int option);
}
