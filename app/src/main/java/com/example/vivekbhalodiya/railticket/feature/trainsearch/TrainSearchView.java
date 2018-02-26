package com.example.vivekbhalodiya.railticket.feature.trainsearch;

import com.example.vivekbhalodiya.railticket.api.model.TrainBetween.TrainsItem;
import com.example.vivekbhalodiya.railticket.api.model.TrainFare.ClassesItem;
import com.example.vivekbhalodiya.railticket.feature.base.MvvmView;
import java.util.ArrayList;

/**
 * Created by vivekbhalodiya on 2/15/18.
 */

public interface TrainSearchView extends MvvmView {
  void selectDate();

  void showProgress(boolean show);

  void triggerResultActivity(ArrayList<TrainsItem> listOfTrains,
      ArrayList<ArrayList<ClassesItem>> listOfClasses);
}
