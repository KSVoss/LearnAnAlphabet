package ksvoss.backend.alphabet;

import ksvoss.backend.models.Alphabet;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface AlphabetRepository extends MongoRepository<Alphabet, Integer> {

    }
