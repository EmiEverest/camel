package com.eip.demo.component;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

import com.eip.demo.model.Product;

@Component
public class ContentBasedRouterDefinition extends RouteBuilder {

	private String selectedRoute;
    @Override
    public void configure() throws Exception {
    	
        errorHandler(deadLetterChannel("kafka:dead-letter-queue?brokers={{spring.kafka.bootstrap-servers}}")
                .useOriginalMessage()
                .logStackTrace(true)
                .maximumRedeliveries(3) // Numero massimo di tentativi
                .redeliveryDelay(2000) // Ritardo tra i tentativi (millisecondi)
            );
    	
        from("kafka:products-to-routed?brokers={{spring.kafka.bootstrap-servers}}")
            .routeId("content-based-router-example")
            .log("Received Product: ${body}")
            
            // Deserializza JSON in Product (Jackson usato implicitamente)
            .unmarshal().json(Product.class)
            .process(exchange -> {
            	Product product = exchange.getIn().getBody(Product.class);

                // Simula un errore
                if (product.getProductName().length()==0) {
                    throw new RuntimeException("Errore simulato nell'elaborazione dell'ordine!");
                }
            })
            // Verifica se c'è stato un errore
            .onException(Exception.class) 
                .handled(true) // Contrassegna l'errore come gestito
                .log("Errore intercettato! Messaggio inviato alla Dead Letter Queue.")
                .stop() // Termina la route dopo aver inviato alla Dead Letter Queue
            .end()
            // Content-Based Routing
            .choice()
                .when(simple("${body.price} <= 20.00"))
                    .marshal().json()
                    .process(
                    		exchange ->{
                    			selectedRoute="to low-priced-products topic";
                    		}
                    		)
                    .log("Routing to Low-Priced Products: ${body}")
                    .to("kafka:low-priced-products?brokers={{spring.kafka.bootstrap-servers}}")
                .when(simple("${body.price} > 20.00 && ${body.price} <= 100.00"))
                    .marshal().json()
                    .process(
                    		exchange ->{
                    			selectedRoute="to medium-priced-products topic";
                    		}
                    		)
                    .log("Routing to Medium-Priced Products: ${body}")
                    .to("kafka:medium-priced-products?brokers={{spring.kafka.bootstrap-servers}}")
                .otherwise()
                    .marshal().json()  
                    .process(
                    		exchange ->{
                    			selectedRoute="to high-priced-products topic";
                    		}
                    		)
                    .log("Routing to High-Priced Products: ${body}")
                    .to("kafka:high-priced-products?brokers={{spring.kafka.bootstrap-servers}}")
            .end();
    }
	public String getSelectedRoute() {
		return selectedRoute;
	}
	public void setSelectedRoute(String selectedRoute) {
		this.selectedRoute = selectedRoute;
	}
}
