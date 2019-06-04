package com.yummy.service;

import com.yummy.model.ApprovalMerchant;

import java.util.Set;

public interface ManagerService {

    public Set<ApprovalMerchant> getApprovalMerchants();

    public Set<ApprovalMerchant> getApprovedMerchants();

    public Set<ApprovalMerchant> getUnhandledEvents();

    public boolean approveMerchantModify(int amid);

    public boolean refuseMerchantModify(int mid);

    public Integer login(String email,String password);

    public Set<ApprovalMerchant> getRefusedMerchants();

    public int[] getMerchantTypeStatistic();

    public int[] getUserLevelStatistic();

}
