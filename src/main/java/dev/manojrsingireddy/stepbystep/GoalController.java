// package dev.manojrsingireddy.stepbystep;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.RestController;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.http.HttpStatus;
// import org.springframework.http.HttpStatusCode;
// import org.bson.types.ObjectId;
// import org.springframework.beans.factory.annotation.Autowired;

// import java.util.ArrayList;
// import java.util.List;
// import org.springframework.web.bind.annotation.PathVariable;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.PutMapping;
// import org.springframework.web.bind.annotation.RequestBody;

// import java.util.Optional;
// import org.springframework.web.bind.annotation.CrossOrigin;
// import org.springframework.web.bind.annotation.DeleteMapping;

// import java.util.Map;

// /*
//  * Run and then visit http://localhost:8080/swagger-ui/index.html#/ to view documentation * 
// */

// @RestController
// @CrossOrigin(origins = {"http://localhost:3000", "http://localhost:8080"})
// @RequestMapping("/api/v1/goals")
// public class GoalController {
//     @Autowired GoalService goalService;
//     @Autowired
//     private QuizService quizService;
//     @Autowired
//     private UserService userService;

//     // Get All Goals by User
//     @GetMapping("/{username}")
//     public ResponseEntity<List<Goal>> getGoalsByUsername(@PathVariable String username){
//         List<Goal> goals = goalService.getGoalsByUser(username);
//         return goals != null ? new ResponseEntity<List<Goal>>(goals, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//     }

//     // Get Todays Goal by User


//     // Create New Goals
//     @PostMapping("/create")
//     public ResponseEntity<List<Goal>> createNewGoals(@RequestBody String username){




//     }


//     // Update Goal


// }
