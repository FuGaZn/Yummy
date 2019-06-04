package com.yummy.factory;

import com.yummy.service.ManagerService;
import com.yummy.service.MerchantService;
import com.yummy.service.OrderService;
import com.yummy.service.UserService;
import com.yummy.serviceImpl.ManagerServiceImpl;
import com.yummy.serviceImpl.MerchantServiceImpl;
import com.yummy.serviceImpl.OrderServiceImpl;
import com.yummy.serviceImpl.UserServiceImpl;

public class ServiceFactory {
    public static UserService getUserService(){
        return UserServiceImpl.getInstance();
    }

    public static MerchantService getMerchantService(){
        return MerchantServiceImpl.getInstance();
    }

    public static OrderService getOrderService(){
        return OrderServiceImpl.getInstance();
    }

    public static ManagerService getManagerService(){
        return ManagerServiceImpl.getInstance();
    }
}
