package com.yummy.factory;

import com.yummy.dao.ManagerDao;
import com.yummy.dao.MerchantDao;
import com.yummy.dao.OrderDao;
import com.yummy.dao.UserDao;
import com.yummy.daoImpl.ManagerDaoImpl;
import com.yummy.daoImpl.MerchantDaoImpl;
import com.yummy.daoImpl.OrderDaoImpl;
import com.yummy.daoImpl.UserDaoImpl;

public class DaoFactory {

    public static UserDao getUserDao(){
        return UserDaoImpl.getInstance();
    }

    public static MerchantDao getMerchantDao(){
        return MerchantDaoImpl.getInstance();
    }

    public static OrderDao getOrderDao(){
        return OrderDaoImpl.getInstance();
    }

    public static ManagerDao getManagerDao(){
        return ManagerDaoImpl.getInstance();
    }
}
