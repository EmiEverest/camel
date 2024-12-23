package com.eip.demo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class OrderBatch {
    

    private List<Order> orders;

    // Getter e Setter
    public List<Order> getOrders() { return orders; }
    public void setOrders(List<Order> orders) { this.orders = orders; }

    @Override
    public String toString() {
        return "OrderBatch{orders=" + orders + "}";
    }
}

