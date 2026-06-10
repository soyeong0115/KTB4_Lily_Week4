package org.example.communityapi.common;

public class ApiResponse<T> {

    private String message;
    private T data;

    public ApiResponse(String message, T data) {
        this.message = message;
        this.data = data;
    }

    public static <T> ApiResponse<T> success(String message, T data) {
        return new ApiResponse<>(message, data);
    }

    public static ApiResponse<Void> fail(String message) {
        return new ApiResponse<>(message, null);
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }
}