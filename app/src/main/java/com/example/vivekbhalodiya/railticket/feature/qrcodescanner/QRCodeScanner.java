package com.example.vivekbhalodiya.railticket.feature.qrcodescanner;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.PointF;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;
import com.dlazaro66.qrcodereaderview.QRCodeReaderView;
import com.example.vivekbhalodiya.railticket.R;
import com.example.vivekbhalodiya.railticket.databinding.ActivityQrcodeScannerBinding;
import com.example.vivekbhalodiya.railticket.feature.base.BaseActivity;
import timber.log.Timber;

public class QRCodeScanner extends BaseActivity<ActivityQrcodeScannerBinding,QRCodeScannerViewModel,QRCodeScannerView>
    implements QRCodeReaderView.OnQRCodeReadListener {
  QRCodeReaderView qrCodeReaderView;
  final private int REQUEST_CODE_ASK_PERMISSIONS = 123;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    
    qrCodeReaderView = binding.qrdecoderview;
    requestPermission();
  }

  private void setUpQrCodeView() {
    qrCodeReaderView.setOnQRCodeReadListener(this);

    // Use this function to enable/disable decoding
    qrCodeReaderView.setQRDecodingEnabled(true);

    // Use this function to change the autofocus interval (default is 5 secs)
    qrCodeReaderView.setAutofocusInterval(2000L);

    // Use this function to set back camera preview
    qrCodeReaderView.setBackCamera();
  }

  private void requestPermission() {
    if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED ) {
      ActivityCompat
          .requestPermissions(QRCodeScanner.this, new String[]{Manifest.permission.CAMERA}, REQUEST_CODE_ASK_PERMISSIONS);
    }
  }

  @Override
  public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
    switch (requestCode) {
      case REQUEST_CODE_ASK_PERMISSIONS:
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
          // Permission Granted
          setUpQrCodeView();
          Toast.makeText(QRCodeScanner.this, "Permission Granted", Toast.LENGTH_SHORT)
              .show();
        } else {
          // Permission Denied
          Toast.makeText(QRCodeScanner.this, "Permission Denied", Toast.LENGTH_SHORT)
              .show();
        }
        break;
      default:
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
  }

  @Override protected int getLayoutId() {
    return R.layout.activity_qrcode_scanner;
  }

  @Override protected void onComponentCreated() {
    viewModel = new QRCodeScannerViewModel();
  }

  @Override public void onQRCodeRead(String text, PointF[] points) {
    Timber.d("QRCODEResult %s ",text);
  }

  @Override
  protected void onResume() {
    super.onResume();
    qrCodeReaderView.startCamera();
  }

  @Override
  protected void onPause() {
    super.onPause();
    qrCodeReaderView.stopCamera();
  }

}
