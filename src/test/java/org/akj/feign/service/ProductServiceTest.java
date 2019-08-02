package org.akj.feign.service;

import org.akj.feign.service.model.Product;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;

class ProductServiceTest {

	private ProductService productService = null;

	@BeforeEach
	public void setup() {
		Product product = new Product();
		product.setName("xxx");

		productService = Mockito.mock(ProductService.class);
		Mockito.when(productService.findAllProducts()).thenReturn(Arrays.asList(product));

//		productService = Feign.builder().options(new Options(10, 10)).retryer(new Retryer.Default(10, 10, 3))
//				.decoder(new JacksonDecoder()).encoder(new JacksonEncoder())
//				.target(ProductService.class, "http://127.0.0.1:9001");

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
