package com.yummy.dao;

import com.sun.org.apache.xpath.internal.operations.Or;
import com.yummy.model.Order;

import java.util.Set;

public interface OrderDao {
    public Set<Order> getOrdersByUser(int uid);

    public Set<Order> getOrdersByMerchant(int mid);

    public Integer addOrder(Order order);

    public Integer addUnpayedOrder(Order order);

    public boolean updateOrder(Order order);

    public Order getOrder(int oid);

    public Order getOrder(String serial);

    public boolean updateSituation(int oid, int situation);

    public boolean finishOrder(int oid);

    public String addOrderBackSerial(Order order);

    public String payUnpayedOrder(int oid);

}
