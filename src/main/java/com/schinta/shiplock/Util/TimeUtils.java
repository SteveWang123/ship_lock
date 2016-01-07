package com.schinta.shiplock.Util;

import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by wangyuannan on 2015/12/22.
 */
public class TimeUtils {

    /*将分钟整数转为时间格式HH24：MI*/
    public static String convertMinutes2Time(Integer mins){

        if(mins >= 1440){
            mins = mins - 1440;
        }
        int hour = (int) mins / 60;
        int minu = (int) mins % 60;
        return String.format("%02d", hour) + ":" + String.format("%02d", minu);
    }



    /*将一天的分钟整数转为时间格式HH24：MI
    * 注意：当分钟数大于1440时，需要天数+1
    * */
    public static String convertMin2Time(String arrDate, Integer mins){
        int hour = 0;
        int minu = 0;
        String arriveDate = arrDate;
        if(mins >= 1440){
            mins = mins - 1440;
            hour = (int)mins / 60;
            minu = (int)mins % 60;
            Calendar c = Calendar.getInstance();
            c.setTime(TimeUtils.str2Date(arrDate + " 00:00"));
            c.add(Calendar.DAY_OF_YEAR, 1);
            arriveDate = TimeUtils.date2Str(c.getTime());
            arriveDate = arrDate.split(" ")[0];
        }
        else {
            hour = (int)mins / 60;
            minu = (int)mins % 60;

        }
        return arriveDate + " " + String.format("%02d", hour) + ":" + String.format("%02d", minu);
    }


    /*根据年份获取该年的总分钟数*/
    public static Integer getTotalMinsByYear(int year){
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        int sum = 0;
        for(int i = 0; i < 12; i++){
            c.set(Calendar.MONTH, i);
            c.set(Calendar.DATE, 1);
            c.roll(Calendar.DATE, -1);
            int minCnt = c.get(Calendar.DATE) * 24 * 60;
            sum = sum + minCnt;
        }
        return sum;
    }

    /*根据分钟数计算当前的日期
    * @param    year 年份， min 当年累计的分钟数
    * @return  格式 e.g."2012-04-04 15:00"
    * */
    public static String getTimeFromMin(int year, int mins) throws Exception{
        if(mins < 0) throw new Exception("分钟数不可以小于0");
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);

        List<Integer> minCnts = new ArrayList<Integer>();
        for(int i = 0; i < 12; i++){
            c.set(Calendar.MONTH, i);
            c.set(Calendar.DATE, 1);
            c.roll(Calendar.DATE, -1);
            int minCnt = c.get(Calendar.DATE) * 24 * 60;
            minCnts.add(minCnt);
        }

        List<Integer> minTrd = new ArrayList<Integer>();
        for(int i = 0; i < minCnts.size(); i++){
            int sum = 0;
            for(int j = 0; j <= i; j++){
                sum = sum + minCnts.get(j);
            }
            minTrd.add(sum);
        }
//        System.out.println(minTrd);

        int index = 0;
        for(int i = 0; i<minTrd.size(); i++){
            if((mins / minTrd.get(i)) >= 1)continue;
            else{
                index = i;  //月份：正常月份-1 = index
                break;
            }
        }
//        System.out.println("index:" +index);
        int index_day = 0;
        int minTillLastMonth =0;
        if(index == 0) minTillLastMonth = 0;
        else minTillLastMonth = minTrd.get(index-1);
        int minInThisMonth = mins - minTillLastMonth;
        for(int i = 0; ; i++){
            if(minInThisMonth / ((i+1) * 24 * 60) >=1 ) continue;
            else{
                index_day = i;  //天数：正常天数-1 = index_day
                break;
            }
        }
//        System.out.println("index_day:" +index_day);
        int minLeft = minInThisMonth - index_day * 60 * 24;
        String datetime = String.format("%02d", year) + "-" + String.format("%02d", index + 1) + "-" + String.format("%02d", index_day + 1) + " " + convertMinutes2Time(minLeft);
        return datetime;
    }


    /*将时间string（yyyy-MM-dd HH:mm） to date*/
    public static Date str2Date(String datetime){
        Date date = new Date();
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        try {
            date = sdf.parse(datetime);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date;
    }

    /*将日期转换为string（yyyy-MM-dd HH:mm）*/
    public static String date2Str(Date date){
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        sdf.format(date);
        return sdf.format(date);
    }

    /*将输入时间加上m分钟*/
    public static Date plusMinutes(Date date, int m){
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.MINUTE, m);
        return  c.getTime();
    }


    /*根据两个时间，求出时间间隔，以分钟表示
    * @param    earlier 早的时间， later 晚的时间(yyyy-mm-dd HH24:MI)
    *
    * @return   两个时间间隔，分钟表示 long类型
    * @exception
    * */
    public static long intervalBetweenTimes(Date earlier, Date later) throws Exception {
        if(later.before(earlier)){
            throw new Exception("时间输入不正确");
        }
//        System.out.println(later.getTime() - earlier.getTime());
        return later.getTime() / 60000 - earlier.getTime() / 60000;
    }


    public static void main(String[] args) throws Exception {
        System.out.println(TimeUtils.convertMin2Time("2015-10-10", 1480));
//        System.out.println(TimeUtils.str2Date("2020-10-10 14:20"));
//        System.out.println(TimeUtils.date2Str(new Date()));
//        System.out.println(TimeUtils.date2Str(TimeUtils.plusMinutes(new Date(), 20)));
//        System.out.println(TimeUtils.getTimeFromMin(2015, 368600));
//        System.out.println(TimeUtils.getTotalMinsByYear(2012));
//        System.out.println((int)TimeUtils.intervalBetweenTimes(str2Date("2020-01-01 00:40"), str2Date("2020-01-02 03:40")));
    }


}
