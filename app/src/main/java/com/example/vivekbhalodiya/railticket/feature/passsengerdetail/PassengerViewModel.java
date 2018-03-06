package com.example.vivekbhalodiya.railticket.feature.passsengerdetail;

import com.example.vivekbhalodiya.railticket.feature.base.BaseViewModel;

/**
 * Created by vivekbhalodiya on 3/3/18.
 */

public class PassengerViewModel extends BaseViewModel<PassengerView> {
  String SUCCESS = "success";

  private String passengerName = "";
  private String passengerAge = "";
  private String passengerAadharCar = "";
  private String passengerEmail = "";
  private String passengerMobile = "";
  private String passengerJourneyDate = "";
  private String availStatus = "";
  private String pref = "";
  private String trainNum = "";
  private String trainName = "";
  private String toStationName = "";
  private String toStationCode = "";
  private String fromStationName = "";
  private String fromSatationCode = "";
  private String srcDeptTime = "";
  private String destArrivalTime = "";
  private String travelTime = "";

  /*
  private void setTrainData() {
    trainNum = trainsItem.getNumber();
    toStationName = trainsItem.getToStation().getName();
    toStationCode = trainsItem.getToStation().getCode();
    fromStationName = trainsItem.getFromStation().getName();
    fromSatationCode = trainsItem.getFromStation().getCode();
    trainName = trainsItem.getName();
    srcDeptTime = trainsItem.getSrcDepartureTime();
    destArrivalTime = trainsItem.getDestArrivalTime();
    travelTime = trainsItem.getTravelTime();
  }
  */

  PassengerViewModel(){

  }

  void setPassengerName(String passengerName) {
    this.passengerName = passengerName;
  }

  void setPassengerAge(String passengerAge) {
    this.passengerAge = passengerAge;
  }

  void setPassengerAadharCar(String passengerAadharCar) {
    this.passengerAadharCar = passengerAadharCar;
  }

  void setPassengerEmail(String passengerEmail) {
    this.passengerEmail = passengerEmail;
  }

  void setPassengerMobile(String passengerMobile) {
    this.passengerMobile = passengerMobile;
  }

  void setPassengerJourneyDate(String passengerJourneyDate) {
    this.passengerJourneyDate = passengerJourneyDate;
  }

  void setAvailStatus(String availStatus) {
    this.availStatus = availStatus;
  }

  void setPref(String pref) {
    this.pref = pref;
  }

  void setTrainNum(String trainNum) {
    this.trainNum = trainNum;
  }

  void setTrainName(String trainName) {
    this.trainName = trainName;
  }

  void setToStationName(String toStationName) {
    this.toStationName = toStationName;
  }

  void setToStationCode(String toStationCode) {
    this.toStationCode = toStationCode;
  }

  void setFromStationName(String fromStationName) {
    this.fromStationName = fromStationName;
  }

  void setFromSatationCode(String fromSatationCode) {
    this.fromSatationCode = fromSatationCode;
  }

  void setSrcDeptTime(String srcDeptTime) {
    this.srcDeptTime = srcDeptTime;
  }

  void setDestArrivalTime(String destArrivalTime) {
    this.destArrivalTime = destArrivalTime;
  }

  void setTravelTime(String travelTime) {
    this.travelTime = travelTime;
  }

  String checkErrors() {
    return SUCCESS;
  }

  public String getPassengerName() {
    return passengerName;
  }

  public String getPassengerAge() {
    return passengerAge;
  }

  public String getPassengerAadharCar() {
    return passengerAadharCar;
  }

  public String getPassengerEmail() {
    return passengerEmail;
  }

  public String getPassengerMobile() {
    return passengerMobile;
  }

  public String getPassengerJourneyDate() {
    return passengerJourneyDate;
  }

  public String getAvailStatus() {
    return availStatus;
  }

  public String getPref() {
    return pref;
  }

  public String getTrainNum() {
    return trainNum;
  }

  public String getTrainName() {
    return trainName;
  }

  public String getToStationName() {
    return toStationName;
  }

  public String getToStationCode() {
    return toStationCode;
  }

  public String getFromStationName() {
    return fromStationName;
  }

  public String getFromSatationCode() {
    return fromSatationCode;
  }

  public String getSrcDeptTime() {
    return srcDeptTime;
  }

  public String getDestArrivalTime() {
    return destArrivalTime;
  }

  public String getTravelTime() {
    return travelTime;
  }
}
