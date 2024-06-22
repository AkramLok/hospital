package com.hospital.payload.response;

public class MessageResponse {
  private boolean success;
  private String message;

  public MessageResponse(String message) {
    this.message = message;
  }
  public MessageResponse(String message, boolean success) {
    this.message = message;
    this.success = success;
  }

  public boolean isSuccess() {
    return success;
  }

  public void setSuccess(boolean success) {
    this.success = success;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }
}
