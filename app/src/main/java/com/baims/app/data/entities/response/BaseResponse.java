package com.baims.app.data.entities.response;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Radwa Elsahn on 2/10/2019.
 */
public class BaseResponse {
    @SerializedName("StatusCode")
    private
    int statusCode;
    @SerializedName("Message")
    private
    String message;


    public String toString() {
        return "AccountResponse{" +
                "msg = '" + getMessage() + "\'".toString() +
                ",code = '" + getStatusCode() + "\'".toString() +
                "}";
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
