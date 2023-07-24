package dev.manojrsingireddy.stepbystep;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import java.util.Optional;
import org.bson.types.ObjectId;

@Service
public class GoalService {
    @Autowired
    private GoalRepository goalRepository;
    public List<Goal> getGoalsByUser(String username){
        Optional <List<Goal>> goalList = goalRepository.findByUsername(username);
        return goalList.isPresent() ? goalList.get() : null;
    }
    
}
