package com.example.vivekbhalodiya.railticket.feature.trainsearch;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import com.example.vivekbhalodiya.railticket.R;
import com.example.vivekbhalodiya.railticket.api.model.TrainBetween.TrainsItem;
import com.example.vivekbhalodiya.railticket.api.model.TrainFare.ClassesItem;
import com.example.vivekbhalodiya.railticket.constants.AppConstant;
import com.example.vivekbhalodiya.railticket.databinding.ActivityTrainSearchBinding;
import com.example.vivekbhalodiya.railticket.feature.base.BaseActivity;
import com.example.vivekbhalodiya.railticket.feature.trainresult.TrainResultActivity;
import com.pnikosis.materialishprogress.ProgressWheel;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import timber.log.Timber;

public class TrainSearchActivity extends BaseActivity<ActivityTrainSearchBinding, TrainSearchViewModel, TrainSearchView>
    implements TrainSearchView, DatePickerDialog.OnDateSetListener {
  private ProgressWheel mProgressWheel;
  private ProgressDialog progressDialog;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    progressDialog=new ProgressDialog(this);
    progressDialog.setMessage("Loading...");
  }

  @Override protected int getLayoutId() {
    return R.layout.activity_train_search;
  }

  @Override protected void onComponentCreated() {
    viewModel = new TrainSearchViewModel();
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
    binding.DatePicker.setText(journeyDate);
  }

  @Override public void selectDate() {
    Calendar now = Calendar.getInstance();
    DatePickerDialog dateDialog = DatePickerDialog.newInstance(this
        , now.get(Calendar.YEAR)
        , now.get(Calendar.MONTH)
        , now.get(Calendar.DAY_OF_MONTH));
    dateDialog.show(getFragmentManager(), "DatePickerDialog");
  }

  @Override public void showProgress(boolean show) {
   if(show)
     progressDialog.show();
   else
     progressDialog.dismiss();

  }

  @Override public void triggerResultActivity(ArrayList<TrainsItem> listOfTrains,
      ArrayList<ArrayList<ClassesItem>> listOfClasses) {
    Timber.i("InitialTrain %s", listOfClasses.size());
    Bundle bundle = new Bundle();
    bundle.putSerializable("trains", (Serializable)listOfTrains);
    bundle.putSerializable("classes",(Serializable)listOfClasses);
    Intent intent = new Intent(this, TrainResultActivity.class);
    intent.putExtra("intent",bundle);
    showProgress(false);
    startActivity(intent);
  }
}
