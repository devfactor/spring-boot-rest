package com.devfactor.dao;

import com.devfactor.exception.NumberNotFoundException;
import com.devfactor.model.MyNumber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Repository
@Transactional
public class MyDao {
    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final RowMapper<MyNumber> rowMapper = new MyDataRowMapper();

    @Autowired
    public MyDao(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public List<MyNumber> getAllData() {
        String sql = "SELECT id, name FROM test.mydata";
        return this.jdbcTemplate.query(sql, rowMapper);
    }

    public MyNumber getDataById(int id) throws NumberNotFoundException {
        String sql = "SELECT id, name FROM test.mydata where id = ? ";
        RowMapper<MyNumber> rowMapper = new MyDataRowMapper();
        List<MyNumber> result = this.jdbcTemplate.query(sql, rowMapper, id);
        if (result.size() == 0) {
            throw new NumberNotFoundException("Number not found");
        }
        return result.get(0);
    }

    public void updateNumbers(List<MyNumber> numbers) {
        String sql = "update test.mydata set name = :name where id = :id ";
        SqlParameterSource[] batch = SqlParameterSourceUtils.createBatch(numbers.toArray());
        int[] updateCounts = namedParameterJdbcTemplate.batchUpdate(sql, batch);
    }

    public void createNumbers(final List<MyNumber> numbers) {
        String sql = "insert into test.mydata (id, name) values (?, ?) ";
        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ps.setInt(1, numbers.get(i).getId());
                ps.setString(2, numbers.get(i).getName());
            }

            @Override
            public int getBatchSize() {
                return numbers.size();
            }
        });
    }

    public void deleteNumber(int id) {
        String sql = "delete from test.mydata where id = :id ";
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("id", id);
        namedParameterJdbcTemplate.update(sql, params);
    }
}
