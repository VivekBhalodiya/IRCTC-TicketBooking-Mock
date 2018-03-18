package com.example.vivekbhalodiya.railticket.util;

import android.graphics.Bitmap;
import android.util.Log;
import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;
import com.google.zxing.WriterException;

import static com.example.vivekbhalodiya.railticket.constants.AppConstant.TAG;

/**
 * Created by vivekbhalodiya on 3/14/18.
 */

public class QRCodeManager {

  private Bitmap bitmap;

  public Bitmap generateQRCode(String encodedValue) {
    // Initializing the QR Encoder with your value to be encoded, type you required and Dimension
    QRGEncoder qrgEncoder = new QRGEncoder(encodedValue, null, QRGContents.Type.TEXT, 240);
    try {
      // Getting QR-Code as Bitmap
      bitmap = qrgEncoder.encodeAsBitmap();
    } catch (WriterException e) {
      Log.v(TAG, e.toString());
    }
    return bitmap;
  }
}
