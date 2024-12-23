package com.eip.demo.service;

import java.util.List;

import org.apache.camel.ProducerTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.eip.demo.common.EipCommon;
import com.eip.demo.component.*;
import com.eip.demo.kafka.service.KafkaService;
import com.eip.demo.mapper.ProductMapper;
import com.eip.demo.model.Order;
import com.eip.demo.model.Post;
import com.eip.demo.model.Product;
import com.eip.demo.model.dto.ProductDTO;
import com.fasterxml.jackson.core.JsonProcessingException;

@Service
public class EipServiceImpl implements EipService {
	@Autowired
	ProducerTemplate producerTemplate;
	
	@Autowired
	SplitterRouteDefinition splitterRouteDefinition;
	
	@Autowired
	SplitterRouterKafka splitterRouteKafka;
	
	@Autowired
	AggregateRouteDefinition aggregateRouteDefinition;
	
	@Autowired
	ContentBasedRouterDefinition contentBasedRouterDefinition;
	
	@Autowired
	RecipientListDefinition recipientListDefinition;
	
	@Autowired
	ProductMapper productMapper;
	
	@Autowired
	KafkaService kafkaService;
	
	@Value(value = "${products.topic.name}")
	String product_topic;
	
	@Value(value = "${products.conted-based.topic.name}")
	String product_conted_based_topic;
	
	@Value(value = "${post.topic.name}")
	String post_topic;
	
	@Override
	public Order fetchOrder(List<Product> products) {
		products.add(new Product("Maglia",1,99));
		products.add(new Product("Pantaloni",1,100));
		 EipCommon.increase_Splitter_stand_alone_Id();
		 Order test=new Order(EipCommon.getSplitter_stand_alone_Id(),products);
		 return test;
	}
	
	@Override
	public void execuiteEipPattern(String destination,Order order)  {
		producerTemplate.sendBody(destination, order);
		 try {
			// Aspetta qualche secondo per vedere il risultato nei log
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	@Override
	public List<ProductDTO> fetchSplittedProduct(){
		return productMapper.productsToProductDTOs(splitterRouteDefinition.getProducts());
	}

	@Override
	public void execuiteEipAggregatePattern(String destination, List<Order> orders) {
		// TODO Auto-generated method stub
		producerTemplate.sendBody(destination, orders);
		 try {
			// Aspetta qualche secondo per vedere il risultato nei log
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	}
	
	@Override
	public	List<ProductDTO> executeEipSplittedProduct(List<Product> products) throws JsonProcessingException{
		kafkaService.insertListProductInTopic(products);
		 try {
			// Aspetta qualche secondo per vedere il risultato nei log
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return productMapper.productsToProductDTOs(splitterRouteKafka.getProducts());
	}
	
	@Override
	public	String executeEipAggregateProduct(Product product) throws JsonProcessingException{
		kafkaService.insertProductInTopic(product,this.product_topic);
		 try {
			// Aspetta qualche secondo per vedere il risultato nei log
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return aggregateRouteDefinition.getAggregateOrders();
	}
	
	@Override
	public	String executeEipContedBaseProduct(Product product) throws JsonProcessingException{
		kafkaService.insertProductInTopic(product,product_conted_based_topic);
		 try {
			// Aspetta qualche secondo per vedere il risultato nei log
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return contentBasedRouterDefinition.getSelectedRoute();
	}
	
	@Override
	public	String executeEipRecipientListProduct(Post post) throws JsonProcessingException{
		kafkaService.insertPostInTopic(post,post_topic);
		 try {
			// Aspetta qualche secondo per vedere il risultato nei log
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return recipientListDefinition.getSendMessage();
	}
	


}
