package com.eip.demo.strategy;

import org.apache.camel.AggregationStrategy;
import org.apache.camel.Exchange;

import com.eip.demo.model.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductAggregationStrategy implements AggregationStrategy {

	@Override
	public Exchange aggregate(Exchange oldExchange, Exchange newExchange) {
	    Product newProduct = newExchange.getIn().getBody(Product.class);
	    System.out.println("Aggregating product: " + newProduct.getProductName());

	    if (oldExchange == null) {
	        List<Product> productList = new ArrayList<>();
	        productList.add(newProduct);
	        newExchange.getIn().setBody(productList);
	        return newExchange;
	    }

	    List<Product> productList = oldExchange.getIn().getBody(List.class);
	    productList.add(newProduct);
	    oldExchange.getIn().setBody(productList);
	    return oldExchange;
	}

}
