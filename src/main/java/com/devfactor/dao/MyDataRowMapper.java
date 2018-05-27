package com.devfactor.dao;

import com.devfactor.model.MyNumber;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MyDataRowMapper implements RowMapper<MyNumber> {
    @Override
    public MyNumber mapRow(ResultSet rs, int rowNum) throws SQLException {
        MyNumber myNumber = new MyNumber();
        myNumber.setId(rs.getInt("id"));
        myNumber.setName(rs.getString("name"));
        return myNumber;
    }
}
