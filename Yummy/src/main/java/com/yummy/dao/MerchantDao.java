package com.yummy.dao;

import com.yummy.model.Merchant;

import java.util.Set;

public interface MerchantDao {
    public String addMerchant(Merchant merchant);

    public Merchant getMerchant(String serial);

    public boolean updateMerchant(Merchant merchant);

    public Merchant getMerchant(int mid);

    public Integer login(String serial, String password);

    public Set<Merchant> getMerchantsByType(int type,String address_code);

    public Set<Merchant> getMerchantsByAddress(String address_code);

    public boolean addPackage(Merchant merchant);

    public boolean deleteCoupon(int cid);

    public boolean updateBaseInfo(Merchant merchant);

    public boolean deleteGood(int gid);

    public boolean deletePackage(int pid);

    public boolean acceptCancelOrder(int oid);

}
