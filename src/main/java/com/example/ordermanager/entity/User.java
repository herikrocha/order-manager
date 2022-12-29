package com.example.ordermanager.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "sys_user", uniqueConstraints = @UniqueConstraint(columnNames = {"email"}))
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "seq_user")
    @SequenceGenerator(name = "seq_user", sequenceName = "sequence_user",  allocationSize = 1)
    private Long id;

    private String name;

    private String email;
}

