package com.study.sns.global.response;

import lombok.Getter;

import java.util.List;

@Getter
public class ListResponse<T> extends CommonResponse {

    private final List<T> data;

    public ListResponse(
            Boolean isSuccess,
            Integer status,
            String message,
            List<T> data
    ) {
        super(isSuccess, status, message);
        this.data = data;
    }
}
