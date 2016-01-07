package com.schinta.shiplock.dao;

import com.schinta.shiplock.model.Student.Student;

import java.util.List;

/**
 * Created by wangyuannan on 2015/12/30.
 */
public interface testDao {

    public void update(Student student);

    public void insert(Student student);

    public void delete(int studentId);

    public Student findById(int studentId);

    public List<Student> list();

}
