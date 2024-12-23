package com.eip.demo.component;

import java.util.ArrayList;
import java.util.List;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.stereotype.Component;

import com.eip.demo.model.Product;

@Component
public class SplitterRouteDefinition extends RouteBuilder {

	private List<Product> products;

	@Override
	public void configure() throws Exception {
		// A rotta che prende un ordine (Order), lo converte in un JSON
		this.products = new ArrayList<Product>();
		from("direct:start").routeId("orderRoute")
//		.marshal().json(JsonLibrary.Jackson) // Seriale l'oggetto Order in
																						// JSON
				.log("Order in JSON format: ${body}").split(simple("${body.products}"))
//				.jsonpath("$.products")// Splitta la lista dei prodotti
				.process(exchange -> {
					Product product = exchange.getIn().getBody(Product.class);
					products.add(product);
				}).end();
//	            .streaming()
//	            .log("Processing product: ${body}")  // Log di ogni prodotto che si sta processando
//	            .to("direct:processProduct");
//	         Una rotta separata che processa singoli prodotti
//	        from("direct:processProduct")
//	            .routeId("processProductRoute")
//	            .log("Product processed: ${body}");

	}

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

}
