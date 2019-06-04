package com.yummy.serviceImpl;

import com.yummy.dao.MerchantDao;
import com.yummy.factory.DaoFactory;
import com.yummy.model.*;
import com.yummy.model.Package;
import com.yummy.service.MerchantService;

import javax.mail.*;
import java.util.Set;

public class MerchantServiceImpl implements MerchantService {
    MerchantDao merchantDao = DaoFactory.getMerchantDao();
    static MerchantService service = new MerchantServiceImpl();

    public static MerchantService getInstance() {
        return service;
    }

    @Override
    public String merchantRegister(Merchant merchant) {
        return merchantDao.addMerchant(merchant);
    }

    @Override
    public Merchant getMerchant(String serial) {
        return merchantDao.getMerchant(serial);
    }

    @Override
    public Merchant getMerchant(int mid) {
        return merchantDao.getMerchant(mid);
    }

    @Override
    public String getLogo(int mid) {
        Merchant merchant = merchantDao.getMerchant(mid);
        if(merchant.getImage() == null)  return null;
        return merchant.getImage();
    }

    @Override
    public boolean updateMerchant(Merchant merchant) {
        return merchantDao.updateMerchant(merchant);
    }

    @Override
    public Integer merchantLogin(String serial, String password) {
        return merchantDao.login(serial,password);
    }

    @Override
    public Set<Merchant> getMerchantsByType(int type,String address_code) {
        return merchantDao.getMerchantsByType(type,address_code);
    }

    @Override
    public Set<Merchant> getMerchantsByAddress(String address_code) {
        return merchantDao.getMerchantsByAddress(address_code);
    }

    @Override
    public boolean modifyInfo(ApprovalMerchant approvalMerchant) {
        if (approvalMerchant.isApproval()){
            Merchant merchant = merchantDao.getMerchant(approvalMerchant.getMid());
            merchant.setName(approvalMerchant.getName());
            merchant.setAddress(approvalMerchant.getAddress());
            merchant.setBriefIntroduction(approvalMerchant.getBriefIntroduction());
            merchant.setBulletin(approvalMerchant.getBulletin());
            merchant.setPhone(approvalMerchant.getPhone());
            merchant.setType(approvalMerchant.getType());
            merchant.setImage(approvalMerchant.getImage());
            return merchantDao.updateMerchant(merchant);
        }else if(approvalMerchant.isRefused())
            return false;
        return false;
    }

    @Override
    public Set<Coupon> getCoupons(int mid) {
        Merchant merchant = merchantDao.getMerchant(mid);
        return merchant.getCoupons();
    }

    @Override
    public Set<Package> getPackages(int mid) {
        return merchantDao.getMerchant(mid).getPackages();
    }

    @Override
    public Set<Good> getGoods(int mid) {
        return merchantDao.getMerchant(mid).getGoods();
    }

    @Override
    public Set<Merchant> searchMerchant(String info) {
        return null;
    }

    @Override
    public boolean addPackage(Merchant merchant) {
        return merchantDao.addPackage(merchant);
    }

    @Override
    public boolean deleteCoupon(int cid) {
        return merchantDao.deleteCoupon(cid);
    }

    @Override
    public boolean updateBaseInfo(Merchant merchant) {
        return merchantDao.updateBaseInfo(merchant);
    }

    @Override
    public boolean deletePackage(int pid) {
        return merchantDao.deletePackage(pid);
    }

    @Override
    public boolean deleteGood(int gid) {
        return merchantDao.deleteGood(gid);
    }
}
