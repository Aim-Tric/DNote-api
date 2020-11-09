package top.devonte.note.common;

public class ApiResult<T> {

    private int code;
    private String msg;
    private T data;

    public static <T> ApiResult<T> ok() {
        return new ApiResult<>(10000, "请求成功");
    }

    public static <T> ApiResult<T> ok(String msg) {
        return new ApiResult<>(10000, msg);
    }

    public static <T> ApiResult<T> ok(T data) {
        return new ApiResult<>(10000, "请求成功", data);
    }

    public static <T> ApiResult<T> fail(String msg) {
        return new ApiResult<>(40000, msg);
    }

    public ApiResult(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public ApiResult(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
