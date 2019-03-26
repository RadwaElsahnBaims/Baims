package com.baims.app.data.entities.response;

/**
 * Created by Radwa Elsahn on 3/7/2019.
 */
public class ErrorBody {
    private String Message;
    private String StatusCode;

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public String getStatusCode() {
        return StatusCode;
    }

    public void setStatusCode(String statusCode) {
        StatusCode = statusCode;
    }
}
