package com.eip.demo.component;

import java.util.ArrayList;
import java.util.List;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.eip.demo.model.Order;
import com.eip.demo.model.OrderBatch;
import com.eip.demo.model.Product;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class SplitterRouterKafka extends RouteBuilder {

	private List<Product> products= new ArrayList<>();
	@Override
	@Scheduled(cron = "${save.scheduled-task.cron.expression}")
	public void configure() throws Exception {
		// TODO Auto-generated method stub
	
		// Rotta Kafka per leggere dal topic "incoming-orders"
        from("kafka:incoming-products?brokers={{spring.kafka.bootstrap-servers}}")
            .routeId("kafka-batch-splitter-route")
            .log("Ricevuto Messaggio JSON Batch: ${body}")
            .wireTap("kafka:bck-incoming-products?brokers={{spring.kafka.bootstrap-servers}}")

            // Deserializza JSON in OrderBatch (usa Jackson automatico in Camel)
            .process(exchange -> {
                String json = exchange.getIn().getBody(String.class); // Prendi il corpo come Stringa JSON
                ObjectMapper objectMapper = new ObjectMapper();
                products = objectMapper.readValue(json, new TypeReference<List<Product>>() {});
                exchange.getIn().setBody(products); // Imposta la lista deserializzata come nuovo corpo
            })
//            .process(exchange -> {
//            	products=exchange.getIn().getBody(List.class);
//            })
            // Split della lista degli ordini
            .split(body())
//                .log("Ordine Splittato: ${body}")
//                .process(exchange -> {
//                    
//                	Product product = exchange.getIn().getBody(Product.class);
//                    System.out.println("Elaborazione prodotto: " + product);
//                    products.add(product);
//                })
                // Invia ogni ordine splittato al topic "processed-orders"
                .marshal().json() // Marshal di nuovo come JSON tramuta l'oggetto in json serializzazione
                .to("kafka:splitted-products?brokers={{spring.kafka.bootstrap-servers}}")
            .end(); // Fine dello splitter

        log.info("Tutti gli ordini dal batch sono stati processati.");
		
	}
	public List<Product> getProducts() {
		return products;
	}
	public void setProducts(List<Product> products) {
		this.products = products;
	}

}
