package com.example.vivekbhalodiya.railticket.feature.trainlivestatus;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import com.example.vivekbhalodiya.railticket.R;
import com.example.vivekbhalodiya.railticket.api.model.TrainLiveStatus.TrainLiveStatusResponse;
import com.example.vivekbhalodiya.railticket.databinding.ActivityTrainLiveStationSearchBinding;
import com.example.vivekbhalodiya.railticket.feature.base.BaseActivity;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import java.util.Calendar;
import java.util.Objects;

public class TrainLiveStationSearchActivity
    extends BaseActivity<ActivityTrainLiveStationSearchBinding, TrainLiveStatusViewModel, TrainLiveStatusSearchView>
    implements DatePickerDialog.OnDateSetListener,TrainLiveStatusSearchView{

  private String trainNumber="";
  private DatePickerDialog dateDialog;
  String journeyDate = "";

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    setUpDatePicker();
    binding.liveStationDate.setOnClickListener(v -> dateDialog.show(getFragmentManager(), "DatePickerDialog"));
    binding.getLiveStationButton.setOnClickListener(v -> {
      trainNumber = binding.trainNumberLiveStation.getText().toString();
      if (!Objects.equals(trainNumber, "") && !Objects.equals(journeyDate, "")) {
        if (isTrainNumberCorrect(trainNumber)) {
          viewModel.getTrainLiveStatus(trainNumber, journeyDate);
        } else {
          Toast.makeText(getApplicationContext(), "Train Number or Date Not Valid", Toast.LENGTH_SHORT).show();
        }
      }
      else
        Toast.makeText(getApplicationContext(), "Train Number or Date Not Valid", Toast.LENGTH_SHORT).show();
    });
  }

  private boolean isTrainNumberCorrect(String trainNumber) {
    int trainNumberInt = Integer.parseInt(trainNumber);
    return (trainNumberInt > 9999 && trainNumberInt < 100000);
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
    switch (monthOfYear) {
      case 0:
        month = "01";
        break;
      case 1:
        month = "02";
        break;
      case 2:
        month = "03";
        break;
      case 3:
        month = "04";
        break;
      case 4:
        month = "05";
        break;
      case 5:
        month = "06";
        break;
      case 6:
        month = "07";
        break;
      case 7:
        month = "08";
        break;
      case 8:
        month = "09";
        break;
      case 9:
        month = "10";
        break;
      case 10:
        month = "11";
        break;
      case 11:
        month = "12";
        break;
      default:
        break;
    }
    journeyDate = dayOfMonth + "-" + month + "-" + year;
    binding.liveStationDate.setText(journeyDate);
  }

  @Override protected int getLayoutId() {
    return R.layout.activity_train_live_station_search;
  }

  @Override protected void onComponentCreated() {
    viewModel = new TrainLiveStatusViewModel();
  }

  @Override public void triggerNextActivity(TrainLiveStatusResponse trainLiveStatusResponse) {
    Intent intent = new Intent(this,TrainLiveStationActivity.class);
    Bundle bundle = new Bundle();
    bundle.putSerializable("response",trainLiveStatusResponse);
    intent.putExtras(bundle);
    startActivity(intent);
  }

  @Override public void showError(String message) {
    showSneakerError(message);
  }

  @Override public void showSneakerError(String message) {
    super.showSneakerError(message);
  }
}
