package com.globalogic.test;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.http.HttpStatus;
import org.junit.jupiter.api.Test;
import com.globalogic.test.entity.User;
import com.globalogic.test.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers; 
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import com.globalogic.test.web.Controller;
@SpringBootTest
@AutoConfigureMockMvc  
//@WebMvcTest(Controller.class)
//@Import(ManualScanConfig.class)
public class TestGetEndpoint {
 
    @Autowired
        MockMvc mockMvc;
    
    @MockBean
    private UserService userService;

   @Test
   public void testAddUserTest() throws Exception {

        when(userService.saveService(any(User.class)))
            .thenReturn(ResponseEntity.status(HttpStatus.CREATED).build());

      String requets = "{\"name\": \"Diego Mesa\",\"email\": \"codigo7267911@gmail.com\",\"password\": \"a2asffAdfdf4\"}";
      //   String requets = "";   
        this.mockMvc.perform(post("/api/usuarios/create")
                  .contentType(MediaType.APPLICATION_JSON)
                  .content(requets))
                  .andExpect(status().isCreated())
                  .andDo(print()) ;
       }
      @Test
     public void testGetUser() throws Exception {
          this.mockMvc.perform(get("/api/usuarios/login"))
            .andDo(print())
         .andExpect(status().isOk());
       }

       @Test
     public void testGetUserAnswer() throws Exception {
          this.mockMvc.perform(get("/api/usuarios/login"))
            .andDo(print())
         .andExpect(status().isOk())
         .andReturn().getResponse().getContentAsString();         
       }
}
