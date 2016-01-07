package com.schinta.shiplock.Util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 数组中任意元素和为定值的组合,返回不重复的组合列表
 */
public class SolveProb {
    // Arrays.sort(arr);
    Double[] flag = new Double[1000];
    int index = 0;// 记录当前
    List k = new ArrayList();  //返回的list

    public List<List<Double>> numGroup(Double[] arr, int start, int length, double sum) {

        if (sum == 0) {
            ArrayList c = new ArrayList();
            for (int j = 0; j < index; j++) {
//                System.out.print(flag[j] + " ");
                c.add(flag[j]);
            }
            /*判断之前的组合是否有重复的，若没有，将新的组合加入k*/
//            ArrayList tmp = null;
//            if(0 == k.size()){
//                k.add(c);
//            }
//            tmp = (ArrayList) k.get(k.size()-1);
//            if (tmp.size() != c.size()) {
//                for (int i = 0; i < tmp.size(); i++) {
//                    if (tmp.get(i) != c.get(i)) {
//                        k.add(c);
//                        break;
//                    }
//                }
//            }
//            System.out.println();
        } else {
            for (int i = start; i < length; i++) {
                flag[index++] = arr[i];
                this.numGroup(arr, i + 1, length-1, sum - arr[i]);
            }
        }
        index--;

        return k;
    }

    public static void main(String[] args) {
        Double[] arr = { 10.0, 14.0, 13.0, 11.0, 13.6, 11.0,11.0, 14.0, 15.0,12.0, 13.4 };
//        Double[] arr = { 16.3, 16.3};
        Arrays.sort(arr);
//        System.out.println(arr);
        double sum = 32.6;
        List a = new SolveProb().numGroup(arr, 0, arr.length, sum);
        System.out.println(a);
    }
}