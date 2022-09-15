package ksvoss.backend.alphabet;

import ksvoss.backend.models.Alphabet;
import ksvoss.backend.models.NameOfAlphabet;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping

public class AlphabetController {
    private final AlphabetService alphabetService;

    public AlphabetController(AlphabetService alphabetService) {
        this.alphabetService = alphabetService;
    }

    @PostMapping("/createdatabase")
    void createAlphabetDatabase(){
        alphabetService.createAlphabetTestDatabase();
    }

    @GetMapping("/getallalphabets")
    List<Alphabet> getAllAlphabets(){
        return alphabetService.getAllAlphabets();
    }

    @GetMapping("/getalphabet/{alphabetid}")
    Alphabet getAlphabet(@PathVariable int alphabetid){
         return alphabetService.getAlphabet(alphabetid);
    }

    @GetMapping("/getnamesofalphabets/{preferredlanguage}")
    List<NameOfAlphabet> getNamesOfAlphabets(@PathVariable String preferredlanguage){
        System.out.println("Controller:"+preferredlanguage);

        return alphabetService.getNamesOfAlphabet(preferredlanguage);
    }



}
