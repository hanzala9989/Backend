package com.example.eventOrganizer.Uitility;

import java.util.ArrayList;
import java.util.List;

public class ResponseHandler {
    private String StatusCode;
    private String StatusMessage;
    private List<Object> Result = new ArrayList<>();

    public ResponseHandler() {
    }

    
    public ResponseHandler(String statusCode, String statusMessage) {
        this.StatusCode = statusCode;
        this.StatusMessage = statusMessage;
    }
    
    public ResponseHandler(String statusCode, String statusMessage, List<Object> result) {
        this.StatusCode = statusCode;
        this.StatusMessage = statusMessage;
        this.Result = result;
    }

    public String getStatusCode() {
        return StatusCode;
    }

    public ResponseHandler setStatusCode(String statusCode) {
        StatusCode = statusCode;
        return this;
    }

    public String getStatusMessage() {
        return StatusMessage;
    }

    public ResponseHandler setStatusMessage(String statusMessage) {
        StatusMessage = statusMessage;
        return this;
    }

    public List<Object> getResult() {
        return Result;
    }

    public ResponseHandler setResult(List<Object> result) {
        Result = result;
        return this;
    };

}

