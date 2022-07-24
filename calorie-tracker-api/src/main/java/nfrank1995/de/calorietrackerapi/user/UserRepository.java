package nfrank1995.de.calorietrackerapi.user;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findById(String id);
}
