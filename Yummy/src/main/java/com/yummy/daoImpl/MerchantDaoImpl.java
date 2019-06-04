package com.yummy.daoImpl;

import com.sun.org.apache.xpath.internal.operations.Or;
import com.yummy.dao.ManagerDao;
import com.yummy.dao.MerchantDao;
import com.yummy.dao.OrderDao;
import com.yummy.dao.UserDao;
import com.yummy.factory.DaoFactory;
import com.yummy.model.*;
import com.yummy.model.Package;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.text.SimpleDateFormat;
import java.util.*;


public class MerchantDaoImpl implements MerchantDao {
    private static SessionFactory factory = new Configuration().configure().buildSessionFactory();

    static MerchantDao dao = new MerchantDaoImpl();
    static OrderDao orderDao = DaoFactory.getOrderDao();
    static UserDao userDao = DaoFactory.getUserDao();
    static ManagerDao managerDao = DaoFactory.getManagerDao();

    public static MerchantDao getInstance() {
        return dao;
    }

    @Override
    public Merchant getMerchant(String serial) {
        Session session = factory.openSession();
        try {
            String hql = "from Merchant where serial='"+serial+"'";
            Query query = session.createQuery(hql);
            Merchant merchant = (Merchant) query.uniqueResult();
            return merchant;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            session.close();
        }
        return null;
    }

    @Override
    public String addMerchant(Merchant merchant) {
        Session session = factory.openSession();
        Transaction tx = null;
        try {
            String serial = null;
            tx = session.beginTransaction();
            SimpleDateFormat format = new SimpleDateFormat("yyMMddHHmmss");
            while (true){
                Long val = Long.parseLong(format.format(new Date()));
                serial = LastMerchantSerial.int2CodeString(val);
                LastMerchantSerial lastMerchantSerial = this.getLastSerial();
                String last_serial = null;
                if (lastMerchantSerial != null)
                    last_serial = lastMerchantSerial.getSerial();
                if (last_serial ==null || !last_serial.equals(serial)){
                    merchant.setSerial(serial);
                    session.save(merchant.getAddress());
                    session.save(merchant);
                    if (lastMerchantSerial == null) {
                        lastMerchantSerial = new LastMerchantSerial();
                        lastMerchantSerial.setSerial(serial);
                        session.save(lastMerchantSerial);
                    }else {
                        lastMerchantSerial.setSerial(serial);
                        session.update(lastMerchantSerial);
                    }
                    break;
                }
            }
            tx.commit();
            return serial;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            session.close();
        }
        return merchant.getSerial();
    }

    public LastMerchantSerial getLastSerial(){
        Session session = factory.openSession();
        try {
            Query query = session.createQuery("from LastMerchantSerial where lid=1");
            if (query == null || query.list().size() == 0){
                return null;
            }else{
                LastMerchantSerial lastMerchantSerial = (LastMerchantSerial)query.uniqueResult();
                return lastMerchantSerial;
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            session.close();
        }
        return null;
    }

    @Override
    public Merchant getMerchant(int mid) {
        Session session = factory.openSession();
        try {
            String hql = "from Merchant where mid="+mid;
            Query query = session.createQuery(hql);
            Merchant merchant = (Merchant) query.uniqueResult();
            return merchant;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            session.close();
        }
        return null;
    }

    @Override
    public boolean updateMerchant(Merchant merchant) {
        Session session = factory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            for (Coupon c:merchant.getCoupons()){
                session.saveOrUpdate(c);
            }
            for (Good good: merchant.getGoods()) {
                session.saveOrUpdate(good);
            }
            for (Package p:merchant.getPackages()) {
                session.saveOrUpdate(p);
            }
            session.update(merchant);
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
    public boolean deleteGood(int gid) {
        Session session = factory.openSession();
        Transaction tx =null;
        try {
            tx = session.beginTransaction();
            Good good = this.getGood(gid);
            session.delete(good);
            tx.commit();
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            session.close();
        }
        return false;
    }

    public Good getGood(int gid){
        Session session = factory.openSession();
        Transaction tx =null;
        try {
            String hql = "from Good where gid="+gid;
            Query query = session.createQuery(hql);
            Good item =(Good) query.uniqueResult();
            return item;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            session.close();
        }
        return null;
    }

    @Override
    public boolean deletePackage(int pid) {
        Session session = factory.openSession();
        Transaction tx =null;
        try {
            tx = session.beginTransaction();
            Package p = this.getPackage(pid);
            session.delete(p);
            tx.commit();
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            session.close();
        }
        return false;
    }

    public Package getPackage(int gid){
        Session session = factory.openSession();
        Transaction tx =null;
        try {
            String hql = "from Package where pid="+gid;
            Query query = session.createQuery(hql);
            Package item =(Package) query.uniqueResult();
            return item;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            session.close();
        }
        return null;
    }

    @Override
    public boolean deleteCoupon(int cid) {
        Session session = factory.openSession();
        Transaction tx =null;
        try {
            tx = session.beginTransaction();
            Coupon coupon = this.getCoupon(cid);
            session.delete(coupon);
            tx.commit();
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            session.close();
        }
        return false;
    }

    public Coupon getCoupon(int cid){
        Session session = factory.openSession();
        Transaction tx =null;
        try {
            String hql = "from Coupon where cid="+cid;
            Query query = session.createQuery(hql);
            Coupon coupon =(Coupon) query.uniqueResult();
            return coupon;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            session.close();
        }
        return null;
    }
    @Override
    public Integer login(String serial, String password) {
        Session session = factory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            String hql = "from Merchant where serial='"+serial+"'";
            Query query = session.createQuery(hql);
            Merchant merchant = (Merchant) query.uniqueResult();
            if (merchant == null)
                return null;
            if (merchant.getPassword().equals(password)) {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                String now = format.format(new Date());
                if(!now.equals(merchant.getTodayDate())){
                    merchant.setBalance(merchant.getBalance()+merchant.getTodayEarning());
                    merchant.setTodayEarning(0);
                    merchant.setTodayDate(now);
                    session.update(merchant);
                    tx.commit();
                }
                return merchant.getMid();
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            session.close();
        }
        return null;
    }

    @Override
    public Set<Merchant> getMerchantsByType(int type,String address_code) {
        Session session = factory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            String hql = "from Merchant where type="+type+" and address.part_code="+address_code;
            Query query = session.createQuery(hql);
            List<Merchant> merchants = query.list();
            Set<Merchant> set = new HashSet<>();
            for (Merchant m: merchants){
                set.add(m);
            }
            return set;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            session.close();
        }
        return null;
    }

    @Override
    public Set<Merchant> getMerchantsByAddress(String address_code) {
        Session session = factory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            String hql = "from Merchant where address.part_code="+address_code;
            Query query = session.createQuery(hql);
            List<Merchant> merchants = query.list();
            Set<Merchant> set = new HashSet<>();
            for (Merchant m: merchants){
                set.add(m);
            }
            return set;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            session.close();
        }
        return null;
    }

