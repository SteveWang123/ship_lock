package com.schinta.shiplock.service.impl;

import com.schinta.shiplock.Util.NormalUtil;
import com.schinta.shiplock.Util.TimeUtils;
import com.schinta.shiplock.model.ShipLockCategory.ShipLockCategory;
import com.schinta.shiplock.model.ship.Ship;
import com.schinta.shiplock.model.shipLock.ShipLock;
import com.schinta.shiplock.service.Packer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.*;

/**
 * Created by wangyuannan on 2015/12/22.
 */
public class PackerImpl implements Packer {

    static public Logger logger = LoggerFactory.getLogger(PackerImpl.class);

    @Override
    public ShipLock doDownDimensionPacking(ShipLockCategory shipLockCategory, List<Ship> ships) {

        double slLength = shipLockCategory.getLength();
        double slWidth = shipLockCategory.getWidth();
        double slOuterWidth = shipLockCategory.getOuterWidth();
        double slOuterLength = shipLockCategory.getOuterLength();
        int slRuntime = shipLockCategory.getRuntime();  //运行一次的时间（单位：分钟）

        if (0 == ships.size()) {
            return null;
        }

        /*开始排船*/
        List<Ship> shipsAtWidth = _findShips4MaxSideUse(shipLockCategory, "width", slWidth, "length", slLength, ships);//找到在船闸宽度方向上利用率最大的船只组合
        shipsAtWidth = _sortByDimention("length", shipsAtWidth);//将这些船按照长度排序

        List<Ship> packedShips = new ArrayList<Ship>();   //装进去的船
        List<Double[]> packLocations = new ArrayList();//   可排点集

        if (shipsAtWidth.size() != 0) {
            for (Ship ship : shipsAtWidth) {   //从sequence中去掉shipsAtWidth的这些船
                ships.remove(ship);
            }

            double firstShipLen = shipsAtWidth.get(0).getLength();  //找到最左上角的船
            double left = slLength - firstShipLen;  //   计算在船闸长度的维度上还剩下的长度
            List<Ship> shipsAtLength = _findShips4MaxSideUse(shipLockCategory, "length", left, "width", slWidth, ships);//找到在船闸长度方向上利用率最大的船只组合
            shipsAtLength = _sortByDimention("width", shipsAtLength);//将这些船按照宽度排序
            for (Ship ship : shipsAtLength) {   //从sequence中去掉shipsAtLength的这些船
                ships.remove(ship);
            }
            /*将shipsAtWidth的船排进船闸，并计算可排点*/
            Double[] origin = new Double[]{0.0, 0.0};
            for (int i = 0; i < shipsAtWidth.size(); i++) {  //确定船的x，y坐标
                Ship ship = shipsAtWidth.get(i);

                ship.setX(origin[0]);   //该船在闸室停泊位置的x轴坐标
                ship.setY(origin[1]);    //该船在闸室停泊位置的y轴坐标
                packedShips.add(ship);  //将船排进船闸
                origin = new Double[]{origin[0], origin[1] + ship.getWidth()};            //新的origin
            }

            for (Ship ship : shipsAtWidth) {  //确定可排点集
                packLocations.add(new Double[]{ship.getX() + ship.getLength(), ship.getY()});
            }
            packLocations.add(new Double[]{shipsAtWidth.get(shipsAtWidth.size() - 1).getX(),
                    shipsAtWidth.get(shipsAtWidth.size() - 1).getY() + shipsAtWidth.get(shipsAtWidth.size() - 1).getWidth()}); //宽度维度最短的一条船的左下角是一个可排点

            /*将shipsAtLength的船排进船闸，并计算可排点*/
            if (shipsAtLength.size() != 0) {
                Double[] origin2 = new Double[]{shipsAtWidth.get(0).getLength(), 0.0};
                for (int i = 0; i < shipsAtLength.size(); i++) {  //确定船的x，y坐标
                    Ship ship = shipsAtLength.get(i);

                    ship.setX(origin2[0]);   //该船在闸室停泊位置的x轴坐标
                    ship.setY(origin2[1]);    //该船在闸室停泊位置的y轴坐标
                    packedShips.add(ship);  //将船排进船闸
                    origin2 = new Double[]{origin2[0] + ship.getLength(), origin2[1]};            //新的origin
                }

                /*在边界的关键位置排好以后，确定可排点集*/
                for (Ship ship : shipsAtLength) {
                    packLocations.add(new Double[]{ship.getX(), ship.getY() + ship.getWidth()});
                }
                packLocations.add(new Double[]{shipsAtLength.get(shipsAtLength.size() - 1).getX() + shipsAtLength.get(shipsAtLength.size() - 1).getLength(),
                        shipsAtLength.get(shipsAtLength.size() - 1).getY()}); //长度维度上最后一条船的最右边是一个可排点

                double x = shipsAtLength.get(0).getX();
                double y = shipsAtLength.get(0).getY();
                Iterator iter = packLocations.iterator();
                while(iter.hasNext()){
                    Double[] item = (Double[])iter.next();
                    if(item[0] == x && item[1] == y){
                        iter.remove();
                    }
                }
                /* END 在边界的关键位置排好以后，确定可排点集，存在packLocations中*/
            }
            /* END 将shipsAtLength的船排进船闸，并计算可排点*/

            packLocations = _sortLocations(packLocations);  //将可排点集按照约定排序
        }


        /*剩下的船采用贪婪算法排挡*/
        ShipLock shipLock = null;
        if (ships.size() == 0) {  //没有剩下的船了，也就是说，在之前两个维度上船已经排挡排完了
            shipLock = new ShipLock(); //生成返回的船闸闸次的实体类
            shipLock.setShips(packedShips);
            shipLock.setCategory(shipLockCategory);
        } else {
            if (packLocations.size() == 0) {  //说明降维算法根本没有找到船排进去
                packLocations.add(new Double[]{0.0, 0.0});
            }
            boolean isPackable = false; //用来标识 剩下的排挡过程中是否有船装进去
            for (Ship ship : ships) {  //对待排的船遍历
                for (Double[] location : packLocations) {  //对可排点遍历，直至没有可排点为止，说明整条船在所有的可排点都排不进去
                    if (_isPackable(ship, location, packedShips, shipLockCategory)) { //ship这个船在location这个可排点可以排挡
                        shipLock = new ShipLock(); //生成返回的船闸闸次的实体类
                        shipLock.setShips(packedShips);

                        ship.setX(location[0]);   //该船在闸室停泊位置的x轴坐标
                        ship.setY(location[1]);    //该船在闸室停泊位置的y轴坐标
                        packedShips.add(ship);

                        shipLock.setCategory(shipLockCategory);
                        shipLock.setShips(packedShips);

                        Double[] location1 = new Double[]{location[0] + ship.getLength(), location[1]}; //新产生的可排点坐标1
                        Double[] location2 = new Double[]{location[0], location[1] + ship.getWidth()}; //新产生的可排点坐标2
                        packLocations.add(location1);
                        packLocations.add(location2);
                        packLocations.remove(location);
                        packLocations = _sortLocations(packLocations);  //每放进去一个船，可排点发生改变，需要重新生成可排点并排序

                        isPackable = true;
                        break;
                    }
                }
            }

            if (!isPackable) { //用贪婪算法，但剩下的船都排不进去
                shipLock = new ShipLock(); //生成返回的船闸闸次的实体类
                shipLock.setShips(packedShips);
                shipLock.setCategory(shipLockCategory);
            }
        }

        double useRatio = NormalUtil.getUseRatio(shipLock);
        shipLock.setUseRatio(useRatio);

        return shipLock;
    }

