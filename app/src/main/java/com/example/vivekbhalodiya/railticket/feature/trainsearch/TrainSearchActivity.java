package com.example.vivekbhalodiya.railticket.feature.trainsearch;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.example.vivekbhalodiya.railticket.R;
import com.example.vivekbhalodiya.railticket.api.model.TrainBetween.TrainsItem;
import com.example.vivekbhalodiya.railticket.api.model.TrainFare.ClassesItem;
import com.example.vivekbhalodiya.railticket.constants.AppConstant;
import com.example.vivekbhalodiya.railticket.databinding.ActivityTrainSearchBinding;
import com.example.vivekbhalodiya.railticket.feature.base.BaseActivity;
import com.example.vivekbhalodiya.railticket.feature.mybookings.MyBookings;
import com.example.vivekbhalodiya.railticket.feature.trainresult.TrainResultActivity;
import com.example.vivekbhalodiya.railticket.feature.userlogin.UserLoginActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.lapism.searchview.SearchAdapter;
import com.lapism.searchview.SearchView;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import timber.log.Timber;

public class TrainSearchActivity extends BaseActivity<ActivityTrainSearchBinding, TrainSearchViewModel, TrainSearchView>
    implements TrainSearchView, DatePickerDialog.OnDateSetListener {
  SearchAdapter searchAdapter;
  private boolean isDatePickerClicked = false;
  private boolean isToStationClicked = false;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    Toolbar toolbar = findViewById(R.id.search_trains_toolbar);
    setSupportActionBar(toolbar);

    binding.searchView.setVisibility(View.INVISIBLE);

    binding.fromStation.setOnClickListener(v -> {
      isToStationClicked = false;
      binding.searchView.setVisibility(View.VISIBLE);
      binding.searchView.open(false);
    });

    binding.toStation.setOnClickListener(v -> {
      isToStationClicked = true;
      binding.searchView.setVisibility(View.VISIBLE);
      binding.searchView.open(false);
    });

    binding.searchTrainDatePicker.setOnClickListener(v -> {
      selectDate();
      isDatePickerClicked = true;
    });

    setupSearchView();
  }

  private void setupSearchView() {
    binding.searchView.setAlpha(1.0f);
    binding.searchView.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.white));
    searchAdapter = new SearchAdapter(this);
    searchAdapter.setHasStableIds(true);
    binding.searchView.setAdapter(searchAdapter);
    binding.searchView.setShouldClearOnClose(true);
    binding.searchView.setOnOpenCloseListener(new SearchView.OnOpenCloseListener() {
      @Override public boolean onClose() {
        binding.searchView.setVisibility(View.INVISIBLE);
        return false;
      }

      @Override public boolean onOpen() {
        return false;
      }
    });
    viewModel.setSearchAdapter(searchAdapter);

    searchAdapter.addOnItemClickListener((view, position) -> {
      TextView textView = view.findViewById(R.id.textView_item_text);
      String query = textView.getText().toString();
      String[] codeFromStationName;
      codeFromStationName = query.split("---");
      //Get Source Station From Search Bar
      if (!isToStationClicked) {
        //FromStation Clicked
        binding.fromStation.setText(codeFromStationName[1]);
        TrainSearchViewModel.sourceCode = codeFromStationName[1];
      } else {
        //TO Station Clicked
        binding.toStation.setText(codeFromStationName[1]);
        TrainSearchViewModel.destCode = codeFromStationName[1];
      }
      binding.searchView.close(false);
    });

    binding.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
      @Override public boolean onQueryTextSubmit(String query) {
        viewModel.searchStationNames(query);
        return false;
      }

      @Override public boolean onQueryTextChange(String newText) {
        return false;
      }
    });
  }

  @Override protected int getLayoutId() {
    return R.layout.activity_train_search;
  }

  @Override protected void onComponentCreated() {
    viewModel = new TrainSearchViewModel();
  }

  @Override public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
    String month = "";
    String monthNumeric = "";
    String journeyDate;
    switch (monthOfYear) {
      case 0:
        month = AppConstant.JAN;
        monthNumeric = "01";
        break;
      case 1:
        month = AppConstant.FEB;
        monthNumeric = "02";
        break;
      case 2:
        month = AppConstant.MARCH;
        monthNumeric = "03";
        break;
      case 3:
        month = AppConstant.APR;
        monthNumeric = "04";
        break;
      case 4:
        month = AppConstant.MAY;
        monthNumeric = "05";
        break;
      case 5:
        month = AppConstant.JUN;
        monthNumeric = "06";
        break;
      case 6:
        month = AppConstant.JUL;
        monthNumeric = "07";
        break;
      case 7:
        month = AppConstant.AUG;
        monthNumeric = "08";
        break;
      case 8:
        month = AppConstant.SEP;
        monthNumeric = "09";
        break;
      case 9:
        month = AppConstant.OCT;
        monthNumeric = "10";
        break;
      case 10:
        month = AppConstant.NOV;
        monthNumeric = "11";
        break;
      case 11:
        month = AppConstant.DEC;
        monthNumeric = "12";
        break;
      default:
        break;
    }
    journeyDate = dayOfMonth + " " + month + " " + year;
    if (isDatePickerClicked) {
      TrainSearchViewModel.journeyDate = dayOfMonth+"-"+monthNumeric+"-"+year;
      binding.searchTrainDatePicker.setText(journeyDate);
      isDatePickerClicked = false;
    }
  }

  public void selectDate() {
    Calendar now = Calendar.getInstance();
    DatePickerDialog dateDialog = DatePickerDialog.newInstance(this
        , now.get(Calendar.YEAR)
        , now.get(Calendar.MONTH)
        , now.get(Calendar.DAY_OF_MONTH));
    dateDialog.show(getFragmentManager(), "DatePickerDialog");
  }

  @Override public void showProgress(String message,boolean show) {
    if (show) {
      showPreogressDialog(message, show);
    } else {
      showPreogressDialog(message, show);
    }
  }

  @Override public void triggerResultActivity(ArrayList<TrainsItem> listOfTrains,
      ArrayList<ArrayList<ClassesItem>> listOfClasses) {
    Timber.i("InitialTrain %s", listOfClasses.size());
    Bundle bundle = new Bundle();
    bundle.putSerializable("trains", (Serializable) listOfTrains);
    bundle.putSerializable("classes", (Serializable) listOfClasses);
    Intent intent = new Intent(this, TrainResultActivity.class);
    intent.putExtra("intent", bundle);
    showProgress("",false);
    startActivity(intent);
  }

  @Override public void showSneaker(String message) {
    showSneakerError(message);
  }

  @Override public boolean onCreateOptionsMenu(Menu menu) {
    MenuInflater menuInflater = getMenuInflater();
    menuInflater.inflate(R.menu.users_option_menu, menu);
    return true;
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    if (item.getItemId() == R.id.my_bookings) {
      startActivity(new Intent(this, MyBookings.class));
    } else {
      FirebaseAuth.getInstance().signOut();
      Toast.makeText(this, "Logged Out Successfully", Toast.LENGTH_SHORT).show();
      startActivity(new Intent(this, UserLoginActivity.class));
      finish();
    }
    return true;
  }

  @Override public void showSneakerError(String message) {
    super.showSneakerError(message);
  }

  @Override public void showPreogressDialog(String message, boolean show) {
    super.showPreogressDialog(message, show);
  }
}
