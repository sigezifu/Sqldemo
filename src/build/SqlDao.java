package build;

import java.sql.ResultSet;
import java.util.Map;

public interface SqlDao {

	/**
	 * select 
	 */	
	public ResultSet selectexecute(String Sql, Map<String,String> values);
	
	/**
	 * insert
	 */
	public int insertexecute(String Sql, String[] arg);
	
	
}
