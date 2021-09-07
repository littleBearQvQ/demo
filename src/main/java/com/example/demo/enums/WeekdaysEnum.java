package com.example.demo.enums;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 团子等等俺
 * */
public enum WeekdaysEnum {

    MONDAY(1,"周一"),
    TUESDAY(2, "周二"),
    WEDNESDAY(3, "周三"),
    THURSDAY(4, "周四"),
    FRIDAY(5, "周五"),
    SATURDAY(6, "周六"),
    SUNDAY(7, "周日");

    private int code;
    private String desc;


    WeekdaysEnum(int code,String desc){
        this.code=code;
        this.desc=desc;
    }

    private static final Map<Integer,WeekdaysEnum> WEEK_ENUM_MAP = new HashMap<Integer, WeekdaysEnum>();

    static{
        for(WeekdaysEnum weekdaysEnum:WeekdaysEnum.values()){
            WEEK_ENUM_MAP.put(weekdaysEnum.code,weekdaysEnum);
        }
    }

    public static WeekdaysEnum findByCode(int code){
        return WEEK_ENUM_MAP.get(code);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public static Map<Integer, WeekdaysEnum> getWeekEnumMap() {
        return WEEK_ENUM_MAP;
    }

    public String toString(){

        return MessageFormat.format("weekdaysEnum info: code:{0} desc:{1}",this.code,this.desc);
    }
}
