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
	void execuiteEipAggregatePattern(String destination,List<Order> orders) ;
	List<ProductDTO> fetchSplittedProduct();


	List<ProductDTO> executeEipSplittedProduct(List<Product> products) throws JsonProcessingException;

	String executeEipAggregateProduct(Product product) throws JsonProcessingException;

	String executeEipContedBaseProduct(Product product) throws JsonProcessingException;

	String executeEipRecipientListProduct(Post post) throws JsonProcessingException;
	

}
