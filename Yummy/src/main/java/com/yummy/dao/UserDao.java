package com.yummy.dao;

import com.yummy.model.Address;
import com.yummy.model.ChargeRecord;
import com.yummy.model.Coupon;
import com.yummy.model.User;

public interface UserDao {
    public Integer addUser(User user);

    public User getUser(String email);

    public User getUser(int uid);

    public Integer login(String email, String password);

    public boolean updateUser(User user);

    public boolean deleteAddress(int aid);

    public Address getAddress(int aid);

    public boolean deleteCartItem(int ciid);

    public boolean clearCart(int uid,int mid);
}
