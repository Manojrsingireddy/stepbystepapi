package dev.manojrsingireddy.goal;


import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
// import org.springframework.data.mongodb.core.mapping.DocumentReference;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import dev.manojrsingireddy.support.ObjectIdDeserializer;
import dev.manojrsingireddy.support.ObjectIdSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;

import org.springframework.data.annotation.Id;

import lombok.NoArgsConstructor;
import java.util.List;

@Document(collection = "goals")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Goal {
    @JsonSerialize(using = ObjectIdSerializer.class)
    @JsonDeserialize(using = ObjectIdDeserializer.class)
    private ObjectId id;
    private String username;
    private String goalText;
    private boolean completed;
    private boolean rejected;
}
