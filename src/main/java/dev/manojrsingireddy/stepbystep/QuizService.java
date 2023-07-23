package dev.manojrsingireddy.stepbystep;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import java.util.Optional;
import org.bson.types.ObjectId;

@Service
public class QuizService {
    @Autowired
    private QuizRepository quizRepository;

    public Quiz createQuiz(List<Question> questions){
        return quizRepository.save(new Quiz(questions));
    }
    public Quiz getQuiz(ObjectId id){
        Optional<Quiz> quiz = quizRepository.findById(id);
        if (quiz.isPresent()) {
            return quiz.get();
        }
        return null;
    }
}
