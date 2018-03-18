package com.example.vivekbhalodiya.railticket.feature.trainsearch;

import android.app.ProgressDialog;
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
import com.lapism.searchview.SearchItem;
import com.lapism.searchview.SearchView;
import com.pnikosis.materialishprogress.ProgressWheel;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import timber.log.Timber;

public class TrainSearchActivity extends BaseActivity<ActivityTrainSearchBinding, TrainSearchViewModel, TrainSearchView>
    implements TrainSearchView, DatePickerDialog.OnDateSetListener {
  private ProgressWheel mProgressWheel;
  private ProgressDialog progressDialog;
  private SearchView searchView;
  private TextView sourceStation;
  SearchAdapter searchAdapter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    progressDialog = new ProgressDialog(this);
    progressDialog.setMessage("Loading...");
    Toolbar toolbar = findViewById(R.id.train_search_toolbar);
    searchView = findViewById(R.id.searchView);
    searchView.setVisibility(View.INVISIBLE);
    sourceStation = findViewById(R.id.from_station);
    sourceStation.setOnClickListener(v -> {
      searchView.setVisibility(View.VISIBLE);
      searchView.open(false);
    });
    setupSearchView();
    setSupportActionBar(toolbar);
  }

  private void setupSearchView() {
    searchView.setAlpha(1.0f);
    searchView.setHintColor(ContextCompat.getColor(getApplicationContext(),R.color.colorAccent));
    searchView.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.colorPrimary));
    searchAdapter = new SearchAdapter(this);
    searchAdapter.setHasStableIds(true);
    searchView.setAdapter(searchAdapter);
    searchView.setShouldClearOnClose(true);
    searchView.setOnOpenCloseListener(new SearchView.OnOpenCloseListener() {
      @Override public boolean onClose() {
        searchView.setVisibility(View.INVISIBLE);
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
      searchView.setQuery(query, false);
      searchView.close(false);
      Timber.d("Item Clicked %s %s", position, query);
    });

    searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
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
    if (show) {
      progressDialog.show();
    } else {
      progressDialog.dismiss();
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
    showProgress(false);
    startActivity(intent);
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
    }
    return true;
  }
}
