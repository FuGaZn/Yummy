package com.yummy.dao;

import com.yummy.model.ApprovalMerchant;
import com.yummy.model.Manager;

import java.util.List;
import java.util.Set;

public interface ManagerDao {
    public Set<ApprovalMerchant> getApprovalMerchants();

    public Set<ApprovalMerchant> getApprovedMerchants();

    public Set<ApprovalMerchant> getUnhandledEvents();

    public Set<ApprovalMerchant> getRefusedMerchants();

    public boolean approveMerchantModify(int amid);

    public boolean refuseMerchantModify(int mid);

    public ApprovalMerchant getApprovalMerchant(int amid);

    public boolean updateApprovalMerchant(ApprovalMerchant approvalMerchant);

    public Integer login(String email, String password);

    public int[] getMerchantTypeStatistic();

    public int[] getUserLevelStatistic();

    public Manager getManager(int mid);

    public boolean updateManager(Manager manager);

    public int getMerchantNumber();

    public int getUserNumber();

}
