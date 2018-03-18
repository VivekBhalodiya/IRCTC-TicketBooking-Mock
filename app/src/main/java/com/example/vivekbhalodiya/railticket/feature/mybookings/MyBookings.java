package com.example.vivekbhalodiya.railticket.feature.mybookings;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import com.example.vivekbhalodiya.railticket.R;
import com.example.vivekbhalodiya.railticket.databinding.ActivityMyBookingsBinding;
import com.example.vivekbhalodiya.railticket.feature.base.BaseActivity;
import com.example.vivekbhalodiya.railticket.feature.passsengerdetail.PassengerViewModel;
import com.example.vivekbhalodiya.railticket.firebase.FirebaseManager;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;
import timber.log.Timber;

interface BookingsCallback {
  void onDataUpdatedFireabse(List<PassengerViewModel> passengerViewModelList);
}

public class MyBookings extends BaseActivity<ActivityMyBookingsBinding, MyBookingsViewModel, MyBookingsView> implements BookingsCallback {

  FirebaseManager firebaseManager = new FirebaseManager();
  List<PassengerViewModel> passengerViewModel = new ArrayList<>();
  MyBookingsAdapter myBookingsAdapter = new MyBookingsAdapter();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    getSpecificTicketFromFirebase(this);
    binding.myBookingsRecyclerview.setLayoutManager(new LinearLayoutManager(this));
    binding.myBookingsRecyclerview.setAdapter(myBookingsAdapter);
  }

  @Override protected int getLayoutId() {
    return R.layout.activity_my_bookings;
  }

  @Override protected void onComponentCreated() {
    viewModel = new MyBookingsViewModel();
  }

  private void getSpecificTicketFromFirebase(BookingsCallback bookingsCallback) {
    DatabaseReference ref =
        FirebaseDatabase.getInstance().getReference().child("user").child(firebaseManager.getFireBaseUser().getUid()).child("ticket");

    ref.addValueEventListener(new ValueEventListener() {
      @Override public void onDataChange(DataSnapshot dataSnapshot) {
        for (DataSnapshot data : dataSnapshot.getChildren()) {
          passengerViewModel.add(data.getValue(PassengerViewModel.class));
        }
        bookingsCallback.onDataUpdatedFireabse(passengerViewModel);
      }

      @Override public void onCancelled(DatabaseError databaseError) {
        Timber.e(databaseError.toException());
      }
    });
  }

  @Override public void onDataUpdatedFireabse(List<PassengerViewModel> passengerViewModelList) {
    myBookingsAdapter.setBookingsAdapterData(passengerViewModelList);
  }
}
