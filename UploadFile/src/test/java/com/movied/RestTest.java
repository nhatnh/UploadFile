package com.movied;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import com.movie.model.MovieRecord;
import com.movie.service.MovieRecordService;

import java.util.Arrays;
 
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
 
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestContext.class, WebApplicationContext.class})
@WebAppConfiguration
public class RestTest {
 
    private MockMvc mockMvc;
 
    @Autowired
    private MovieRecordService movieRecordServiceMock;
 
    //Add WebApplicationContext field here.
 
    //The setUp() method is omitted.
 
    /*
     * Unit Test For Get - Rest API on Getting All Movie Records
     */
    @Test
    public void findAllMovieRecord() throws Exception {
        MovieRecord first = new MovieRecord.Builder()
				.id(Long.valueOf(1))
				.title("Titanic")
        		.checkSum("13d0583476e63446a60862887f6f2939")
                .director("James Cameron")
                .description("Romantic Love Movie")
                .producer("New Picture")
                .mainActor("Jack, Rose")
                .sourceFile("Titanic.mp4")
                .build();
        MovieRecord second = new MovieRecord.Builder()
				.id(Long.valueOf(2))
				.title("King Kong")
        		.checkSum("13d0583476e63446a60862887f6f2939")
                .director("Peter Jackson")
                .description("An Amazing Adventure Movie")
                .producer("ABC Picture")
                .mainActor("Naomi, Adrien")
                .sourceFile("")
                .build();
 
        when(movieRecordServiceMock.findAllMovieRecords()).thenReturn(Arrays.asList(first, second));
 
        mockMvc.perform(get("/record/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].title", is("Titanic")))
                .andExpect(jsonPath("$[0].checkSum", is("13d0583476e63446a60862887f6f2939")))
                .andExpect(jsonPath("$[0].director", is("James Cameron")))
                .andExpect(jsonPath("$[1].id", is(1)))
                .andExpect(jsonPath("$[1].title", is("King Kong")))
                .andExpect(jsonPath("$[1].checkSum", is("21d0583476e63446a60862887f6f2112")))
                .andExpect(jsonPath("$[1].director", is("MR ABC")));
 
        verify(movieRecordServiceMock, times(1)).findAllMovieRecords();
        verifyNoMoreInteractions(movieRecordServiceMock);
    }
}