/******************************************************************************

 This is a demo page to experiment with binary tree based
 algorithms for packing blocks into a single 2 dimensional bin.

 See individual .js files for descriptions of each algorithm:

 * packer.js         - simple algorithm for a fixed width/height bin
 * packer.growing.js - complex algorithm that grows automatically

 TODO
 ====
 * step by step animated render to watch packing in action (and help debug)
 * optimization - mark branches as "full" to avoid walking them
 * optimization - dont bother with nodes that are less than some threshold w/h (2? 5?)

 *******************************************************************************/
fit = [];
nofit = [];
ratio = '';

fit_history = []; //存放每一次摆放的信息  map类型的item： {seq: 1, fitted_item: []}

Demo = {

    init: function () {

        Demo.el = {
            blocks: [],
            size: $('#size'),
            sort: $('#sort'),
        };

        Demo.el.blocks = Demo.blocks._2List(Demo.blocks.examples.current());
        Demo.run();
    },

    //---------------------------------------------------------------------------

    run: function () {

        var blocks = Demo.el.blocks;
        var packer = Demo.packer();

        Demo.sort.now(blocks);

        packer.fit(blocks);

        Demo.report(blocks, packer.root.w, packer.root.h);
    },

    //---------------------------------------------------------------------------

    packer: function () {
        var size = Demo.el.size.val();
        var size = '34x280';
        if (size == 'automatic') {
            return new GrowingPacker();
        }
        else {
            var dims = size.split("x");
            return new Packer(parseInt(dims[0]), parseInt(dims[1]));
        }
    },

    //---------------------------------------------------------------------------

    report: function (blocks, w, h) {
        var fittedArea = 0, block, n, len = blocks.length;
        /*需要将全局变量fit nofit 和ratio 清零*/
        fit = [];
        nofit = [];
        ratio = '';
        for (n = 0; n < len; n++) {
            block = blocks[n];
            if (block.fit) {
                fit.push(block);
                fittedArea = fittedArea + block.area;
            }
            else
                nofit.push(block);
        }

        ratio = Math.round(100 * fittedArea / (w * h));
    },

    //---------------------------------------------------------------------------

    sort: {

        random: function (a, b) {
            return Math.random() - 0.5;
        },
        w: function (a, b) {
            return b.w - a.w;
        },
        h: function (a, b) {
            return b.h - a.h;
        },
        a: function (a, b) {
            return b.area - a.area;
        },
        max: function (a, b) {
            return Math.max(b.w, b.h) - Math.max(a.w, a.h);
        },
        min: function (a, b) {
            return Math.min(b.w, b.h) - Math.min(a.w, a.h);
        },
        height: function (a, b) {
            return Demo.sort.msort(a, b, ['h', 'w']);
        },
        width: function (a, b) {
            return Demo.sort.msort(a, b, ['w', 'h']);
        },
        area: function (a, b) {
            return Demo.sort.msort(a, b, ['a', 'h', 'w']);
        },
        maxside: function (a, b) {
            return Demo.sort.msort(a, b, ['max', 'min', 'h', 'w']);
        },

        msort: function (a, b, criteria) { /* sort by multiple criteria */
            var diff, n;
            for (n = 0; n < criteria.length; n++) {
                diff = Demo.sort[criteria[n]](a, b);
                if (diff != 0)
                    return diff;
            }
            return 0;
        },

        now: function (blocks) {
            var sort = 'area';
            if (sort != 'none')
                blocks.sort(Demo.sort[sort]);
        }
    },

    //---------------------------------------------------------------------------

    blocks: {

        examples: {

            simple: [
                {w: 15, h: 24, num: 72},
                {w: 5, h: 30, num: 25},
                {w: 15, h: 20, num: 19},
                {w: 5, h: 10, num: 21},
                {w: 15, h: 5, num: 40}
            ],

            current: function () {
                return Demo.blocks.examples['simple'];
            }

        },

        /*返回一个list，存放的是解序列化的object。 e.g. [{area:10000, h:200, w:500}, {},...]*/
        _2List: function (simple) {

            var returnList = [];
            for (var i = 0; i < simple.length; i++) {
                var num = simple[i]['num'];
                var h = simple[i]['h'];
                var w = simple[i]['w'];
                var name = simple[i]['name'];
                var T_min = simple[i]['T_min'];
                var T_max = simple[i]['T_max'];
                var area = h * w;
                for (var j = 0; j < num; j++) {
                    var item = {};
                    item['area'] = area;
                    item['h'] = h;
                    item['w'] = w;
                    item['name'] = name;
                    item['T_min'] = T_min;
                    item['T_max'] = T_max;
                    returnList.push(item);
                }
            }
            return returnList;
        }

    },

};

