package com.hesicare.health.entity;

import com.baomidou.mybatisplus.annotation.TableName;

import java.util.Date;


@TableName("patient_bmi_view")
public class PatientBmiView {

  private long id;
  private String idcard;
  private String hight;
  private String weight;
  private String mearsureTime;
  private String status;
  private long deptid;


  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }


  public String getIdcard() {
    return idcard;
  }

  public void setIdcard(String idcard) {
    this.idcard = idcard;
  }


  public String getHight() {
    return hight;
  }

  public void setHight(String hight) {
    this.hight = hight;
  }


  public String getWeight() {
    return weight;
  }

  public void setWeight(String weight) {
    this.weight = weight;
  }


  public String getMearsureTime() {
    return mearsureTime;
  }

  public void setMearsureTime(String mearsureTime) {
    this.mearsureTime = mearsureTime;
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
