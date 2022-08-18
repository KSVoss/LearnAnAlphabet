package ksvoss.backend.user;

import ksvoss.backend.models.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


class UserServiceTest {
    private final UserRepository userRepository = mock(UserRepository.class);
    private final UserService userService = new UserService(userRepository);

    @Test
    void addUserTestPass() {
        //given
        NewUser newUserToAdd = new NewUser("a@b.com", "Anton", "wird nicht verraten");

        when(userRepository.existsUserByMailadress(newUserToAdd.mailadress())).thenReturn(false);
        when(userRepository.existsUserByNickname(newUserToAdd.nickname())).thenReturn(false);

        User addedUser = new User("a@b.com", "Anton", "wird nicht verraten");
        when(userRepository.save(addedUser)).thenReturn(addedUser);


        //when
        User actual = userService.addUser(newUserToAdd);

        //then
        Assertions.assertEquals(addedUser.getNickname(), actual.getNickname());
    }

    @Test
    void addUserTestFailMailadress() {
        //given
        NewUser newUserToAdd = new NewUser("a@b.com", "Anton", "wird nicht verraten");

        when(userRepository.existsUserByMailadress(newUserToAdd.mailadress())).thenReturn(true);
        when(userRepository.existsUserByNickname(newUserToAdd.nickname())).thenReturn(false);

        User addedUser = new User("a@b.com", "Anton", "wird nicht verraten");
        when(userRepository.save(addedUser)).thenReturn(addedUser);


        //when
        User actual = userService.addUser(newUserToAdd);

        //then
        Assertions.assertNull(actual);

    }

    @Test
    void addUserTestFailNickname() {
        //given
        NewUser newUserToAdd = new NewUser("a@b.com", "Anton", "wird nicht verraten");

        when(userRepository.existsUserByMailadress(newUserToAdd.mailadress())).thenReturn(false);
        when(userRepository.existsUserByNickname(newUserToAdd.nickname())).thenReturn(true);

        User addedUser = new User("a@b.com", "Anton", "wird nicht verraten");
        when(userRepository.save(addedUser)).thenReturn(addedUser);


        //when
        User actual = userService.addUser(newUserToAdd);

        //then
        Assertions.assertNull (actual);

    }

}