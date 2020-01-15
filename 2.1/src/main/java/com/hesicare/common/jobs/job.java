package com.hesicare.common.jobs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class job {

    private static  PressureJob pressureJob=new PressureJob();
    private static  GlucoseJob glucoseJob=new GlucoseJob();
/*    private static  BmiJob bmiJob=new BmiJob();*/
  /*  @Scheduled(cron = "0 0/1 * * * ?")
    public void doPressure(){
       pressureJob.doXueYaIt();
    }*/
    @Scheduled(cron = "0 0/1 * * * ?")
    public void doGlucose(){
     //   System.out.println(pressureDao.selectAll());
       /* glucoseJob.doXueTangIt();*/
    }
/*    @Scheduled(cron = "0 0/3 * * * ?")
    public void doBmi(){
        bmiJob.doBmiIt();
    }*/

}
