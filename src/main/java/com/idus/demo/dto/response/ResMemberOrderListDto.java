package com.idus.demo.dto.response;

import com.idus.demo.dto.response.inner.ResOrderDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ResMemberOrderListDto {

    @ApiModelProperty(value = "주문 목록", required = true)
    private List<ResOrderDto> orderList = new ArrayList<>();

}
