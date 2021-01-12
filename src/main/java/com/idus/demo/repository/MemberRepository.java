package com.idus.demo.repository;

import com.idus.demo.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByEmail(String email);

    boolean existsByEmail(String email);

    Page<Member> findByName(String name, PageRequest pr);

    Page<Member> findByEmail(String email, PageRequest pr);
}
