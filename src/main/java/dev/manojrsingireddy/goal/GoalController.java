package dev.manojrsingireddy.goal;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.util.ArrayList;
import java.util.Map;
import java.util.List;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.ObjectMapper;

import dev.manojrsingireddy.quiz.Quiz;
import dev.manojrsingireddy.quiz.QuizService;
import dev.manojrsingireddy.user.User;
import dev.manojrsingireddy.user.UserService;

import com.fasterxml.jackson.core.type.TypeReference;





import java.util.Map;
import org.springframework.beans.factory.annotation.Value;

/*
 * Run and then visit http://localhost:8080/swagger-ui/index.html#/ to view documentation * 
*/

@RestController
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:8080"})
@RequestMapping("/api/v1/goals")
public class GoalController {

    @Autowired 
    GoalService goalService;
    
    private final String open_ai_url = "https://api.openai.com/v1/chat/completions"; // Updated endpoint
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private QuizService quizService;
    @Autowired
    private UserService userService;

    @Value("${open.ai.key}")
     String openaiApiKey;
    
    // Get All Goals by User
    @GetMapping()
    public ResponseEntity<List<Goal>> getGoalsByUsername(@RequestBody Map<String, String> payload){
        List<Goal> goals = goalService.getGoalsByUser(payload.get("username"));
        return goals != null ? new ResponseEntity<List<Goal>>(goals, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    // Get Todays Goal by User
    @GetMapping("/today")
    public ResponseEntity<List<Goal>> getTodaysGoal(@RequestBody Map<String, String> payload){
        List<Goal> goals = goalService.getGoalsByUser(payload.get("username"));


        return goals != null ? new ResponseEntity<List<Goal>>(goals, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }


    // Create New Goals
    @PostMapping("/create")
    public ResponseEntity<List<Goal>> createNewGoals(@RequestBody Map<String, String> payload){
        String username = payload.get("username");
        List<Goal> goalList = getGoals(username, 20);
        if(goalList == null){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        else{
            return new ResponseEntity<>(goalList, HttpStatus.OK);
        }
    }

    private List<Goal> getGoals(String username, int number_of_goals) {
        // Get the user with the given username
        Optional<User> user = userService.getSingleUser(username);

        // If the user doesn't exist or doesn't have a quiz, return an empty list
        if (!user.isPresent() || user.get().getQuizId() == null){
            return new ArrayList<>();
        }

        // Get the quiz associated with the user
        Quiz quiz = quizService.getQuiz(user.get().getQuizId());

        // Prepare the request payload for OpenAI API
        OpenAIAPIRequest requestPayload = new OpenAIAPIRequest();
        requestPayload.setModel("gpt-3.5-turbo");

        // Add system and user messages to the request payload
        OpenAIAPIRequestMessage systemMessage1 = new OpenAIAPIRequestMessage();
        systemMessage1.setRole("system");
        systemMessage1.setContent("You are a medical professional. A user will provide you with a sample of a quiz with health questions and answers that corresond to their health situation. You will generate 20 specific and measurable daily goals for an individual based on the provided \"questions\" and \"answers\" array. The goals should focus on building healthier long-term health habits and improving their overall health. Please ensure that the goals are presented without numbering or unnecessary information, and use medical data from the \"answers\" array to make the goals personalized and relevant to the individual's health profile.");

        OpenAIAPIRequestMessage systemMessage2 = new OpenAIAPIRequestMessage();
        systemMessage2.setRole("system");
        systemMessage2.setContent("Your responses should be in JSON format. Here is an example of what I want the response to look like [ {\"1\" : \"First Goal Value\"},   {\"2\" : \"Second Goal Value\"},  {\"3\" : \"Third Goal Value\"} ]");

        OpenAIAPIRequestMessage userMessage = new OpenAIAPIRequestMessage();
        userMessage.setRole("user");
        userMessage.setContent(quiz.toString());

        // Set messages and other parameters for the request
        requestPayload.setMessages(List.of(systemMessage1, systemMessage2, userMessage));
        requestPayload.setMax_tokens(512);
        requestPayload.setTop_p(1);
        requestPayload.setTemperature(0);
        requestPayload.setFrequency_penalty(0);
        requestPayload.setPresence_penalty(0.2);

        String requestJson;
        try {
            // Convert the request payload to JSON string
            ObjectMapper objectMapper = new ObjectMapper();
            requestJson = objectMapper.writeValueAsString(requestPayload);
        } catch (Exception e) {
            // If there is an error in JSON serialization, return null
            return null;
        }

        // Prepare headers for the API call
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + openaiApiKey);

        // Create the HTTP request entity with the payload and headers
        HttpEntity<String> requestHttp = new HttpEntity<>(requestJson, headers);

        // Make a POST request to the OpenAI API and get the response
        ResponseEntity<String> response = restTemplate.postForEntity(open_ai_url, requestHttp, String.class);

        // Check if the response is not successful (not HttpStatus.OK)
        if(response.getStatusCode()!= HttpStatus.OK){
            // Return null if there was an error in the API call
            return null;
        }

        // Prepare a list to hold the generated goals
        List<Goal> goals = new ArrayList<>();
        try {
            // Parse the response JSON to extract the generated goals
            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, Object> jsonMap = objectMapper.readValue(response.getBody(), Map.class);

            List<Map<String, Object>> choices = (List<Map<String, Object>>) jsonMap.get("choices");
            if (choices != null && !choices.isEmpty()) {
                Map<String, Object> choice = choices.get(0);
                Map<String, Object> message = (Map<String, Object>) choice.get("message");
                String content = (String) message.get("content");

                // Deserialize the generated goals from JSON to a list of maps
                ObjectMapper mapper = new ObjectMapper();
                List<Map<String, String>> goalList = mapper.readValue(content, new TypeReference<List<Map<String, String>>>() {});

                int goalCounter = 1;
                // Convert each map entry to a Goal object and add to the goals list
                for (Map<String, String> goalMap : goalList) {
                    String goalText = goalMap.get(String.valueOf(goalCounter++));
                    Goal goal = new Goal();
                    goal.setUsername(username); // Set the username
                    goal.setGoalText(goalText);
                    goal.setCompleted(false); // Set completed and rejected to default values
                    goal.setRejected(false);
                    goalService.addGoal(goal); // Add the goal to the database
                    goals.add(goal); // Add the goal to the list
                }
            }
        } catch (Exception e) {
            // If there was an error in JSON deserialization or other processing, print the stack trace
            e.printStackTrace();
        }

        // Return the list of generated goals
        return goals;
}
    // Update Goal
    @PutMapping()
    public ResponseEntity<Goal> updateGoal(@RequestBody Map<String, String> payload){
        String goalId = payload.get("goalId");
        String goalText = payload.get("goalText");
        String completed = payload.get("completed");
        String rejected = payload.get("rejected");
        Goal goal = goalService.getGoal(goalId);
        if(goal == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        else{
            goal.setGoalText(goalText);
            goal.setCompleted(Boolean.parseBoolean(completed));
            goal.setRejected(Boolean.parseBoolean(rejected));
            goalService.addGoal(goal);
            return new ResponseEntity<>(goal, HttpStatus.OK);
        }
    }


}
