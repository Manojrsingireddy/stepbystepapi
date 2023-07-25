package dev.manojrsingireddy.stepbystep;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OpenAIAPIRequest {
    private String model;
    private List<OpenAIAPIRequestMessage> messages;
    private int max_tokens;
    private double top_p;
    private double temperature;
    private double frequency_penalty;
    private double presence_penalty;
}
