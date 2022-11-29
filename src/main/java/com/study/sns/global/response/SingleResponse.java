package com.study.sns.global.response;

import lombok.Getter;

@Getter
public class SingleResponse<T> extends CommonResponse {

    private final T data;

    public SingleResponse(
            Boolean isSuccess,
            String message,
            T data
    ) {
        super(isSuccess, message);
        this.data = data;
    }
}
