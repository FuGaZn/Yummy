package com.yummy.serviceImpl;

import com.sun.mail.util.MailSSLSocketFactory;
import com.yummy.dao.UserDao;
import com.yummy.daoImpl.UserDaoImpl;
import com.yummy.model.*;
import com.yummy.model.Address;
import com.yummy.service.UserService;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.*;

public class UserServiceImpl implements UserService{
    static UserService userService = new UserServiceImpl();
    static UserDao userDao = new UserDaoImpl();

    private UserServiceImpl(){

    }

    @Override
    public Set<ChargeRecord> getChargeRecords(int uid) {
        User user = userDao.getUser(uid);
        if (user == null)
            return null;
        return user.getChargeRecords();
    }

    @Override
    public Set<Coupon> getCoupons(int uid) {
        User user = userDao.getUser(uid);
        if (user == null)
            return null;
        return null;
    }

    @Override
    public Set<Address> getAddresses(int uid) {
        User user = userDao.getUser(uid);
        if (user == null)
            return null;
        return user.getAddresses();
    }

    @Override
    public User getSimpleUser(int uid) {
        User user = userDao.getUser(uid);
        user.setCoupons(null);
        user.setCart(null);
        user.setAddresses(null);
        user.setChargeRecords(null);
        user.setImage(null);
        return user;
    }

    @Override
    public Cart getCart(int uid) {
        User user = userDao.getUser(uid);
        if (user.getCart() == null){
            user.setCart(new Cart());
            userDao.updateUser(user);
        }
        return userDao.getUser(uid).getCart();
    }

    @Override
    public boolean updateCart(Cart cart) {
        int uid = cart.getUid();
        User user = userDao.getUser(uid);
        user.setCart(cart);
        userDao.updateUser(user);
        return true;
    }

    @Override
    public boolean updateUser(User user) {
        return userDao.updateUser(user);
    }

    @Override
    public String getHeadImage(int uid) {
        User user = userDao.getUser(uid);
        if (user == null)
            return null;
        if (user.getImage() == null) return null;
        return user.getImage();
    }

    @Override
    public Integer memberRegister(String email, String password) {
        User user = new User(email, password);
        return userDao.addUser(user);
    }


    @Override
    public User getUser(int uid) {
        return userDao.getUser(uid);
    }

    @Override
    public Integer login(String email, String password) {
        return userDao.login(email,password);
    }

    @Override
    public String sendVerify(String email){
        return this.sendEmail(email,getRandomVerifyCode(),"【验证码】");
    }

    @Override
    public String sendEmail(String email, String context,String head) {
        final String from = "jiaxing_f@163.com";
        final String code = "qwert2019226";
        String host = "localhost";
        Properties props = System.getProperties();
        props.setProperty("mail.smtp.host", "smtp.163.com");
        props.setProperty("mail.smtp.port", "465");
        props.setProperty("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.auth", "true");
        props.put("mail.debug", "true");
        props.put("mail.store.protocol", "pop3");
        props.put("mail.transport.protocol", "smtp");

        try{

            MailSSLSocketFactory sf = null;
            sf = new MailSSLSocketFactory();
            // 设置信任所有的主机
            sf.setTrustAllHosts(true);
            props.put("mail.smtp.ssl.enable", "true");
            props.put("mail.smtp.ssl.socketFactory", sf);

            Session session = Session.getDefaultInstance(props, new Authenticator() {
                // 认证信息，需要提供"用户账号","授权码"
                public PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(from, code);
                }
            });
            // 创建默认的 MimeMessage 对象
            MimeMessage message = new MimeMessage(session);
            // Set From: 头部头字段
            message.setFrom(new InternetAddress(from));
            // Set To: 头部头字段
            message.addRecipient(Message.RecipientType.TO,
                    new InternetAddress(email));
            // Set Subject: 头部头字段
            message.setSubject(head);
            // 设置消息体
            message.setText(context);

            // 发送消息
            Transport.send(message);
        }catch (Exception mex) {
            mex.printStackTrace();
        }
        return context;
    }

    public String getRandomVerifyCode(){
        String sources = "0123456789qwertyuiopasdfghjklzxcvbnm";
        Random rand = new Random();
        StringBuffer flag = new StringBuffer();
        for (int j = 0; j < 6; j++)
        {
            flag.append(sources.charAt(rand.nextInt(9)) + "");
        }
        return flag.toString();
    }

    @Override
    public boolean deleteAddress(int aid) {
        return userDao.deleteAddress(aid);
    }

    @Override
    public boolean deleteCartItem(int ciid) {
        return userDao.deleteCartItem(ciid);
    }

    @Override
    public boolean clearCart(int uid,int mid) {
        return userDao.clearCart(uid,mid);
    }

    public static UserService getInstance(){
        return userService;
    }
}
