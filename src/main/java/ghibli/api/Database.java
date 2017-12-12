package ghibli.api;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Driver;
import com.mysql.jdbc.PreparedStatement;

/**
 * @author manuel
 *
 */
public class Database {

	public static final String URL = "jdbc:mysql://localhost:3306/ghibli";
	public static final String USER  = "root";
	public static final String PASS  = "root";
	
	private static Logger LOGGER = Logger.getLogger(Database.class);; 
	/**
	 * Get a connection to database
	 * @return Connection object
	 * @throws ClassNotFoundException 
	*/
	public static Connection getConnection() {
	    try {
	        DriverManager.registerDriver(new Driver());
	        return (Connection) DriverManager.getConnection(URL, USER, PASS);
	    } catch (SQLException ex)
	    {
	    	System.out.print(ex.getMessage());
	        throw new RuntimeException("Error connecting to the database", ex);
	    }
	}
	
	public static void executeQuery (String _query, List<Object> param) throws SQLException{
		Connection con = getConnection();
		int cpt = 1;
		 StringBuilder query = new StringBuilder().append(_query); 
	      
	     // create the mysql insert preparedstatement.
	     PreparedStatement preparedStmt;
		try {
			preparedStmt = (PreparedStatement) con.prepareStatement(query.toString());
			 for(Object o : param ){
				 if (o instanceof String)
				    preparedStmt.setString (cpt, o.toString());
				 else if(o instanceof Integer)
					preparedStmt.setInt(cpt, ((Integer) o).intValue());
				 cpt ++;
			 }
		    // execute the preparedstatement.
		    preparedStmt.execute();
		      
		      con.close();
		} catch (SQLException e) {
			LOGGER.error(e.getMessage(), e);
			throw e;
		}
	}
	
	public static void emptyDatabase(){
		String query = "SET SQL_SAFE_UPDATES = 0;";
		try {
			executeQuery(query, new ArrayList<Object>());
			query = "delete from favorite;";
			executeQuery(query, new ArrayList<Object>());
		} catch (SQLException e) {
			LOGGER.error(e.getMessage(), e);
		}
	}
}
