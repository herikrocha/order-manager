package com.example.ordermanager.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "seq_item")
    @SequenceGenerator(name = "seq_item", sequenceName = "sequence_item",  allocationSize = 1)
    private Long id;

    private String name;

    private Integer stockAmount;

}

