package dev.manojrsingireddy.stepbystep;


import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
// import org.springframework.data.mongodb.core.mapping.DocumentReference;


import lombok.AllArgsConstructor;
import lombok.Data;

import org.springframework.data.annotation.Id;

import lombok.NoArgsConstructor;
import java.util.List;

@Document(collection = "quizzes")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Goal {
    @Id
    private ObjectId id;
    private String username;
    private String goalText;
    private boolean completed;
    private boolean rejected;
}