    /*将船在dimen这个维度降序排序
    * 若ships为空，则返回一个空的List<Ship>
    * */
    private List<Ship> _sortByDimention(String dimen, List<Ship> ships) {
        IdentityHashMap<Double, Ship> map = new IdentityHashMap<Double, Ship>();

        for (Ship ship : ships) {
            Double value = 0.0;
            try {
                Field field = ship.getClass().getDeclaredField(dimen);
                field.setAccessible(true);
                value = (Double) field.get(ship);
            } catch (Exception e) {
                e.printStackTrace();
            }
            map.put(value, ship);
        }

        List<Map.Entry<Double, Ship>> list = new ArrayList<Map.Entry<Double, Ship>>(map.entrySet());

        Collections.sort(list, new Comparator<Map.Entry<Double, Ship>>() {
            @Override
            public int compare(Map.Entry<Double, Ship> o1, Map.Entry<Double, Ship> o2) {  //按照船长度的升序排序
                if (o1.getValue().getLength() < o2.getValue().getLength()) {
                    return 1;
                }
                return -1;
            }
        });

        List<Ship> s = new ArrayList<Ship>();
        for (Map.Entry<Double, Ship> ship : list) {
            s.add(ship.getValue());
        }

        return s;
    }

    @Override
    public ShipLock doGreedyPacking(ShipLockCategory shipLockCategory, List<Ship> ships) {

        double slLength = shipLockCategory.getLength();
        double slWidth = shipLockCategory.getWidth();
        double slOuterWidth = shipLockCategory.getOuterWidth();
        double slOuterLength = shipLockCategory.getOuterLength();
        int slRuntime = shipLockCategory.getRuntime();  //运行一次的时间（单位：分钟）
        boolean res = shipLockCategory.isInwaterRestricted(); //是否有吃水限制
        double inWaterRes = 0;
        if(res){
            inWaterRes = shipLockCategory.getInwater();
        }
        boolean isCapacity = shipLockCategory.isCapacity();//是否有转载吨数的限制

        if (0 == ships.size()) {   //没有船要装
            return null;
        }

        /*开始排船*/
        List<Ship> packedShips = new ArrayList<Ship>();   //装进去的船
        List<Double[]> packLocations = new ArrayList();//   可排点集
        packLocations.add(new Double[]{0.0, 0.0});   //初始的可排点（0,0）
        ShipLock shipLock = new ShipLock(); //生成返回的船闸闸次的实体类

        for (Ship ship : ships) {  //对待排的船遍历

            if(res){  //该船闸有吃水的限制
                double inWater = ship.getLoad() * ship.getMaxInWater();
                if(inWater >= inWaterRes) continue; //该船的吃水深度大于了该船闸的吃水限制
            }

            for (Double[] location : packLocations) {  //对可排点遍历，直至没有可排点为止，说明整条船在所有的可排点都排不进去

//                if(location[0] == 265.0 || location[0] == 245.0){
//                    System.out.println("可排点的x轴坐标为：" + location[0]);
//                    System.out.println(packedShips);
//                }
                if (_isPackable(ship, location, packedShips, shipLockCategory)) { //ship这个船在location这个可排点可以排挡

                    if(isCapacity){  //如果有转载吨数的限制，则需要对装载吨数进行约束
                        List<Ship> s = shipLock.getShips();
                        double total = 0;
                        if(s == null || s.size() == 0){//还没有装船，当前船是第一个可以装进去的船，需要对这个船进行吨位校验
                            total = total + ship.getCapacity() * ship.getLoad(); //总吨位 = 总吨位 + 当前的可排船ship的吨位
                        }
                        if(s != null && s.size() != 0){ //检查装载吨数的限制
                            for(Ship c : s){  //得到已经装载进去的船的总吨位
                                total = total + c.getCapacity() * c.getLoad();
                            }
                            total = total + ship.getCapacity() * ship.getLoad(); //总吨位 = 总吨位 + 当前的可排船ship的吨位
                        }
                        if(total > shipLockCategory.getCapacity()) {   //大于了装载吨位数
                            break;
                        }
                    }


                    ship.setX(location[0]);   //该船在闸室停泊位置的x轴坐标
                    ship.setY(location[1]);    //该船在闸室停泊位置的y轴坐标
                    packedShips.add(ship);

                    shipLock.setCategory(shipLockCategory);
                    shipLock.setShips(packedShips);

                    Double[] location1 = new Double[]{location[0] + ship.getLength(), location[1]}; //新产生的可排点坐标1
                    Double[] location2 = new Double[]{location[0], location[1] + ship.getWidth()}; //新产生的可排点坐标2
                    packLocations.add(location1);
                    packLocations.add(location2);
                    packLocations.remove(location);
                    packLocations = _sortLocations(packLocations);  //每放进去一个船，可排点发生改变，需要重新生成可排点并排序
                    break;
                }
            }

        }

        if(shipLock == null ){
            return null;
        }
        if(shipLock.getShips() == null ){
            return null;
        }
        else {
            double useRatio = NormalUtil.getUseRatio(shipLock);
            if(useRatio > 1.0){
                System.out.println("budui");
            }
            shipLock.setUseRatio(useRatio);
        }
        return shipLock;
    }

