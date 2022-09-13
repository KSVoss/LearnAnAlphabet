package ksvoss.backend.models;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LetterTest {

    @Test
    void testEquals() {
        // given
        Letter letter1=new Letter(5,"X",null,"ix",0,0,null);
        Letter letter2=new Letter(5,"X",null,"ix",0,0,null);
        // when
        boolean actual=letter1.equals(letter2);
        //then
        Assertions.assertTrue(actual);
    }

    @Test
    void testHashCode() {
        // given
        Letter letter1=new Letter(5,"X",null,"ix",0,0,null);
        Letter letter2=new Letter(5,"X",null,"ix",0,0,null);
        // when
        int actual=letter1.hashCode();

        // then
        Assertions.assertEquals(letter2.hashCode(),actual);
    }

    @Test
    void testToString() {
        // given
        Letter letter=new Letter(5,"X",null,"ix",0,0,null);
        // when
        String actual=letter.toString();
        // then
        System.out.println(actual);
        Assertions.assertEquals(
                "Letter{id=5, signAsText='X', signAsPicture=null, spelling='ix', pronunciationStartInMs=0, pronunciationDurationMs=0, pronunciationUrl='null'}"
                ,actual);
     }
}