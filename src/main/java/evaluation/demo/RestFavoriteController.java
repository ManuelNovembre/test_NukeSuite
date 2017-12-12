package evaluation.demo;

import java.sql.SQLException;
import java.util.List;

import ghibli.api.Film;
import ghibli.api.FilmService;

import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;

/**
 * @author manuel
 *
 */
@RestController
@RequestMapping(value = "/")
class RestFavoriteController {

	public static final Logger LOGGER = Logger.getLogger(RestFavoriteController.class);
	
	private static FilmService service = new FilmService();
	

	/**
	 * 
	 * @return
	 */
	@RequestMapping(value = "/movie", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<List<Film>> listAllFilms() {
		List<Film> films = Film.getFilmsFromApi();
		if (films.isEmpty()) {
			return new ResponseEntity<List<Film>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Film>>(films, HttpStatus.OK);
	}

	/**
	 * @param json
	 * @return
	 */
	@RequestMapping(value = "/favorite", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<HttpStatus> saveFavorite(@RequestBody String json) {
		Gson gson = new Gson();
		Film film = gson.fromJson(json, Film.class);
		try {
			service.saveFavoriteFilm(film.getId());
		} catch (SQLException e) {
			LOGGER.error(e.getMessage(), e);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}
	
	/**
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/favorite/{ID}", method = RequestMethod.DELETE)
	public @ResponseBody ResponseEntity<HttpStatus> removeFavorite(@PathVariable(value="ID") String id) {
		Film film = service.pickSingleFavoriteFilm(id);
		if (film == null) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		}
		try {
			service.removeFavoriteFilm(id);
		} catch (SQLException e) {
			LOGGER.error(e.getMessage(), e);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
		return ResponseEntity.status(HttpStatus.OK).build();
	}
	
	/**
	 * @return
	 */
	@RequestMapping(value = "/favorite", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<List<Film>> showFavorite() {
		List<Film> films = service.pickFavoriteFilm();
		if (films.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		}
		return new ResponseEntity<List<Film>>(films, HttpStatus.OK); 
	}
	
	/**
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/favorite/{ID}", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<Film> showSingleFavorite(@PathVariable(value="ID") String id) {
		Film film = service.pickSingleFavoriteFilm(id);
		if (film == null) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		}
		else{
			return new ResponseEntity<Film>(film, HttpStatus.OK); 
		}
	}
	
	/**
	 * @return
	 */
	@RequestMapping(value = "/stats", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<List<Film>> showStats() {
		List<Film> films = service.statFavoriteFilm();
		if (films.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		}
		return new ResponseEntity<List<Film>>(films, HttpStatus.OK); 
	}

}