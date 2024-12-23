package com.eip.demo.component;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class RecipientListDefinition extends RouteBuilder {
private String sendMessage;
    @Override
    public void configure() throws Exception {
        from("kafka:posts?brokers={{spring.kafka.bootstrap-servers}}")
            .routeId("recipient-list-example")
            .log("Received post: ${body}")
            
            // Converti il messaggio JSON in una mappa (Map)
            .unmarshal().json()
            
            // Determina le destinazioni in base al contenuto del messaggio
            .setHeader("recipients", simple("${body[destinatari]}"))
            .marshal().json()
            .process(
            		exchange ->{
            			sendMessage="Message sent to destinations: "+exchange.getIn().getHeader("recipients", String.class);;
            		}
            		)
            // Utilizza Recipient List per inviare il messaggio
            .recipientList(header("recipients"))
            .delimiter(",")
            .log("Message sent to destinations: ${header.recipients}");
    }
	public String getSendMessage() {
		return sendMessage;
	}
	public void setSendMessage(String sendMessage) {
		this.sendMessage = sendMessage;
	}
}
