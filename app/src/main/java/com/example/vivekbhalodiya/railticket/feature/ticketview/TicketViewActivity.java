package com.example.vivekbhalodiya.railticket.feature.ticketview;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import com.example.vivekbhalodiya.railticket.R;
import com.example.vivekbhalodiya.railticket.databinding.ActivityTicketViewBinding;
import com.example.vivekbhalodiya.railticket.feature.base.BaseActivity;
import com.example.vivekbhalodiya.railticket.feature.base.BaseViewModel;
import com.example.vivekbhalodiya.railticket.feature.base.MvvmView;
import com.example.vivekbhalodiya.railticket.feature.passsengerdetail.PassengerViewModel;
import com.example.vivekbhalodiya.railticket.util.QRCodeManager;

public class TicketViewActivity extends BaseActivity<ActivityTicketViewBinding, BaseViewModel<MvvmView>, MvvmView> {

  PassengerViewModel ticketData = new PassengerViewModel();

  private ImageView qrCodeImageView;
  private QRCodeManager qrCodeManager = new QRCodeManager();
  private boolean isQRCodeShown = true;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    ticketData = (PassengerViewModel) getIntent().getExtras().getSerializable("ticket");
    isQRCodeShown = getIntent().getBooleanExtra("noqrcode",true);
    setTicketData();
    Toast.makeText(getApplicationContext(), "Have a safe journey.", Toast.LENGTH_SHORT).show();
    if(!isQRCodeShown)
      qrCodeImageView.setVisibility(View.INVISIBLE);
    qrCodeImageView = findViewById(R.id.ticket_qr_code);
    String pnr = getIntent().getStringExtra("pnr");
    qrCodeImageView.setImageBitmap(qrCodeManager.generateQRCode(ticketData.getPnr()));

    // getSpecificTicketFromFirebase(pnr);
  }

  @SuppressLint("SetTextI18n") private void setTicketData() {
    binding.ticketDesCode.setText(ticketData.getToStationCode() + " - ");
    binding.ticketDestName.setText(ticketData.getToStationName());
    binding.ticketDestTime.setText("Arrival - " + ticketData.getDestArrivalTime());
    binding.ticketFromStnName.setText(ticketData.getFromStationName() + " - ");
    binding.ticketSrcCode.setText(ticketData.getFromSatationCode() + " - ");
    binding.ticketPassengerName.setText(ticketData.getPassengerName());
    binding.ticketPassengerPref.setText(ticketData.getPref());
    binding.ticketPnrNumber.setText(ticketData.getPnr());
    binding.ticketSrcName.setText(ticketData.getFromStationName());
    binding.ticketSrcTime.setText("Departure - " + ticketData.getSrcDeptTime());
    binding.ticketToStnName.setText(ticketData.getToStationName());
    binding.ticketTrainNumber.setText(ticketData.getTrainNum() + " - ");
    binding.ticketTrainName.setText(ticketData.getTrainName());
    binding.ticketTravelTime.setText("Travel Time : " + ticketData.getTravelTime() + " hrs");
    binding.ticketSrcDate.setText("Date of Journey - " + ticketData.getPassengerJourneyDate());
  }

  @Override protected int getLayoutId() {
    return R.layout.activity_ticket_view;
  }

  @Override protected void onComponentCreated() {
    viewModel = new BaseViewModel<>();
  }
}
