package com.schinta.shiplock.controller.api;

import com.schinta.shiplock.Util.TimeUtils;
import com.schinta.shiplock.model.Scheme.Scheme;
import com.schinta.shiplock.model.ShipLockCategory.ShipLockCategory;
import com.schinta.shiplock.model.ship.Ship;
import com.schinta.shiplock.model.shipCategory.ShipCategory;
import com.schinta.shiplock.model.shipLock.ShipLock;
import com.schinta.shiplock.service.Calculation;
import com.schinta.shiplock.service.SchemeService;
import com.schinta.shiplock.service.impl.CalculationImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

import static com.schinta.shiplock.Util.StatisticsUtil.distListByMeanAndFluctuation;

/**
 * Created by wangyuannan on 2015/12/21.
 */


@Controller
@RequestMapping("/api")
public class SchemeAPICtrl {

    @Autowired
    private SchemeService schemeService;

    @RequestMapping("/helloWorld")
    public @ResponseBody String doCalculation() {
        Scheme scheme = new Scheme();
        scheme.setName("wyn_Test");
        scheme.setYear("2021");
        scheme.setStartDay("2021-01-01");
        scheme.setEndDay("2021-12-31");
        scheme.setScheduleWeight_ratio(0.6);
        scheme.setTotal(1.05);

        ArrayList<ShipCategory> shipCategories = new ArrayList<ShipCategory>();
        ShipCategory category1 = new ShipCategory("YH1000", "液货船", 11.0, 67.0, 2.6, 0.65, 1000.0, 0.02, 1440);
        ShipCategory category2 = new ShipCategory("YH1500", "液货船",13.0, 75.0, 3.0, 0.72, 1500.0, 0.02, 1440);
        ShipCategory category3 = new ShipCategory("YH2500", "液货船",13.8, 88.0, 3.5, 0.72, 2500.0, 0.02, 1440);
        ShipCategory category4 = new ShipCategory("YH3000", "液货船",15.0, 88.0, 3.5, 0.72, 3000.0, 0.02, 1440);
        ShipCategory category5 = new ShipCategory("YH3500", "液货船",16.3, 88.0, 4.3, 0.65, 3500.0, 0.02, 1440);
        ShipCategory category6 = new ShipCategory("YH5000", "液货船",16.3, 105.0, 4.3, 0.72, 5000.0, 0.02, 1440);
        ShipCategory category7 = new ShipCategory("GSH2500", "干散货船",13.8, 88.0, 3.5, 0.65, 2500.0, 0.02, 1440);
        ShipCategory category8 = new ShipCategory("GSH3000", "干散货船",15.0, 88.0, 3.5, 0.72, 3000.0, 0.02, 1440);
        ShipCategory category9 = new ShipCategory("GSH3500", "干散货船",16.3, 88.0, 4.3, 0.72, 3500.0, 0.02, 1440);
        ShipCategory category10 = new ShipCategory("GSH4500", "干散货船",16.3, 88.0, 4.3, 0.65, 4500.0, 0.02, 1440);
        ShipCategory category11 = new ShipCategory("GSH6500", "干散货船",16.3, 105.0, 5.5, 0.72, 6500.0, 0.02, 1440);
        ShipCategory category12 = new ShipCategory("GSH7500", "干散货船",16.3, 130.0, 5.5, 0.72, 7500.0, 0.02, 1440);
        ShipCategory category13 = new ShipCategory("GSH9000", "干散货船",19.2, 135.0, 5.5, 0.65, 9000.0, 0.02, 1440);
        ShipCategory category14= new ShipCategory("GSH10500", "干散货船",22.0, 135.0, 5.5, 0.72, 10500.0, 0.02, 1440);
        ShipCategory category15 = new ShipCategory("JZX150", "集装箱船",13.8, 88.0, 3.5, 0.65, 2625.0, 0.02, 1440);
        ShipCategory category16 = new ShipCategory("JZX200", "集装箱船",15.0, 88.0, 3.5, 0.72, 3500.0, 0.02, 1440);
        ShipCategory category17 = new ShipCategory("JZX250", "集装箱船",16.3, 88.0, 4.3, 0.72, 4375.0, 0.02, 1440);
        ShipCategory category18 = new ShipCategory("JZX300", "集装箱船",16.3, 110.0, 5.5, 0.65, 5250.0, 0.02, 1440);
        ShipCategory category19 = new ShipCategory("JZX550", "集装箱船",19.2, 135.0, 5.5, 0.72, 9625.0, 0.02, 1440);
        ShipCategory category20 = new ShipCategory("JZX650", "集装箱船",22.0, 135.0, 5.5, 0.65, 11375.0, 0.02, 1440);
        ShipCategory category21 = new ShipCategory("JZX750", "集装箱船",25.0, 135.0, 5.5, 0.72, 13125.0, 0.02, 1440);
        shipCategories.add(category1);
        shipCategories.add(category2);
        shipCategories.add(category3);
        shipCategories.add(category4);
        shipCategories.add(category5);
        shipCategories.add(category6);
        shipCategories.add(category7);
        shipCategories.add(category8);
        shipCategories.add(category9);
        shipCategories.add(category10);
        shipCategories.add(category11);
        shipCategories.add(category12);
        shipCategories.add(category13);
        shipCategories.add(category14);
        shipCategories.add(category15);
        shipCategories.add(category16);
        shipCategories.add(category17);
        shipCategories.add(category18);
        shipCategories.add(category19);
        shipCategories.add(category20);
        shipCategories.add(category21);
        scheme.setShipCategory(shipCategories);

        LinkedHashMap<String, Double> ratios = new LinkedHashMap<String, Double>();  //船型每年占比
        List<Double> ratioMeans = new ArrayList<Double>(Arrays.asList(0.075, 0.100, 0.070, 0.065, 0.065, 0.055, 0.060, 0.015, 0.015, 0.060, 0.050, 0.045, 0.015, 0.005, 0.045
                , 0.075, 0.065, 0.055, 0.035, 0.015, 0.015));//根据波动系数和均值，模拟当前随机值
        List<Double> ratioFluctuations = new ArrayList<Double>(Arrays.asList(0.001, 0.001,0.001,0.001,0.001,0.001,0.001,0.001,0.001,0.001,0.001,0.001,0.001,0.001,0.001,0.001,0.001,
                0.001,0.001,0.001,0.001));
        List<Double> ratioList = null;
        try {
            ratioList = distListByMeanAndFluctuation(ratioMeans, ratioFluctuations);
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (int i = 0; i < scheme.getShipCategory().size(); i++) {
            ShipCategory cate = scheme.getShipCategory().get(i);
            cate.setRatio(ratioList.get(i));
        }

        List<String> months = new ArrayList<String>(Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"));
        LinkedHashMap<String, Double> month = new LinkedHashMap<String, Double>();
        List<Double> means = new ArrayList<Double>(Arrays.asList(0.08, 0.071, 0.079, 0.087, 0.080, 0.088, 0.093, 0.093, 0.076, 0.093, 0.09, 0.07));//根据波动系数和均值，模拟当前随机值
        List<Double> fluctuations = new ArrayList<Double>(Arrays.asList(0.01, 0.01, 0.01, 0.01, 0.01, 0.01, 0.01, 0.01, 0.01, 0.01, 0.01, 0.01));
        List<Double> monthList = null;
        try {
            monthList = distListByMeanAndFluctuation(means, fluctuations);
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (int i = 0; i < 12; i++) {
            month.put(scheme.getYear() + "-" + months.get(i), monthList.get(i));
        }
        scheme.setpByMonth(month);


        /*初始化三峡北线上行船闸*/
        ShipLockCategory shipCateLock = new ShipLockCategory();
        shipCateLock.setLockId(1);
        shipCateLock.setLockName("三峡北线上行");
        shipCateLock.setWidth(32.8);
        shipCateLock.setLength(280);
        shipCateLock.setOuterWidth(34);
        shipCateLock.setOuterLength(280);
        shipCateLock.setRuntime(90);
        shipCateLock.setCnt(0);
        shipCateLock.setUseRatio(0.0);
        shipCateLock.setInwaterRestricted(true);
        shipCateLock.setInwater(4.8);
        shipCateLock.setIsCapacity(false); //没有装载吨数的限制
        /*初始化升船机船闸*/
        ShipLockCategory shipCateLock2 = new ShipLockCategory();
        shipCateLock2.setLockId(2);
        shipCateLock2.setLockName("三峡升船机");
        shipCateLock2.setWidth(16.5);
        shipCateLock2.setLength(120);
        shipCateLock2.setOuterWidth(18);
        shipCateLock2.setOuterLength(120);
        shipCateLock2.setRuntime(40);
        shipCateLock2.setCnt(0);
        shipCateLock2.setUseRatio(0.0);
        shipCateLock2.setInwaterRestricted(false); //没有吃水的限制
        shipCateLock2.setIsCapacity(true);
        shipCateLock2.setCapacity(3500);

        ArrayList<ShipLockCategory> categories = new ArrayList<ShipLockCategory>();
        categories.add(shipCateLock);
        categories.add(shipCateLock2);
        scheme.setShipLockCategory(categories);

        Calculation cal = new CalculationImpl();
        Integer integer = cal.doCalculation4N(scheme);
        int N = integer;
        System.out.println("该年所需的总船数为： " + N);
        List<Ship> sequence = cal.doCalculation4ShipSeries(scheme, N);
        List<ShipLock> shipLockList = cal.doCalculation4Packing(scheme, sequence, categories);
        System.out.println("DONE!");

        /*最终没有被装载进去的船的数量*/
        int packedShipsCnt = 0;
        int unpackedShips = 0;
        for (ShipLock shipLock : shipLockList) {
            packedShipsCnt = packedShipsCnt + shipLock.getShips().size();
        }
        unpackedShips = N - packedShipsCnt;
        if(unpackedShips > 0){
            scheme.setUnpackedCnt(unpackedShips);
        }
        else {
            scheme.setUnpackedCnt(0);
        }


        /*结果输出到文件*/
        File writename = new File("C:/output_test_0.7_100_greedy(3).txt"); // 相对路径，如果没有则要建立一个新的output。txt文件
        try {
            writename.createNewFile(); // 创建新文件
            BufferedWriter out = new BufferedWriter(new FileWriter(writename));

            List<Integer> waits = new ArrayList<Integer>();
            for (ShipLock sl : shipLockList) {
                out.write("===========船闸" + sl.getCategory().getLockName() + " 第" + sl.getCnt() + "次运行=================" + "\n");
                out.newLine();
                out.write("开始运行时间：" + sl.getStartTime());
                out.newLine();
                out.write("结束运行时间：" + sl.getEndTime());
                out.newLine();
                out.write("装船数：" + sl.getShips().size());
                out.newLine();
                out.write("闸室利用率：" + Math.round(sl.getUseRatio() * 100) + "%");
                out.newLine();
                out.write("当前锚地压船数： " + sl.getUnpacked());
                out.newLine();
                List<Ship> ships = sl.getShips();
                for (int i = 0; i < ships.size(); i++) {
                    Ship ship = ships.get(i);
                    out.write("第" + (i + 1) + "个船： " + "长：" + ship.getLength() + "m, 宽：" + ship.getWidth() + "m, 闸室坐标x：" + ship.getX() + ", y: " + ship.getY());
                    out.newLine();
                    String arrTime = ship.getArrivalDatetime();
                    String depTime = ship.getDepartDatetime();
                    int wait = 0;
                    try {
                        wait = (int) TimeUtils.intervalBetweenTimes(TimeUtils.str2Date(arrTime), TimeUtils.str2Date(depTime));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    waits.add(wait);
                    out.write("到达：" + TimeUtils.str2Date(arrTime) + ", 离开锚地时间：" + TimeUtils.str2Date(depTime) + ", 等待时间: " + wait + "mins!");
                    out.newLine();
                }
                out.newLine();
            }
            int sum = 0;
            for (int wait : waits) {
                sum = sum + wait;
            }
            out.write("船只的平均等待时间：" + sum / waits.size());
            out.newLine();
            for (ShipLockCategory ctg : categories) {
                ctg.setUseRatio(Math.round(ctg.getUseRatio() / ctg.getCnt() * 100));
                out.write("船闸：" + ctg.getLockName() + "的平均闸室利用率为： " + ctg.getUseRatio() + " ");
            }
            out.flush(); // 把缓存区内容压入文件
            out.close(); // 关闭文件
        } catch (IOException e) {
            e.printStackTrace();
        }

        schemeService.saveScheme(scheme, shipLockList);
        return "FINISHED";
    }

    @RequestMapping("/schemes")
    public @ResponseBody List<Scheme> schemes(){
        return schemeService.getSchemes(10);
    }

    @RequestMapping(value="/startCal", method= RequestMethod.GET)
    public @ResponseBody String  startCal(HttpServletRequest request,
                                          HttpServletResponse response){
        System.out.println("test");
        return null;
    }

}
