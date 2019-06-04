package com.yummy.daoImpl;

import com.yummy.dao.UserDao;
import com.yummy.model.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class UserDaoImpl implements UserDao {
    private static SessionFactory factory = new Configuration().configure().buildSessionFactory();
    static UserDao userDao = new UserDaoImpl();

    public static UserDao getInstance() {
        return userDao;
    }

    @Override
    public User getUser(int uid) {
        Session session = factory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            String hql = "from User where uid=" + uid;
            Query query = session.createQuery(hql);
            User user = (User) query.uniqueResult();
            return user;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return null;
    }

    @Override
    public User getUser(String email) {
        Session session = factory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            String hql = "from User where email='" + email + "'";
            Query query = session.createQuery(hql);
            User user = (User) query.uniqueResult();
            return user;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return null;
    }

    @Override
    public boolean updateUser(User user) {
        System.out.println(user.getUid());
        Session session = factory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            if (user.getCart() != null) {
                if(user.getCart().getCartItems()!=null) {
                    for (CartItem c : user.getCart().getCartItems()) {
                        session.saveOrUpdate(c);
                    }
                }
                session.saveOrUpdate(user.getCart());
            }
            session.update(user);
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return true;
    }

    @Override
    public Integer addUser(User user) {
        Session session = factory.openSession();
        Transaction tx = null;
        Integer id = null;
        try {
            tx = session.beginTransaction();
            Cart cart = new Cart();
            cart.setUid(user.getUid());
            cart.setMid(0);
            cart.setCartItems(new HashSet<CartItem>());
            cart.setTotal(0);
            cart.setTotalNumber(0);
            user.setCart(cart);
            session.save(cart);
            id = (Integer) session.save(user);
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return id;
    }

    @Override
    public Integer login(String email, String password) {
        Session session = factory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            String hql = "from User where email='" + email + "'";
            Query query = session.createQuery(hql);
            User user = (User) query.uniqueResult();
            if (user == null)
                return null;
            else if (user.getPassword().equals(password))
                return user.getUid();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return null;
    }

    @Override
    public boolean deleteAddress(int aid) {
        Session session = factory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Address address = userDao.getAddress(aid);
            session.delete(address);
            tx.commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return false;
    }

    @Override
    public Address getAddress(int aid) {
        Session session = factory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            String hql = "from Address where aid=" + aid;
            Query query = session.createQuery(hql);
            Address address = (Address) query.uniqueResult();
            return address;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return null;
    }

    @Override
    public boolean deleteCartItem(int ciid) {
        Session session = factory.openSession();
        try {
            Transaction tx = session.beginTransaction();
            String hql = "from CartItem where cart_item_id=" + ciid;
            Query query = session.createQuery(hql);
            CartItem cartItem = (CartItem) query.uniqueResult();
            session.delete(cartItem);
            tx.commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return false;
    }

    @Override
    public boolean clearCart(int uid, int mid) {
        Session session = factory.openSession();
        try {
            Transaction tx = session.beginTransaction();
            String hql = "from User where uid=" + uid;
            Query query = session.createQuery(hql);
            User user = (User) query.uniqueResult();
            Cart cart = user.getCart();
            cart.setMid(mid);
            cart.setCartItems(new HashSet<CartItem>());
            session.update(cart);
            session.update(user);
            tx.commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return false;
    }
}
