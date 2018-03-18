package com.example.vivekbhalodiya.railticket.feature.trainlivestatus;

import android.os.Bundle;
import android.view.View;
import com.example.vivekbhalodiya.railticket.R;
import com.example.vivekbhalodiya.railticket.constants.AppConstant;
import com.example.vivekbhalodiya.railticket.databinding.ActivityTrainLiveStationSearchBinding;
import com.example.vivekbhalodiya.railticket.feature.base.BaseActivity;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import java.util.Calendar;

public class TrainLiveStationSearchActivity
    extends BaseActivity<ActivityTrainLiveStationSearchBinding, TrainLiveStatusViewModel, TrainLiveStatusView> implements DatePickerDialog.OnDateSetListener {

  private String trainNumber;
  private String date;
  private DatePickerDialog dateDialog;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    setUpDatePicker();
    trainNumber = binding.trainNumberLiveStation.getText().toString();
    binding.liveStationDate.setOnClickListener(v -> dateDialog.show(getFragmentManager(), "DatePickerDialog"));
  }

  private void setUpDatePicker() {
    Calendar now = Calendar.getInstance();
    dateDialog = DatePickerDialog.newInstance(this
        , now.get(Calendar.YEAR)
        , now.get(Calendar.MONTH)
        , now.get(Calendar.DAY_OF_MONTH));
  }

  @Override public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
    String month = "";
    String journeyDate;
    switch (monthOfYear) {
      case 0:
        month = AppConstant.JAN;
        break;
      case 1:
        month = AppConstant.FEB;
        break;
      case 2:
        month = AppConstant.MARCH;
        break;
      case 3:
        month = AppConstant.APR;
        break;
      case 4:
        month = AppConstant.MAY;
        break;
      case 5:
        month = AppConstant.JUN;
        break;
      case 6:
        month = AppConstant.JUL;
        break;
      case 7:
        month = AppConstant.AUG;
        break;
      case 8:
        month = AppConstant.SEP;
        break;
      case 9:
        month = AppConstant.OCT;
        break;
      case 10:
        month = AppConstant.NOV;
        break;
      case 11:
        month = AppConstant.DEC;
        break;
      default:
        break;
    }
    journeyDate = dayOfMonth + " " + month + " " + year;
    binding.liveStationDate.setText(journeyDate);
  }


  @Override protected int getLayoutId() {
    return R.layout.activity_train_live_station_search;
  }

  @Override protected void onComponentCreated() {
    viewModel = new TrainLiveStatusViewModel();
  }
}
