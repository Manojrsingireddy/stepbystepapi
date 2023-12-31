package dev.manojrsingireddy.quiz;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.manojrsingireddy.user.User;
import dev.manojrsingireddy.user.UserService;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.http.HttpStatus;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;

import java.util.Map;

/*
 * Run and then visit http://localhost:8080/swagger-ui/index.html#/ to view documentation * 
*/

@RestController
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:8080", "https://stepbystep-webapp.vercel.app"})
@RequestMapping("/api/v1/quizzes")
public class QuizController {
    @Autowired
    private QuizService quizService;
    @Autowired
    private UserService userService;

    @PostMapping()
    public ResponseEntity<Quiz> createQuiz(@RequestBody Map<String, Object> payload){
        String username = (String) payload.get("username");
        List<Map<String, Object>> questionsData = (List<Map<String, Object>>) payload.get("questionList");
        if (username == null || questionsData == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        List<Question> questions = new ArrayList<>();
        for (Map<String, Object> questionData : questionsData) {
            String questionText = (String) questionData.get("question");
            String answerText = (String) questionData.get("answer");

            if (questionText != null && answerText != null) {
                questions.add(new Question(questionText, answerText));
            }
        }
        if (questions.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Optional<User> user = userService.getSingleUser(username);
        if (!user.isPresent()) {
            return new ResponseEntity<>(new Quiz(), HttpStatus.NOT_FOUND);
        }
        Quiz quiz = quizService.createQuiz(questions);
        userService.setQuiz(user.get().getId(), quiz.getId());
        return new ResponseEntity<>(quiz, HttpStatus.CREATED);
    }

    @GetMapping("/{username}")
    public ResponseEntity<Quiz> getQuiz(@PathVariable String username){
        if (username == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Optional<User> user = userService.getSingleUser(username);
        if (!user.isPresent()) {
            return new ResponseEntity<>(new Quiz(), HttpStatus.NOT_FOUND);
        }
        if(user.get().getQuizId() == null){
            return new ResponseEntity<>(new Quiz(), HttpStatus.NOT_FOUND);
        }
        Quiz quiz = quizService.getQuiz(user.get().getQuizId());
        if (quiz == null) {
            return new ResponseEntity<>(new Quiz(), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(quiz, HttpStatus.OK);
    }
    @PutMapping("/{username}")
public ResponseEntity<Quiz> updateQuiz(@PathVariable String username, @RequestBody Quiz payload) {
    if (username == null || payload == null) {
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    Optional<User> user = userService.getSingleUser(username);
    if (!user.isPresent()) {
        return new ResponseEntity<>(new Quiz(), HttpStatus.NOT_FOUND);
    }

    if (user.get().getQuizId() == null) {
        return new ResponseEntity<>(new Quiz(), HttpStatus.NOT_FOUND);
    }

    Quiz existingQuiz = quizService.getQuiz(user.get().getQuizId());
    if (existingQuiz == null) {
        return new ResponseEntity<>(new Quiz(), HttpStatus.NOT_FOUND);
    }

    // Update the existing quiz with the data from the payload
    existingQuiz.setQuestions(payload.getQuestions());

    // Save the updated quiz
    quizService.updateQuiz(existingQuiz);

    return new ResponseEntity<>(existingQuiz, HttpStatus.OK);
}

}