    @Override
    public boolean addPackage(Merchant merchant) {
        Session session = factory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            for (Package p: merchant.getPackages()){
                for (GoodItem g: p.getGoods()){
                    session.saveOrUpdate(g);
                }
                session.saveOrUpdate(p);
            }
            session.update(merchant);
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
    public boolean updateBaseInfo(Merchant merchant) {
        ApprovalMerchant approvalMerchant = new ApprovalMerchant();
        approvalMerchant.setName(merchant.getName());
        approvalMerchant.setType(merchant.getType());
        approvalMerchant.setAddress(merchant.getAddress());
        approvalMerchant.setBriefIntroduction(merchant.getBriefIntroduction());
        approvalMerchant.setBulletin(merchant.getBulletin());
        approvalMerchant.setPhone(merchant.getPhone());
        approvalMerchant.setImage(merchant.getImage());
        approvalMerchant.setMid(merchant.getMid());
        Session session = factory.openSession();
        try {
            Transaction tx = session.beginTransaction();
            session.save(approvalMerchant);
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
    public boolean acceptCancelOrder(int oid) {
        Session session = factory.openSession();
        try {
            Transaction tx = session.beginTransaction();
            Order order = orderDao.getOrder(oid);
            order.setSituation(4);
            Manager manager = managerDao.getManager(1);
            Merchant merchant = this.getMerchant(order.getMid());
            if (order.getBeforeSituation() == 4){
                merchant.setTodayEarning(merchant.getTodayEarning()-order.getTotal()*0.9);
                manager.setTodayEarning(manager.getTodayEarning()-order.getTotal()*0.1);
            }else{
                manager.setTodayEarning(manager.getTodayEarning()-order.getTotal());
            }
            for(GoodItem gi: order.getGoodItems()){
                if (gi.getPid()==0){
                    for (Good good:merchant.getGoods()){
                        if(gi.getGid() == good.getGid()) {
                            good.setBought_number(good.getBought_number() - gi.getNumber());
                            session.update(good);
                        }
                    }
                }else{
                    List<Integer> list = new ArrayList<>();
                    for (Package p:merchant.getPackages()){
                        if(gi.getPid() == p.getPid()){
                            if(!list.contains(gi.getPid())){
                                list.add(gi.getPid());
                                p.setBought_number(p.getBought_number()-gi.getNumber()/p.getGoodItemNumber(gi.getGood_item_id()));
                                session.update(p);
                            }
                        }
                    }
                }
            }
            User user = userDao.getUser(order.getUid());
            user.setBalance(user.getBalance()+order.getTotal());
            session.update(user);
            session.update(merchant);
            session.update(manager);
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
}
