package com.deep.library.exceptions.handler;

import lombok.Data;

import java.util.Date;

@Data
public class ApiResponse {
    private Integer status;
    private Boolean success;
    private Date timestamp;
    private String message;
    private String details;

    public ApiResponse(Integer status, Boolean success, String message, String details) {
        this.status = status;
        this.success = success;
        this.timestamp = new Date();
        this.message = message;
        this.details = details;
    }
}