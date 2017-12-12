package evaluation.demo;

import static org.junit.Assert.*;
import ghibli.api.Database;
import ghibli.api.FilmService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


@RunWith(SpringRunner.class)
@SpringBootTest
@EnableJpaRepositories(basePackages={"org.springframework.test.web.servlet.MockMvc"})
@WebAppConfiguration
@ContextConfiguration(classes = DemoApplication.class)
public class DemoApplicationTests {

    private MockMvc mockMvc;
    
    @Autowired
    private WebApplicationContext wac;
    
    @Autowired
    private RestFavoriteController controller;
    
    private static String ID = "2baf70d1-42bb-4437-b551-e5fed5a87abe";
    private static FilmService filmService = new FilmService();
 
    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
        Database.emptyDatabase();
    } 
    
    @Test
    public void contexLoads() throws Exception {
        assertNotNull(controller);
    }

    @Test
    public void testPickFavoriteFilmsOK() throws Exception {
    	//Given database with at least one movie stored.
    	filmService.saveFavoriteFilm(ID);
    	
    	//Check we retrieve films after calling '/favorite'.
    	this.mockMvc.perform(MockMvcRequestBuilders.get("/favorite"))
        			.andExpect(MockMvcResultMatchers.status().isOk());
    }
    
    @Test
    public void testPickFavoriteFilmKO() throws Exception {
    	//Given an empty database.
    	
    	//Check if there is no content.
    	StringBuilder sb = new StringBuilder().append("/favorite");
    	this.mockMvc.perform(MockMvcRequestBuilders.get(sb.toString()))
        			.andExpect(MockMvcResultMatchers.status().isNoContent());
    }
    

    @Test
    public void testPickSingleFavoriteFilmOK() throws Exception {
    	//Given database with at least one movie stored.
    	filmService.saveFavoriteFilm(ID);
    	
    	//Check we retrieve film after calling "/favorite/{id}".
    	StringBuilder sb = new StringBuilder().append("/favorite/").append(ID);
    	this.mockMvc.perform(MockMvcRequestBuilders.get(sb.toString()))
        			.andExpect(MockMvcResultMatchers.status().isOk());
    }
    
    @Test
    public void testSaveFavoriteFilmOk() throws Exception {
    	//Given an empty database.
    	
    	//Check favorite movie is stored.
    	StringBuilder sb = new StringBuilder().append("{\n\"id\" : \"").append(ID).append("\"\n}");
     	this.mockMvc.perform(MockMvcRequestBuilders.post("/favorite")
     			.content(sb.toString())
     			.contentType(MediaType.APPLICATION_JSON)
     			.accept(MediaType.APPLICATION_JSON))
        		.andExpect(MockMvcResultMatchers.status().isCreated());
    }
    
    @Test
    public void testDeleteFavoriteFilmOK() throws Exception {
    	//Given database with at least one movie stored.
    	filmService.saveFavoriteFilm(ID);
    	
    	//Check if the film is deleted.
    	StringBuilder sb = new StringBuilder().append("/favorite/").append(ID);
    	this.mockMvc.perform(MockMvcRequestBuilders.delete(sb.toString()))
        			.andExpect(MockMvcResultMatchers.status().isOk());
    }
    
    @Test
    public void testStatsOK() throws Exception {
    	//Given database with at least one movie stored.
    	filmService.saveFavoriteFilm(ID);
    	
    	//Check we retrieve films after calling '/favorite'.
    	this.mockMvc.perform(MockMvcRequestBuilders.get("/stats"))
        			.andExpect(MockMvcResultMatchers.status().isOk());
    }
    
    @Test
    public void testStatsKO() throws Exception {
    	//Given an empty database.
    	
    	//Check if there is no content.
    	StringBuilder sb = new StringBuilder().append("/stats");
    	this.mockMvc.perform(MockMvcRequestBuilders.get(sb.toString()))
        			.andExpect(MockMvcResultMatchers.status().isNoContent());
    }
}

