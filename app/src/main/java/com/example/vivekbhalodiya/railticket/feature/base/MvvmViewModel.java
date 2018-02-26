package com.example.vivekbhalodiya.railticket.feature.base;

/**
 * Created by vivekbhalodiya on 2/7/18.
 */

public interface MvvmViewModel<T extends MvvmView> {

  void attach(T view);
  T getView();
}
