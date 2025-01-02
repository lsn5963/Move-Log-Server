package com.movelog.global.payload;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.*;
import org.springframework.validation.FieldError;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
public class ErrorResponse {
    private LocalDateTime timestamp = LocalDateTime.now();

    private String message;

    private String code;

    @JsonProperty("class")
    private String clazz;

    private int status;
    private static final ObjectMapper objectMapper = new ObjectMapper();
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("errors")
    private List<CustomFieldError> customFieldErrors = new ArrayList<>();

    public String convertToJson() throws JsonProcessingException {
        return objectMapper.writeValueAsString(this);
    }
    @Builder
    public ErrorResponse(String code, int status, String message, String clazz, List<FieldError> fieldErrors){
        this.code = code;
        this.status = status;
        this.message = message;
        this.clazz = clazz;
        setFieldErrors(fieldErrors);
    }

    public void setFieldErrors(List<FieldError> fieldErrors) {
        if(fieldErrors != null){
            fieldErrors.forEach(error -> {
                customFieldErrors.add(new CustomFieldError(
                        error.getField(),
                        error.getRejectedValue(),
                        error.getDefaultMessage()
                ));
            });
        }
    }
    public static class CustomFieldError {

        private String field;
        private Object value;
        private String reason;

        public CustomFieldError(String field, Object value, String reason) {
            this.field = field;
            this.value = value;
            this.reason = reason;
        }

        public String getField() {
            return field;
        }

        public Object getValue() {
            return value;
        }

        public String getReason() {
            return reason;
        }
    }
}
