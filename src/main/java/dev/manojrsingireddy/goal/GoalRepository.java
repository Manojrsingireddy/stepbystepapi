package dev.manojrsingireddy.goal;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.List;

public interface GoalRepository extends MongoRepository<Goal, ObjectId>{
    Optional<List<Goal>> findByUsername(String username);
    Optional<List<Goal>> findByUsernameAndRejectedIsFalseAndCompletedIsFalse(String username);
    Optional<Goal> findById(String id);
}
