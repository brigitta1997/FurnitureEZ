package com.example.user.furnitureproject2.Model;

public class ResponseMessage {
    private String success;
    private String error;
    private String message;

    public ResponseMessage(String success, String error, String message) {
        this.success = success;
        this.error = error;
        this.message = message;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
