package io.github.arthurdev.rest.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreatePostRequest {
    
    @NotBlank(message = "text is required")
    private String text;
}
