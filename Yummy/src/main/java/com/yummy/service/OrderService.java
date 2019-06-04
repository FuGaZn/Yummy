package com.yummy.service;

import com.yummy.model.Order;

import java.util.Set;

public interface OrderService {
    public Set<Order> getOrdersByUser(int uid);

    public Set<Order> getOrdersByMerchant(int mid);

    public Integer addOrder(Order order);

    public boolean updateOrder(Order order);

    public Order getOrder(int oid);

    public boolean updateSituation(int oid, int situation);

}
