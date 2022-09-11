package ksvoss.backend.user;

import ksvoss.backend.models.NameOfAlphabetsAndSelectedAlphabet;
import ksvoss.backend.models.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class UserControllerTest {

    private final UserService testUserService = mock(UserService.class);
    private final UserController testUserController = new UserController(testUserService);
@Test
    void addUserTestPass(){
    // given
        NewUser addedUser=new NewUser("a@b.com","Anton","wird nicht verraten");
        User testUser=new User("a@b.com","Anton","wird nicht verraten");
    // when
        when(testUserService.addUser(addedUser)).thenReturn(testUser);
        User actual= testUserController.addUser(addedUser) ;




    // then
    assertAll(
            ()->assertEquals(testUser.getNickname(),actual.getNickname()),
            ()->assertEquals(testUser.getMailadress(),actual.getMailadress())
     );


}
    @Test
    void addUserTestFail(){
        // given
        NewUser addedUser=new NewUser("a@b.com","Anton","wird nicht verraten");
        User testUser=new User("a@b.com","Anton","wird nicht verraten");
        User differentUser=new User("a@b.de","Anton","wird nicht verraten");




        // when
        when(testUserService.addUser(addedUser)).thenReturn(differentUser);
        User actual= testUserController.addUser(addedUser) ;



        // then

        Assertions.assertNotEquals( testUser.getMailadress(),actual.getMailadress());

    }



}