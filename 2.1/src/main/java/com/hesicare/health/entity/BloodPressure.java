package com.hesicare.health.entity;

import com.baomidou.mybatisplus.annotation.TableName;

@TableName("patient_blood_pressure_view")
public class BloodPressure {

  private long id;
  private long userid;
  private String name;
  private String birth;
  private String sex;
  private String measureTime;
  private String identifyCard;
  private String cardNumber;
  private Double systolic;
  private Double diastolic;
  private Double pluse;
  private Double isAverage;
  private String evaluation;
  private String hand;
  private String devicesn;
  private String parentId;
  private String status;
  private long deptid;


  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }


  public long getUserid() {
    return userid;
  }

  public void setUserid(long userid) {
    this.userid = userid;
  }


  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }


  public String getBirth() {
    return birth;
  }

  public void setBirth(String birth) {
    this.birth = birth;
  }


  public String getSex() {
    return sex;
  }

  public void setSex(String sex) {
    this.sex = sex;
  }


  public String getMeasureTime() {
    return measureTime;
  }

  public void setMeasureTime(String measureTime) {
    this.measureTime = measureTime;
  }


  public String getIdentifyCard() {
    return identifyCard;
  }

  public void setIdentifyCard(String identifyCard) {
    this.identifyCard = identifyCard;
  }


  public String getCardNumber() {
    return cardNumber;
  }

  public void setCardNumber(String cardNumber) {
    this.cardNumber = cardNumber;
  }


  public Double getSystolicPressure() {
    return systolic;
  }

  public void setSystolicPressure(Double systolicPressure) {
    this.systolic = systolicPressure;
  }


  public Double getDiastolicPressure() {
    return diastolic;
  }

  public void setDiastolicPressure(Double diastolicPressure) {
    this.diastolic = diastolicPressure;
  }


  public Double getPluse() {
    return pluse;
  }

  public void setPluse(Double pluse) {
    this.pluse = pluse;
  }


  public Double getIsAverage() {
    return isAverage;
  }

  public void setIsAverage(Double isAverage) {
    this.isAverage = isAverage;
  }


  public String getEvaluation() {
    return evaluation;
  }

  public void setEvaluation(String evaluation) {
    this.evaluation = evaluation;
  }


  public String getHand() {
    return hand;
  }

  public void setHand(String hand) {
    this.hand = hand;
  }


  public String getDevicesn() {
    return devicesn;
  }

  public void setDevicesn(String devicesn) {
    this.devicesn = devicesn;
  }


  public String getParentId() {
    return parentId;
  }

  public void setParentId(String parentId) {
    this.parentId = parentId;
  }


  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }


  public long getDeptid() {
    return deptid;
  }

  public void setDeptid(long deptid) {
    this.deptid = deptid;
  }

}
