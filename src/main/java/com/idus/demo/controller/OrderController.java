package com.idus.demo.controller;

import com.idus.demo.dto.request.ReqOrderDto;
import com.idus.demo.dto.response.ResMemberDetailDto;
import com.idus.demo.dto.response.ResMemberListDto;
import com.idus.demo.dto.response.ResMemberOrderListDto;
import com.idus.demo.dto.response.Response;
import com.idus.demo.dto.response.inner.ResMemberDto;
import com.idus.demo.dto.response.inner.ResOrderDto;
import com.idus.demo.entity.Member;
import com.idus.demo.entity.Orders;
import com.idus.demo.repository.OrderRepository;
import com.idus.demo.service.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api(tags = {"2. Order"})
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/order")
public class OrderController {

    private final OrderRepository orderRepository;
    private final OrderService orderService;

    @ApiOperation(value = "주문하기")
    @PostMapping("")
    public Response createOrder(@AuthenticationPrincipal Member member,
                                @Valid @RequestBody ReqOrderDto dto) {

        orderService.createOrder(dto, member);
        return Response.OK();
    }

    @ApiOperation(value = "단일 회원의 주문 목록 조회")
    @GetMapping("/list")
    public Response<ResMemberOrderListDto> getMemberOrderList(@AuthenticationPrincipal Member member) {
        List<Orders> list = orderRepository.findAllByMemberOrderByCreatedAtDesc(member);

        ResMemberOrderListDto dto = new ResMemberOrderListDto();
        list.forEach(o -> dto.getOrderList().add(new ResOrderDto(o)));
        return Response.OK(dto);
    }
}
