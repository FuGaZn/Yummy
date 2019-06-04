package com.yummy.serviceImpl;

import com.yummy.dao.OrderDao;
import com.yummy.factory.DaoFactory;
import com.yummy.model.Order;
import com.yummy.service.OrderService;

import java.util.Set;

public class OrderServiceImpl implements OrderService {
    static OrderService orderService = new OrderServiceImpl();
    private OrderDao orderDao = DaoFactory.getOrderDao();

    public static OrderService getInstance(){
        return orderService;
    }

    @Override
    public Order getOrder(int oid) {
        return orderDao.getOrder(oid);
    }

    @Override
    public Set<Order> getOrdersByMerchant(int mid) {
        return orderDao.getOrdersByMerchant(mid);
    }

    @Override
    public Set<Order> getOrdersByUser(int uid) {
        return orderDao.getOrdersByUser(uid);
    }

    @Override
    public Integer addOrder(Order order) {

        return orderDao.addOrder(order);
    }

    @Override
    public boolean updateOrder(Order order) {
        return orderDao.updateOrder(order);
    }

    @Override
    public boolean updateSituation(int oid, int situation) {
        return orderDao.updateSituation(oid,situation);
    }
}
