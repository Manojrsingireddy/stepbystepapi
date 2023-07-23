package dev.manojrsingireddy.stepbystep;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import java.util.Optional;
import org.bson.types.ObjectId;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    public List<User> allUsers(){
        return userRepository.findAll();
    }    
    public Optional<User> getSingleUser(String username){
        return userRepository.findByUsername(username);
    }

    public User login(String username, String password){
        Optional<User> user = userRepository.findByUsername(username);
        if(user.isPresent()){
            if(user.get().getPassword().equals(password))
                return user.get();
        }
        return null;
    }

    public boolean existsByUsername(String username){
        return userRepository.existsByUsername(username);
    }

    

    public User deleteUser(String username){
        Optional<User> user = userRepository.findByUsername(username);
        if(user.isPresent()){
            userRepository.delete(user.get());
            return user.get();
        }
        return null;
    }

    public User updateUser(ObjectId id, User update_user){
        Optional<User> user = userRepository.findById(id);
        if(user.isPresent()){
            user.get().setUsername(update_user.getUsername());
            user.get().setPassword(update_user.getPassword());
            user.get().setEmail(update_user.getEmail());
            return userRepository.save(user.get());
        }
        return null;
    }

    public void setQuiz(ObjectId userId, ObjectId quizId){
        Optional<User> user = userRepository.findById(userId);
        if(user.isPresent()){
            user.get().setQuizId(quizId);
            userRepository.save(user.get());
        }
    }

    public User register(String username, String password, String email){
        return userRepository.save(new User(username, password, email));
    }
}