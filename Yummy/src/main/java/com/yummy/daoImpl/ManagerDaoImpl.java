package com.yummy.daoImpl;

import com.yummy.dao.ManagerDao;
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

public class ManagerDaoImpl implements ManagerDao {
    private static ManagerDao managerDao = new ManagerDaoImpl();
    private static SessionFactory factory = new Configuration().configure().buildSessionFactory();
    private static Transaction tx = null;

    public static ManagerDao getInstance() {
        return managerDao;
    }

    @Override
    public Set<ApprovalMerchant> getApprovalMerchants() {
        Session session = factory.openSession();
        try {
            tx = session.beginTransaction();
            String hql = "from ApprovalMerchant";
            Query query = session.createQuery(hql);
            List<ApprovalMerchant> list = (List<ApprovalMerchant>)query.list();
            Set<ApprovalMerchant> set =new HashSet<>();
            for (ApprovalMerchant a:list){
                set.add(a);
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
    public Set<ApprovalMerchant> getUnhandledEvents() {
        Session session = factory.openSession();
        try {
            tx = session.beginTransaction();
            String hql = "from ApprovalMerchant where approval=false and refused=false";
            Query query = session.createQuery(hql);
            List<ApprovalMerchant> list = (List<ApprovalMerchant>)query.list();
            Set<ApprovalMerchant> set =new HashSet<>();
            for (ApprovalMerchant a:list){
                set.add(a);
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
    public ApprovalMerchant getApprovalMerchant(int amid) {
        Session session = factory.openSession();
        try {
            tx = session.beginTransaction();
            String hql = "from ApprovalMerchant where amid="+amid;
            Query query = session.createQuery(hql);
            return (ApprovalMerchant) query.uniqueResult();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            session.close();
        }
        return null;
    }

    @Override
    public boolean approveMerchantModify(int amid) {
        ApprovalMerchant approvalMerchant = managerDao.getApprovalMerchant(amid);
        approvalMerchant.setApproval(true);
        return managerDao.updateApprovalMerchant(approvalMerchant);
    }

    @Override
    public boolean refuseMerchantModify(int amid) {
        ApprovalMerchant approvalMerchant = managerDao.getApprovalMerchant(amid);
        approvalMerchant.setRefused(true);
        return managerDao.updateApprovalMerchant(approvalMerchant);
    }

    @Override
    public boolean updateApprovalMerchant(ApprovalMerchant approvalMerchant) {
        Session session = factory.openSession();
        try {
            tx = session.beginTransaction();
            session.update(approvalMerchant);
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
    public Integer login(String email, String password) {
        Session session = factory.openSession();
        try {
            tx = session.beginTransaction();
            Query query = session.createQuery("from Manager where email='"+email+"'");
            Manager manager =(Manager) query.uniqueResult();
            if (manager == null)
                return null;
            if (!manager.getPassword().equals(password))
                return null;
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            String now = format.format(new Date());
            if(!now.equals(manager.getTodayDate())){
                manager.setBalance(manager.getBalance()+manager.getTodayEarning());
                ManagerDateEarning managerDateEarning = new ManagerDateEarning();
                managerDateEarning.setDate(manager.getTodayDate());
                managerDateEarning.setEarning(manager.getTodayEarning());
                manager.setTodayEarning(0);
                manager.setTodayDate(now);
                session.save(managerDateEarning);
                session.update(manager);
                tx.commit();
            }
            return manager.getManager_id();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            session.close();
        }
        return null;
    }

    @Override
    public Set<ApprovalMerchant> getRefusedMerchants() {
        Session session = factory.openSession();
        try {
            tx = session.beginTransaction();
            String hql = "from ApprovalMerchant where refused=true";
            Query query = session.createQuery(hql);
            List<ApprovalMerchant> list = (List<ApprovalMerchant>)query.list();
            Set<ApprovalMerchant> set =new HashSet<>();
            for (ApprovalMerchant a:list){
                set.add(a);
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
    public Set<ApprovalMerchant> getApprovedMerchants() {
        Session session = factory.openSession();
        try {
            tx = session.beginTransaction();
            String hql = "from ApprovalMerchant where approval=true";
            Query query = session.createQuery(hql);
            List<ApprovalMerchant> list = (List<ApprovalMerchant>)query.list();
            Set<ApprovalMerchant> set =new HashSet<>();
            for (ApprovalMerchant a:list){
                set.add(a);
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
    public int[] getMerchantTypeStatistic() {
        int[] res = new int[5];
        Session session = factory.openSession();
        try {
            Query query = session.createQuery("from Merchant");
            List<Merchant> list = query.list();
            for (Merchant m: list){
                res[m.getType()]++;
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            session.close();
        }
        return res;
    }

    @Override
    public int[] getUserLevelStatistic() {
        int[] res = new int[6];
        Session session = factory.openSession();
        try {
            Query query = session.createQuery("from User");
            List<User> list = query.list();
            for (User u:list){
                res[u.getLevel()]++;
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            session.close();
        }
        return res;
    }

    @Override
    public Manager getManager(int mid) {
        Session session = factory.openSession();
        try {
            Query query = session.createQuery("from Manager where manager_id="+mid);
            Manager manager =  (Manager) query.uniqueResult();
            return manager;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            session.close();
        }
        return null;
    }

    @Override
    public int getMerchantNumber() {
        Session session = factory.openSession();
        try {
            Query query = session.createQuery("from Merchant");
            return query.list().size();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            session.close();
        }
        return 0;
    }

    @Override
    public int getUserNumber() {
        Session session = factory.openSession();
        try {
            Query query = session.createQuery("from User");
            return query.list().size();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            session.close();
        }
        return 0;
    }

    @Override
    public boolean updateManager(Manager manager) {
        Session session = factory.openSession();
        try {
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
