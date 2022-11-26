package com.study.sns.global.response;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResponseService {

    /**
     * 성공 결과만 처리하는 비즈니스 로직
     */
    public CommonResponse getSuccessResult(
            Integer status,
            String message
    ) {
        return new CommonResponse(true, status, message);
    }

    /**
     * 단일건 성공 결과를 처리하는 비즈니스 로직
     */
    public <T> SingleResponse<T> getSingleResult(
            Integer status,
            String message,
            T data
    ) {
        return new SingleResponse<>(true, status, message, data);
    }

    /**
     * 다중건 성공 결과를 처리하는 비스니스 로직
     */
    public <T> ListResponse<T> getListResult(
            Integer status,
            String message,
            List<T> data
    ) {
        return new ListResponse<>(true, status, message, data);
    }
}
