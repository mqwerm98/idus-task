package com.idus.demo.dto.response;

import com.idus.demo.dto.response.inner.ResMemberDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ResMemberListDto {

    @ApiModelProperty(value = "회원 목록", required = true)
    private List<ResMemberDto> memberList = new ArrayList<>();
}
