package com.hesicare.common.jobs;

import com.hesicare.common.utils.Constants;
import com.hesicare.common.utils.HospitalEnum;
import com.hesicare.common.utils.HttpClientUtil;
import com.hesicare.common.utils.Patdata;
import com.hesicare.common.utils.wonders.InterfaceEnCode;
import com.hesicare.health.dao.BloodGlucoseDAO;
import com.hesicare.health.dao.PatientBmiViewDao;
import com.hesicare.health.entity.PatientBmiView;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.PropertyFilter;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.SimpleDateFormat;
import java.util.*;

public class BmiJob {
    @Autowired
    private PatientBmiViewDao patientBmiViewDao;
    @Autowired
    private JobUtils jobUtils;
    public void doBmiIt() {
      /*  try{
            List<PatientBmiView> bmiview =patientBmiViewDao.getBmiByState("0");
            for (PatientBmiView patientBmiView : bmiview) {
                jobUtils.printwrites("D:/Hesicare/java项目/logs/sg/"+patientBmiView.getDeptid()+"/"+new SimpleDateFormat("yyyy-MM-dd").format(new Date()) +".txt","job 身高定时器：" + Constants.convert(new Date(), Constants.format2));
                HospitalEnum hospitalEnum=jobUtils.info(patientBmiView.getDeptid());
                String comcode=hospitalEnum.getComcode();
                String name=hospitalEnum.getName();
                boolean result = requestSG(patientBmiView,comcode,name);
                if (result) {
                    patientBmiViewDao.updateBmiState(patientBmiView.getId(),(short) 1);
                }
                else{
                    patientBmiViewDao.updateBmiState(patientBmiView.getId(),(short) 3);
                }
                Thread.sleep(1000);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }
    public boolean requestSG(PatientBmiView patientBmiView, String comcode, String name) throws Exception {
        String url = "http://173.18.2.19:9071/measure/api/patSignSubmit";
        boolean requestXT = false;
        Patdata patdata = new Patdata();
        IdcardInfoExtractor idcardInfo = new IdcardInfoExtractor(String.valueOf(patientBmiView.getIdcard()));
        patdata.setPersoncard(patientBmiView.getIdcard());
        patdata.setMeasureTime(Constants.convert(Constants.convert(patientBmiView.getMearsuretime(), Constants.format2), Constants.format2));
        patdata.setMeasureSourceId(String.valueOf("0002"));
        patdata.setMeasureLocation(String.valueOf(3));
        patdata.setMeasureOrgId(comcode);
        patdata.setMeasureMode(String.valueOf(1));
        patdata.setDeviceId("2019082419072633005003");
        patdata.setDeviceType(String.valueOf("HS-3001"));
        BloodGlucoseVo bloodGlucoseVo=new BloodGlucoseVo();
        BloodGlucoseVo bloodGlucoseVo2=new BloodGlucoseVo();
        String resultBg = "";
        bloodGlucoseVo.setMeasureType("1003");
        bloodGlucoseVo.setMeasureData(patientBmiView.getHeight());
        bloodGlucoseVo2.setMeasureType("1004");
        bloodGlucoseVo2.setMeasureData(patientBmiView.getWeight());
        List<BloodGlucoseVo> datalist = new ArrayList<BloodGlucoseVo>();
        datalist.add(bloodGlucoseVo);
        datalist.add(bloodGlucoseVo2);
        patdata.setDataList(datalist);
        Map<String, String> map = new HashMap<String, String>();
        JsonConfig jsonConfig = new JsonConfig();
        PropertyFilter filter = new PropertyFilter() {
            public boolean apply(Object object, String fieldName, Object fieldValue) {
                return null == fieldValue;
            }
        };
        jsonConfig.setJsonPropertyFilter(filter);
        JSONObject json = JSONObject.fromObject(patdata, jsonConfig);
        jobUtils.printwrites("D:/Hesicare/java项目/logs/sg/"+patientBmiView.getDeptid()+"/"+new SimpleDateFormat("yyyy-MM-dd").format(new Date()) +".txt",name+"-请求数据：" + json.toString());
        String header = InterfaceEnCode.getInterfaceKey();
        map.put("param", InterfaceEnCode.encrypt(json.toString()));
        HttpClientUtil util = new HttpClientUtil();
        String result = util.doPost(header, url, map);
        JSONObject resultJson = JSONObject.fromObject(result);
        String code = resultJson.getString("code");
        if (code.equals("1")) {
            requestXT = true;
        }
        jobUtils.printwrites("D:/Hesicare/java项目/logs/sg/"+patientBmiView.getDeptid()+"/"+new SimpleDateFormat("yyyy-MM-dd").format(new Date()) +".txt",name+"-"+result);
        return requestXT;
    }
}*/
    }
}