    /*对可排点集进行排序，x轴值越小排在前，x相等时，y轴值越小排在前*/
    private List<Double[]> _sortLocations(List<Double[]> packLocations) {
        //sort
        IdentityHashMap<Double, Double> sortMap = new IdentityHashMap<Double, Double>();
        for (Double[] l : packLocations) {
            double x = l[0];
            double y = l[1];
            sortMap.put(x, y);
        }

        List<Map.Entry<Double, Double>> list = new ArrayList<>(sortMap.entrySet());

        Collections.sort(list, new Comparator<Map.Entry<Double, Double>>() {
            @Override
            public int compare(Map.Entry<Double, Double> o1, Map.Entry<Double, Double> o2) {
                if (o1.getKey() < o2.getKey()) {
                    return -1;
                } else if (o1.getKey() > o2.getKey()) {
                    return 1;
                } else if (o1.getKey() == o2.getKey()) {
                    if (o1.getValue() < o2.getValue()) {
                        return -1;
                    } else return 1;
                }
                return 0;
            }
        });


        ArrayList<Double[]> lst = new ArrayList<Double[]>();
        for (int i = 0; i < list.size(); i++) {
            Map.Entry<Double, Double> entey = (Map.Entry<Double, Double>) list.get(i);
            Double x = entey.getKey();
            Double y = entey.getValue();
            Double[] arr = new Double[]{x, y};
            lst.add(arr);
        }
        return lst;
    }

