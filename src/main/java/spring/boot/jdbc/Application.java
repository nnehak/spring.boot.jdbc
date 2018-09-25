package spring.boot.jdbc;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import spring.boot.jdbc.dao.Student;
import spring.boot.jdbc.exception.CustomSQLErrorCodeTranslator;
import spring.boot.jdbc.repository.StudentJdbcRepository;

//https://dzone.com/articles/spring-boot-and-spring-jdbc-with-h2
//https://www.baeldung.com/spring-jdbc-jdbctemplate

@SpringBootApplication
public class Application implements CommandLineRunner {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	StudentJdbcRepository repository;

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
		
	}

	public void run(String... arg0) throws Exception {
		//CustomSQLErrorCodeTranslator customSQLErrorCodeTranslator = new CustomSQLErrorCodeTranslator();
		//repository.jdbcTemplate.setExceptionTranslator(customSQLErrorCodeTranslator);
		logger.info("Student id 10001 -> {}", repository.findById(10001L));
		logger.info("All users 1 -> {}", repository.findAll());

		logger.info("Inserting -> {}", repository.insert(new Student(10010L, "John", "A1234657")));
		logger.info("All users 2 -> {}", repository.findAll());
		logger.info("Update 10001 -> {}", repository.update(new Student(10001L, "Name-Updated", "New-Passport")));
		logger.info("Delating -> {}", repository.deleteById(10002L));
		logger.info("All users 1 -> {}", repository.findAll());
		logger.info("Count student -> {}", repository.namedParameterGetByName(new Student("John", "E1234567")));
		
		List<Student> students = new ArrayList<>();
		students.add(new Student(10L,"Ten", "T1012345"));
		students.add(new Student(11L,"ELE", "T1112345"));
		students.add(new Student(12L,"TWL", "T1212345"));
		students.add(new Student(13L,"THI", "T1312345"));
		
		logger.info("Batch Update");
		repository.batchUpdateUsingJdbcTemplate(students);
		logger.info("All users 5 -> {}", repository.findAll());
		
	}
}
