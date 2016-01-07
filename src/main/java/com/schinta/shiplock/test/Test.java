package com.schinta.shiplock.test;

/**
 * Created by wangyuannan on 2015/12/24.
 */

import sun.reflect.generics.tree.Tree;

import java.util.*;

/**
 * 列举数组中任意元素和为定值的组合
 */
public class Test {



    public static void main(String[] args) {
        double[] arr = { 16.3, 16.3};
        Arrays.sort(arr);
        double sum = 32.6;

        TreeMap map  =new TreeMap<>(new Comparator() {
            @Override
            public int compare(Object o1, Object o2) {
                int i1 = (int)o1;
                int i2 = (int)o2;
                if(i1 < i2){return 1;}
                else return -1;
            }
        });
        map.put(1,"dsds");
        map.put(2,"dsds");
        map.put(3,"dsds");
        map.put(1,"dsds");
        map.put(2,"dsds");
        System.out.println(map.containsKey(1));

    }
}
