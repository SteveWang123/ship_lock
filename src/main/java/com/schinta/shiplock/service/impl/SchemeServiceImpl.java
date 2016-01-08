package com.schinta.shiplock.service.impl;

import com.schinta.shiplock.dao.SchemeDao;
import com.schinta.shiplock.model.Scheme.Scheme;
import com.schinta.shiplock.model.shipLock.ShipLock;
import com.schinta.shiplock.service.SchemeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Created by wangyuannan on 2016/1/4.
 */
@Service
public class SchemeServiceImpl  implements SchemeService {

    static private Logger logger=LoggerFactory.getLogger(SchemeServiceImpl.class);

    @Autowired
    private SchemeDao schemeDao;

    @Transactional
    @Override
    public void saveScheme(Scheme scheme, List<ShipLock> shipLockList) {
        long schemeId = schemeDao.insertScheme(scheme);
        schemeDao.insertPmonth(schemeId, scheme);
        schemeDao.insertShipLock(schemeId, shipLockList);
        List<Map<String, Object>> ids = schemeDao.queryShipLockIdsByScheme((int) schemeId);
        schemeDao.insertShip(schemeId, ids, shipLockList);
    }

    @Override
    public List<Scheme> getSchemes(int cnt) {
        List<Map<String, Object>> list = schemeDao.querySchemeList();
        List<Map<String, Object>> returnList = new ArrayList<Map<String, Object>>();
        List<Integer> schemeIds = new ArrayList<>();
        for (Map<String, Object> map : list) {
            int schemeId = (int) map.get("scheme_id");
            if (!schemeIds.contains(schemeId)) {
                schemeIds.add(schemeId);
            }
            if (schemeIds.size() == cnt + 1) break;
            returnList.add(map);
        }

        List<Integer> ids = new ArrayList<>();
        List<Map<String, Object>> data = new ArrayList<>();
        for (Map<String, Object> map : returnList) {
            int schemeId = (int) map.get("scheme_id");
            if (!ids.contains(schemeId)) {
                Map<String, Double> pmonth = new HashMap<String, Double>();
                for (Map<String, Object> map1 : returnList) {
                    if ((int) map1.get("scheme_id") == schemeId) {
                        pmonth.put((String) map1.get("month"), (Double) map1.get("ratio"));
                    }
                }
                map.put("pmonth", pmonth);
                map.remove("month");
                map.remove("ratio");
                data.add(map);
                ids.add(schemeId);
            }
        }

        List<Scheme> schemes = new ArrayList<>();
        for (Map<String, Object> map : data) {
            Scheme scheme = new Scheme();
            scheme.setId((int) map.get("scheme_id"));
            scheme.setName((String) map.get("scheme_name"));
            scheme.setStartDay((String) map.get("start_day"));
            scheme.setEndDay((String) map.get("end_day"));
            scheme.setTotal((Double) map.get("total"));
            scheme.setCreateTime((String) map.get("create_time"));
            scheme.setpByMonth((HashMap<String, Double>) map.get("pmonth"));
            schemes.add(scheme);
        }

        logger.debug(schemes.toString());
        return schemes;
    }

    @Override
    public List<Map<String, Object>> getDftPmonth() {
        return schemeDao.queryDftPmonth();
    }
}
