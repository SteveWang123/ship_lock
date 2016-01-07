package com.schinta.shiplock.dao.impl;

import com.schinta.shiplock.dao.testDao;
import com.schinta.shiplock.model.Student.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by wangyuannan on 2015/12/30.
 */
public class testDaoImpl implements testDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void update(Student student) {
        String sql = " UPDATE student SET studentName=?,studentHobby=?,studentMobile=?,studentDOB=?,studentSkills=? WHERE studentId=?";
        jdbcTemplate.update(sql, student.getStudentName(), student.getStudentHobby(), student.getStudentMobile(), student.getStudentDOB(), student.getStudentSkills(), student.getStudentId());
    }

    @Override
    public void insert(Student student) {
        String sql = " INSERT INTO student(studentName, studentHobby,studentMobile,studentDOB,studentSkills) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, student.getStudentName(), student.getStudentHobby(), student.getStudentMobile(), student.getStudentDOB(), student.getStudentSkills());

    }

    @Override
    public void delete(int studentId) {
        String sql = "DELETE from student WHERE studentId=?";
        jdbcTemplate.update(sql, studentId);
    }

    @Override
    public Student findById(int studentId) {
        String sql = "SELECT * FROM contact WHERE contact_id=" + studentId;
        return jdbcTemplate.query(sql, new ResultSetExtractor<Student>() {

            @Override
            public Student extractData(ResultSet rs) throws SQLException, DataAccessException {
                if (rs.next()) {
                    Student s = new Student();
                    s.setStudentId(rs.getInt("studentId"));
                    s.setStudentName(rs.getString("studentName"));
                    s.setStudentHobby(rs.getString("studentHobby"));
                    s.setStudentMobile(rs.getLong("studentMobile"));
                    s.setStudentSkills(rs.getString("studentSkills"));
                    return s;
                }
                return null;
            }
        });
    }

    @Override
    public List<Student> list() {
        String sql = "SELECT * FROM student ORDER BY studentId DESC";
        List<Student> students = jdbcTemplate.query(sql, new studentRowMapper());
        return students;
    }

    private static class studentRowMapper implements RowMapper<Student> {

        @Override
        public Student mapRow(ResultSet rs, int rowInt) throws SQLException {
            Student s = new Student();

            s.setStudentId(rs.getInt("studentId"));
            s.setStudentName(rs.getString("studentName"));
            s.setStudentHobby(rs.getString("studentHobby"));
            s.setStudentDOB(rs.getDate("studentDOB"));
            s.setStudentMobile(rs.getLong("studentMobile"));
            s.setStudentSkills(rs.getString("studentSkills"));
            return s;
        }
    }
}
