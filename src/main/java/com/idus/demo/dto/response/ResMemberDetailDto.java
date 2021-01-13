package com.idus.demo.dto.response;

import com.idus.demo.entity.Gender;
import com.idus.demo.entity.Member;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ResMemberDetailDto {

    @ApiModelProperty(value = "이름", required = true)
    private String name;

    @ApiModelProperty(value = "별명", required = true)
    private String nickname;

    @ApiModelProperty(value = "연락처", required = true)
    private String phone;

    @ApiModelProperty(value = "이메일", required = true)
    private String email;

    @ApiModelProperty(value = "성별(M/F)")
    private Gender gender;

    public ResMemberDetailDto(Member member) {
        this.name = member.getName();
        this.nickname = member.getNickname();
        this.phone = member.getPhone();
        this.email = member.getEmail();
        this.gender = member.getGender();
    }
}
