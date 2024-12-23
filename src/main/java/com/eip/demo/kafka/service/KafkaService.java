package com.eip.demo.kafka.service;

import com.eip.demo.model.Post;
import com.eip.demo.model.Product;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;
public interface KafkaService {
	
	void insertProductInTopic(Product product,String topic) throws JsonProcessingException;
	
	void insertListProductInTopic(List<Product> products) throws JsonProcessingException;
	
	void insertPostInTopic(Post post,String topic) throws JsonProcessingException;

}
