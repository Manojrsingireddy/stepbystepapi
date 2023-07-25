package dev.manojrsingireddy.stepbystep;

import java.time.LocalDateTime;

import org.bson.types.ObjectId;

import lombok.AllArgsConstructor;
import lombok.Data;

import org.springframework.data.annotation.Id;

import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class Question {
    @Id
    private ObjectId id;
    private String question;
    private String answer;
    Question(String question, String answer){
        this.question = question;
        this.answer = answer;
    }
    public String toString(){
        return question + ": " + answer;
    }
}
