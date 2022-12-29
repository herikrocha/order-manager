package com.example.ordermanager.entity;

import com.example.ordermanager.enums.OrderType;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StockMovement {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "seq_stock_movement")
    @SequenceGenerator(name = "seq_stock_movement", sequenceName = "sequence_stock_movement",  allocationSize = 1)
    private Long id;

    private LocalDateTime creationDate;

    private Integer quantity;

    private OrderType type;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "itemId", referencedColumnName = "id")
    private Item item;

}

