package pl.edu.wat.demo.dtos;

import lombok.Data;

@Data
public class AddCommentToStepRequest {
    private String gainedStepId;
    private String comment;
}
