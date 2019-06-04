package com.yummy.daoImpl;

import com.sun.org.apache.xerces.internal.impl.dtd.models.DFAContentModel;
import com.sun.org.apache.xpath.internal.operations.Or;
import com.yummy.dao.ManagerDao;
import com.yummy.dao.MerchantDao;
import com.yummy.dao.OrderDao;
import com.yummy.dao.UserDao;
import com.yummy.factory.DaoFactory;
import com.yummy.model.*;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class OrderDaoImpl implements OrderDao {
    static OrderDao orderDao = new OrderDaoImpl();
    private static SessionFactory factory = new Configuration().configure().buildSessionFactory();
    private static Transaction tx = null;
    static ManagerDao managerDao = DaoFactory.getManagerDao();
    static MerchantDao merchantDao = DaoFactory.getMerchantDao();

    public static OrderDao getInstance() {
        return orderDao;
    }

    @Override
    public Set<Order> getOrdersByMerchant(int mid) {
        Session session = factory.openSession();
        try {
            tx = session.beginTransaction();
            List<Order> list = session.createQuery("from Order o where o.mid="+mid).list();
            Set<Order> set = new HashSet<>();
            for (Order o:list)
                set.add(o);
            return set;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            session.close();
        }
        return null;
    }

    @Override
    public Set<Order> getOrdersByUser(int uid) {
        Session session = factory.openSession();
        try {
            tx = session.beginTransaction();
            String hql = "from Order where uid="+uid;
            Query query = session.createQuery(hql);
            List<Order> orders = query.list();
            Set<Order> set = new HashSet<>();
            for (Order o:orders)
                set.add(o);
            return set;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            session.close();
        }
        return null;
    }

    @Override
    public Integer addOrder(Order order) {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        String serial = format.format(new Date());
        serial+=order.getMid();
        serial+=order.getUid();
        order.setSerial(serial);
        SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        order.setTime(format2.format(new Date()));
        Session session = factory.openSession();
        try {
            Manager manager = managerDao.getManager(1);
            manager.setBalance(manager.getBalance()+order.getTotal());
            tx = session.beginTransaction();
            session.update(manager);
            for (Coupon c:order.getCoupons())
                session.save(c);
            for (GoodItem g:order.getGoodItems())
                session.save(g);
            session.save(order.getAddress());
            Integer res = (Integer) session.save(order);
            tx.commit();
            return res;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            session.close();
        }
        return null;
    }

    @Override
    public String addOrderBackSerial(Order order) {
        int oid = orderDao.addOrder(order);
        Order order1 = orderDao.getOrder(oid);
        return order1.getSerial();
    }

    @Override
    public boolean updateOrder(Order order) {
        Session session = factory.openSession();
        try {
            tx = session.beginTransaction();
            session.update(order);
            tx.commit();
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            session.close();
        }
        return false;
    }

    @Override
    public Order getOrder(String serial) {
        Session session = factory.openSession();
        try {
            tx = session.beginTransaction();
            String hql = "from Order where serial='"+serial+"'";
            Query query = session.createQuery(hql);
            Order order = (Order) query.uniqueResult();
            return order;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            session.close();
        }
        return null;
    }

    @Override
    public Integer addUnpayedOrder(Order order) {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        String serial = format.format(new Date());
        serial+=order.getMid();
        serial+=order.getUid();
        order.setSerial(serial);
        SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        order.setTime(format2.format(new Date()));
        Session session = factory.openSession();
        try {
            tx = session.beginTransaction();
            for (Coupon c:order.getCoupons())
                session.save(c);
            for (GoodItem g:order.getGoodItems())
                session.save(g);
            session.save(order.getAddress());
            Integer res = (Integer) session.save(order);
            tx.commit();
            return res;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            session.close();
        }
        return null;
    }

    @Override
    public Order getOrder(int oid) {
        Session session = factory.openSession();
        try {
            tx = session.beginTransaction();
            String hql = "from Order where oid="+oid;
            Query query = session.createQuery(hql);
            Order order = (Order) query.uniqueResult();
            return order;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            session.close();
        }
        return null;
    }

    @Override
    public String payUnpayedOrder(int oid) {
        UserDao userDao = DaoFactory.getUserDao();
        Order order = orderDao.getOrder(oid);
        order.setSituation(1);
        Manager manager = managerDao.getManager(1);
        manager.setBalance(manager.getBalance()+order.getTotal());
        managerDao.updateManager(manager);
        orderDao.updateOrder(order);
        return "";
    }

    @Override
    public boolean updateSituation(int oid, int situation) {
        Order order = orderDao.getOrder(oid);
        order.setBeforeSituation(order.getSituation());
        order.setSituation(situation);
        return orderDao.updateOrder(order);
    }

    @Override
    public boolean finishOrder(int oid) {
        Order order = orderDao.getOrder(oid);
        order.setSituation(4);
        Manager manager = managerDao.getManager(1);
        manager.setTodayEarning(manager.getTodayEarning()+order.getTotal()*0.1);
        Merchant merchant = merchantDao.getMerchant(order.getMid());
        merchant.setTodayEarning(merchant.getTodayEarning()+order.getTotal()*0.9);
        Session session = factory.openSession();
        try {
            Transaction tx = session.beginTransaction();
            session.update(order);
            session.update(merchant);
            session.update(manager);
            tx.commit();
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            session.close();
        }
        return false;
    }
}
