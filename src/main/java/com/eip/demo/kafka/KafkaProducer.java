package com.eip.demo.kafka;

import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class KafkaProducer {
	@Autowired
	KafkaTemplate kafkaTemplate;
	
	  @Value(value = "${products.topic.name}")
      private String topicName;

	 public void sendMessage(String message) {

         CompletableFuture<SendResult<String, String>> future = kafkaTemplate.send(topicName, message);
         future.whenComplete((result, ex) -> {

             if (ex == null) {
                 System.out.println("Sent message=[" + message + "] with offset=[" + result.getRecordMetadata()
                     .offset() + "]");
             } else {
                 System.out.println("Unable to send message=[" + message + "] due to : " + ex.getMessage());
             }
         });
     }
	 
	 public <T> void sendMessage(T obj,String topicName) throws JsonProcessingException {
		 ObjectMapper o = new ObjectMapper();
		 String message =o.writeValueAsString(obj);
         CompletableFuture<SendResult<String, String>> future = kafkaTemplate.send(topicName, message);
         future.whenComplete((result, ex) -> {

             if (ex == null) {
                 System.out.println("Sent message=[" + message + "] with offset=[" + result.getRecordMetadata()
                     .offset() + "]");
             } else {
                 System.out.println("Unable to send message=[" + message + "] due to : " + ex.getMessage());
             }
         });
     }
	 
	 
}