    /*该方法仅仅验证给定的条件下能否排挡
    * 输入
    *   ship：待排挡的船
    *   location：可排点
    *   packedShips： 该船闸已经排挡的船
    *   shipLockCategory：船闸的信息
    * 输出：
    *   true/false: 该船是否能排下
    * */
    private boolean _isPackable(Ship ship, Double[] location, List<Ship> packedShips, ShipLockCategory shipLockCategory) {

        boolean packable = true;

        double W = shipLockCategory.getWidth();  //船闸的宽
        double L = shipLockCategory.getLength(); //船闸的长
        double A = W * L;                        //船闸的面积

        if(ship.getWidth() >= W) return false;
        if(ship.getLength() >= L) return false;

        boolean res = shipLockCategory.isInwaterRestricted(); //该船闸是否有吃水的限制
        double inWaterRes = 0;
        if(res){
            inWaterRes = shipLockCategory.getInwater();
        }

        double sum = 0.0;
        for (Ship s : packedShips) {
            double a = s.getLength() * s.getWidth();
            sum = sum + a;
        }

        double xLeni = location[0];    //可排点的x坐标轴
        double yWidi = location[1];    //可排点的y坐标轴

        double leni = ship.getLength();  //待排挡船的长
        double widi = ship.getWidth();   //待排挡船的宽

        if (res && ship.getLoad() * ship.getMaxInWater() > inWaterRes) { //该船闸有吃水限制并且这条船的吃水已经超过了限制
            packable = false;
        }
        else {
            for (Ship packedShip : packedShips) {  //对每个已排挡的船遍历

                boolean constrain1 = true;  //面积约束条件
                boolean constrain2 = true;  //约束：不能排挡排到船闸外面
                boolean constrain3 = true;  //约束：不重叠

                double lenj = packedShip.getLength();  //已排挡船的长
                double widj = packedShip.getWidth();   //已排挡船的宽
                double xLenj = packedShip.getX();      //已排挡船的停泊位置的X
                double yWidj = packedShip.getY();      //已排挡船的停泊位置的Y

                if (A - sum <= leni * widi) {
                    constrain1 = false;
                } else if (xLeni + leni > L || yWidi + widi > W) {
                    constrain2 = false;
                } else if (!(xLeni + leni <= xLeni ||
                        xLeni >= xLenj + lenj ||
                        yWidi + widi <= yWidj ||
                        yWidi >= yWidj + widj)) {
                    constrain3 = false;
                }

                if (!(constrain1 && constrain2 && constrain3)) {
                    packable = false;
                    break;
                }
            }
        }
        return packable;
    }

