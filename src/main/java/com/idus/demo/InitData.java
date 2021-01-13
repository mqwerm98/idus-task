package com.idus.demo;

import com.idus.demo.entity.Gender;
import com.idus.demo.entity.Member;
import com.idus.demo.entity.Orders;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import java.util.UUID;

import static com.idus.demo.service.OrderService.longToBase64;

@Component
@RequiredArgsConstructor
public class InitData {

    private final InitService initService;

    @PostConstruct
    public void init() {
//        initService.create();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {

        private final EntityManager em;
        private final PasswordEncoder passwordEncoder;

        public void create() {
            Member member = new Member("박현진", "hjpark", passwordEncoder.encode("Test1234!!!!"), "01012341234", "hjpark@test.com", Gender.F);
            em.persist(member);

            String orderNo = (longToBase64(System.currentTimeMillis()) + UUID.randomUUID().toString()).substring(0, 12).toUpperCase();
            Orders order = new Orders(orderNo, "test order", member);
            em.persist(order);
        }

    }
}
