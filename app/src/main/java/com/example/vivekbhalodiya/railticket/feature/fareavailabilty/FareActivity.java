package com.example.vivekbhalodiya.railticket.feature.fareavailabilty;

import android.os.Bundle;
import com.example.vivekbhalodiya.railticket.R;
import com.example.vivekbhalodiya.railticket.databinding.ActivityFareBinding;
import com.example.vivekbhalodiya.railticket.feature.base.BaseActivity;

public class FareActivity extends BaseActivity<ActivityFareBinding, FareViewModel, FareView> implements FareView {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Override protected int getLayoutId() {
    return R.layout.activity_fare;
  }

  @Override protected void onComponentCreated() {
    viewModel = new FareViewModel();
  }
}

