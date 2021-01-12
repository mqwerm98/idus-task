package com.idus.demo.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class ReqLoginDto {

    @ApiModelProperty(value = "이메일", required = true)
    @NotBlank
    @Email
    private String email;

    @ApiModelProperty(value = "비밀번호", required = true)
    @NotBlank
    @Size(min=10)
    private String password;

}
