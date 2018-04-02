package com.example.vivekbhalodiya.railticket.feature.qrcodescanner;

import com.example.vivekbhalodiya.railticket.feature.base.MvvmView;
import com.example.vivekbhalodiya.railticket.feature.passsengerdetail.PassengerViewModel;

/**
 * Created by vivekbhalodiya on 3/24/18.
 */

interface QRCodeScannerView extends MvvmView{
  void triggerTicketViewActivity(PassengerViewModel model);
  void triggerActivity();
}
