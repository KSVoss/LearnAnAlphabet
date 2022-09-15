package ksvoss.backend.alphabet;

import ksvoss.backend.models.Alphabet;
import ksvoss.backend.models.AlphabetNameInDifferentLanguage;
import ksvoss.backend.models.Letter;
import ksvoss.backend.models.NameOfAlphabet;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AlphabetService {
    private final AlphabetRepository alphabetRepository;

    public AlphabetService(AlphabetRepository alphabetRepository) {
        this.alphabetRepository = alphabetRepository;
    }

    public List<NameOfAlphabet> getNamesOfAlphabet(String preferredlanguage) {
        List<NameOfAlphabet> nameOfAlphabetList=new ArrayList<>();
 
        List<Alphabet>  alphabets=alphabetRepository.findAll();
        if(alphabets.isEmpty())return nameOfAlphabetList;
        for(int i=0;i<alphabets.size();i++)
        {
            nameOfAlphabetList.add(new NameOfAlphabet(i,alphabets.get(i).name(preferredlanguage)));

        }



        return nameOfAlphabetList;
    }

    public List<Alphabet> getAllAlphabets(){
        return alphabetRepository.findAll();
    }

    public Alphabet getAlphabet(int id){
        Optional<Alphabet> alphabet=alphabetRepository.findById(id);
        if(alphabet.isPresent())return alphabet.get();
        return null;
    }
    public void createAlphabetTestDatabase()
    // ich weiß, dass das nicht gut ist.
    // Brauche ich aber für Flapdoodle in den Tests und falls mal jemand das Programm auf seinem Rechner ausprobiert
    // und ich weiß auch, dass ich keine Kommentare verwenden soll.
    {
        // alpha beta gamma Delta Pi Sigma Omega   griechisch
        // a i u e o                    hiragana
        if(!alphabetRepository.findAll().isEmpty())return;
        AlphabetNameInDifferentLanguage griechischAufDeutsch=new AlphabetNameInDifferentLanguage("griechisch","deutsch");
        AlphabetNameInDifferentLanguage griechischAufEnglisch=new AlphabetNameInDifferentLanguage("greek","english");
        AlphabetNameInDifferentLanguage hiraganaAufDeusch=new AlphabetNameInDifferentLanguage("hiragane","deutsch");
        AlphabetNameInDifferentLanguage hiraganaAufEnglisch=new AlphabetNameInDifferentLanguage("hiragana","englisch");
        Letter alpha= new Letter(0,"\u03B1",null,"alpha",0,0,null);
        Letter beta=new Letter(1,"\u03B2",null,"beta",0,0,null);
        Letter gamma=new Letter(2,"\u03B3",null,"gamma",0,0,null);
        Letter Delta=new Letter(3,"\u0394",null,"Delta",0,0,null);
        Letter Pi=new Letter(4,"\u03A0",null,"Pi",0,0,null);
        Letter Sigma=new Letter(5,"\u03A3",null,"Sigma",0,0,null);
        Letter Omega=new Letter(6,"\u03A9",null,"Omega",0,0,null);
        Letter a=new Letter(0,"\u3041",null,"a",0,0,null);
        Letter i=new Letter(1,"\u3043",null,"i",0,0,null);
        Letter u=new Letter(2,"\u3045",null,"u",0,0,null);
        Letter e=new Letter(3,"\u3047",null,"e",0,0,null);
        Letter o=new Letter(4,"\u3049",null,"o",0,0,null);
        Alphabet griechisch=new Alphabet(0,
                new AlphabetNameInDifferentLanguage[]{griechischAufDeutsch,griechischAufEnglisch}
                , List.of(alpha,beta,gamma,Delta,Pi,Sigma,Omega));
        Alphabet hiragana=new Alphabet(1,
                new AlphabetNameInDifferentLanguage[]{hiraganaAufDeusch,hiraganaAufEnglisch}
                , List.of(a,i,u,e,o));
        alphabetRepository.save(griechisch);
        alphabetRepository.save(hiragana);

    }
}
