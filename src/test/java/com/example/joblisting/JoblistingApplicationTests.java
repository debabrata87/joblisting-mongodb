package com.example.joblisting;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;
import org.mockito.MockedStatic;
import com.example.joblisting.utility.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.web.servlet.MockMvc;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import com.example.joblisting.controller.PostController;
import com.example.joblisting.repo.PostRepository;
import com.example.joblisting.repo.SearchRepository;
import com.example.joblisting.service.UserService;
import com.example.joblisting.service.UtilityService;
import com.fasterxml.jackson.databind.ObjectMapper;



@WebMvcTest(PostController.class)
//@RunWith(PowerMockRunner.class)
//@PrepareForTest(Utility.class) 
class JoblistingApplicationTests {

	@Autowired
	private MockMvc mockMvc; // Inject MockMvc for making HTTP requests in the test

	@MockBean
	private PostRepository repo;

	@MockBean
	private SearchRepository repo2;
	
	@MockBean
	private UserService usrService;
	
	@Autowired
	UtilityService utilService;

	@Autowired
	private ObjectMapper objectMapper; // For converting objects to JSON

	
	/*
	@TestConfiguration
    static class TestConfig {

        @Bean
        public UtilityService utilityService() {
            // Return real instance or mocked one here
            return new UtilityService();
            // Or: return Mockito.mock(UtilityService.class);
        }
    }
	*/
	
	@BeforeEach
	void setUp() {
		// Perform setup actions if necessary. For example, mocking services or
		// resetting states.
		// MockMvc and WebContext are already initialized by @WebMvcTest.
	}

	@Test
	void greetShouldReturnMessage() throws Exception {
		String name = "John";

		mockMvc.perform(get("/welcome/{name}", name)) // Perform GET request
				.andExpect(status().isOk()) // Expect HTTP status 200 OK
				.andExpect(content().string("Hello, " + name + "!")); // Expect response body
	}

	/*
	@Test
	public void testProcessMessage_withMockedStatic() {
		// Arrange
		PowerMockito.mockStatic(Utility.class);
		Mockito.when(Utility.getWelcomeMessageStatic()).thenReturn( "Mocked Hello") ;

		//UtilityService service = new UtilityService();

		// Act
		String result = utilService.processMessage();

		// Assert
		assertEquals("MOCKED HELLO", result);
	}
   */
	
	
	@Test
    void testStaticMethodApproach1() {
        try (MockedStatic<Utility> mocked = mockStatic(Utility.class)) {
            mocked.when(Utility::getWelcomeMessageStatic).thenReturn("Mocked Value");

            String result = Utility.getWelcomeMessageStatic();
            assertEquals("Mocked Value", result);
        }
    }
	
	@Test
    void testStaticMethodApproach2() {
        try (MockedStatic<Utility> mocked = mockStatic(Utility.class)) {
            mocked.when(Utility::getWelcomeMessageStatic).thenReturn("Mocked Value");

            // Act
    		String result = utilService.processMessage();

    	
    		assertTrue("Mocked Value".equalsIgnoreCase(result));
        }
    }
	
	@Test
    void testNonStaticMethodApproach1() {
            
            Utility utilityMock = mock(Utility.class);

            // Stub the method to return "Mocked Value" when called
            when(utilityMock.getWelcomeMessageNonStatic()).thenReturn("Mocked Value");

            // Call the method
            String result = utilityMock.getWelcomeMessageNonStatic();

            // Assert
            assertEquals("Mocked Value", result);
        
    }
	
	
	 void testNonStaticMethodApproach2() {
	        

         Utility util = new Utility();
         String result = util.getWelcomeMessageNonStatic();
         //assertEquals("Mocked Value", result);
         assertTrue( "Welcome Powermock Static Uitlity ".equalsIgnoreCase(result));
         
         
     
 }
	 
}
