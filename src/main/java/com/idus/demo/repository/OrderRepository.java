package com.idus.demo.repository;

import com.idus.demo.entity.Member;
import com.idus.demo.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Orders, Long> {
    List<Orders> findAllByMemberOrderByCreatedAtDesc(Member member);

    Optional<Orders> findFirstByMemberOrderByCreatedAtDesc(Member member);
}
