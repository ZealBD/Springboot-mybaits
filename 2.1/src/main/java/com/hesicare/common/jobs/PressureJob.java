package com.hesicare.common.jobs;

import com.hesicare.common.utils.Constants;
import com.hesicare.common.utils.HospitalEnum;
import com.hesicare.health.dao.BloodPressureDAO;
import com.hesicare.health.entity.BloodPressure;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.PropertyFilter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class PressureJob {
    @Autowired
    private BloodPressureDAO bloodPressureDAO;
    @Autowired
    private JobUtils jobUtils;
    public void doXueYaIt() {
        try {
            List<BloodPressure> bloodPressures = bloodPressureDAO.getPressureByState();
            Map<String, List<BloodPressure>> groupBy = bloodPressures.stream().collect(Collectors.groupingBy(BloodPressure::getIdentifierCode));
            JSONObject jsonObject = JSONObject.fromObject(groupBy);
            Iterator<Map.Entry<String, List<BloodPressure>>> it = jsonObject.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<String, List<BloodPressure>> entry = it.next();
                JSONArray list = (JSONArray) entry.getValue();
                List<BloodPressure> bplist = (List<BloodPressure>) JSONArray.toCollection(list, BloodPressure.class);
                sortClass sort = new sortClass();
                Collections.sort(bplist, sort);
                if (String.valueOf(bplist.get(0).getIdCard()).length()<15){
                    continue;
                }
                if (StringUtils.isBlank(bplist.get(0).getCheckTime())){
                    continue;
                }
                if (StringUtils.isBlank(bplist.get(0).getHand())){
                    continue;
                }
                if (StringUtils.isBlank(bplist.get(0).getSystolicPressure())){
                    continue;
                }if (bplist.get(1).getIsAverage().equals("1")){
                    continue;
                }

                if (StringUtils.isBlank(bplist.get(0).getDiastolicPressure())){
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
                HospitalEnum hospitalEnum=jobUtils.info(bplist.get(0).getDeptid());
                boolean result=false;
                String comcode=hospitalEnum.getComcode();
                String name=hospitalEnum.getName();
                result=requestBP(bplist,comcode,name);
                if (result) {
                    for (BloodPressure bpVo : bplist)
                        bloodPressureDAO.updatePressureById(bpVo.getId(), (short) 1);
                }
                else{
                    for (BloodPressure bpVo : bplist)
                        bloodPressureDAO.updatePressureById(bpVo.getId(), (short) 3);
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
            int flag = bp0.getId().compareTo(bp1.getId());
            return flag;
        }
    }

    public boolean requestBP(List<BloodPressure> bplist,String comcode,String name) throws Exception {
        String url = "http://173.18.2.19:9071/measure/api/chronic/submit";
        boolean requestBP = false;
        Pwdbpdata bpdata = new Pwdbpdata();
        BloodPressure bp = bplist.get(bplist.size()-1);
        IdcardInfoExtractor idcardInfo = new IdcardInfoExtractor(String.valueOf(bp.getIdCard().trim()));
        bpdata.setSfzhm(String.valueOf(bp.getIdCard()).toUpperCase());
        bpdata.setClDate(Constants.convert(Constants.convert(bp.getCheckTime(), Constants.format1),Constants.format1));
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
        if (bp.getCardNumber().length()==9){
            bpdata.setJyklx(String.valueOf("0"));}
        if (bp.getCardNumber().length()==10){
            bpdata.setJyklx(String.valueOf("1"));}
        bpdata.setMeterNo(bp.getDeviceId());
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

        bpdata.setXm(bp.getNAME());

        BloodPressureVo bpVo = new BloodPressureVo();

        if (bp.getHand().equals("left")) {
            bpVo.setClsb(Integer.valueOf(1));
        } else if (bp.getHand().equals("right")) {
            bpVo.setClsb(Integer.valueOf(2));
        } else {
            bpVo.setClsb(Integer.valueOf(3));
        }
        if (bplist.size() == 2) {
            bpVo.setBgzmbjcTimes1(Integer.valueOf(0));
            bpVo.setDbp1(Integer.valueOf(bp.getDiastolicPressure()));
            bpVo.setSbp1(Integer.valueOf(bp.getSystolicPressure()));
            bpVo.setMb1(Integer.valueOf(bp.getPluse()));
            bpVo.setStydjcTimes1(Integer.valueOf(0));
            bpVo.setFinishTime1(Constants.convert(Constants.convert(bp.getCheckTime(), Constants.format2), Constants.format2));
            bpVo.setBgzmbjcTimes2(Integer.valueOf(0));
            bpVo.setDbp2(Integer.valueOf(bp.getDiastolicPressure()));
            bpVo.setSbp2(Integer.valueOf(bp.getSystolicPressure()));
            bpVo.setMb2(Integer.valueOf(bp.getPluse()));
            bpVo.setStydjcTimes2(Integer.valueOf(0));
            bpVo.setFinishTime2(Constants.convert(Constants.convert(bp.getCheckTime(), Constants.format2), Constants.format2));
            BloodPressure bp2 = bplist.get(0);
            bpVo.setSbpAve(bp2.getSystolicPressure());
            bpVo.setDbpAve(bp2.getDiastolicPressure());
            bpVo.setMbAve(bp2.getPluse());

        } else if (bplist.size() == 3) {
            BloodPressure bp1 = bplist.get(1);
            bpVo.setBgzmbjcTimes1(Integer.valueOf(2));
            bpVo.setDbp1(Integer.valueOf(bp1.getDiastolicPressure()));
            bpVo.setSbp1(Integer.valueOf(bp1.getSystolicPressure()));
            bpVo.setMb1(Integer.valueOf(bp1.getPluse()));
            bpVo.setStydjcTimes1(Integer.valueOf(0));
            bpVo.setFinishTime1(Constants.convert(Constants.convert(bp1.getCheckTime(), Constants.format2), Constants.format2));
            BloodPressure bp2 = bplist.get(2);
            bpVo.setBgzmbjcTimes2(Integer.valueOf(0));
            bpVo.setDbp2(Integer.valueOf(bp2.getDiastolicPressure()));
            bpVo.setSbp2(Integer.valueOf(bp2.getSystolicPressure()));
            bpVo.setMb2(Integer.valueOf(bp2.getPluse()));
            bpVo.setStydjcTimes2(Integer.valueOf(0));
            bpVo.setFinishTime2(Constants.convert(Constants.convert(bp2.getCheckTime(), Constants.format2), Constants.format2));
            BloodPressure bp3 = bplist.get(0);
            bpVo.setSbpAve(bp3.getSystolicPressure());
            bpVo.setDbpAve(bp3.getDiastolicPressure());
            bpVo.setMbAve(bp3.getPluse());
        }
        else if (bplist.size() >= 4) {
            BloodPressure bp1 = bplist.get(1);
            bpVo.setBgzmbjcTimes1(Integer.valueOf(0));
            bpVo.setDbp1(Integer.valueOf(bp1.getDiastolicPressure()));
            bpVo.setSbp1(Integer.valueOf(bp1.getSystolicPressure()));
            bpVo.setMb1(Integer.valueOf(bp1.getPluse()));
            bpVo.setStydjcTimes1(Integer.valueOf(0));
            bpVo.setFinishTime1(Constants.convert(Constants.convert(bp1.getCheckTime(), Constants.format2), Constants.format2));
            BloodPressure bp2 = bplist.get(2);
            bpVo.setBgzmbjcTimes2(Integer.valueOf(0));
            bpVo.setDbp2(Integer.valueOf(bp2.getDiastolicPressure()));
            bpVo.setSbp2(Integer.valueOf(bp2.getSystolicPressure()));
            bpVo.setMb2(Integer.valueOf(bp2.getPluse()));
            bpVo.setStydjcTimes2(Integer.valueOf(0));
            bpVo.setFinishTime2(Constants.convert(Constants.convert(bp2.getCheckTime(), Constants.format2), Constants.format2));
            BloodPressure bp3 = bplist.get(3);
            bpVo.setBgzmbjcTimes3(Integer.valueOf(0));
            bpVo.setDbp3(Integer.valueOf(bp3.getDiastolicPressure()));
            bpVo.setSbp3(Integer.valueOf(bp3.getSystolicPressure()));
            bpVo.setMb3(Integer.valueOf(bp3.getPluse()));
            bpVo.setStydjcTimes3(Integer.valueOf(0));
            bpVo.setFinishTime3(Constants.convert(Constants.convert(bp3.getCheckTime(), Constants.format2), Constants.format2));
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
        jobUtils.printwrites("D:/Hesicare/java项目/logs/xy/"+bplist.get(0).getDeptid()+"/"+new SimpleDateFormat("yyyy-MM-dd").format(new Date()) +".txt",name+"-请求数据：" + json.toString());
        String result = jobUtils.sendHttpPost(url, json.toString());
        JSONObject resultJson = JSONObject.fromObject(result);
        jobUtils.printwrites("D:/Hesicare/java项目/logs/xy/"+bplist.get(0).getDeptid()+"/"+new SimpleDateFormat("yyyy-MM-dd").format(new Date()) +".txt",name+"-"+"返回结果：" + result);
        String code=resultJson.getString("errno");
        if(code.equals("0")){
            requestBP = true;
        }
        return requestBP;

    }

}
