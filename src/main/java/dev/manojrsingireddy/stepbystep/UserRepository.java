package dev.manojrsingireddy.stepbystep;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.List;

@Repository
public interface UserRepository extends MongoRepository<User, ObjectId>{
    Optional<User> findByUsername(String username);
    boolean existsByUsername(String username);
    Optional<User> findById(ObjectId id);
    List<User> findAll();
}

