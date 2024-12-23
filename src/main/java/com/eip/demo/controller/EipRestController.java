package com.eip.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.camel.ProducerTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.eip.demo.common.EipManageEndPoint;
import com.eip.demo.kafka.service.KafkaService;
import com.eip.demo.model.Order;
import com.eip.demo.model.Post;
import com.eip.demo.model.Product;
import com.eip.demo.model.dto.ProductDTO;
import com.eip.demo.service.EipService;
import com.fasterxml.jackson.core.JsonProcessingException;

@RestController
public class EipRestController {

	@Autowired
	EipService eipService;

	


	@GetMapping("/split-products")
	public ResponseEntity<List<ProductDTO>> startSplitter() {

		Order test = eipService.fetchOrder(new ArrayList<Product>());
		// Invia oggetto alla rotta direct:start
		eipService.execuiteEipPattern(EipManageEndPoint.SPLITTER_STANDALONE_ROUTE, test);
		List<ProductDTO> fetchSplittedProduct = eipService.fetchSplittedProduct();
		return new ResponseEntity<>(fetchSplittedProduct, HttpStatus.OK);
	}

	@PostMapping("/aggregate-products")
	public ResponseEntity<String> startAggregate(@RequestBody Product product) throws JsonProcessingException {
		

//		kafkaService.insertProductInTopic(product,product_topic);
		String aggregateOrders = eipService.executeEipAggregateProduct(product);
		return new ResponseEntity<>(aggregateOrders, HttpStatus.OK);
	}
	
	@PostMapping("/split-products")
	public ResponseEntity<List<ProductDTO>> startSplit(@RequestBody List<Product> products) throws JsonProcessingException {
		List<ProductDTO> executeEipSplittedProduct = eipService.executeEipSplittedProduct(products);

//		kafkaService.insertListProductInTopic(products);
//		List<Product> result=new ArrayList<>();
//		result=products;
//		List<String> aggregateOrders = eipService.fetchAggregateOrders();
		return new ResponseEntity<>(executeEipSplittedProduct, HttpStatus.OK);
	}
	
	@PostMapping("/content-based-products")
	public ResponseEntity<String> startContentBased(@RequestBody Product product) throws JsonProcessingException {
		String executeEipContedBaseProduct = eipService.executeEipContedBaseProduct(product);
		return new ResponseEntity<>(executeEipContedBaseProduct, HttpStatus.OK);

//		kafkaService.insertProductInTopic(product,product_conted_based_topic);
//		List<String> aggregateOrders = eipService.fetchAggregateOrders();
//		return new ResponseEntity<>(aggregateOrders, HttpStatus.OK);
	}
	
	@PostMapping("/recipient-list-post")
	public ResponseEntity<String> startContentBased(@RequestBody Post post) throws JsonProcessingException {
		
		String executeEipRecipientListProduct = eipService.executeEipRecipientListProduct(post);
		return new ResponseEntity<>(executeEipRecipientListProduct, HttpStatus.OK);
//		kafkaService.insertPostInTopic(post,post_topic);
//		List<String> aggregateOrders = eipService.fetchAggregateOrders();
//		return new ResponseEntity<>(aggregateOrders, HttpStatus.OK);
	}
}
