package pl.edu.wat.demo.dtos.request;

import lombok.Data;

@Data
public class AddCommentToStepRequest {
    private String gainedStepId;
    private String comment;
}
