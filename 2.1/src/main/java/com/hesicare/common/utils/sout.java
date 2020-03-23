package com.hesicare.common.utils;

import java.util.Date;

public enum  sout {
    xy("12");
   // xy(String.format("D:/Hesicare/java项目/logs/sg/%s/%s.txt", this.getLogName(), Constants.convert(new Date(), Constants.format1))),
    private sout(String path){

    }
    private  String Logpath;
    private String LogName;

    public String getLogpath() {
        return Logpath;
    }

    public void setLogpath(String logpath) {
        Logpath = logpath;
    }

    public String getLogName() {
        return LogName;
    }

    public void setLogName(String logName) {
        LogName = logName;
    }
}
