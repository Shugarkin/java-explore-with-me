package ru.practicum.main.exception;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ErrorResponse {

    private String status;
    private String reason;
    private String message;
    private LocalDateTime timestamp;

    public ErrorResponse(String status, String reason, String message, LocalDateTime timestamp) {
        this.status = status;
        this.reason = reason;
        this.message = message;
        this.timestamp = timestamp;
    }
//          "status": "BAD_REQUEST",
//          "reason": "Incorrectly made request.",
//          "message": "Field: name. Error: must not be blank. Value: null",
//          "timestamp": "2022-09-07 09:10:50"
}
