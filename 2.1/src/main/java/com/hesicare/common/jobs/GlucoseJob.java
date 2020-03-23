package com.hesicare.common.jobs;

import com.hesicare.common.utils.*;
import com.hesicare.common.utils.wonders.InterfaceEnCode;
import com.hesicare.health.dao.GlucoseDao;
import com.hesicare.health.dao.PressureDao;
import com.hesicare.health.entity.BloodGlucose;
import com.hesicare.health.entity.BloodGlucoseVo;
import com.hesicare.health.entity.BloodPressure;
/*import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.PropertyFilter;*/
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.PropertyFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
@Service
public class GlucoseJob {
    @Autowired
    private GlucoseDao glucoseDao;
    @Autowired
    private JobUtils jobUtils;
    static  String path_begin="D:/Hesicare/java项目/logs/bg/";
    public void doXueTangIt() throws IOException {
        jobUtils.printlog(path_begin+Constants.convert(new Date(),Constants.format1)+".txt",Constants.convert(new Date(),Constants.format2)+" :血糖定时器启动");
        try {
            List<BloodGlucose> bloodGlucoses1 =glucoseDao.selectList(null);
            System.out.println(bloodGlucoses1.size());
            for (BloodGlucose bloodGlucose : bloodGlucoses1) {
                HospitalEnum hospitalEnum=jobUtils.info(String.valueOf(bloodGlucose.getDeptid()));
                String comcode=hospitalEnum.getComcode();
                String name=hospitalEnum.getName();
                boolean result = requestXT(bloodGlucose,comcode,name);
                if (result) {
                    glucoseDao.updatebyid(bloodGlucose.getDeptid(),"1");
                }
                else{
                    glucoseDao.updatebyid(bloodGlucose.getId(),"3");
                }
                Thread.sleep(1000);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public boolean requestXT(BloodGlucose bloodGlucose, String comcode, String name) throws Exception {
        boolean requestXT = false;
        String url = "http://173.18.2.19:9071/measure/api/patSignSubmit";
        Patdata patdata = new Patdata();
        IdcardInfoExtractor idcardInfo = new IdcardInfoExtractor(String.valueOf(bloodGlucose.getIdentifyCard()));
        patdata.setPersoncard(bloodGlucose.getIdentifyCard());
        patdata.setMeasureTime(String.valueOf(Constants.convert(Constants.convert(bloodGlucose.getMeasureTime(), Constants.format2), Constants.format2)));
        patdata.setMeasureSourceId(String.valueOf("0002"));
        patdata.setMeasureLocation(String.valueOf(3));
        patdata.setMeasureOrgId(comcode);
        patdata.setMeasureMode(String.valueOf(1));
        patdata.setDeviceId(bloodGlucose.getDevicesn());
        patdata.setDeviceType(String.valueOf("Accu-Chek"));

        BloodGlucoseVo bloodGlucoseVo=new BloodGlucoseVo();

        String bgStatus = bloodGlucose.getStatus();
        String status = "";
        switch (bgStatus) {
            case "KF":
                status = "1";
            case "ZCH":
                status = "2";
            case "WUCQ":
                status = "1";
            case "WUCH":
                status = "2";
            case "WACQ":
                status = "1";
            case "WACH":
                status = "2";
            case "SQ":
                status = "3";
            case "LC":
                status = "3";
            case "LC3":
                status = "3";
            case "SJ":
                status = "3";
                break;
            default:
                break;
        }

        Double value = bloodGlucose.getMeatureValue();
        String resultBg = "";

        if (value < 3.3) {
            resultBg ="4";
        }else if (!value.equals(991) && !value.equals(990) && value > 11.0){
            resultBg ="3";
        }else if (value.equals(991) || value.equals(990)){
            resultBg ="2";
        }else
            resultBg = "1";
        bloodGlucoseVo.setMeasureType("2001");
        bloodGlucoseVo.setMeasureData(bloodGlucose.getMeatureValue()+"|"+"mmol/L" +"|"+"0.6-33.3"+"|"+resultBg+"|"+status+"|"+ "1");
        List<BloodGlucoseVo> datalist = new ArrayList<BloodGlucoseVo>();
        datalist.add(bloodGlucoseVo);
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
        jobUtils.printlog(path_begin+Constants.convert(new Date(),Constants.format1)+".txt",json.toString());
        String header = InterfaceEnCode.getInterfaceKey();
        map.put("param", InterfaceEnCode.encrypt(json.toString()));
        HttpClientUtil util = new HttpClientUtil();
        String result = util.doPost(header, url, map);
        jobUtils.printlog("D:/Hesicare/java项目/logs/xt/"+bloodGlucose.getDeptid()+"/"+new SimpleDateFormat("yyyy-MM-dd").format(new Date()) +".txt",name+"-"+"返回结果：" + result);
        JSONObject resultJson = JSONObject.fromObject(result);
        String code = resultJson.getString("code");
        if (code.equals("1")) {
            requestXT = true;
        }
        else{
            jobUtils.printlog(path_begin+Constants.convert(new Date(),Constants.format1)+".txt", String.valueOf(resultJson));

        }
        requestXT=true;
        return requestXT;
    }
    }
