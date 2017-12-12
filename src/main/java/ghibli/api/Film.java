package ghibli.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.google.gson.Gson;

/**
 * @author manuel
 *
 */
public class Film {
    private String id;
    private String title;
    private String description;
    private String director;
    private String producer;
    private int  release_date;
    private int rt_score;
    
    public static String GHIBLI_URL = "https://ghibliapi.herokuapp.com/";
    public static String FILMS = "films";
    
    public static Map<String, Film>  MAPFILMS = mapFilms(getFilmsFromApi());
    
    /**
     * 
     */
    public Film(){
    	
    }
    
	/**
	 * @param id
	 * @param title
	 * @param description
	 * @param director
	 * @param producer
	 * @param release_date
	 * @param rt_score
	 */
	public Film(String id, String title, String description, String director,
			String producer, int release_date, int rt_score) {
		super();
		this.id = id;
		this.title = title;
		this.description = description;
		this.director = director;
		this.producer = producer;
		this.release_date = release_date;
		this.rt_score = rt_score;
	}
	
	public String getId() {
		return id;
	}
	public String getTitle() {
		return title;
	}
	public String getDescription() {
		return description;
	}
	public String getDirector() {
		return director;
	}
	public String getProducer() {
		return producer;
	}
	public int getRelease_date() {
		return release_date;
	}
	public int getRt_score() {
		return rt_score;
	}
	
	/**
	 * Retrieve movies
	 * return film list from Ghibli API 
	 * @return
	 */
	public static List<Film> getFilmsFromApi(){
		List<Film>  films = new ArrayList<Film>();
		HttpURLConnection con = null;
		StringBuilder url = new StringBuilder().append(GHIBLI_URL).append(FILMS); 
        URL u;
		try {
			u = new URL(url.toString());
	        con = (HttpURLConnection) u.openConnection();
	        con.setRequestMethod("GET");
			StringBuilder sb = new StringBuilder();
			BufferedReader br = null;
			String line;
			br = new BufferedReader(new InputStreamReader(con.getInputStream()));
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}
			Gson gson = new Gson();
			Film[] f = gson.fromJson(sb.toString(), Film[].class);
			films.addAll(Arrays.asList(f));
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
		}
		return films;
	}

	
	
	/**
	 * Convert films list to map 
	 * @param films
	 * @return
	 */
	public static Map<String, Film> mapFilms(List<Film> films){
		Map<String, Film> map = new HashMap<String, Film>();
		for (Film f : films)
			map.put(f.getId(), f);
		return map;
		
	}
}
	
	

