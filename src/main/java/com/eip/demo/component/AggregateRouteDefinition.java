package com.eip.demo.component;

import java.util.ArrayList;
import java.util.List;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

import com.eip.demo.model.Product;
import com.eip.demo.strategy.ProductAggregationStrategy;

@Component
public class AggregateRouteDefinition extends RouteBuilder {

	private String aggregateOrders;
    @Override
    public void configure() throws Exception {from("kafka:products?brokers={{spring.kafka.bootstrap-servers}}")
        .routeId("kafka-product-aggregation")
        .log("Received message: ${body}")
        .unmarshal().json(Product.class)
        // Estrarre 'productName' e usarlo come chiave di aggregazione
        .setHeader("productName", simple("${body.productName}"))
        .aggregate(header("productName"), new ProductAggregationStrategy())
            .completionSize(3)
            .completionTimeout(50000)
        .log("Aggregated products: ${body}")
        .marshal().json()
        .process(exchange -> {
        	aggregateOrders = exchange.getIn().getBody(String.class);
        })
        .to("kafka:aggregated-products?brokers={{spring.kafka.bootstrap-servers}}")
        .end();
    }
	public String getAggregateOrders() {
		return aggregateOrders;
	}
	public void setAggregateOrders(String aggregateOrders) {
		this.aggregateOrders = aggregateOrders;
	}
}
