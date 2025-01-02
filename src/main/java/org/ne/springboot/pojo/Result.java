package org.ne.springboot.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Result<T> {
    private Integer code;
    private String msg;
    private T data;
    public static <E> Result<E> success(E data) {
        return new Result<>(0, "success", data);
    }

    public static Result success() {
        return new Result(0, "success", null);
    }

    public static Result error(String msg) {
        return new Result(1, msg, null);
    }
}
