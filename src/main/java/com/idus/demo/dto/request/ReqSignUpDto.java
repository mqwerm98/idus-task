package com.idus.demo.dto.request;

import com.idus.demo.entity.Gender;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.*;


@Data
public class ReqSignUpDto {

    @ApiModelProperty(value = "이름", required = true)
    @NotBlank
    @Size(max=20)
    @Pattern(regexp = "^[가-힣a-zA-Z]{2,20}$")
    private String name;

    @ApiModelProperty(value = "별명", required = true)
    @NotBlank
    @Size(max=30)
    @Pattern(regexp = "^[a-z]{3,30}$")
    private String nickname;

    @ApiModelProperty(value = "비밀번호", required = true)
    @NotBlank
    @Size(min=10, max=50)
    @Pattern(regexp = "^(?=.*[a-zA-z])(?=.*[0-9])(?=.*[$`~!@$!%*#^?&\\\\(\\\\)\\-_=+]).{10,50}$")
    private String password;

    @ApiModelProperty(value = "연락처", required = true)
    @NotBlank
    @Size(min=10)
    @Pattern(regexp = "^\\d{3}\\d{3,4}\\d{4}$")
    private String phone;

    @ApiModelProperty(value = "이메일", required = true)
    @NotBlank
    @Email
    private String email;

    @ApiModelProperty(value = "성별")
    private Gender gender;
}
