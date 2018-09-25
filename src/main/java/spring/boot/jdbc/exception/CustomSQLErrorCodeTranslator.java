package spring.boot.jdbc.exception;

import java.sql.SQLException;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.support.SQLErrorCodeSQLExceptionTranslator;

public class CustomSQLErrorCodeTranslator extends SQLErrorCodeSQLExceptionTranslator {
	
	 @Override
	    protected DataAccessException customTranslate
	      (String task, String sql, SQLException sqlException) {
	        if (sqlException.getErrorCode() == -104) {
	            return new DuplicateKeyException(
	                "Custom Exception translator - Integrity constraint violation.", sqlException);
	        }
	        return null;
	    }
    
}