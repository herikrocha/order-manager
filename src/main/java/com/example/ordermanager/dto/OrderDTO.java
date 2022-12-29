package com.example.ordermanager.dto;

import com.example.ordermanager.entity.Item;
import com.example.ordermanager.entity.User;
import com.example.ordermanager.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {
    private Long id;
    private LocalDateTime creationDate;
    @NotNull(message = "Quantity must be informed!")
    private Integer quantity;
    @NotBlank(message = "Status must be informed!")
    private OrderStatus status;
    @NotNull(message = "ItemId must be informed!")
    private Long itemId;
    @NotNull(message = "UserId must be informed!")
    private Long userId;
}
