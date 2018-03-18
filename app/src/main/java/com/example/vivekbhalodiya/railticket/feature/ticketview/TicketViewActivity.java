package com.example.vivekbhalodiya.railticket.feature.ticketview;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import com.example.vivekbhalodiya.railticket.R;
import com.example.vivekbhalodiya.railticket.feature.passsengerdetail.PassengerViewModel;
import com.example.vivekbhalodiya.railticket.firebase.FirebaseManager;
import com.example.vivekbhalodiya.railticket.util.QRCodeManager;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.Iterator;
import timber.log.Timber;

public class TicketViewActivity extends AppCompatActivity {

  PassengerViewModel passengerViewModel;
  ProgressDialog progressDialog;
  FirebaseManager firebaseManager = new FirebaseManager();
  private ImageView qrCodeImageView;
  private QRCodeManager qrCodeManager = new QRCodeManager();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_ticket_view);
    qrCodeImageView = findViewById(R.id.ticket_qr_code);

    String pnr = getIntent().getStringExtra("pnr");
    qrCodeImageView.setImageBitmap(qrCodeManager.generateQRCode(pnr));

   // getSpecificTicketFromFirebase(pnr);
  }

  private void getSpecificTicketFromFirebase(String pnr) {
    Timber.d("Inside Pull Data %s ",firebaseManager.getFireBaseUser().getUid()) ;
    FirebaseDatabase.getInstance().getReference().addListenerForSingleValueEvent(new ValueEventListener() {
      @Override public void onDataChange(DataSnapshot dataSnapshot) {
        for (DataSnapshot data : dataSnapshot.getChildren()) {
          passengerViewModel = data.child("user")
              .child(firebaseManager.getFireBaseUser().getUid())
              .child("ticket")
              .child(pnr)
              .getValue(PassengerViewModel.class);
        }
        progressDialog.dismiss();
        Timber.d("Passenger Name %s",passengerViewModel.getPassengerName());
      }

      @Override public void onCancelled(DatabaseError databaseError) {
        Timber.e(databaseError.getDetails());
        Timber.e(databaseError.getMessage());
        Timber.e(databaseError.toException());
      }
    });
  }
}
