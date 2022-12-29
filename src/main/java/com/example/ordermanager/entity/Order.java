package com.example.ordermanager.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "buy_order")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "seq_order")
    @SequenceGenerator(name = "seq_order", sequenceName = "sequence_order",  allocationSize = 1)
    private Long id;

    private LocalDateTime creationDate;

    private Integer quantity;

    private String status;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "itemId", referencedColumnName = "id")
    private Item item;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "userId", referencedColumnName = "id")
    private User user;

}

