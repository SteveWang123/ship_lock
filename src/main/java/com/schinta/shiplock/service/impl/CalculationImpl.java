package com.schinta.shiplock.service.impl;

import com.schinta.shiplock.Util.NormalUtil;
import com.schinta.shiplock.Util.StatisticsUtil;
import com.schinta.shiplock.Util.TimeUtils;
import com.schinta.shiplock.model.Scheme.Scheme;
import com.schinta.shiplock.model.ShipLockCategory.ShipLockCategory;
import com.schinta.shiplock.model.ship.Ship;
import com.schinta.shiplock.model.shipCategory.ShipCategory;
import com.schinta.shiplock.model.shipLock.ShipLock;
import com.schinta.shiplock.service.Calculation;
import com.schinta.shiplock.service.Packer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * Created by wangyuannan on 2015/12/17.
 */
public class CalculationImpl   implements Calculation {

    static public Logger logger = LoggerFactory.getLogger(CalculationImpl.class);

    public static final int yidun2dun = 100000000;  //亿吨转换为吨的常量参数

    @Override
    public Integer doCalculation4N(Scheme scheme) {
        ArrayList<ShipCategory> shipCategory = scheme.getShipCategory();
        HashMap<String, Double> pByMonth = scheme.getpByMonth();
        Double total = scheme.getTotal();

        double p_chuan;  //船型所占比
        double load;  //该船型在该方案实际的负载系数
        double t;   //吨位
        double tmp;  //累加计算每一项的值
        double sum = 0.0;  //累加计算的和
        for(ShipCategory category : shipCategory){
            p_chuan = category.getRatio();
            load = category.getDesignLoad();
            t = category.getCapacity();

            for(Iterator it = pByMonth.entrySet().iterator();it.hasNext();){ //遍历月份
                Map.Entry<String, Double> entry1 = (Map.Entry<String, Double>)it.next();
                double p_month = entry1.getValue();
                tmp = p_chuan * load * t *p_month;
                sum = sum + tmp;
            }

        }

        int N = (int) (total * yidun2dun/sum);

        return  N;
    }

