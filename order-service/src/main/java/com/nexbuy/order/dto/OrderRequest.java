package com.nexbuy.order.dto;

import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class OrderRequest {

	@NotEmpty
	private List<OrderItemRequest> items;
}
