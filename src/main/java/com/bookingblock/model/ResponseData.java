package com.bookingblock.model;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseData<T> {
    private final T data;
    private final String message;

    public ResponseData(String message) {
        this.message = message;
        this.data = null;
    }

    public ResponseData(T data) {
        this.data = data;
        this.message = null;
    }

    public String getMessage() {
        return this.message;
    }

    public T getData() {
        return this.data;
    }
}
