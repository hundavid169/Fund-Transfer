package com.fund.fund_transfer.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.util.HashMap;

@Data
@Builder
@ToString
@AllArgsConstructor
public class ApiResponse<T> {

    public static final Integer OK = 200;
    public static final Integer NOT_FOUND = 404;
    public static final Integer BAD_REQUEST = 400;
    public static final Integer UNAUTHORIZED = 401;
    public static final Integer INTERNAL_SERVER_ERROR = 500;

    private Integer code;
    private String message;

    private HashMap<String, String> errors;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Pagination pagination;

    public ApiResponse() {
        this.code = OK;
        this.message = "OK";
    }

    public ApiResponse(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public ApiResponse(T data) {
        this.code = OK;
        this.message = "OK";
        this.data = data;
    }

    public ApiResponse(T data, Pagination pagination) {
        this.code = OK;
        this.message = "OK";
        this.data = data;
        this.pagination = pagination;
    }

    public ApiResponse(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public ApiResponse(Integer code, T data) {
        this.code = code;
        this.data = data;
    }

    public ApiResponse(HashMap<String, String> errors) {
        this.code = BAD_REQUEST;
        this.errors = errors;
        this.message = "The given data was invalid.";
    }

    public String toJson() throws JsonProcessingException {
        ObjectMapper objMapper = new ObjectMapper();
        objMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        return objMapper.writeValueAsString(this);
    }
}
