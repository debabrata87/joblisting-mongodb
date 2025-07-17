package com.example.joblisting;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.example.joblisting.model.Post;
import com.example.joblisting.repo.PostRepository;
import com.example.joblisting.repo.SearchRepository;
import com.example.joblisting.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest  // Loads the entire Spring context, including repositories
@AutoConfigureMockMvc  // Automatically configures MockMvc
public class JobApplicationTestMockMvcActualRepoCall {

	
	@Autowired
	private MockMvc mockMvc;  // Inject MockMvc for making HTTP requests in the test

	@MockBean
	private PostRepository repo;
	
	@Autowired
	private SearchRepository repo2;
	
	@Autowired
	private UserService usrService;
	
	@Autowired
	private ObjectMapper objectMapper; // For converting objects to JSON
	    
	
	@BeforeEach
    void setUp() {
        // Perform setup actions if necessary. For example, mocking services or resetting states.
        // MockMvc and WebContext are already initialized by @WebMvcTest.
    }
	    
	@Test
	 void greetShouldReturnMessage() throws Exception {
        String name = "John";
        
       
        mockMvc.perform(get("/welcome/{name}", name))  // Perform GET request
        .andExpect(status().isOk())  // Expect HTTP status 200 OK
        .andExpect(content().string("Hello, " + name + "!"));  // Expect response body
    }

	
	@Test
	void filterJobPostTestWithActualRepoDatabaseCall() throws Exception {
		String searchText = "kolkata"; // The search text input
		
		// Act & Assert
		mockMvc.perform(get("/filterjobpost/{searchtext}", searchText)) // Simulate GET request to
																		// /jobs/filter/{searchtext}
				.andExpect(status().isOk()) // Check HTTP status is 200 OK
				.andExpect(jsonPath("$[0].profile").value("Block Chain Developer")); 
	}
}
