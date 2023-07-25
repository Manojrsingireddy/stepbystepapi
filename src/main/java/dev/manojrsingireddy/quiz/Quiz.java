package dev.manojrsingireddy.quiz;

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
import java.util.List;
import java.lang.StringBuilder;

@Document(collection = "quizzes")
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Quiz {
    @Id
    private ObjectId id;
    private List<Question> questions;
    Quiz(List<Question> questions){
        this.questions = questions;
    }
    public String toString(){
        StringBuilder sb = new StringBuilder();
        for(Question q : questions){
            sb.append(q.toString() + "; ");
        }
        return sb.toString();
    }
}
