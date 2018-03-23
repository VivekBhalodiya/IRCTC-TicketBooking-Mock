package com.example.vivekbhalodiya.railticket.feature.mybookings;

import android.app.ProgressDialog;
import com.example.vivekbhalodiya.railticket.feature.base.BaseViewModel;
import com.example.vivekbhalodiya.railticket.feature.passsengerdetail.PassengerViewModel;
import com.example.vivekbhalodiya.railticket.firebase.FirebaseManager;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import timber.log.Timber;

/**
 * Created by vivekbhalodiya on 3/14/18.
 */

public class MyBookingsViewModel extends BaseViewModel<MyBookingsView> {

  ProgressDialog progressDialog;
  FirebaseManager firebaseManager = new FirebaseManager();
  PassengerViewModel passengerViewModel;

  private void getSpecificTicketFromFirebase(String pnr) {
    DatabaseReference ref =
        FirebaseDatabase.getInstance().getReference().child("user");
    ref.addListenerForSingleValueEvent(new ValueEventListener() {
      @Override public void onDataChange(DataSnapshot dataSnapshot) {
        for (DataSnapshot data : dataSnapshot.getChildren()) {
          passengerViewModel = data.child("ticket")
              .child(firebaseManager.getFireBaseUser().getUid())
              .child("ticket")
              .child(pnr)
              .getValue(PassengerViewModel.class);
        }
        progressDialog.dismiss();
        Timber.d("Passenger Name %s", passengerViewModel.getPassengerName());
      }

      @Override public void onCancelled(DatabaseError databaseError) {
        Timber.e(databaseError.getDetails());
        Timber.e(databaseError.getMessage());
        Timber.e(databaseError.toException());
      }
    });
  }
}
