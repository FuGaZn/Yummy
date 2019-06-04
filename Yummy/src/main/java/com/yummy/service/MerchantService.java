package com.yummy.service;

import com.yummy.model.*;
import com.yummy.model.Package;

import java.util.Set;

public interface MerchantService {
    public String merchantRegister(Merchant merchant);

    public Integer merchantLogin(String id, String password);

    public Merchant getMerchant(String serial);

    public Merchant getMerchant(int mid);

    public boolean updateMerchant(Merchant merchant);

    public String getLogo(int mid);

    public Set<Merchant> getMerchantsByAddress(String address_code);

    public Set<Merchant> getMerchantsByType(int type, String address_code);

    public Set<Merchant> searchMerchant(String info);

    public boolean modifyInfo(ApprovalMerchant approvalMerchant);

    public Set<Coupon> getCoupons(int mid);

    public Set<Package> getPackages(int mid);

    public boolean addPackage(Merchant merchant);

    public Set<Good> getGoods(int mid);

    public boolean deleteCoupon(int cid);

    public boolean updateBaseInfo(Merchant merchant);

    public boolean deleteGood(int gid);

    public boolean deletePackage(int pid);
}
