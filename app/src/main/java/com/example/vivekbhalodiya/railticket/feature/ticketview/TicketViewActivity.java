package com.example.vivekbhalodiya.railticket.feature.ticketview;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.Toast;
import com.example.vivekbhalodiya.railticket.R;
import com.example.vivekbhalodiya.railticket.databinding.ActivityTicketViewBinding;
import com.example.vivekbhalodiya.railticket.feature.base.BaseActivity;
import com.example.vivekbhalodiya.railticket.feature.base.BaseViewModel;
import com.example.vivekbhalodiya.railticket.feature.base.MvvmView;
import com.example.vivekbhalodiya.railticket.feature.passsengerdetail.PassengerViewModel;
import com.example.vivekbhalodiya.railticket.firebase.FirebaseManager;
import com.example.vivekbhalodiya.railticket.util.QRCodeManager;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.Iterator;
import timber.log.Timber;

public class TicketViewActivity extends BaseActivity<ActivityTicketViewBinding,BaseViewModel<MvvmView>,MvvmView> {

  PassengerViewModel passengerViewModel;
  PassengerViewModel ticketData = new PassengerViewModel();
  ProgressDialog progressDialog;
  FirebaseManager firebaseManager = new FirebaseManager();
  private ImageView qrCodeImageView;
  private QRCodeManager qrCodeManager = new QRCodeManager();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    ticketData = (PassengerViewModel) getIntent().getExtras().getSerializable("ticket");
    setTicketData();
    Toast.makeText(getApplicationContext(),"Have a safe journey.",Toast.LENGTH_SHORT).show();
    qrCodeImageView = findViewById(R.id.ticket_qr_code);

   // getSpecificTicketFromFirebase(pnr);
  }

  private void setTicketData() {
    qrCodeImageView.setImageBitmap(qrCodeManager.generateQRCode(ticketData.getPnr()));
    binding.ticketDesCode.setText(ticketData.getToStationCode()+" - ");
    binding.ticketDestName.setText(ticketData.getToStationName());
    binding.ticketDestTime.setText("Arrival - "+ticketData.getDestArrivalTime());
    binding.ticketFromStnName.setText(ticketData.getFromStationName()+" - ");
    binding.ticketSrcCode.setText(ticketData.getFromSatationCode()+" - ");
    binding.ticketPassengerName.setText(ticketData.getPassengerName());
    binding.ticketPassengerPref.setText(ticketData.getPref());
    binding.ticketPnrNumber.setText(ticketData.getPnr());
    binding.ticketSrcName.setText(ticketData.getFromStationName());
    binding.ticketSrcTime.setText("Departure - "+ticketData.getSrcDeptTime());
    binding.ticketToStnName.setText(ticketData.getToStationName());
    binding.ticketTrainNumber.setText(ticketData.getTrainNum()+" - ");
    binding.ticketTrainName.setText(ticketData.getTrainName());
    binding.ticketTravelTime.setText("Travel Time : "+ticketData.getTravelTime() + " hrs");
    binding.ticketSrcDate.setText("Date of Journey - "+ticketData.getPassengerJourneyDate());
  }

  @Override protected int getLayoutId() {
    return R.layout.activity_ticket_view;
  }

  @Override protected void onComponentCreated() {
    viewModel = new BaseViewModel<>();
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
