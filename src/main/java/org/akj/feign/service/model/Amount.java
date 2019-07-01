package org.akj.feign.service.model;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Amount {
	private String currency = "CNY";

	private BigDecimal amount;

}