    @Override
    public List<Ship> doCalculation4ShipSeries(Scheme scheme, Integer N) {

        /* 计算 N * pMonth， 得到每个月的来船总数 存于distByMonth */
        Map<String, Double> pMonth = scheme.getpByMonth();
        Map<String, Integer> distByMonth = _breakNByDistribution(pMonth, N);
        /* end 计算 N * pMonth， 得到每个月的来船总数*/


        Iterator itera = distByMonth.entrySet().iterator();
        while(itera.hasNext()){
            Map.Entry<String, Integer> entry = (Map.Entry<String, Integer>)itera.next();
            System.out.println(entry.getKey() + ":" + entry.getValue());
        }

        /*计算 cntByMonth * ship_ratio 得到每个月份内每种船型的数量 存于 distByMonthAndShipCategory */
        //将船型比例准备成一个Map: shipDistribut
        Map<String, Double> shipDistribute = new HashMap<String, Double>();
        String shipId = null;
        Double ratio = null;
        ArrayList<ShipCategory> ships = scheme.getShipCategory();
        for(ShipCategory category : ships){
            shipId = category.getCategoryId();
            ratio = category.getRatio();
            shipDistribute.put(shipId, ratio);
        }


        Integer totalByMonth = null;
        Map<String, Object> distByMonthAndShipCategory = new LinkedHashMap<String, Object>();
        distByMonthAndShipCategory.putAll(distByMonth);
        for(Iterator ite = distByMonthAndShipCategory.entrySet().iterator(); ite.hasNext(); ){
            Map.Entry<String, Object> entry =  (Map.Entry<String, Object>)ite.next();
            totalByMonth = (Integer)entry.getValue();
            Map<String, Integer> b = _breakNByDistribution(shipDistribute, totalByMonth);
            entry.setValue(b);
        }
        /* end 计算 cntByMonth * ship_ratio 得到每个月份内每种船型的数量 存于distByMonthAndShipCategory */


        /* 计算每一天每种船到达的数量 存于 map 变量*/
        //准备好数据格式
        HashMap<String, Object> map = new LinkedHashMap<String, Object>();
        for(Iterator ite = distByMonthAndShipCategory.entrySet().iterator(); ite.hasNext();){
            Map.Entry<String, Object> entry =  (Map.Entry<String, Object>)ite.next();
            String date = entry.getKey();
            Map<String, Object> aMap = genDaysSeries(date);
            for(Iterator it = aMap.entrySet().iterator(); it.hasNext();){
                Map.Entry<String, Object> e =  (Map.Entry<String, Object>)it.next();
                e.setValue(new LinkedList<Object>());
            }
            map.putAll(aMap);
        }


        for(Iterator ite = distByMonthAndShipCategory.entrySet().iterator(); ite.hasNext();){
            Map.Entry<String, Object> entry =  (Map.Entry<String, Object>)ite.next();
            String date = entry.getKey();
            Map<String, Object> m = genDaysSeries(date);  //当前月的以每一天为key的map
            Map<String, Double> p = genP_DaysSeries(m);  //生成以日为key，概率分布为value的map  {'2020-01-01':0.032, '2020-01-02':0.034,...}
            HashMap<String, Object> shipCategories = (HashMap<String, Object>)entry.getValue();

            for(Iterator it = shipCategories.entrySet().iterator(); it.hasNext();){
                Map.Entry<String, Object> shipEntry = (Map.Entry<String, Object>)it.next();
                String name = shipEntry.getKey();
                Integer number = (Integer) shipEntry.getValue();
                Map<String, Integer> k = _breakNByDistribution(p, number);  //{'2020-01-01':21, '2020-01-02':22,...}
                Map<String, Object> j = new LinkedHashMap<String, Object>();
                j.putAll(k);

                for(Iterator i = j.entrySet().iterator(); i.hasNext();){
                    Map.Entry<String, Object> a =  (Map.Entry<String, Object>)i.next();
                    Map<String, Integer> newMap  = new HashMap<String, Integer>();
                    newMap.put(name, (Integer) a.getValue());
                    a.setValue(newMap);
                }

                for(Iterator i = j.entrySet().iterator(); i.hasNext();){
                    Map.Entry<String, Object> b =  (Map.Entry<String, Object>)i.next();
                    date = b.getKey();
                    for(Iterator n = map.entrySet().iterator(); n.hasNext();){
                        Map.Entry<String, Object> c =  (Map.Entry<String, Object>)n.next();
                        if(date.equalsIgnoreCase(c.getKey())){
                            LinkedList<Object> list = (LinkedList<Object>)c.getValue(); //获取到最一开始初始化的以日期为key，以linkedlist为value的list， 向list中放进
                            list.add(b.getValue());
                            break;
                        }
                    }
                }
            }
        }
        /* end 计算每一天每种船到达的数量 */


        /*模拟每一天 船舶到达情况 */
        for(Iterator iter = map.entrySet().iterator(); iter.hasNext();){
            Map.Entry<String, Object> entry = (Map.Entry<String, Object>)iter.next();
            LinkedList<Object> list = (LinkedList<Object>)entry.getValue();
            for(Object item : list){
                HashMap<String, Object> e = (HashMap<String, Object>)item;
                for(Iterator ite = e.entrySet().iterator(); ite.hasNext();){
                    Map.Entry<String, Object> en = (Map.Entry<String, Object>)ite.next();
//                    System.out.println(en.getKey());
                    Integer n = (Integer)en.getValue();
                    List estimatedTimes = StatisticsUtil.shipArravals(n);
                    List t = new ArrayList();
                    int estimatedTime = 0;
                    for(Object time : estimatedTimes){
                        estimatedTime = estimatedTime + (Integer)time;
                        t.add(estimatedTime);
                    }
                    en.setValue(t);
//                    System.out.println(en);
                }

            }

        }
        /* end 计算每一天每种船到达的数量 存于 map 变量*/


        /*根据来船顺序生成船的序列，存于sequence中: [{船的实体类}, {}, {}, {}...]*/
        List<Ship> sequence = new ArrayList<Ship>();
        int cnt = 0; //船的id
        for(Iterator iter = map.entrySet().iterator(); iter.hasNext(); ){
            Map.Entry<String, Object> ship = (Map.Entry<String, Object>)iter.next();
            String arrvalDate = ship.getKey();
            LinkedList<HashMap> categories = (LinkedList<HashMap>)ship.getValue();
            for(HashMap<String,Object> item : categories){
                for(Iterator ite = item.entrySet().iterator(); ite.hasNext(); ){
                    Map.Entry<String, Object> it = (Map.Entry<String, Object>)ite.next();
                    String categoryId = it.getKey();

                    ArrayList<ShipCategory> categories1 = scheme.getShipCategory();
                    double width = 0, length =0, maxInWater = 0, capacity = 0,  designedLoad = 0, fluctuation = 0, load = 0;
                    int maxWaitTime= 0;
                    ShipCategory c = null;
                    for(ShipCategory category : categories1){
                        if(categoryId.equalsIgnoreCase(category.getCategoryId())){
                            width = category.getWidth();
                            length = category.getLength();
                            maxInWater = category.getMaxInWater();
                            capacity = category.getCapacity();
                            maxWaitTime = category.getMaxWaitTime();
                            designedLoad = category.getDesignLoad();
                            fluctuation = category.getFluctuation();
                            load = StatisticsUtil.normalDistRandomByFluctuation(designedLoad, fluctuation);
                            c = category;
                        }
                    }

                    ArrayList<Integer> times = (ArrayList<Integer>)it.getValue();
                    for(int time : times){
                        cnt++;
                        Ship aShip = new Ship(cnt, c, width, length, maxInWater, capacity, load, maxWaitTime, false);
                        String arrivalDateTime = TimeUtils.convertMin2Time(arrvalDate, time);
                        aShip.setArrivalDatetime(arrivalDateTime);
                        sequence.add(aShip);
                    }
                }
            }
        }

        sequence = NormalUtil.sortShips(sequence);  //将船按照来船的时间先后顺序排序，来船时间越早在前

        return sequence;
    }

