package ksvoss.backend.user;

import ksvoss.backend.alphabet.AlphabetRepository;
import ksvoss.backend.models.Alphabet;
import ksvoss.backend.models.AlphabetNameInDifferentLanguage;
import ksvoss.backend.models.Letter;
import ksvoss.backend.models.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest

@AutoConfigureMockMvc
class UserIntegrationTest {
    @Autowired
    MockMvc mockMvc;


   /* @Autowired
    private AlphabetRepository alphabetRepository;
    @Autowired
    private UserRepository userRepository=new UserRepository() {
    };
    private UserService userServiceMocked=new UserService();
    @DirtiesContext
    @Test
    void getNamesOfAlphabetsTest(){
        User user=new userService
    }


    */

    /*
        @GetMapping("/getallalphabetnames/{userid}")
    public NameOfAlphabetsAndSelectedAlphabet getNamesOfAlphabets(@PathVariable String userid){
         return userService.getNamesOfAlphabet(userid);
    }
     */


    @DirtiesContext
@Test
    void addUserTestPassed() throws Exception {
        MvcResult result = mockMvc.perform(post("/user/newuser")
                        .contentType(APPLICATION_JSON)
                        .content("""
                                {
                                "mailadress":"F@b.com ",
                                "nickname":"Fnna",
                                "passoword":"geheim"
                                }
                                """)
                )
                .andExpect(status().is(200))
                .andReturn();
        String content = result.getResponse().getContentAsString();
        Assertions.assertTrue(content.contains("Fnna"));
    }




}
