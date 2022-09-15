package ksvoss.backend.user;

import ksvoss.backend.alphabet.AlphabetRepository;
import ksvoss.backend.models.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.bind.annotation.PostMapping;

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


    @Autowired
    private AlphabetRepository alphabetRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;
    /*
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

    void createAlphabet() {
        AlphabetNameInDifferentLanguage griechischAufDeutsch = new AlphabetNameInDifferentLanguage("griechisch", "deutsch");
        AlphabetNameInDifferentLanguage griechischAufEnglisch = new AlphabetNameInDifferentLanguage("greek", "english");
        AlphabetNameInDifferentLanguage hiraganaAufDeusch = new AlphabetNameInDifferentLanguage("hiragane", "deutsch");
        AlphabetNameInDifferentLanguage hiraganaAufEnglisch = new AlphabetNameInDifferentLanguage("hiragana", "englisch");
        Letter alpha = new Letter(0, "\u03B1", null, "alpha", 0, 0, null);
        Letter beta = new Letter(1, "\u03B2", null, "beta", 0, 0, null);
        Letter gamma = new Letter(2, "\u03B3", null, "gamma", 0, 0, null);
        Letter Delta = new Letter(3, "\u0394", null, "Delta", 0, 0, null);
        Letter Pi = new Letter(4, "\u03A0", null, "Pi", 0, 0, null);
        Letter Sigma = new Letter(5, "\u03A3", null, "Sigma", 0, 0, null);
        Letter Omega = new Letter(6, "\u03A9", null, "Omega", 0, 0, null);
        Letter a = new Letter(0, "\u3041", null, "a", 0, 0, null);
        Letter i = new Letter(1, "\u3043", null, "i", 0, 0, null);
        Letter u = new Letter(2, "\u3045", null, "u", 0, 0, null);
        Letter e = new Letter(3, "\u3047", null, "e", 0, 0, null);
        Letter o = new Letter(4, "\u3049", null, "o", 0, 0, null);
        Alphabet griechisch = new Alphabet(0,
                new AlphabetNameInDifferentLanguage[]{griechischAufDeutsch, griechischAufEnglisch}
                , List.of(alpha, beta, gamma, Delta, Pi, Sigma, Omega));
        Alphabet hiragana = new Alphabet(1,
                new AlphabetNameInDifferentLanguage[]{hiraganaAufDeusch, hiraganaAufEnglisch}
                , List.of(a, i, u, e, o));
        alphabetRepository.save(griechisch);
        alphabetRepository.save(hiragana);

    }


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
