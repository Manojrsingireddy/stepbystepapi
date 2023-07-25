package dev.manojrsingireddy.stepbystep;

import lombok.AllArgsConstructor;
import lombok.Data;

import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OpenAIAPIRequestMessage {
    private String role;
    private String content;
}
