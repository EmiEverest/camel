package com.eip.demo.service;

import java.util.List;

import com.eip.demo.model.Order;
import com.eip.demo.model.Post;
import com.eip.demo.model.Product;
import com.eip.demo.model.dto.ProductDTO;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface EipService {

	Order fetchOrder(List<Product> l);

	void execuiteEipPattern(String destination, Order order) ;
	
	List<ProductDTO> fetchSplittedProduct();


	void insertAndTriggerEipSplittedProduct(List<Product> products) throws JsonProcessingException;

	void insertAndTriggerEipAggregateProduct(Product product) throws JsonProcessingException;

	void insertAndTriggerEipContedBaseProduct(Product product) throws JsonProcessingException;

	void insertAndTriggerEipRecipientListProduct(Post post) throws JsonProcessingException;

	List<ProductDTO> fetchSplittedProducts();

	String fetchSelectedRoute();

	String fetchRecipientList();

	String fetchAggregateProducts();
	

}
