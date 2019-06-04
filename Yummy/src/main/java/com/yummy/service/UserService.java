package com.yummy.service;

import com.yummy.model.*;

import java.util.Set;

public interface UserService {
    public Integer memberRegister(String email, String password);

    public String sendVerify(String email);

    public String sendEmail(String email, String context, String head);

    public Integer login(String email, String password);

    public User getUser(int uid);

    public boolean updateUser(User user);

    public Set<ChargeRecord> getChargeRecords(int uid);

    public Set<Coupon> getCoupons(int uid);

    public Set<Address> getAddresses(int uid);

    public User getSimpleUser(int uid);

    public String getHeadImage(int uid);

    public Cart getCart(int uid);

    public boolean updateCart(Cart cart);

    public boolean deleteAddress(int aid);

    public boolean deleteCartItem(int ciid);

    public boolean clearCart(int uid,int mid);

}
