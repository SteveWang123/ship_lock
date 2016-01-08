package com.schinta.shiplock.dao.impl;

import com.schinta.shiplock.dao.AbstractDao;
import com.schinta.shiplock.dao.SchemeDao;
import com.schinta.shiplock.model.Scheme.Scheme;
import com.schinta.shiplock.model.ship.Ship;
import com.schinta.shiplock.model.shipLock.ShipLock;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.*;
import java.util.*;

/**
 * Created by wangyuannan on 2015/12/31.
 */
@Repository
public class SchemeDaoImpl extends AbstractDao implements SchemeDao {

    public SchemeDaoImpl() {
        logger = LoggerFactory.getLogger("SchemeDaoImpl.class");
    }

    @Override
    public long insertScheme(final Scheme scheme) {
        final String sql = "INSERT INTO tb_scheme (scheme_name,`year`,start_day,end_day,total, unpacked_cnt, create_time) VALUES(?,?,?,?,?,?,?)";

        //创建一个主键持有者
        KeyHolder key = new GeneratedKeyHolder();
        getJdbcTemplate().update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement preState = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                preState.setString(1, scheme.getName());
                preState.setString(2, scheme.getYear());
                preState.setString(3, scheme.getStartDay());
                preState.setString(4, scheme.getEndDay());
                preState.setDouble(5, scheme.getTotal());
                preState.setInt(6, scheme.getUnpackedCnt());
                preState.setTimestamp(7, new Timestamp(System.currentTimeMillis()));
                return preState;
            }
        }, key);

        return (long) key.getKey();
    }

    @Override
    public void insertPmonth(long schemeId, Scheme scheme) {

        HashMap<String, Double> pMonth = scheme.getpByMonth();

        String sql = "INSERT INTO tb_pmonth (scheme_id, `month`, ratio) VALUES(?,?,?)";
        List<Object[]> batchArgs = new ArrayList<>();
        Iterator iter = pMonth.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry<String, Double> entry = (Map.Entry<String, Double>) iter.next();
            Object[] args = {schemeId, entry.getKey(), entry.getValue()};
            batchArgs.add(args);
        }
        getJdbcTemplate().batchUpdate(sql, batchArgs);

//        throw new RuntimeException("runtime exception for TEST!");
    }

    @Override
    public int[] insertShipLock(final long schemeId, final List<ShipLock> shipLocks) {
        final String sql = "INSERT INTO tb_shiplock (\n" +
                "  scheme_id,\n" +
                "  cnt,\n" +
                "  startTime,\n" +
                "  endTime,\n" +
                "  useRatio,\n" +
                "  unpacked,\n" +
                "  lock_name,\n" +
                "  width,\n" +
                "  `length`,\n" +
                "  outerWidth,\n" +
                "  outerLength,\n" +
                "  runtime,\n" +
                "  runtimeThre,\n" +
                "  inwaterRestricted,\n" +
                "  inwater,\n" +
                "  isCapacity,\n" +
                "  capacity,\n" +
                "  weight\n" +
                ") \n" +
                "VALUES\n" +
                "  (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? ,?)";

        int[] updateCounts = getJdbcTemplate().batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
                preparedStatement.setLong(1, schemeId);
                preparedStatement.setInt(2, shipLocks.get(i).getCnt());
                preparedStatement.setString(3, shipLocks.get(i).getStartTime());
                preparedStatement.setString(4, shipLocks.get(i).getEndTime());
                preparedStatement.setDouble(5, shipLocks.get(i).getUseRatio());
                preparedStatement.setInt(6, shipLocks.get(i).getUnpacked());
                preparedStatement.setString(7, shipLocks.get(i).getCategory().getLockName());
                preparedStatement.setDouble(8, shipLocks.get(i).getCategory().getWidth());
                preparedStatement.setDouble(9, shipLocks.get(i).getCategory().getLength());
                preparedStatement.setDouble(10, shipLocks.get(i).getCategory().getOuterWidth());
                preparedStatement.setDouble(11, shipLocks.get(i).getCategory().getOuterLength());
                preparedStatement.setInt(12, shipLocks.get(i).getCategory().getRuntime());
                preparedStatement.setInt(13, shipLocks.get(i).getCategory().getRuntimeThre());
                preparedStatement.setBoolean(14, shipLocks.get(i).getCategory().isInwaterRestricted());
                preparedStatement.setDouble(15, shipLocks.get(i).getCategory().getInwater());
                preparedStatement.setBoolean(16, shipLocks.get(i).getCategory().isCapacity());
                preparedStatement.setDouble(17, shipLocks.get(i).getCategory().getCapacity());
                preparedStatement.setDouble(18, shipLocks.get(i).getWeight());
            }

            @Override
            public int getBatchSize() {
                return shipLocks.size();
            }
        });
        return updateCounts;
    }

    @Override
    public void insertShip(long schemeId, List<Map<String, Object>> shipLockIds, List<ShipLock> shipLocks) {

        String sql = "INSERT INTO tb_ship (scheme_id, category_id, category_name, width, `length` , maxInWater, capacity, " +
                "designLoad, fluctuation, ratio, maxWaitTime, shiplock_id, `load`, `x`, `y`, arrivalDatetime, departDatetime, waitTime) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        List<Object[]> batchArgs = new ArrayList<>();
        ShipLock sl = null;
        for (int i = 0; i < shipLocks.size(); i++) {
            sl = shipLocks.get(i);
            List<Ship> ships = sl.getShips();
            Ship ship = null;
            for (int j = 0; j < ships.size(); j++) {
                ship = ships.get(j);
                Object[] args = {schemeId, ship.getShipcategory().getCategoryId(), ship.getShipcategory().getCategoryName(), ship.getShipcategory().getWidth(), ship.getShipcategory().getLength(),
                        ship.getShipcategory().getMaxInWater(), ship.getShipcategory().getCapacity(), ship.getShipcategory().getDesignLoad(), ship.getShipcategory().getFluctuation(), ship.getShipcategory().getRatio(),
                        ship.getShipcategory().getMaxWaitTime(), shipLockIds.get(i).get("shiplock_id"), ship.getLoad(), ship.getX(), ship.getY(), ship.getArrivalDatetime(), ship.getDepartDatetime(), ship.getWaitTime()};
                batchArgs.add(args);
            }
        }
        getJdbcTemplate().batchUpdate(sql, batchArgs);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Map<String, Object>> queryShipLockIdsByScheme(int schemeId) {
        return getJdbcTemplate().queryForList("SELECT shiplock_id FROM tb_shiplock WHERE scheme_id = ? ORDER BY shiplock_id ASC", new Object[]{schemeId});
    }

    @Override
    public List<Map<String, Object>> querySchemeList() {

        String sql = "SELECT \n" +
                "  scheme.scheme_id, scheme.scheme_name, scheme.start_day, scheme.end_day, scheme.total, scheme.unpacked_cnt, DATE_FORMAT(scheme.create_time, '%Y-%m-%d %H:%i') AS create_time,\n" +
                "  pmonth.month,\n" +
                "  pmonth.ratio \n" +
                "FROM\n" +
                "  tb_scheme scheme \n" +
                "  LEFT JOIN tb_pmonth pmonth \n" +
                "    ON scheme.scheme_id = pmonth.scheme_id \n" +
                "    ORDER BY scheme.create_time DESC, pmonth.month ASC";
        return getJdbcTemplate().queryForList(sql);
    }

    @Override
    public List<Map<String, Object>> queryDftPmonth() {
        String sql = "SELECT * FROM tb_pmonth WHERE default_value = 1";
        return getJdbcTemplate().queryForList(sql);
    }
}
