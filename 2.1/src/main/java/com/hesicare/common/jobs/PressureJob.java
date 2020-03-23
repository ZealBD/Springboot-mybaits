package com.hesicare.common.jobs;

import com.hesicare.common.utils.Constants;
import com.hesicare.common.utils.HospitalEnum;
import com.hesicare.common.utils.IdcardInfoExtractor;
import com.hesicare.common.utils.Pwdbpdata;
import com.hesicare.health.dao.PressureDao;
import com.hesicare.health.entity.BloodPressure;
import com.hesicare.health.entity.BloodPressureVo;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.PropertyFilter;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;
@Service
public class PressureJob {
    @Autowired
    private PressureDao bloodPressureDAO;
    private static  String path_begin="D:/Hesicare/java项目/logs/bp/";
    static  JobUtils jobUtils=new JobUtils();
    public void doXueYaIt() throws IOException {
        jobUtils.printlog(path_begin+Constants.convert(new Date(),Constants.format1)+".txt",Constants.convert(new Date(),Constants.format2)+" :血压定时器启动");
        try {
            List<BloodPressure> bloodPressures = bloodPressureDAO.selectList(null);
            System.out.println(bloodPressures.size());
            Map<String, List<BloodPressure>> groupBy=bloodPressures.stream().collect(Collectors.groupingBy(BloodPressure::getParentId));
            JSONObject jsonObject = JSONObject.fromObject(groupBy);
            Iterator<Map.Entry<String, List<BloodPressure>>> it = jsonObject.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<String, List<BloodPressure>> entry = it.next();
                JSONArray list = (JSONArray) entry.getValue();
                List<BloodPressure> bplist = (List<BloodPressure>) JSONArray.toCollection(list, BloodPressure.class);
                sortClass sort = new sortClass();
                Collections.sort(bplist, sort);
                if (String.valueOf(bplist.get(0).getIdentifyCard()).length()<15){
                    continue;
                }
                if (StringUtils.isBlank(bplist.get(0).getMeasureTime())){
                    continue;
                }
                if (StringUtils.isBlank(bplist.get(0).getHand())){
                    continue;
                }
                if (StringUtils.isBlank(String.valueOf(bplist.get(0).getSystolicPressure()))){
                    continue;
                }
                if (StringUtils.isBlank(String.valueOf(bplist.get(0).getDiastolicPressure()))){
                    continue;
                }
                if (bplist.size()<=2) {
                    continue;
                }
                if (String.valueOf(bplist.get(0).getIsAverage()).equals("0")) {
                    continue;
                }
                if (String.valueOf(bplist.get(bplist.size()-1).getIsAverage()).equals("1")) {
                    continue;
                }
                HospitalEnum hospitalEnum=jobUtils.info(String.valueOf(bplist.get(0).getDeptid()));
                boolean result=false;
                String comcode=hospitalEnum.getComcode();
                String name=hospitalEnum.getName();
                result=requestBP(bplist,comcode,name);
                /*数据状态：1 发送成功， 2 在1 的基础上第二次发送成功   3   第一次发送失败  */
                if (result) {
                    for (BloodPressure bpVo : bplist){
                        bloodPressureDAO.updatebyid(bpVo.getId(), (short) 1);
                    }
                }
                else{
                    for (BloodPressure bpVo : bplist){
                        bloodPressureDAO.updatebyid(bpVo.getId(), (short) 3);
                    }
                }
                Thread.sleep(1000);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public class sortClass implements Comparator {
        public int compare(Object arg0, Object arg1) {
            BloodPressure bp0 = (BloodPressure) arg0;
            BloodPressure bp1 = (BloodPressure) arg1;
            int flag = String.valueOf(bp0.getId()).compareTo(String.valueOf(bp1.getId()));
            return flag;
        }
    }

    public boolean requestBP(List<BloodPressure> bplist,String comcode,String name) throws Exception {
        String url = "http://173.18.2.19:9071/measure/api/chronic/submit";
        boolean requestBP = false;
        Pwdbpdata bpdata = new Pwdbpdata();
        BloodPressure bp = bplist.get(bplist.size()-1);
        IdcardInfoExtractor idcardInfo = new IdcardInfoExtractor(String.valueOf(bp.getIdentifyCard().trim()));
        bpdata.setSfzhm(String.valueOf(bp.getIdentifyCard()).toUpperCase());
        bpdata.setClDate(Constants.convert(Constants.convert(bp.getMeasureTime(), Constants.format1),Constants.format1));
        bpdata.setClff(String.valueOf(1));
        bpdata.setClysNm(String.valueOf("XXX"));
        bpdata.setClysNo(String.valueOf(000));

        if (org.apache.commons.lang.StringUtils.isNotBlank(String.valueOf(bp.getBirth()))){
            bpdata.setCsDate(bp.getBirth());
        }else if (org.apache.commons.lang.StringUtils.isNotBlank(String.valueOf(idcardInfo)) && idcardInfo.getBirthday() != null)
        {   bpdata.setCsDate(Constants.convert(idcardInfo.getBirthday(), Constants.format1));}
        else
        {
            bpdata.setCsDate("");
        }
        bpdata.setJykh(bp.getCardNumber());
        /*卡号长度为9:芯片卡(社保卡)
        * 卡号长度为10:磁条卡
        * 0.社保卡 1.医保卡*/
        if (bp.getCardNumber().length()==9){
            bpdata.setJyklx(String.valueOf("0"));}
        else{
            bpdata.setJyklx(String.valueOf("1"));}
        bpdata.setMeterNo(bp.getDevicesn());
        bpdata.setMeterType(String.valueOf("AND:TM-2656VP"));

        bpdata.setOrg(comcode);
        bpdata.setSourceId(String.valueOf("02"));

        bpdata.setStationId(String.valueOf("000"));


        if (org.apache.commons.lang.StringUtils.isBlank(String.valueOf(idcardInfo.getGender()))) {
            bpdata.setXb("");
        } else if ((org.apache.commons.lang.StringUtils.isNotBlank(String.valueOf(idcardInfo.getGender()))&& String.valueOf(idcardInfo.getGender()).equals("0"))) {
            bpdata.setXb(String.valueOf("2"));
        } else {
            bpdata.setXb(idcardInfo.getGender());
        }

        bpdata.setXm(bp.getName());

        BloodPressureVo bpVo = new BloodPressureVo();

        if (bp.getHand().equals("left")) {
            bpVo.setClsb("1");
        } else if (bp.getHand().equals("right")) {
            bpVo.setClsb("2");
        } else {
            bpVo.setClsb("3");
        }
      if (bplist.size() == 3) {
            BloodPressure bp1 = bplist.get(1);
            bpVo.setDbp1(bp1.getDiastolicPressure());
            bpVo.setSbp1(bp1.getSystolicPressure());
            bpVo.setMb1(bp1.getPluse());
            bpVo.setStydjcTimes1("0");
            bpVo.setBgzmbjcTimes1("0");
            bpVo.setFinishTime1(Constants.convert(Constants.convert(bp1.getMeasureTime(), Constants.format2), Constants.format2));
            BloodPressure bp2 = bplist.get(2);
            bpVo.setDbp2(bp2.getDiastolicPressure());
            bpVo.setSbp2(bp2.getSystolicPressure());
            bpVo.setMb2(bp2.getPluse());
            bpVo.setStydjcTimes2("0");
            bpVo.setBgzmbjcTimes2("0");
            bpVo.setFinishTime2(Constants.convert(Constants.convert(bp2.getMeasureTime(), Constants.format2), Constants.format2));
            BloodPressure bp3 = bplist.get(0);
            bpVo.setSbpAve(bp3.getSystolicPressure());
            bpVo.setDbpAve(bp3.getDiastolicPressure());
            bpVo.setMbAve(bp3.getPluse());
        }
        else if (bplist.size() >= 4) {
            BloodPressure bp1 = bplist.get(1);
            bpVo.setDbp1(bp1.getDiastolicPressure());
            bpVo.setSbp1(bp1.getSystolicPressure());
            bpVo.setMb1(bp1.getPluse());
            bpVo.setStydjcTimes1("0");
            bpVo.setBgzmbjcTimes1("0");
            bpVo.setFinishTime1(Constants.convert(Constants.convert(bp1.getMeasureTime(), Constants.format2), Constants.format2));
            BloodPressure bp2 = bplist.get(2);
            bpVo.setStydjcTimes2("0");
            bpVo.setBgzmbjcTimes2("0");
            bpVo.setDbp2(bp2.getDiastolicPressure());
            bpVo.setSbp2(bp2.getSystolicPressure());
            bpVo.setMb2(bp2.getPluse());
            bpVo.setFinishTime2(Constants.convert(Constants.convert(bp2.getMeasureTime(), Constants.format2), Constants.format2));
            BloodPressure bp3 = bplist.get(3);
            bpVo.setDbp3(bp3.getDiastolicPressure());
            bpVo.setSbp3(bp3.getSystolicPressure());
            bpVo.setMb3(bp3.getPluse());
            bpVo.setStydjcTimes3("0");
            bpVo.setBgzmbjcTimes3("0");
            bpVo.setFinishTime3(Constants.convert(Constants.convert(bp3.getMeasureTime(), Constants.format2), Constants.format2));
            BloodPressure bp4 = bplist.get(0);
            bpVo.setSbpAve(bp4.getSystolicPressure());
            bpVo.setDbpAve(bp4.getDiastolicPressure());
            bpVo.setMbAve(bp4.getPluse());
        }
        List<BloodPressureVo> datalist = new ArrayList<BloodPressureVo>();
        datalist.add(bpVo);
        bpdata.setCljgList(datalist);
        JsonConfig jsonConfig = new JsonConfig();
        PropertyFilter filter = new PropertyFilter() {
            public boolean apply(Object object, String fieldName, Object fieldValue) {
                return null == fieldValue;
            }
        };
        jsonConfig.setJsonPropertyFilter(filter);
        JSONObject json = JSONObject.fromObject(bpdata, jsonConfig);
        jobUtils.printlog(path_begin+Constants.convert(new Date(),Constants.format1)+".txt",json.toString());
        String result = jobUtils.sendHttpPost(url, json.toString());
        JSONObject resultJson = JSONObject.fromObject(result);
        String code=resultJson.getString("errno");
        if(code.equals("0")){
            requestBP = true;
        }
        else{
            jobUtils.printlog(path_begin+Constants.convert(new Date(),Constants.format1)+".txt", String.valueOf(resultJson));
        }
        return requestBP;

    }
}
