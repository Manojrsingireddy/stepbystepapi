package dev.manojrsingireddy.user;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
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
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:8080"})
@RequestMapping("/api/v1/users")
public class UserController {
    @Autowired
    private UserService userService;
    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestBody User user){
        User res = userService.login(user.getUsername(), user.getPassword());
        return res == null ? new ResponseEntity<>(HttpStatus.BAD_REQUEST) : new ResponseEntity<>(res, HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody User user){
        if(userService.existsByUsername(user.getUsername()))
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        else
            return new ResponseEntity<User>(userService.register(user.getUsername(), user.getPassword(), user.getEmail()), HttpStatus.CREATED);
    }

    // @GetMapping("/{id}")
    // public ResponseEntity<Optional<User>> getUser(@PathVariable String id){
    //     return new ResponseEntity<Optional<User>>(userService.getSingleUser(id), HttpStatus.OK);
    // }

    // @PutMapping("/{id}")
    // public ResponseEntity<User> updateUser(@PathVariable String id, @RequestBody Map<String, String> payload){
    //     User updatedUser = new User(payload.get("username"), payload.get("password"), payload.get("email"));
    //     return new ResponseEntity<User>(userService.updateUser(new ObjectId(id), updatedUser), HttpStatus.OK);
    // }

    // @DeleteMapping("/{id}")
    // public ResponseEntity<User> deleteUser(@PathVariable String id){
    //     return new ResponseEntity<User>(userService.deleteUser(id), HttpStatus.OK);
    // }
}