package com.eip.demo.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Order {
    private Integer orderId;
    
    private List<Product> products;



    public Order() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Order(Integer orderId, List<Product> products) {
		super();
		this.orderId = orderId;
		this.products = products;
	}

	public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }
    @JsonProperty
    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}