    /*找到使得某一个维度（长或者宽）上利用率最大的船只组合（并且保证他们的等待时间是可能解中最长的）
    * 若没有找到，则返回一个空的List<Ship>
    * @param: lockCategory:船闸的类型（需要吃水限制的参数）， flag：排序的维度（"width" 或者 "length"）, sum:排序维度的最大值, restrictType：另一个维度的限制（"width" 或者 "length"）， restrict：限制的数值
    * @return: 在某一维度使其达到最大利用率的船舶组合
    *
    */
    private List<Ship> _findShips4MaxSideUse(ShipLockCategory lockCategory, String flag, double sum, String restrictType, double restrict, List<Ship> ships) {
        List<Ship> shipsAtMaxSide = new ArrayList<Ship>();

        /*找到所有可能的和的集合，为了方便循环 TODO：只考虑两个船的和，没有考虑三个及以上船的情况*/
        TreeMap sumMap = new TreeMap<>(new Comparator() {
            @Override
            public int compare(Object o1, Object o2) {
                double d1  =(double) o1;
                double d2  =(double) o2;
                if(d1 <= d2) return 1;
                else return -1;
            }
        });

        double res = 0;  //船在restrictType维度上的长度；限定条件：res < restrict

        for (int i = 0; i < ships.size(); i++) {
            double value1 = 0;
            try {
                Field field = ships.get(i).getClass().getDeclaredField(flag);
                field.setAccessible(true);
                value1 = (Double) field.get(ships.get(i)); //这个船的长 或者 宽
            } catch (Exception e) {
                e.printStackTrace();
            }
            for(int j = i ; j < ships.size(); j++){
                double value2 = 0;
                if(j + 1 == ships.size()){
                    break;
                }
                else {
                    int k = j + 1;
                    try {
                        Field field = ships.get(k).getClass().getDeclaredField(flag);
                        field.setAccessible(true);
                        value2 = (Double) field.get(ships.get(k));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    try {  //获得restrictType的值赋值给res
                        Field field = ships.get(k).getClass().getDeclaredField(restrictType);
                        field.setAccessible(true);
                        res = (Double) field.get(ships.get(k));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                double tmp = value1 + value2;
                if(lockCategory.isInwaterRestricted() && tmp < sum && res < restrict){ //如果该船闸有吃水的限制，则每条船需要满足： 吃水深度 < 吃水限制
                    double inWaterRes = lockCategory.getInwater();
                    double ship1Inwater = ships.get(i).getMaxInWater() * ships.get(i).getLoad();
                    double ship2Inwater = ships.get(j+1).getMaxInWater() * ships.get(j+1).getLoad();
                    if(ship1Inwater < inWaterRes && ship2Inwater < inWaterRes){  //两条船都满足吃水的限制
                        List<Ship> list = new ArrayList<Ship>();
                        list.add(ships.get(i));
                        list.add(ships.get(j+1));
                        sumMap.put(tmp, list);
                    }
                }
                else {  //该船闸没有吃水的限制
                    if(tmp < sum && res < restrict){
                        List<Ship> list = new ArrayList<Ship>();
                        list.add(ships.get(i));
                        list.add(ships.get(j+1));
                        sumMap.put(tmp, list);
                    }
                }
            }
        }

        for(Ship ship : ships){   //船只本身的长度也要考虑
            double value = 0;
            try {
                Field field = ship.getClass().getDeclaredField(flag);
                field.setAccessible(true);
                value = (Double) field.get(ship); //这个船的长 或者 宽
            } catch (Exception e) {
                e.printStackTrace();
            }
            if(value <= sum){
                List<Ship> a = new ArrayList<Ship>();
                a.add(ship);
                sumMap.put(value, a);
            }
        }

//        if(sumMap.size() != 0){
//            List<Map.Entry<Double, List<Ship>>> list = new ArrayList<Map.Entry<Double, List<Ship>>>(sumMap.entrySet());
//            Map.Entry<Double, List<Ship>> entry = list.get(0);
//            double d = entry.getKey(); //可能和中的最大值，即是要找的能在该维度上组合的最大值
//            shipsAtMaxSide = entry.getValue();
//        }

        double k = 0;
        try {
            Field field = lockCategory.getClass().getDeclaredField(flag);
            field.setAccessible(true);
            k = (Double) field.get(lockCategory); //这个船闸的长 或者 宽
        } catch (Exception e) {
            e.printStackTrace();
        }

        Iterator iter = sumMap.entrySet().iterator();
        while(iter.hasNext()){
            Map.Entry<Double, List<Ship>> entry = (Map.Entry<Double, List<Ship>>)iter.next();
            if(entry.getKey() < k){
                shipsAtMaxSide = entry.getValue();
                break;
            }
        }
        return shipsAtMaxSide;
    }

    private List<Ship> _findShipWithLongestWaitTime(List<Double> combination, List<Ship> ships) {

        List<Ship> returnShips = new ArrayList<Ship>();

        List<Ship> copyShips = new ArrayList<Ship>();
        copyShips.addAll(ships);

        IdentityHashMap<Date, Ship> tmp = new IdentityHashMap<Date, Ship>();

        for (Double d : combination) {  //对组合中的每一个元素遍历
            tmp.clear();
            for (Ship ship : copyShips) {   //对船遍历，找到那些长(/宽)等于元素的船
                double width = ship.getWidth();
                if (d == width) {
                    tmp.put(TimeUtils.str2Date(ship.getArrivalDatetime()), ship);
                }
            }
            if (tmp.size() != 0) {
                List<Map.Entry<Date, Ship>> list = null;
                list = new ArrayList<Map.Entry<Date, Ship>>(tmp.entrySet());
                Collections.sort(list, new Comparator<Map.Entry<Date, Ship>>() {
                    @Override
                    public int compare(Map.Entry<Date, Ship> o1, Map.Entry<Date, Ship> o2) {
                        if (o1.getKey().before(o2.getKey())) {
                            return -1;
                        } else return 1;
                    }
                });
                Ship s = list.get(0).getValue();
                returnShips.add(s);
                copyShips.remove(s);
            }
        }

        return returnShips;
    }


    public static void main(String[] args) {
        List<Double[]> list = new ArrayList<Double[]>();
        Double[] d1 = {0.0, 16.3};
        Double[] d2 = {0.0, 13.3};
        Double[] d3 = {117.0, 13.3};
        Double[] d4 = {117.0, 12.3};

        list.add(d1);
        list.add(d2);
        list.add(d3);
        list.add(d4);
        list = new PackerImpl()._sortLocations(list);

//        System.out.println(new PackerImpl()._sortLocations(list));
    }


}
