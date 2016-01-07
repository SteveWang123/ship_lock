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
import org.springframework.web.bind.annotation.RequestMapping;

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

    @RequestMapping("/calculation")
    public String calculationHome(ModelMap map){
        List<Map<String, Object>> shipCategories = shipService.getShipCategories();
        logger.debug("船类型有：" + shipCategories.size() + "个");

        map.put("shipCategories", shipCategories);
        return "shiplockviews/calculation";
    }


}
