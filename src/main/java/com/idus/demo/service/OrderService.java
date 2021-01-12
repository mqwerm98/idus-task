package com.idus.demo.service;

import com.idus.demo.dto.request.ReqOrderDto;
import com.idus.demo.dto.response.inner.ResOrderDto;
import com.idus.demo.entity.Member;
import com.idus.demo.entity.Orders;
import com.idus.demo.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {

    private final OrderRepository orderRepository;

    @Transactional
    public void createOrder(ReqOrderDto dto, Member member) {
        String orderNo = (longToBase64(System.currentTimeMillis()) + UUID.randomUUID().toString()).substring(0, 12).toUpperCase();
        Orders order = new Orders(orderNo, dto.getProductName(), member);
        orderRepository.save(order);
    }

    public ResOrderDto getLastOrderByMember(Member member) {
        Optional<Orders> order = orderRepository.findFirstByMemberOrderByCreatedAtDesc(member);
        if(order.isEmpty()) return null;
        return new ResOrderDto(order.get());
    }

    private String longToBase64(long v) {
        final char[] digits = {
                '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j',
                'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't',
                'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D',
                'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N',
                'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X',
                'Y', 'Z', '#', '$'
        };
        int shift = 6;
        char[] buf = new char[64];
        int charPos = 64;
        int radix = 1 << shift;
        long mask = radix - 1;
        long number = v;
        do {
            buf[--charPos] = digits[(int) (number & mask)];
            number >>>= shift;
        } while (number != 0);
        return new String(buf, charPos, (64 - charPos));
    }
}
