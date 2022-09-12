package ksvoss.backend.models;

import ksvoss.backend.exeptions.CombinationLetterInAnAlphabetException;
import ksvoss.backend.exeptions.EmptyAlphabetDatabaseException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AlphabetTest {

    private List<Alphabet> getListOfAlphabetsForMocking(){
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
        List<Alphabet> alphabetList=new ArrayList<>();
        alphabetList.add(griechisch);
        alphabetList.add(hiragana);
        return alphabetList;

    }
    private Alphabet getAnAlphabetForMocking(){
        List<Alphabet> alphabetList=getListOfAlphabetsForMocking();
        return alphabetList.get(0);
    }

    @Test
    void testEquals() {
        // given
        Letter letter1=new Letter(5,"X",null,"ix",0,0,null);
        Letter letter2=new Letter(6,"Y",null,"ypsilon",0,0,null);


        AlphabetNameInDifferentLanguage deutsch=new AlphabetNameInDifferentLanguage("deutsch","alt-griechisch");
        Alphabet alphabet1=new Alphabet(5
                , new AlphabetNameInDifferentLanguage[]{deutsch}
                , List.of( letter1,letter2));
        Alphabet alphabet2=new Alphabet(5
                , new AlphabetNameInDifferentLanguage[]{deutsch}
                 ,List.of( letter1,letter2));
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

                ,List.of(letter1,letter2));
        Alphabet alphabet2=new Alphabet(5
                , new AlphabetNameInDifferentLanguage[]{deutsch}
                 ,List.of(letter1,letter2));
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

                , List.of(letter1,letter2));
        // when
        String actual=alphabet.toString();

        // then
        Assertions.assertEquals("Alphabet{id=5, names=[AlphabetNameInDifferentLanguage[name=deutsch, language=alt-griechisch]], letters=[Letter{id=5, signAsText='X', signAsPicture=null, spelling='ix'}, Letter{id=6, signAsText='Y', signAsPicture=null, spelling='ypsilon'}]}",actual);

    }
    @Test
    void findLetterByIdTest(){
        Alphabet alphabet=getAnAlphabetForMocking();
        Letter letter=alphabet.findLetterById(3);
        Assertions.assertEquals("Letter{id=3, signAsText='Δ', signAsPicture=null, spelling='Delta'}",letter.toString());
    }
    @Test
    void findLetterByIdTestEmptyAlphabet(){
        AlphabetNameInDifferentLanguage griechischAufDeutsch=new AlphabetNameInDifferentLanguage("griechisch","deutsch");
        Alphabet alphabet=new Alphabet(0,
                new AlphabetNameInDifferentLanguage[]{griechischAufDeutsch }
                , List.of( ));
        try{
            Letter actual=alphabet.findLetterById(3);
            fail();
        }catch(EmptyAlphabetDatabaseException e){
            Assertions.assertTrue(true);
        }
    }
    @Test
    void findLetterByIdTestLetterNotInAlphabet(){
        Alphabet alphabet=getAnAlphabetForMocking();
        try{
            Letter actual=alphabet.findLetterById(30);
            fail();
        }catch (CombinationLetterInAnAlphabetException e){
            assertTrue(true);
        }
    }
    @Test
    void nameTest(){
        AlphabetNameInDifferentLanguage griechischAufDeutsch=new AlphabetNameInDifferentLanguage("griechisch","deutsch");
        Alphabet alphabet1=new Alphabet(0,
                new AlphabetNameInDifferentLanguage[]{griechischAufDeutsch }
                , List.of( ));
        Alphabet alphabet2=getAnAlphabetForMocking();
        Alphabet alphabet3=new Alphabet(0,null,List.of());
        Alphabet alphabet4=new Alphabet(0,new AlphabetNameInDifferentLanguage[]{},List.of());
        String actual1=alphabet1.name("english");
        String actual2=alphabet2.name("english");
        String actual3=alphabet2.name("klingonic");
        String actual4=alphabet3.name("babbel");
        String actual5=alphabet4.name("elbisch");
        assertAll(
                ()->assertEquals("griechisch",actual1),
                ()->assertEquals("greek",actual2),
                ()->assertEquals("griechisch",actual3),
                ()->assertEquals("No_Name",actual4),
                ()->assertEquals("No_Name",actual5)
        );
    }





}
/*
	public String name(String preferredLanguage){

		if(names.length==1){
			return names[0].name();
		}
		for (AlphabetNameInDifferentLanguage name : names) {
			if (name.language().equals(preferredLanguage)) return name.name();
		}
		return names[0].name();
	}
 */