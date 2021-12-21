package com.mycompany.webapp.dto.order;

import java.util.List;

import com.mycompany.webapp.dto.product.OrderItemDto;

import lombok.Data;

@Data
public class CancelOrderItemResult {
	List<OrderItemDto> cancelOrderItemList;
}