function reformat(nofit) {

    var returnList = [];

    var w = nofit[0].w;
    var h = nofit[0].h;
    var name = nofit[0].name;
    var T_min = nofit[0].T_min;
    var T_max = nofit[0].T_max;

    var firstItem = {};

    firstItem['w'] = w;
    firstItem['h'] = h;
    firstItem['num'] = 1;
    firstItem['name'] = name;
    firstItem['T_min'] = T_min;
    firstItem['T_max'] = T_max;

    returnList.push(firstItem);


    for (var i = 1; i < nofit.length; i++) {
        var flag = true;
        var w = nofit[i].w;
        var h = nofit[i].h;
        var name = nofit[i].name;
        var T_min = nofit[i].T_min;
        var T_max = nofit[i].T_max;

        for (var j = 0; j < returnList.length; j++) {
            if (returnList[j].w == w && returnList[j].h == h) {
                returnList[j].num++;
                flag = false;
                break;
            }
        }
        if (flag) {
            var anotherItem = {};
            anotherItem['w'] = w;
            anotherItem['h'] = h;
            anotherItem['num'] = 1;
            anotherItem['name'] = name;
            anotherItem['T_max'] = T_max;
            anotherItem['T_min'] = T_min;
            returnList.push(anotherItem);
        }
    }

    return returnList;

};

function doPacking() {

    /*先初始化Demo.blocks.examples.simple*/
    var N_this_month = Math.ceil(N_per_year * p_month[new Date().getMonth()]); //计算当前月的来船数量
    N_per_day_this_month = Math.ceil(N_this_month / 30);          //计算当前月平均每天的来船数量
    initSimple = [];
    for (var i = 0; i < p_chuan.length; i++) {
        N = Math.ceil(p_chuan[i] * N_per_day_this_month);
        item = {};
        item['w'] = width[i];
        item['h'] = length_max[i];
        item['num'] = N;
        item['name'] = chuan_name[i];
        item['T_min'] = T_min[i];
        item['T_max'] = T_max[i];
        initSimple.push(item);
    }
    Demo.blocks.examples.simple = initSimple;
    /*Demo.blocks.examples.simple初始化结束*/


    for (var count = 0 /*装箱的总次数*/; ;) {

        if (count == 0) { //第一次准备装箱时候启动装箱
            Demo.init();  //装箱操作
            count++;//装箱的次数+1
        }

        if (nofit.length > 0) { //表示还有对象没有放进去
            var item = {};
            item['ratio'] = ratio;
            item['seq'] = count;
            item['fitted_item'] = fit;
            fit_history.push(item);

            /*用没有放进去的对象nofit对象再次初始化 Demo.blocks.examples.simple*/
            Demo.blocks.examples.simple = reformat(nofit);
            Demo.init(); //装箱操作
            count++;
            continue;
        }
        else {          //表示所有对象已经装箱完毕
            var item = {};
            item['ratio'] = ratio;
            item['seq'] = count;
            item['fitted_item'] = fit;
            fit_history.push(item);
            break;//结束循环
        }
    }
    //console.log(fit_history);

    /*开始画调度计算展示的table*/
    heads = ['调度顺序', '船只组合', '闸室利用率', '总吨位(t)', '船位分布图示'];

    createTable(fit_history.length, heads.length);

    var canvas_divs = $("canvas");
    for(var i = 0; i < fit_history.length ; i++){
        var aCanvas = canvas_divs[i];
        var fit_items = fit_history[i];
        drawGraph(aCanvas, fit_items);
    }

    $("#basic_schedule_div").attr('hidden', false);

    var description = "船闸宽34m，长280m。 该月每日来船" + "\<strong\>"+ N_per_day_this_month +"\<\/strong\>个，每天共需要船闸运行\<strong\>" + fit_history.length + "\<\/strong\>次可以将船只运送通过！";

    $("#description").html(description);
};

function createTable(rowCount, cellCount) {
    var table = $("<table class='table table-striped table-bordered table-hover'>");
    var thead = $("<thead></thead>");
    var tr = $("<tr></tr>");
    tr.appendTo(thead);
    thead.appendTo(table);
    for (var k = 0; k < cellCount; k++) {
        var tb_head = $("<th class='center'>" + heads[k] + "</th>");
        tb_head.appendTo(tr);
    }
    var tbody = $("<tbody></tbody>");
    tbody.appendTo(table);
    for (var i = 0; i < rowCount; i++) {
        var tr = $("<tr></tr>");
        tr.appendTo(tbody);
        for (var j = 0; j < cellCount; j++) {
            if (j == 0) {
                var td = $("<td>" + (i + 1) + "</td>");
                td.appendTo(tr);
            }
            if (j == 1) {
                var td = $("<td>" + serialize2String(prepareContent(fit_history[i])) + "</td>");
                td.appendTo(tr);
            }
            if (j == 2) {
                var td = $("<td>" + fit_history[i].ratio + "%" + "</td>");
                td.appendTo(tr);
            }
            if (j == 3) {
                var td = $("<td>" + getWeightRange2String(fit_history[i]) + "</td>");
                td.appendTo(tr);
            }
            if (j == 4) {
                var td = $("<td name='canvases' style='padding:10px;'><canvas id='canvas'>" +
                    "<div id='unsupported'>Sorry, this example cannot be run because your browser does not support the &lt;canvas&gt; element</div>" +
                    "</canvas></td>");
                td.appendTo(tr);
            }
        }
    }
    table.append("</table>");
    table.appendTo($("#schedule_table"));
};

