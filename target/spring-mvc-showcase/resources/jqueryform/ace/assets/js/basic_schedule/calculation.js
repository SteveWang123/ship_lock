/**
 * Created by wangyuannan on 2015/12/9.
 */


/*echart图表js库引入*/
require.config({
    paths: {
        echarts: "../resources/ace/assets/js/echart/dist"
    }
});


const yidun2dun = 100000000;

getRandomT = function(i){
    var t_max = parseInt(T_max[i]);
    var t_min = parseInt(T_min[i]);
    var random = Math.random();
    var T = t_min + (t_max - t_min) * random;
    return T;
};

collectionParams = function(){

    /*开始获取表单的值*/
    total = $("#total").val();  //每年需要运过去的货物总量（亿吨）
    unstable = $("#unstable").val();  //不均衡系数
    days = $("#days").val();   //每年的工作天数
    T_max = [];                    //每种船的吨位上限
    T_min = [];                    //每种船的吨位下限
    loads = [];                //每个月的负载系数
    p_month = [];              //船艘次按月占比
    p_chuan = [];              //船型的分布
    length_max = [];              //船只长度最大值
    length_min = [];              //船只长度最小值
    width = [];              //船只的宽度
    p_chuan = [];              //船型的分布
    chuan_name = ['长江水系货-24','长江水系货-29','长江水系货-35','长江水系货-36','长江水系货-37','长江水系集-14'
        ,'长江水系货-18','长江水系货-19'];         //有几种不同的船  TODO:前台改成select控件，然后获取表单中船的name

    $("input[name='T_min']").each(function(){
        T_min.push($(this).val());
    });

    $("input[name='T_max']").each(function(){
        T_max.push($(this).val());
    });

    $("input[name='load']").each(function(){
        loads.push($(this).val());
    });

    $("input[name='p_per_month']").each(function(){
        var p = ($(this).val());
        p = p.substring(0, p.length-1);
        p = p/100.0;
        p = Math.round(p *100)/100;
        p_month.push(p);
    });

    $("input[name='p_per_chuan']").each(function(){
        var p = ($(this).val());
        p = p/100.0;
        p_chuan.push(p);
    });

    $("input[name='length_max']").each(function(){
        var p = ($(this).val());
        length_max.push(p);
    });

    $("input[name='length_min']").each(function(){
        var p = ($(this).val());
        length_min.push(p);
    });

    $("input[name='width']").each(function(){
        var p = ($(this).val());
        width.push(p);
    });
    /*结束获取表单的值*/


    /*根据公式计算每年的船数-N*/
    sum = 0.0;
    for(var i = 0; i< loads.length; i++){

        for (j = 0; j< p_chuan.length; j++){
            var tmp = p_chuan[j] * getRandomT(j) * p_month[i] * loads[i];
            sum = sum + tmp;
        }
    }

    showMessage(sum);

    N_per_year = Math.ceil(total * yidun2dun/sum);  /*total的单位是亿吨*/
    var N_per_month = Math.ceil(N_per_year/12);
    var N_per_day = Math.ceil(N_per_year/days);   //平均每天的船只数量
    /*N的计算结束*/

    /*开始准备结果展现的数据*/
    var data = []; //存放各种船型的名称，月总量，按月分布
    for(var i = 0; i< chuan_name.length; i++){
        var chuan_i = {};
        chuan_i['name'] = chuan_name[i];
        chuan_i['count_by_month'] = Math.ceil(N_per_year * p_chuan[i]);

        chuan_i['distribution_by_month'] = [];
        for(var j = 0; j < p_month.length; j++){
            var temp = Math.ceil(chuan_i['count_by_month'] * p_month[j]);
            chuan_i['distribution_by_month'].push(temp);
        }

        data.push(chuan_i);
    }

    showMessage(data);

    var chart1_title_name = '船只数量按类型分布图'; //图表1的title
    var chart1_legend_data = chuan_name;  ////图表的图例数据
    var chart1_series_data = []; //饼图数据

    for(var i = 0 ;i<chuan_name.length; i++){
        var item= {};
        item['name'] = chuan_name[i];
        item['value'] = Math.ceil(p_chuan[i] * N_per_year);
        chart1_series_data.push(item);
    }


    var chart2_title_name = '船只数量按月按类型分布图'; //图表1的title
    var chart2_legend_data = chuan_name;  ////图表的图例数据
    var chart2_x_axis_data = ['1月','2月','3月','4月','5月','6月','7月','8月','9月','10月','11月','12月'];  //x轴数据

    var chart2_series_data = []; //Y轴数据
    for(var i=0; i<data.length; i++){
        var item= {};
        item['name'] = data[i]['name'];
        item['type'] = 'bar';
        item['data'] = data[i]['distribution_by_month'];
        chart2_series_data.push(item);
    }
    /*结果展现数据结束*/

    /*开始echart画图*/
    require(   //chart1
        [
            'echarts',
            'echarts/chart/pie',
        ],
        function (ec) {
            // 基于准备好的dom，初始化echarts图表
            var myChart1 = ec.init(document.getElementById('chart1'));

            option1 = {
                title : {
                    text: chart1_title_name,
                    x:'center'
                },
                tooltip : {
                    trigger: 'item',
                    formatter: "{a} <br/>{b} : {c} ({d}%)"
                },
                legend: {
                    show: false,
                    orient : 'vertical',
                    x : 'left',
                    data:chart1_legend_data
                },
                toolbox: {
                    show : false,
                    feature : {
                        mark : {show: true},
                        dataView : {show: true, readOnly: false},
                        magicType : {
                            show: true,
                            type: ['pie', 'funnel'],
                            option: {
                                funnel: {
                                    x: '25%',
                                    width: '50%',
                                    funnelAlign: 'left',
                                    max: 1548
                                }
                            }
                        },
                        restore : {show: true},
                        saveAsImage : {show: true}
                    }
                },
                calculable : true,
                series : [
                    {
                        name:chart1_title_name,
                        type:'pie',
                        radius : '55%',
                        center: ['50%', '60%'],
                        data: chart1_series_data
                    }
                ]
            };

            myChart1.setOption(option1);   // 为echarts对象加载数据
        });

    require(   //chart2
        [
            'echarts',
            'echarts/chart/line',
            'echarts/chart/bar'
        ],
        function (ec) {
            // 基于准备好的dom，初始化echarts图表
            var myChart2 = ec.init(document.getElementById('chart2'));

            option2 = {
                title : {
                    text: chart2_title_name
                },
                tooltip : {
                    trigger: 'axis'
                },
                legend: {
                    data: chart2_legend_data,
                    x: 'center',
                    y: 'bottom'
                },
                toolbox: {
                    show : false,
                    feature : {
                        mark : {show: true},
                        dataView : {show: true, readOnly: false},
                        magicType : {show: true, type: ['line', 'bar']},
                        restore : {show: true},
                        saveAsImage : {show: true}
                    }
                },
                calculable : true,
                xAxis : [
                    {
                        type : 'category',
                        data : chart2_x_axis_data
                    }
                ],
                yAxis : [
                    {
                        type : 'value'
                    }
                ],
                series : chart2_series_data
            };

            myChart2.setOption(option2);   // 为echarts对象加载数据
        });
    /*结束画图*/


    /*展现数据*/
    $("#result_div #result_N_per_year").text(N_per_year);
    $("#result_div #result_N_per_month").text(N_per_month);
    $("#result_div #result_N_per_day").text(N_per_day);
    /*展现数据结束*/

    $("#result_div").attr('hidden', false);

    /*开始计算装箱*/
    doPacking();
    /*装箱计算结束*/
};
