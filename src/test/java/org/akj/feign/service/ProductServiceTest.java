package org.akj.feign.service;

import java.util.List;

import org.akj.feign.service.model.Product;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import feign.Feign;
import feign.Request.Options;
import feign.Retryer;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;

class ProductServiceTest {

	private ProductService productService = null;

	@BeforeEach
	public void setup() {
		productService = Feign.builder().options(new Options(10, 10)).retryer(new Retryer.Default(10, 10, 3))
				.decoder(new JacksonDecoder()).encoder(new JacksonEncoder())
				.target(ProductService.class, "http://127.0.0.1:9001");

	}

	@Test
	void test() {
		List<Product> products = productService.findAllProducts();

		Assertions.assertNotNull(products);
		products.forEach(item -> {
			System.out.println(item.getName());
		});
	}

}
