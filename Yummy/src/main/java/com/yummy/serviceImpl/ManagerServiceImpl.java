package com.yummy.serviceImpl;

import com.yummy.dao.ManagerDao;
import com.yummy.factory.DaoFactory;
import com.yummy.factory.ServiceFactory;
import com.yummy.model.ApprovalMerchant;
import com.yummy.service.ManagerService;
import com.yummy.service.MerchantService;
import com.yummy.service.UserService;

import java.util.Set;

public class ManagerServiceImpl implements ManagerService {
    static ManagerService managerService = new ManagerServiceImpl();
    private static ManagerDao managerDao = DaoFactory.getManagerDao();
    private static MerchantService merchantService = ServiceFactory.getMerchantService();
    private static UserService userService = ServiceFactory.getUserService();

    public static ManagerService getInstance() {
        return managerService;
    }

    @Override
    public Set<ApprovalMerchant> getApprovalMerchants() {
        return managerDao.getApprovalMerchants();
    }

    @Override
    public Set<ApprovalMerchant> getUnhandledEvents() {
        return managerDao.getUnhandledEvents();
    }

    @Override
    public boolean approveMerchantModify(int amid) {
        boolean b =  managerDao.approveMerchantModify(amid);
        System.out.println(b);
        if (b == true){
            ApprovalMerchant approvalMerchant = managerDao.getApprovalMerchant(amid);
            return merchantService.modifyInfo(approvalMerchant);
        }
        return false;
    }

    @Override
    public boolean refuseMerchantModify(int mid) {
        return  managerDao.refuseMerchantModify(mid);
    }

    @Override
    public Integer login(String email, String password) {
        return managerDao.login(email,password);
    }

    @Override
    public Set<ApprovalMerchant> getRefusedMerchants() {
        return managerDao.getRefusedMerchants();
    }

    @Override
    public Set<ApprovalMerchant> getApprovedMerchants() {
        return managerDao.getApprovedMerchants();
    }

    @Override
    public int[] getUserLevelStatistic() {
        return managerDao.getUserLevelStatistic();
    }

    @Override
    public int[] getMerchantTypeStatistic() {
        return managerDao.getMerchantTypeStatistic();
    }
}
