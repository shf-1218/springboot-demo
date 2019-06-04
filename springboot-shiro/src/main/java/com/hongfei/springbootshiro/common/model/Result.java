package com.hongfei.springbootshiro.common.model;


public class Result {
    private int code;
    private String message;
    private Object data = "";


    public Result() {
    }

    public Result(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public Result(int code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static Result instance(int code, String msg) {
        return new Result(code, msg);
    }

    public static Result success() {
        return new Result(ResponseCode.success.getCode(), ResponseCode.success.getMessage());
    }

    public static Result success(Object data) {
        return new Result(ResponseCode.success.getCode(), ResponseCode.success.getMessage(), data);
    }

    public static Result error() {
        return new Result(ResponseCode.error.getCode(), ResponseCode.error.getMessage());
    }

    public static Result error(String msg) {
        return new Result(ResponseCode.error.getCode(), msg);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Result{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
