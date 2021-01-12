package com.idus.demo.dto.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@NoArgsConstructor
public class ResponseError {

    @ApiModelProperty("에러 메세지")
    private String message;

    @ApiModelProperty("에러 상태")
    private int status;

    ResponseError(String message, HttpStatus status) {
        this.message = message;
        this.status = status.value();
    }
}
