package ksvoss.backend.user;

import ksvoss.backend.models.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class UserControllerTest {

    private final UserService testUserService = mock(UserService.class);
    private final UserController testUserController = new UserController(testUserService);
/*@Test
    void addUserTestPass(){
    // given
        NewUser addedUser=new NewUser("a@b.com","Anton","wird nicht verraten");
        User testUser=new User("a@b.com","Anton","wird nicht verraten");
    // when
        when(testUserService.addUser(addedUser)).thenReturn(testUser);
        ResponseEntity<User> actual= testUserController.addUser(addedUser) ;




    // then
    Assertions.assertEquals(HttpStatus.CREATED,actual.getStatusCode());

}
    @Test
    void addUserTestFail(){
        // given
        NewUser addedUser=new NewUser("a@b.com","Anton","wird nicht verraten");

        // when
        when(testUserService.addUser(addedUser)).thenReturn(null);
        ResponseEntity<User> actual= testUserController.addUser(addedUser) ;



        // then
        Assertions.assertEquals(HttpStatus.CONFLICT,actual.getStatusCode());

    }*/

}