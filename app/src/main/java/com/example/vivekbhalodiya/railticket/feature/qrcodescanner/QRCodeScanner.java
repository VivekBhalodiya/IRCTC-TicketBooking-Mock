package com.example.vivekbhalodiya.railticket.feature.qrcodescanner;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.PointF;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.SurfaceView;
import android.widget.Toast;
import com.dlazaro66.qrcodereaderview.QRCodeReaderView;
import com.example.vivekbhalodiya.railticket.R;
import com.example.vivekbhalodiya.railticket.databinding.ActivityQrcodeScannerBinding;
import com.example.vivekbhalodiya.railticket.feature.base.BaseActivity;
import github.nisrulz.qreader.QRDataListener;
import github.nisrulz.qreader.QREader;
import timber.log.Timber;

public class QRCodeScanner extends BaseActivity<ActivityQrcodeScannerBinding,QRCodeScannerViewModel,QRCodeScannerView>
    implements QRCodeReaderView.OnQRCodeReadListener {
  private SurfaceView mySurfaceView;
  private QREader qrEader;
  final private int REQUEST_CODE_ASK_PERMISSIONS = 123;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    
    requestPermission();
    mySurfaceView = binding.cameraView;
    qrEader = new QREader.Builder(this, mySurfaceView, new QRDataListener() {
      @Override public void onDetected(String data) {
        Log.d("QREader", "Value : " + data);
      }
    }).enableAutofocus(true)
        .build();
  }

  private void setUpQrCodeView() {

  }

  private void requestPermission(){
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
    qrEader.initAndStart(mySurfaceView);
  }

  @Override
  protected void onPause() {
    super.onPause();
    qrEader.releaseAndCleanup();
  }

}
