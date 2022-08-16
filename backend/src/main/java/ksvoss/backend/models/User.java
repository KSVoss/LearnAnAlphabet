package ksvoss.backend.models;

import java.util.UUID;

import ksvoss.backend.user.NewUser;
import org.springframework.data.annotation.Id;



public class User {
    @Id
    private String id;
    private String mailadress;
    private String nickname;
    private String passwordHashed;
    private byte[] picture;
    private String preferedLanguage;
    private boolean weightedRadomize;
    private LearnedElement[] learnedElements;

    public User(String mailadress, String nickname, String passwordHashed) {
        this.mailadress = mailadress;
        this.nickname = nickname;
        this.passwordHashed = passwordHashed;
        this.id= UUID.randomUUID().toString();
        this.preferedLanguage="deutsch";
    }
    public User(NewUser newUser){
        this.mailadress= newUser.mailadress();
        this.nickname=newUser.nickname();
        this.passwordHashed=newUser.password();
        this.id=UUID.randomUUID().toString();
        this.preferedLanguage="deutsch";
    }

}
