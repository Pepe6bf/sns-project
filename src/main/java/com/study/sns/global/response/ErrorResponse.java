package com.study.sns.global.response;

import com.study.sns.global.exception.ErrorCode;
import com.study.sns.global.util.GsonUtil;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.validation.BindingResult;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ErrorResponse {

    private String message;
    private String code;
    private List<FieldError> errors;
    private LocalDateTime timestamp;

    private ErrorResponse(
            final ErrorCode errorCode
    ) {
        this.message = errorCode.getMessage();
        this.code = errorCode.getCode();
        this.errors = new ArrayList<>();
        this.timestamp = LocalDateTime.now();
    }

    private ErrorResponse(
            final ErrorCode errorCode,
            final String message
    ) {
        this.message = message;
        this.code = errorCode.getCode();
        this.errors = new ArrayList<>();
        this.timestamp = LocalDateTime.now();
    }

    private ErrorResponse(
            final ErrorCode errorCode,
            final List<FieldError> errors
    ) {
        this.message = errorCode.getMessage();
        this.code = errorCode.getCode();
        this.errors = errors;
        this.timestamp = LocalDateTime.now();
    }

    public static ErrorResponse of(
            final ErrorCode errorCode
    ) {
        return new ErrorResponse(errorCode);
    }

    public static ErrorResponse of(
            final ErrorCode errorCode,
            final String message
    ) {
        return new ErrorResponse(errorCode, message);
    }

    public static ErrorResponse of(
            final ErrorCode code,
            final BindingResult bindingResult
    ) {
        return new ErrorResponse(
                code,
                FieldError.of(bindingResult)
        );
    }

    public static ErrorResponse of(
            final ErrorCode errorCode,
            final List<FieldError> errors
    ) {
        return new ErrorResponse(errorCode, errors);
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class FieldError {
        private String field;
        private String value;
        private String reason;

        private FieldError(
                String field,
                String value,
                String reason
        ) {
            this.field = field;
            this.value = value;
            this.reason = reason;
        }

        public static List<FieldError> of(
                final String field,
                final String value,
                final String reason
        ) {
            List<FieldError> fieldErrors = new ArrayList<>();
            fieldErrors.add(new FieldError(field, value, reason));
            return fieldErrors;
        }

        private static List<FieldError> of(
                final BindingResult bindingResult
        ) {
            final List<org.springframework.validation.FieldError> fieldErrors = bindingResult.getFieldErrors();
            return fieldErrors.stream()
                    .map(error -> new FieldError(
                            error.getField(),
                            error.getRejectedValue() == null ? "" : error.getRejectedValue().toString(),
                            error.getDefaultMessage()))
                    .collect(Collectors.toList());
        }
    }

    // Object -> Json (필터에서 사용)
    public String convertJson() {
        return new GsonUtil().toJson(this);
    }
}
