package com.schinta.shiplock.Util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Created by wangyuannan on 2015/12/17.
 */
public class StatisticsUtil {

    private static final double MINUTES_IN_DAY = 1440.0;

    /*根据一天内的来船数量，返回一个数组，该数组保存了下一艘船到达的时间*/
    /*泊松分布的问题：根据一段时间内事件发生的次数，求下一次发生该事件的时间间隔*/
    public static List shipArravals(int cnt){
        ArrayList<Integer> list;
        if(cnt == 0){
            return new ArrayList();
        }
        if(cnt == 1){
            return new ArrayList(Arrays.asList(new java.util.Random().nextInt(20)));
        }
        else{
            double mean = MINUTES_IN_DAY / cnt;
            list = new ArrayList<Integer>();
            while(true){
                int total = 0;
                list.clear();
                for(int i = 0; i < cnt; i++){
                    int value = _poisson(mean);
                    list.add(_poisson(mean));
                    total = total + value;
                }
                if(total <= MINUTES_IN_DAY){
                    break;
                }
            }
        }

        return list;
    }

    private static int _poisson(double mean) {
        int r = 0;
        Random random = new Random();
        double a = random.nextDouble();
        double p = Math.exp(-mean);

        while (a > p) {
            r++;
            a = a - p;
            p = p * mean / r;
        }
        return r;
    }

    /*根据给定的概率分布列表和波动系数列表，返回一个list，该list中的每个值服从给定条件的正态分布，并且确保元素之和为1
    * 输入：means=[0.7, 0.20, 0.1], fluctuations=[0.09, 0.04, 0.02]
    * 输出：[0.6867687716728869, 0.1706250817177517, 0.1426061466093615]
    */
    public static List<Double> distListByMeanAndFluctuation(List<Double> means, List<Double> fluctuations) throws Exception {
        int l1 = means.size();
        int l2 = fluctuations.size();
        if(l1 != l2){
            throw new Exception("the length of two input parameters are not equal");
        }
        double s = NormalUtil.sum(means);
        if(s < 0.98 || s > 1.01){
            throw new Exception("the sum of means should be 1");
        }

        List<Double> dist = new ArrayList<Double>();  //存返回的随机数组

        boolean goon = true;  //goon表示是否需要再次重新生成新的概率分布列表
        while(goon){
            dist.clear();
            double mean = 0.0;
            double fluctuation = 0.0;
            for (int i = 0; i < means.size(); i++) {
                mean = means.get(i);
                fluctuation = fluctuations.get(i);
                dist.add(normalDistRandomByFluctuation(mean, fluctuation));  //生成一组随机数，存于dist
            }

            int cnt = 0;
            for (double i : dist) {  //校验生成元素是否都是正数
                if (i <= 0) {
                    goon = true;
                    break;
                }
                else{
                    cnt++;
                }
            }
            if(cnt == dist.size()) goon = false;

            if(goon == false){
                double sum = NormalUtil.sum(dist);
                double diff = sum - 1.0;  //求出与1的差
                for (double b : dist) {    //将差平均分配给每一个元素
                    b = b - (double) diff / means.size();
                }

                double sum2 = NormalUtil.sum(dist);
                double diff2 = sum2 - 1.0;  //再次校验并求出与1的差
                dist.set(dist.size() - 1, dist.get(dist.size() - 1) - diff);

                int cnt2 = 0;
                for (double i : dist) {  //校验生成元素是否都是正数
                    if (i <= 0) {
                        goon = true;
                        break;
                    }
                    else{
                        cnt++;
                    }
                }
                if(cnt == dist.size()) goon = false;
            }
        }
        return dist;
    }


    /*根据平均值u和波动系数a,返回一个服从正态分布的随机值，该值满足大于u-a， 小于u+a*/
    public static double normalDistRandomByFluctuation(double u, double a) {

        while(true){
            Random ran = new Random();
            double x = (u + ran.nextGaussian()) / a;
            if(x <= u + a && x >= u -a){
                return x;
            }
        }
    }

    /*递归法求最大公约数*/
    public static int maxCommonDivisor(int m, int n) {
        if (m < n) {// 保证m>n,若m<n,则进行数据交换
            int temp = m;
            m = n;
            n = temp;
        }
        if (m % n == 0) {// 若余数为0,返回最大公约数
            return n;
        } else { // 否则,进行递归,把n赋给m,把余数赋给n
            return maxCommonDivisor(n, m % n);
        }
    }


    public static void main(String[] args) throws Exception {
//        System.out.println(StatisticsUtil._poisson(480.3435));
//        System.out.println(StatisticsUtil.shipArravals(5));
//        System.out.println(StatisticsUtil.normalDistRandomByFluctuation(0.09, 0.01));
//        System.out.println(StatisticsUtil.normalDistRandomByFluctuation(0.082, 0.04));
//        System.out.println(StatisticsUtil.normalDistRandomByFluctuation(0.78, 0.04));
//        List<Double> means = new ArrayList<Double>(Arrays.asList(0.7, 0.20, 0.1));
//        List<Double> fluctuations = new ArrayList<Double>(Arrays.asList(0.09, 0.04, 0.02));
//        System.out.println(distListByMeanAndFluctuation(means, fluctuations));
        System.out.println(maxCommonDivisor(60, 70));
    }

}
