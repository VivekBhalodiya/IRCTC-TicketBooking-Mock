package com.example.vivekbhalodiya.railticket.feature.passsengerdetail;

import android.content.Intent;
import android.os.Bundle;
import com.example.vivekbhalodiya.railticket.R;
import com.example.vivekbhalodiya.railticket.api.model.TrainBetween.TrainsItem;
import com.example.vivekbhalodiya.railticket.databinding.ActivityPassengerDetailsBinding;
import com.example.vivekbhalodiya.railticket.feature.base.BaseActivity;
import com.example.vivekbhalodiya.railticket.feature.ticketview.TicketViewActivity;
import com.example.vivekbhalodiya.railticket.firebase.FirebaseManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.Random;
import timber.log.Timber;

public class PassengerDetailsActivity extends BaseActivity<ActivityPassengerDetailsBinding, PassengerViewModel, PassengerView>
    implements PassengerView {
  private TrainsItem trainsItem;
  private String passengerJourneyDate;
  private String availStatus;
  private String pref;
  FirebaseAuth firebaseAuth;
  FirebaseManager firebaseManager;
  PassengerViewModel passengerViewModel;
  DatabaseReference ref;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    firebaseAuth = FirebaseAuth.getInstance();
    firebaseManager = new FirebaseManager();
    passengerViewModel = new PassengerViewModel();
    trainsItem = (TrainsItem) getIntent().getBundleExtra("bundle").getSerializable("train");
    passengerJourneyDate = getIntent().getStringExtra("date");
    availStatus = getIntent().getStringExtra("avail");
    pref = getIntent().getStringExtra("pref");
    ref = FirebaseDatabase.getInstance()
        .getReference()
        .child("user")
        .child(firebaseManager.getFireBaseUser().getUid())
        .child("ticket");

    Timber.i(passengerJourneyDate + availStatus + pref);
    setPassengerData();

    binding.confirmBooking.setOnClickListener(v -> {
      setDataFromPassengerDetails();
      if (viewModel.checkErrors().equals(viewModel.SUCCESS)) {
        //Trigger Activity
        pushTicketDataToFirebase();
      } else {
        //Show Error
      }
    });
  }

  @Override protected int getLayoutId() {
    return R.layout.activity_passenger_details;
  }

  @Override protected void onComponentCreated() {
    viewModel = new PassengerViewModel();
  }

  public void pushTicketDataToFirebase() {
    final String pnr = "pnr-" + (new Random().nextInt(9999999) + 1000000);
    passengerViewModel.setPnr(String.valueOf(pnr));
    ref.push()
        .setValue(passengerViewModel)
        .addOnCompleteListener(this, task -> {
          if (task.isComplete()) {
            Timber.i("Success Adding Ticket");
            Intent intent = new Intent(this, TicketViewActivity.class);
            intent.putExtra("pnr", pnr);
            startActivity(intent);
          } else {
            Timber.e(task.getException());
          }
        });
  }

  private void setPassengerData() {
    passengerViewModel.setAvailStatus(availStatus);
    passengerViewModel.setDestArrivalTime(trainsItem.getDestArrivalTime());
    passengerViewModel.setFromSatationCode(trainsItem.getFromStation().getCode());
    passengerViewModel.setFromStationName(trainsItem.getFromStation().getName());
    passengerViewModel.setPassengerJourneyDate(passengerJourneyDate);
    passengerViewModel.setPref(pref);
    passengerViewModel.setTrainNum(trainsItem.getNumber());
    passengerViewModel.setTrainName(trainsItem.getName());
    passengerViewModel.setToStationName(trainsItem.getToStation().getName());
    passengerViewModel.setToStationCode(trainsItem.getToStation().getCode());
    passengerViewModel.setSrcDeptTime(trainsItem.getSrcDepartureTime());
    passengerViewModel.setTravelTime(trainsItem.getTravelTime());
  }

  private void setDataFromPassengerDetails() {

    passengerViewModel.setPassengerAadharCar(binding.passengerAadhar.getText().toString());
    passengerViewModel.setPassengerAge(binding.passengerAge.getText().toString());
    passengerViewModel.setPassengerEmail(binding.passengerEmail.getText().toString());
    passengerViewModel.setPassengerMobile(binding.passengerMobile.getText().toString());
    passengerViewModel.setPassengerName(binding.passengerName.getText().toString());
  }
}
