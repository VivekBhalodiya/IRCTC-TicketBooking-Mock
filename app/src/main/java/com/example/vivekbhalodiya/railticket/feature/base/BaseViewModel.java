package com.example.vivekbhalodiya.railticket.feature.base;

import android.databinding.BaseObservable;
import android.util.Log;
import java.util.Observable;

/**
 * Created by vivekbhalodiya on 2/5/18.
 */

public class BaseViewModel<T extends MvvmView> extends BaseObservable implements MvvmViewModel<T> {

  private T view;

  @Override public void attach(T view) {
    this.view=view;
  }

  @Override public T getView() {
    return view;
  }
}