    @Override
    public List<ShipLock> doCalculation4Packing(Scheme scheme, List<Ship> sequence, List<ShipLockCategory> shipLockCategories) {
        Packer packer = new PackerImpl();

        List<Ship> shipsTillNow = null;
        int interval = 10;   //TODO 迭代的时间步长:找到最小公倍数
        List<ShipLock> shipLockList = new ArrayList<ShipLock>();

        int number = 0;

        int min2Start = 0;  //计算年的1月1日到计算开始时间的分钟数
        int min2End = 0;  //计算年的1月1日到计算结束时间的分钟数
        try {
            min2Start = (int) TimeUtils.intervalBetweenTimes(TimeUtils.str2Date(scheme.getYear() + "-01-01 00:00"), TimeUtils.str2Date(scheme.getStartDay() + " 00:00"));
            min2End = (int) TimeUtils.intervalBetweenTimes(TimeUtils.str2Date(scheme.getYear() + "-01-01 00:00"), TimeUtils.str2Date(scheme.getEndDay() + " 23:59"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        sequence = _getShipsBetweenStartEndDay(sequence, min2Start, min2End);  //找到在开始时间和结束时间之内的船只次序

        for (int t = min2Start; t <= min2End ; t = t + interval) {

            for (ShipLockCategory ctg : shipLockCategories) {  //遍历不同的船闸
                int runtime = ctg.getRuntime(); //船闸的运行时间
                if (t % runtime == ctg.getRuntimeThre()) {   //每个船闸自己的等待时间是ctg.getRuntimeThre()
                    String now = null;
                    try {
                        now = TimeUtils.getTimeFromMin(Integer.parseInt(scheme.getYear()), t);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    shipsTillNow = NormalUtil.findShipsTillTime(sequence, now);
                    if (shipsTillNow.size() == 0) { //当前时间没有船
                        continue;
                    }
                    number = shipsTillNow.size();  //记录当前锚地船的数量
                    try {
                        System.out.println("当前时间: " + TimeUtils.getTimeFromMin(Integer.parseInt(scheme.getYear()), t) + "， 压船数： " + shipsTillNow.size() + "条船，" + " 船只： " + shipsTillNow);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    /*当前船只（shipsTillNow）中找到等待时间大于最大等待时间的船，放在最前，其他按照到达时间的先后顺序排序*/
                    List<Ship> shipsUrgent = _sortShips(shipsTillNow, now, true); //等待时间大于最大等待时间的船
                    List<Ship> shipsUnurgent = _sortShips(shipsTillNow, now, false); //等待时间小于最大等待时间的船


                    /*根据船闸的基本信息，需要对排挡的方案进行选择
                    * 如果船闸的长<150 && 宽<20,则选择贪婪算法的排挡方案
                    * 如果船闸的长 > 150 && 宽 > 20，则这个是大船闸，选择降维算法以保证闸室的利用率
                    */
                    ShipLock oneShipLock;
                    double slWidth = ctg.getWidth();
                    double slLength = ctg.getLength();
                    if(shipsUrgent.size() > 0){  //如果当前有船只的等待时间大于了最大等待时间，则贪婪算法，保证这些船先过
//                        oneShipLock = packer.doGreedyPacking(ctg, shipsTillNow);
                        shipsUrgent.addAll(shipsUnurgent);
                        oneShipLock = packer.doGreedyPacking(ctg, shipsUrgent);
                    }
                    else{  //s当前的压船中，没有船只超过最大等待时间
                        if(slLength < 150 && slWidth < 20){  //如果船闸的尺寸小，则直接贪婪算法
                            oneShipLock = packer.doGreedyPacking(ctg, shipsTillNow); //装箱之后会返回一个一次装箱的实体（ShipLock），需要对这个实体检查船闸利用率
                        }
                        else {
                            if(shipsTillNow.size() > (int)100 * scheme.getScheduleWeight_ratio()){  //当锚地压船过多时，采用贪婪算法，使最早积压的船优先通过
                                oneShipLock = packer.doGreedyPacking(ctg, shipsTillNow); //装箱之后会返回一个一次装箱的实体（ShipLock），需要对这个实体检查船闸利用率
                            }
                            else {
                                int tmp = 0;
                                if (shipsTillNow.size() > 10){
                                    for (int i = 0; i < 10; i++){ //检查锚地压船考前的船只，如果前面压船的都是宽度大于16.3的大船，则需要用贪婪算法将它们运过去，不然会导致大船大面积的积压
                                        if(shipsTillNow.get(i).getWidth() > 16.3){
                                            tmp++;
                                        }
                                    }
                                }
                                if(tmp >= 5){
                                    oneShipLock = packer.doGreedyPacking(ctg, shipsTillNow); //装箱之后会返回一个一次装箱的实体（ShipLock），需要对这个实体检查船闸利用率
                                }
                                else {
                                    oneShipLock = packer.doDownDimensionPacking(ctg, shipsTillNow); //装箱之后会返回一个一次装箱的实体（ShipLock），需要对这个实体检查船闸利用率
                                }
                            }
                        }
                    }


                    if(oneShipLock == null || oneShipLock.getShips().size() == 0){  //没有装船
                        ctg.setRuntimeThre((ctg.getRuntimeThre() + interval) % ctg.getRuntime()); //等待，在轮寻的下一个时间进行排船
                    }

                    if (oneShipLock != null && oneShipLock.getShips().size() != 0) {
                        double useRatio = oneShipLock.getUseRatio();
                        if (useRatio < 0.40) {  //这次闸室排挡的利用率与阈值比较
                            ctg.setRuntimeThre((ctg.getRuntimeThre() + interval) % ctg.getRuntime()); //等待，在轮寻的下一个时间进行排船
                        } else {  //排挡成功
                            ctg.setCnt(ctg.getCnt() + 1);
                            ctg.setUseRatio(ctg.getUseRatio() + useRatio);
                            oneShipLock.setCategory(ctg);  //该闸次是哪一个船闸
                            oneShipLock.setUseRatio(useRatio);
                            oneShipLock.setCnt(ctg.getCnt()); //第几闸次
                            oneShipLock.setStartTime(now);  //该闸次开始运行时间
                            oneShipLock.setEndTime(TimeUtils.date2Str(TimeUtils.plusMinutes(TimeUtils.str2Date(now), runtime))); //该闸次结束运行时间
                            List<Ship> ships = oneShipLock.getShips();
                            int weight = 0;
                            for (Ship ship : ships) {
                                ship.setDepartDatetime(now);  //离开锚地的时间
                                int waitTime = _getWaitTime(ship.getArrivalDatetime(), ship.getDepartDatetime());
                                ship.setWaitTime(waitTime);  //该船的等待时间(mins)
                                ship.setIsMoved(true);       //标志该船是否过闸
                                ship.setShipLock(oneShipLock);  //该船过得闸次
                                weight = weight + (int)(ship.getShipcategory().getCapacity() * ship.getLoad());
                                sequence.remove(ship);  //这个船已经被装载，从来船序列中删除
                            }
                            oneShipLock.setWeight(weight);
                            if (number - ships.size() >= 0) {
                                number = number - ships.size();
                                oneShipLock.setUnpacked(number);
                            } else oneShipLock.setUnpacked(0);
                            shipLockList.add(oneShipLock);
                        }
                    }
                }
            }
        }
        return shipLockList;
    }

    /*在now这个时间点上，从shipsTillNow中找到大于等于或者小于最大等待时间的船舶
    * flag=true：找大于等于最大等待时间
    * flag=false：找小于最大等待时间
    * */
    private List<Ship> _sortShips(List<Ship> shipsTillNow, String now, boolean flag) {
        List<Ship> ships = new ArrayList<>();
        int minites = 0;
        for(Ship ship: shipsTillNow){
            String arrival = ship.getArrivalDatetime();
            minites = (int)(TimeUtils.str2Date(now).getTime() - TimeUtils.str2Date(arrival).getTime()) / (1000 * 60);
            if(flag){
                if(minites >= ship.getMaxWaitTime()){
                    ships.add(ship);
                }
            }
            else {
                if(minites < ship.getMaxWaitTime()){
                    ships.add(ship);
                }
            }
        }

        return ships;
    }


    /*根据船的到达时间和离开时间，计算等待的分钟数
    *  @param: arrivalDatetime:到达的时间（'YYYY-mm-dd HH24:mi'）, departDatetime:离开的时间（'YYYY-mm-dd HH24:mi'）
    *  @return: 等待的分钟数，int型
    * */
    private int _getWaitTime(String arrivalDatetime, String departDatetime) {
        Date arrival = TimeUtils.str2Date(arrivalDatetime);
        Date depart = TimeUtils.str2Date(departDatetime);
        int wait = (int)(depart.getTime() - arrival.getTime()) / (1000 * 60);
        return wait;
    }

    private List<Ship> _getShipsBetweenStartEndDay(List<Ship> sequence, int min2Start, int min2End) {
        List<Ship> s = new ArrayList<Ship>();
        int min = 0;
        for(Ship ship:sequence){
            String arr = ship.getArrivalDatetime();
            String year = arr.split("-")[0];
            try {
                min = (int) TimeUtils.intervalBetweenTimes(TimeUtils.str2Date(year + "-01-01 00:00"), TimeUtils.str2Date(ship.getArrivalDatetime() + " 00:00"));
            } catch (Exception e) {
                e.printStackTrace();
            }
            if(min <= min2End && min >= min2Start){
                s.add(ship);
            }
            else {
               continue;
            }
        }
        return s;
    }

    private Map<String, Integer> _breakNByDistribution(Map<String, Double> dist, Integer N) {
        Map<String, Double> distribution = new LinkedHashMap<String, Double>();
        distribution.putAll(dist);
        Map<String, Integer> _cntBydist = new LinkedHashMap<String, Integer>();
        int count = 0;
        int a;
        for(Iterator ite = distribution.entrySet().iterator(); ite.hasNext();){
            Map.Entry<String, Object> entry = (Map.Entry<String, Object>) ite.next();
            a =  (int)((double)entry.getValue() * N);
            count = count + a;
            _cntBydist.put(entry.getKey(), a);
        }

        int diff = N - count;
        int k;
        Map.Entry<String, Object> entry;
        for(Iterator ite = _cntBydist.entrySet().iterator(); ite.hasNext();){
            if(diff !=0 ){
                entry = (Map.Entry<String, Object>) ite.next();
                k = (int)entry.getValue();
                entry.setValue(k+1);
                diff = diff - 1;
            }
            else break;
        }

        /*debug 打印*/
//        for(Iterator ite = _cntBydist.entrySet().iterator(); ite.hasNext();){
//            Map.Entry entry = (Map.Entry) ite.next();
//            System.out.println("key: " + entry.getKey() + " , value: " + entry.getValue());
//        }
        return _cntBydist;
    }


    /*根据年和月，返回该年该月份的天数*/
    private Integer _getDaysByMonth(String year, String month){

        List<String> bigMonth = new ArrayList<String>(Arrays.asList("01", "03", "05", "07", "08", "10", "12"));
        List<String> smallMonth = new ArrayList<String>(Arrays.asList("04", "06", "09", "11"));
        List<String> runYear = new ArrayList<String>(Arrays.asList("2012", "2016", "2020", "2024", "2028","2032"));


        if(bigMonth.contains(month)){
            return 31;
        }
        else if(smallMonth.contains(month)){
            return 30;
        }
        else if(runYear.contains(year)){
            return 29;
        }
        return 28;
    }


    /*根据一个日期（2020-10），生成以这个月每一天日期为key的map*/
    private Map<String, Object> genDaysSeries(String date){
        HashMap<String, Object> map = new LinkedHashMap<String, Object>();
        String year = date.split("-")[0];
        String month = date.split("-")[1];
        int day;
        String dayInStr;
        Integer days = _getDaysByMonth(year, month);
        for(int i =0; i < days; i++){
            day = i+1;
            dayInStr = String.format("%02d", day);
            map.put(date + "-" + dayInStr, new HashMap());
        }
        return map;
    }

    /* 根据月份，生成以日为key，概率分布为value的map {'2020-01-01':0.032, '2020-01-02':0.034,...}  */
    private Map<String, Double> genP_DaysSeries(Map<String, Object> m){
        Map<String, Double> r = new LinkedHashMap<String, Double>() ;
        for(Iterator ite = m.entrySet().iterator(); ite.hasNext();){
            Map.Entry<String, Object> entry = (Map.Entry<String, Object>) ite.next();
            r.put(entry.getKey(), (double) (1.0F / m.size()));
        }
        return r;
    }

}
