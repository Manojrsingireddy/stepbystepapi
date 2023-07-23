package dev.manojrsingireddy.stepbystep;

import java.time.LocalDateTime;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
// import org.springframework.data.mongodb.core.mapping.DocumentReference;

import com.mongodb.internal.connection.Time;

import lombok.AllArgsConstructor;
import lombok.Data;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;

import lombok.NoArgsConstructor;

@Document(collection = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    private ObjectId id;
    private String username;
    private String password;
    private String email;
    User(String username, String password, String email){
        this.username = username;
        this.password = password;
        this.email = email;
    }
    User(String username, String password){
        this.username = username;
        this.password = password;
    }
}