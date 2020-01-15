package com.hesicare.health.entity;


import lombok.Data;

import javax.persistence.*;

@Data
@Table(name = "patient_bmi_view")
public class PatientBmiView {
 /* @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)*/
  private long id;
 // @Column(name = "idcard")
  private String idcard;
 // @Column(name = "hight")
  private String hight;
//  @Column(name = "weight")
  private String weight;
  //@Column(name = "mearsure_time")
  private java.sql.Timestamp mearsureTime;
 // @Column(name = "status")
  private String status;
 // @Column(name = "deptid")
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


  public java.sql.Timestamp getMearsureTime() {
    return mearsureTime;
  }

  public void setMearsureTime(java.sql.Timestamp mearsureTime) {
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
