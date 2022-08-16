package ksvoss.backend.user;

import ksvoss.backend.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;
     public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User addUser(NewUser userToAdd){
        if(userRepository.existsUserByMail(userToAdd.mailadress()))return null;
        if(userRepository.existsUserByNickname(userToAdd.nickname()))return null;
        User newUser=new User(userToAdd);
        userRepository.save(newUser);
        return newUser;


    }
}
