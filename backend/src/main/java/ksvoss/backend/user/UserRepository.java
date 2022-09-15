package ksvoss.backend.user;

import ksvoss.backend.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
 public interface UserRepository extends MongoRepository<User, String> {
    boolean existsUserByMailadress(String mailadress);

    boolean existsUserByNickname(String nickname);

    Optional<User> findUserByMailadress(String userMailadress);
}


 