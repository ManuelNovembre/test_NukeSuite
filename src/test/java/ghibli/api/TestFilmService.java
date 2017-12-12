package ghibli.api;

import static org.junit.Assert.*;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.junit.Before;
import org.junit.Test;

public class TestFilmService {
	
	private static FilmService sut = new FilmService();
	private static String FILMID = "2baf70d1-42bb-4437-b551-e5fed5a87abe";

	@Before
	public void before() throws SQLException{
		Database.emptyDatabase();
	}
	
	@Test //Retrieve movies.
	public void testFilm() {
		
		//Given Ghibli API.
		
		//When calling getFilmsFromApi.
		Map<String, Film>  films = Film.mapFilms(Film.getFilmsFromApi());
		
		//Check that we retrieve films API. 
		assertTrue(films.size() > 0);  
	}
	
	@Test  //Add a movie to favorite.
	public void testSaveFavoriteFilm() throws SQLException, ClassNotFoundException{
		
		//Given empty database
		
		//When calling saveFavoriteFilm.
		sut.saveFavoriteFilm(Film.MAPFILMS.get(FILMID).getId());
		
		//Check that favorite film is added in database.
		assertEquals(1, FilmService.countFavoriteFilms());
	}
	
	@Test //Retrieve some statistique.
	public void testPickFavoriteFilm() throws SQLException, ClassNotFoundException{
		
		//Given database with favorite films.
		for(Entry<String, Film> f : Film.MAPFILMS.entrySet()){
			sut.saveFavoriteFilm(f.getValue().getId());
		}
		
		//When calling saveFavoriteFilm.
		List<Film> films = sut.pickFavoriteFilm();	

		//Check that favorite films are retrieved.
		assertNotNull(films);
	} 
	
	@Test // Pick favorite
	public void testPickSingleFavoriteFilm() throws SQLException, ClassNotFoundException{
		
		//Given database with a favorite film.
		sut.saveFavoriteFilm(Film.MAPFILMS.get(FILMID).getId());

		//When calling pickFavoriteFilm.
		Film film = sut.pickSingleFavoriteFilm(FILMID);
		
		//Check that favorite film is retrieved.
		assertTrue(film.getId().equals(FILMID));
	}
	
	@Test //Remove a single favorite.
	public void testRemoveFavoriteFilm() throws SQLException, ClassNotFoundException{
		
		//Given database with a favorite film.
		sut.saveFavoriteFilm(Film.MAPFILMS.get(FILMID).getId());

		//When calling saveFavoriteFilm.
		sut.removeFavoriteFilm(Film.MAPFILMS.get(FILMID).getId());
		
		//Check that favorite film is deleted.
		assertEquals(0, FilmService.countFavoriteFilms());
	}
	
	@Test //Retrieve some statistique.
	public void testStatFavoriteFilm() throws SQLException, ClassNotFoundException{
		
		//Given database with all films.
		for(Entry<String, Film> f : Film.MAPFILMS.entrySet()){
			sut.saveFavoriteFilm(f.getValue().getId());
		}
		
		//When calling saveFavoriteFilm.
		List<Film> films = sut.statFavoriteFilm();	

		//Check that favorite films are retrieved.
		assertNotNull(films);
	}
}
