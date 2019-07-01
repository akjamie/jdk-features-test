package org.akj.feign.service.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Product {
	private String id;

	private String code;

	private String name;

	private Amount price;

	private String description;

	private boolean inStock = false;

}