package com.example.vivekbhalodiya.railticket.feature.trainresult;

import com.example.vivekbhalodiya.railticket.api.model.TrainBetween.ClassesItem;
import com.example.vivekbhalodiya.railticket.feature.base.BaseViewModel;
import java.util.List;

/**
 * Created by vivekbhalodiya on 2/15/18.
 */

public class TrainResultViewModel extends BaseViewModel<TrainResultView> {
  private String train_name="";
  private String train_number="";
  private String departure_time="";
  private String origin_name="";
  private String duration="";
  private String arrival_time="";
  private String destination_name="";
  private List<ClassesItem> classesItemList;

  TrainResultViewModel(){
  }

  public TrainResultViewModel(String train_name, String train_number, String departure_time, String origin_name, String duration,
      String arrival_time, String destination_name) {
    this.train_name = train_name;
    this.train_number = train_number;
    this.departure_time = departure_time;
    this.origin_name = origin_name;
    this.duration = duration;
    this.arrival_time = arrival_time;
    this.destination_name = destination_name;
  }

  public String getTrain_name() {
    return train_name;
  }

  public void setTrain_name(String train_name) {
    this.train_name = train_name;
  }

  public String getTrain_number() {
    return train_number;
  }

  public void setTrain_number(String train_number) {
    this.train_number = train_number;
  }

  public String getDeparture_time() {
    return departure_time;
  }

  public void setDeparture_time(String departure_time) {
    this.departure_time = departure_time;
  }

  public String getOrigin_name() {
    return origin_name;
  }

  public void setOrigin_name(String origin_name) {
    this.origin_name = origin_name;
  }

  public String getDuration() {
    return duration;
  }

  public void setDuration(String duration) {
    this.duration = duration;
  }

  public String getArrival_time() {
    return arrival_time;
  }

  public void setArrival_time(String arrival_time) {
    this.arrival_time = arrival_time;
  }

  public String getDestination_name() {
    return destination_name;
  }

  public void setDestination_name(String destination_name) {
    this.destination_name = destination_name;
  }

  public List<ClassesItem> getClassesItemList() {
    return classesItemList;
  }

  public void setClassesItemList(List<ClassesItem> classesItemList) {
    this.classesItemList = classesItemList;
    getView().initiateDialogBox();
  }
}