package com.example.vivekbhalodiya.railticket.feature.qrcodescanner;

import com.example.vivekbhalodiya.railticket.feature.base.BaseViewModel;
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

/**
 * Created by vivekbhalodiya on 3/24/18.
 */

public class QRCodeScannerViewModel extends BaseViewModel<QRCodeScannerView> {

  List<PassengerViewModel> passengerViewModel = new ArrayList<>();
  DatabaseReference ref =
      FirebaseDatabase.getInstance().getReference().child("user");
  private boolean isTicketFound = false;

  public void scanTicketDataInFirebase(String data) {
    Timber.d("Inside Scanner");
    ref.addListenerForSingleValueEvent(new ValueEventListener() {
      @Override public void onDataChange(DataSnapshot dataSnapshot) {
        for (DataSnapshot data : dataSnapshot.getChildren()) {
          DataSnapshot ticketSnap = data.child("ticket");
          for (DataSnapshot ticket : ticketSnap.getChildren()) {
            passengerViewModel.add(ticket.getValue(PassengerViewModel.class));
          }
        }
        checkForAValidTicketAndDisplayIt(data);
        Timber.d("List Of tickets %s", passengerViewModel.size());
      }

      @Override public void onCancelled(DatabaseError databaseError) {

      }
    });
  }

  private void checkForAValidTicketAndDisplayIt(String data) {
    for (PassengerViewModel model : passengerViewModel) {
      if (model.getPnr().equals(data)) {
        getView().triggerTicketViewActivity(model);
        isTicketFound = true;
      }
    }
    if(!isTicketFound)
      getView().triggerActivity();
  }
}
