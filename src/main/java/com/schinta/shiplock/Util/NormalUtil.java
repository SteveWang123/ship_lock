package com.schinta.shiplock.Util;

import com.schinta.shiplock.model.ship.Ship;
import com.schinta.shiplock.model.shipLock.ShipLock;

import java.util.*;

/**
 * Created by wangyuannan on 2015/12/21.
 */
public class NormalUtil {

    /*求list的和*/
    public static double sum(List<Double> list){
        double sum = 0.0;
        for (double a : list) {
            sum = sum + a;
        }
        return sum;
    }


    /*根据日期找到某一天的船序列*/
    public static List<Ship> findSeriesByDay(List<Ship> sequence, String date){
        ArrayList<Ship> ships = new ArrayList<Ship>();
        for (Ship s : sequence){
            if (date.equalsIgnoreCase(s.getArrivalDatetime().split(" ")[0])){
                ships.add(s);
            }
        }
        return ships;
    }


    /*根据时间（yyyy-MM-dd HH:mm）找到截止当前时间，没有过闸的船只list*/
    public static List<Ship> findShipsTillTime(List<Ship> sequence, String now){
        Date date1,date2 = null;
        date1 = TimeUtils.str2Date(now);
        ArrayList<Ship> ships = new ArrayList<Ship>();

        for(Ship s : sequence){
//            if(s.isMoved())continue;  //若已经过闸
            date2 = TimeUtils.str2Date(s.getArrivalDatetime());
            if(date2.before(date1)){
                ships.add(s);
            }
            else break;
        }
        return ships;
    }


    /*根据一个船闸实体类，计算其利用面积
    * 如果这个闸室没有排船，则返回0.0
    * */
    public static double getUseRatio(ShipLock sl){
        List<Ship> ships = sl.getShips();
        if(ships == null || ships.size() == 0){
            return 0.0;
        }
        double outerWid = sl.getCategory().getOuterWidth();
        double outerLen = sl.getCategory().getOuterLength();
        double s = outerLen * outerWid;

        double sum = 0.0;
        for(Ship ship : ships){
            sum = sum + ship.getWidth() * ship.getLength();
        }
        double k = sum / s;
        return k;
    }


    /*将船的序列排序，按照来船的时间排序*/
    public static List<Ship> sortShips(List<Ship> sequence){
        IdentityHashMap<Date, Ship> sortMap = new IdentityHashMap<Date, Ship>();

        for(Ship ship : sequence){
            Date arrTime = TimeUtils.str2Date(ship.getArrivalDatetime());
            sortMap.put(arrTime, ship);
        }

        List<Map.Entry<Date, Ship>> list = new ArrayList<Map.Entry<Date, Ship>>(sortMap.entrySet());
        Collections.sort(list, new Compare());

        List<Ship> ships = new ArrayList<Ship>();
        for(Map.Entry<Date, Ship> entry : list){
            ships.add(entry.getValue());
        }

        return ships;
    }

}

/*按照时间先后排序的实现*/
class Compare implements Comparator{

    @Override
    public int compare(Object o1, Object o2) {
        Map.Entry<Date, Ship> ship1 = (Map.Entry<Date, Ship>)o1;
        Map.Entry<Date, Ship> ship2 = (Map.Entry<Date, Ship>)o2;

        Date d1 = TimeUtils.str2Date(ship1.getValue().getArrivalDatetime());
        Date d2 = TimeUtils.str2Date(ship2.getValue().getArrivalDatetime());
        int i = 1;
        if(d1.before(d2)){
            i = -1;
        }
        return i;
    }
}
