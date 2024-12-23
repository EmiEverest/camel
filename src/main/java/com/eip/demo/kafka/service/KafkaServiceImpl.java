package com.eip.demo.kafka.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.eip.demo.kafka.KafkaProducer;
import com.eip.demo.model.Post;
import com.eip.demo.model.Product;
import com.fasterxml.jackson.core.JsonProcessingException;

@Service
public class KafkaServiceImpl implements KafkaService{

	@Autowired
	KafkaProducer producer;

	
	@Value(value = "${products.list.topic.name}")
	String products_topic;
	
	@Override
	public void insertProductInTopic(Product product,String topic) throws JsonProcessingException {
		// TODO Auto-generated method stub
		producer.sendMessage(product, topic);
		
	}
	@Override
	public void insertListProductInTopic(List<Product> products) throws JsonProcessingException {
		// TODO Auto-generated method stub
		producer.sendMessage(products, products_topic);
	}
	@Override
	public void insertPostInTopic(Post post, String topic) throws JsonProcessingException {
		// TODO Auto-generated method stub
		producer.sendMessage(post,topic);
		
	}

}
