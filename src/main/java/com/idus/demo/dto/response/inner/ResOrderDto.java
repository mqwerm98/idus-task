package com.idus.demo.dto.response.inner;

import com.idus.demo.entity.Orders;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class ResOrderDto {

    @ApiModelProperty(value = "주문번호", required = true)
    private String orderNo;

    @ApiModelProperty(value = "제품명", required = true)
    private String name;

    @ApiModelProperty(value = "결제일시", required = true)
    private LocalDateTime createdAt;

    public ResOrderDto(Orders orders) {
        this.orderNo = orders.getOrderNo();
        this.name = orders.getName();
        this.createdAt = orders.getCreatedAt();
    }
}
