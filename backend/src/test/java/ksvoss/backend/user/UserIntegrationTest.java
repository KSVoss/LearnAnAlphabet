package ksvoss.backend.user;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@SpringBootTest

@AutoConfigureMockMvc
class UserIntegrationTest {
    @Autowired
    MockMvc mockMvc;

 @DirtiesContext
@Test
    void addUserTestPassed() throws Exception {
        MvcResult result = mockMvc.perform(post("/newuser")
                        .contentType(APPLICATION_JSON)
                        .content("""
                                {
                                "mailadress":"F@b.com ",
                                "nickname":"Fnna",
                                "passoword":"geheim"
                                }
                                """)
                )
                .andExpect(status().is(201))
                .andReturn();
        String content = result.getResponse().getContentAsString();
        Assertions.assertTrue(content.contains("Fnna"));
    }

}
