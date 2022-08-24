package ksvoss.backend.models;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AlphabetTest {

    @Test
    void testEquals() {
        // given
        Letter letter1=new Letter(5,"X",null,"ix",0,0,null);
        Letter letter2=new Letter(6,"Y",null,"ypsilon",0,0,null);


        AlphabetNameInDifferentLanguage deutsch=new AlphabetNameInDifferentLanguage("deutsch","alt-griechisch");
        Alphabet alphabet1=new Alphabet(5
                , new AlphabetNameInDifferentLanguage[]{deutsch}
                ,null
                ,new Letter[]{letter1,letter2});
        Alphabet alphabet2=new Alphabet(5
                , new AlphabetNameInDifferentLanguage[]{deutsch}
                ,null
                ,new Letter[]{letter1,letter2});
        // when
        boolean actual=alphabet1.equals(alphabet2);
        // then

        Assertions.assertTrue(actual);
    }

    @Test
    void testHashCode() {
        // given
        Letter letter1=new Letter(5,"X",null,"ix",0,0,null);
        Letter letter2=new Letter(6,"Y",null,"ypsilon",0,0,null);


        AlphabetNameInDifferentLanguage deutsch=new AlphabetNameInDifferentLanguage("deutsch","alt-griechisch");
        Alphabet alphabet1=new Alphabet(5
                , new AlphabetNameInDifferentLanguage[]{deutsch}
                ,null
                ,new Letter[]{letter1,letter2});
        Alphabet alphabet2=new Alphabet(5
                , new AlphabetNameInDifferentLanguage[]{deutsch}
                ,null
                ,new Letter[]{letter1,letter2});
        // when
        int actual=alphabet1.hashCode();
        // then

        Assertions.assertEquals(alphabet2.hashCode(),actual);
    }

    @Test
    void testToString() {
        // given
        Letter letter1=new Letter(5,"X",null,"ix",0,0,null);
        Letter letter2=new Letter(6,"Y",null,"ypsilon",0,0,null);


        AlphabetNameInDifferentLanguage deutsch=new AlphabetNameInDifferentLanguage("deutsch","alt-griechisch");
        Alphabet alphabet=new Alphabet(5
                , new AlphabetNameInDifferentLanguage[]{deutsch}
                ,null
                ,new Letter[]{letter1,letter2});
        // when
        String actual=alphabet.toString();

        // then
        Assertions.assertEquals(     "Alphabet{id=5, names=[AlphabetNameInDifferentLanguage[name=deutsch, Language=alt-griechisch]], letters=[Letter{id=5, signAsText='X', signAsPicture=null, spelling='ix'}, Letter{id=6, signAsText='Y', signAsPicture=null, spelling='ypsilon'}]}",actual);

    }
}