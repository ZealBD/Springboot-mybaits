package com.hesicare.common.jobs;

import com.hesicare.common.utils.Constants;
import com.hesicare.health.dao.BmiDao;
import com.hesicare.health.dao.GlucoseDao;
import com.hesicare.health.dao.PressureDao;
import com.hesicare.health.services.impl.Testccimpl;
import com.hesicare.health.services.testcc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.util.Date;
@Component
public class job {
    @Autowired
    private PressureJob pressureJob;
    @Autowired
    private GlucoseJob glucoseJob;
    @Autowired
    private BmiJob bmiJob;
    @Scheduled(cron = "0 0/1 * * * ?")
    public void doPressure()throws IOException{
       pressureJob.doXueYaIt();
    }
    @Scheduled(cron = "0 0/1 * *  * ?")
    public void doGlucose() throws IOException {
       glucoseJob.doXueTangIt();
    }
  @Scheduled(cron = "0 0/1 * * * ?")
    public void doBmi() throws IOException {
       bmiJob.doBmiIt();
    }

}
