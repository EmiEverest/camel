package com.eip.demo.component;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class PostEndpoints extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        from("direct:portieri-posts")
            .log("Processing in log1: ${body}")
            .to("kafka:portieri-posts?brokers={{spring.kafka.bootstrap-servers}}");

        from("direct:difensori-posts")
            .log("Processing in log2: ${body}")
            .to("kafka:difensori-posts?brokers={{spring.kafka.bootstrap-servers}}");
    }
}