/*序列化船只组合*/
function prepareContent(obj) {
    var items = obj.fitted_item;
    var returnMap = {};

    for (var i = 0; i < items.length; i++) {
        var item = items[i];
        var name = item.name;

        var flag = true;
        for (var key in returnMap) {
            if (key == name) {
                returnMap[key] = returnMap[key] + 1;
                flag = false;
            }
        }
        if (flag) {
            returnMap[name] = 1;
        }
    }
    return returnMap;
}

/*将map类型转化为string类型*/
function serialize2String(map) {
    var returnString = '';
    for (var key in map) {
        returnString = returnString + key + ": " + map[key] + "\n";
    }
    return returnString;
};

function getWeightRange2String(obj) {
    var items = obj.fitted_item;
    var maxs = [];
    var mins = [];
    for (var i = 0; i < items.length; i++) {
        var item = items[i];
        var max = item.T_max;
        var min = item.T_min;
        maxs.push(max);
        mins.push(min);
    }
    var MAX = 0;
    var MIN = 0;
    for (var j = 0; j < maxs.length; j++) {
        MAX = MAX + parseInt(maxs[j]);
        MIN = MIN + parseInt(mins[j]);
    }

    return MIN + "--" + MAX;

};

function drawGraph(canvas, fit_items){
    var draw = canvas.getContext("2d");
    var fitted_item = fit_items['fitted_item'];
    Canvas.reset(canvas, draw, fitted_item[0].fit.h, fitted_item[0].fit.w); //画出船闸
    Canvas.blocks(canvas, draw, fit_items);                                 //画出每条船的颜色
    Canvas.boundary(canvas, draw, fitted_item[0].fit);                      //画出每条船的边框
};


Canvas = {

    reset: function (canvas,draw, width, height) {
        canvas.width = width + 1; // add 1 because we draw boundaries offset by 0.5 in order to pixel align and get crisp boundaries
        canvas.height = height + 1; // (ditto)
        draw.clearRect(0, 0, canvas.width, canvas.height);
    },

    rect: function (draw, x, y, w, h, color) {   //填充颜色
        draw.fillStyle = color;
        draw.fillRect(x + 0.5, y + 0.5, w, h);
    },

    stroke: function (draw, x, y, w, h) {        //填充边框
        draw.strokeRect(x + 0.5, y + 0.5, w, h);
    },

    blocks: function (canvas, draw, fit_items) {
        var blocks = fit_items.fitted_item;
        var n, block;
        for (n = 0; n < blocks.length; n++) {
            block = blocks[n];
            if (block.fit)
                Canvas.rect(draw, parseFloat(block.fit.y), parseFloat(block.fit.x),
                    parseFloat(block.h), parseFloat(block.w), Canvas.color(n));
        }
    },

    boundary: function (canvas, draw, node) {   //画出每条船的边界
        if (node) {
            Canvas.stroke(draw, parseFloat(node.y), parseFloat(node.x),
                parseFloat(node.h), parseFloat(node.w));
            Canvas.boundary(canvas, draw, node.down);
            Canvas.boundary(canvas, draw,node.right);
        }
    },

    color: function (n) {
        var cols = Canvas.colors['vintage'];
        return cols[n % cols.length];
    },

    colors: {
        pastel: ["#FFF7A5", "#FFA5E0", "#A5B3FF", "#BFFFA5", "#FFCBA5"],
        basic: ["silver", "gray", "red", "maroon", "yellow", "olive", "lime", "green", "aqua", "teal", "blue", "navy", "fuchsia", "purple"],
        gray: ["#111", "#222", "#333", "#444", "#555", "#666", "#777", "#888", "#999", "#AAA", "#BBB", "#CCC", "#DDD", "#EEE"],
        vintage: ["#EFD279", "#95CBE9", "#024769", "#AFD775", "#2C5700", "#DE9D7F", "#7F9DDE", "#00572C", "#75D7AF", "#694702", "#E9CB95", "#79D2EF"],
        solarized: ["#b58900", "#cb4b16", "#dc322f", "#d33682", "#6c71c4", "#268bd2", "#2aa198", "#859900"],
        none: ["transparent"]
    }
};




