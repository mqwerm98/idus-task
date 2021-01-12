package com.idus.demo.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * CREATE TABLE `orders` (
 * 	`id` BIGINT NOT NULL AUTO_INCREMENT,
 * 	`order_no` VARCHAR(12) NOT NULL,
 * 	`name` VARCHAR(100) NOT NULL,
 * 	`create_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
 * 	`member_id` BIGINT NOT NULL,
 * 	PRIMARY KEY (`id`),
 * 	CONSTRAINT `FK__member` FOREIGN KEY (`member_id`) REFERENCES `member` (`id`) ON UPDATE CASCADE ON DELETE CASCADE
 * )
 * COLLATE='utf8_general_ci'
 * ENGINE=InnoDB
 */
@Entity
@Getter
@EqualsAndHashCode(of = "id")
@Table(name = "orders")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Orders {

    @Id @GeneratedValue
    private Long id;

    private String orderNo;

    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private LocalDateTime createdAt;

    public Orders(String orderNo, String name, Member member) {
        this.orderNo = orderNo;
        this.name = name;
        this.member = member;
        this.createdAt = LocalDateTime.now();
    }
}
