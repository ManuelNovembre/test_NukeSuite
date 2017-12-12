package ghibli.api;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

public class FilmService {
	
	private static Logger LOGGER = Logger.getLogger(FilmService.class);; 

	/**
	 * count number of favorite films in database
	 * @return
	 */
	public static int countFavoriteFilms(){
		int count =0;
		Connection con = Database.getConnection();
		String query = "select count(*) as nb from favorite;"; 
	   
		// create the mysql insert preparedstatement.
		PreparedStatement preparedStmt;
		try {
			preparedStmt = (PreparedStatement) con.prepareStatement(query);
			
		    // execute the preparedstatement.
		    ResultSet r1=preparedStmt.executeQuery();

		     if(r1.next()) {
		    	 count = r1.getInt("nb") ;
		      }
		      con.close();
		} catch (SQLException e) {
			LOGGER.error(e.getMessage(), e);
		}
		return count;
	}
	
/**
 * save favorite movie in database
 * @param id
 * @throws SQLException 
 */
public void saveFavoriteFilm(String id) throws SQLException{
	Film film = null;
	if(Film.MAPFILMS.containsKey(id))
		 film = Film.MAPFILMS.get(id);
	else
		return;
	List<Object> param = new ArrayList<Object>();
	param.add(id);
	param.add(film.getTitle());
	param.add(film.getDescription());
	param.add(film.getDirector());
	param.add(film.getProducer());
	param.add(film.getRelease_date());
	param.add(film.getRt_score());
	String query = " insert into favorite (id, title, description, director, producer, release_date, rt_score)"
	        + " values (?, ?, ?, ?, ?, ?, ?);";
	Database.executeQuery(query, param);
}

/**
 * remove favorite film in database
 * @param id
 * @throws SQLException 
 */
public void removeFavoriteFilm(String id) throws SQLException{
	List<Object> param = new ArrayList<Object>();
	param.add(id);
	String query = "delete from favorite where id = ?;";
	Database.executeQuery(query, param);
}


/**
 * retrieve all favorite films from database
 * @return
 */
public List<Film> pickFavoriteFilm(){
	Connection con = Database.getConnection();
	List<Film> films = new ArrayList<Film>();
	String query = "select * from favorite;"; 
      
      // create the mysql insert preparedstatement.
      PreparedStatement preparedStmt;
	try {
		preparedStmt = (PreparedStatement) con.prepareStatement(query);
		
	    // execute the preparedstatement.
	    ResultSet r1=preparedStmt.executeQuery();

	     if(r1.next()) {
	            films.add(new Film(r1.getString("id"), r1.getString("title"), r1.getString("description"), r1.getString("director"), r1.getString("producer"), r1.getInt("release_date"), r1.getInt("rt_score"))) ;
	      }
	      con.close();
	} catch (SQLException e) {
		LOGGER.error(e.getMessage(), e);
	}
	return films;
}		
/**
 * retrieve one favorite film from database
 * @param id
 */
public Film pickSingleFavoriteFilm(String id){
	Connection con = Database.getConnection();
	Film film = null;
	if(Film.MAPFILMS.containsKey(id))
		 film = Film.MAPFILMS.get(id);
	  String query = "select * from favorite where id = ?;"; 
      
      // create the mysql insert preparedstatement.
      PreparedStatement preparedStmt;
	try {
		preparedStmt = (PreparedStatement) con.prepareStatement(query);
	    preparedStmt.setString (1, film.getId());

	    // execute the preparedstatement.
	    ResultSet r1=preparedStmt.executeQuery();

	     if(r1.next()) {
	            film =  new Film(r1.getString("id"), r1.getString("title"), r1.getString("description"), r1.getString("director"), r1.getString("producer"), r1.getInt("release_date"), r1.getInt("rt_score")); ;
	      }
	      con.close();
	} catch (SQLException e) {
		LOGGER.error(e.getMessage(), e);
	}
	return film;
}


/***
 * retrieve movie with specific data
 * @return
 */
public List<Film> statFavoriteFilm(){
	Connection con = Database.getConnection();
	List<Film> films = new ArrayList<Film>();
	String query = "select * from favorite where release_date between 2005 and 2014  and rt_score > 85;"; 
      
      // create the mysql insert preparedstatement.
      PreparedStatement preparedStmt;
	try {
		preparedStmt = (PreparedStatement) con.prepareStatement(query);
		
	    // execute the preparedstatement.
	    ResultSet r1=preparedStmt.executeQuery();

	     if(r1.next()) {
	            films.add(new Film(r1.getString("id"), r1.getString("title"), r1.getString("description"), r1.getString("director"), r1.getString("producer"), r1.getInt("release_date"), r1.getInt("rt_score"))) ;
	      }
	      con.close();
	} catch (SQLException e) {
		LOGGER.error(e.getMessage(), e);
	}
	return films;
}		
}
