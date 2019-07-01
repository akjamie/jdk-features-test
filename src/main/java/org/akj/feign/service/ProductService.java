package org.akj.feign.service;

import java.util.List;

import org.akj.feign.service.model.Product;

import feign.Headers;
import feign.RequestLine;


public interface ProductService {
	
	@Headers({"Content-Type: application/json","Accept: application/json"})
	@RequestLine("GET /products")
	public List<Product> findAllProducts();

}
