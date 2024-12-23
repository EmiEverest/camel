package com.eip.demo.common;

public final class EipManageEndPoint {
	
	public static final String SPLITTER_STANDALONE_ROUTE = "direct:start";
	public static final String AGGREGATE_ROUTE = "kafka:orders?brokers={{spring.kafka.bootstrap-servers}}";
	

}
