package spring.boot.jdbc.repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import spring.boot.jdbc.dao.Student;

@Repository
public class StudentJdbcRepository {
	@Autowired
	public JdbcTemplate jdbcTemplate;
	
	@Autowired
	NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	public Student findById(long id) {
		return jdbcTemplate.queryForObject("select * from student where id=?", new Object[] { id },
				new BeanPropertyRowMapper<Student>(Student.class));
	}

	class StudentRowMapper implements RowMapper<Student> {
		@Override
		public Student mapRow(ResultSet rs, int rowNum) throws SQLException {
			Student student = new Student();
			student.setId(rs.getLong("id"));
			student.setName(rs.getString("name"));
			student.setPassportNumber(rs.getString("passport_number"));
			return student;
		}
	}

	public List<Student> findAll() {
		return jdbcTemplate.query("select * from student", new StudentRowMapper());
	}

	public int deleteById(long id) {
		return jdbcTemplate.update("delete from student where id=?", new Object[] { id });
	}

	public int insert(Student student) {
		return jdbcTemplate.update("insert into student (id, name, passport_number) " + "values(?,  ?, ?)",
				new Object[] { student.getId(), student.getName(), student.getPassportNumber() });
	}

	public int update(Student student) {
		return jdbcTemplate.update("update student " + " set name = ?, passport_number = ? " + " where id = ?",
				new Object[] { student.getName(), student.getPassportNumber(), student.getId() });
	}
	
	public int namedParameterGetByName(Student stu){
		String SELECT_BY_ID = "SELECT COUNT(*) FROM STUDENT WHERE NAME = :name";
		
		SqlParameterSource namedParameters = new BeanPropertySqlParameterSource(stu);
		return namedParameterJdbcTemplate.queryForObject(SELECT_BY_ID, namedParameters, Integer.class);
		
	}
	
	public int[] batchUpdateUsingJdbcTemplate(List<Student> students) {
	    return jdbcTemplate.batchUpdate("INSERT INTO Student VALUES (?, ?, ?)",
	        new BatchPreparedStatementSetter() {
	            @Override
	            public void setValues(PreparedStatement ps, int i) throws SQLException {
	                ps.setLong(1, students.get(i).getId());
	                ps.setString(2, students.get(i).getName());
	                ps.setString(3, students.get(i).getPassportNumber());
	            }
	            @Override
	            public int getBatchSize() {
	                return 2;
	            }
	        });
	}
}
