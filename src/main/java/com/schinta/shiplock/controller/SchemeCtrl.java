package com.schinta.shiplock.controller;

import com.schinta.shiplock.model.Scheme.Scheme;
import com.schinta.shiplock.model.shipCategory.ShipCategory;
import com.schinta.shiplock.service.SchemeService;
import com.schinta.shiplock.service.ShipService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * Created by wangyuannan on 2016/1/7.
 */
@Controller
public class SchemeCtrl {

    static public Logger logger = LoggerFactory.getLogger(SchemeCtrl.class);

    @Autowired
    private SchemeService schemeService;

    @Autowired
    private  ShipService shipService;

    /*系统的主界面*/
    @RequestMapping("/")
    public String schemeHome(ModelMap map){
        List<Scheme> schemes = schemeService.getSchemes(10);
        map.put("schemes", schemes);
        return "shiplockviews/home";
    }

    /*开始计算，新建方案*/
    @RequestMapping("/calculation")
    public String calculationHome(ModelMap map){
        List<Map<String, Object>> shipCategories = shipService.getDftShipCategories();
        logger.debug("船类型有：" + shipCategories.size() + "个");

        /*将ratio的小数点保留三位*/
        for(Map<String, Object> tmp : shipCategories){
            float a = (float)tmp.get("ratio");
            BigDecimal b = new BigDecimal(a);
            double d = b.setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue();
            tmp.put("ratio", d);
        }
        map.put("shipCategories", shipCategories);

        List<Map<String, Object>> pmonths = schemeService.getDftPmonth();
        /*将艘次按月占比的ratio小数点保留三位*/
        for(Map<String, Object> tmp : pmonths){
            double a = (double)tmp.get("ratio");
            BigDecimal b = new BigDecimal(a);
            double d = b.setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue();
            tmp.put("ratio", d);
        }

        map.put("pmonths", pmonths);

        return "shiplockviews/calculation";
    }





}
