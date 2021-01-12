package com.idus.demo.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class ReqOrderDto {

    @ApiModelProperty(value = "상품명", required = true)
    @NotBlank
    @Size(max = 100)
    private String productName;

}
