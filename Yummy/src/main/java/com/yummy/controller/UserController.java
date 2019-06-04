package com.yummy.controller;

import com.yummy.dao.OrderDao;
import com.yummy.dao.UserDao;
import com.yummy.factory.DaoFactory;
import com.yummy.factory.ServiceFactory;
import com.yummy.model.*;
import com.yummy.service.MerchantService;
import com.yummy.service.OrderService;
import com.yummy.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@CrossOrigin
@Controller
@RequestMapping("/end/user")
public class UserController {
    private UserService userService = ServiceFactory.getUserService();
    private OrderService orderService = ServiceFactory.getOrderService();
    private MerchantService merchantService = ServiceFactory.getMerchantService();
    static OrderDao orderDao = DaoFactory.getOrderDao();

    private UserDao userDao = DaoFactory.getUserDao();

    /**
     * 会员注册
     *
     * @param email
     * @param password
     * @return
     */
    @RequestMapping(value = "/register", method = RequestMethod.POST, produces = {"application/json; charset=UTF-8"})
    @ResponseBody
    public String memberRegister(String email, String password) {
        System.out.println(email+" "+password);
        return "" + (this.userService.memberRegister(email, password));
    }

    /**
     * 发送验证码
     *
     * @param email
     * @return 验证码
     */
    @RequestMapping(value = "/send", method = RequestMethod.POST, produces = {"application/json; charset=UTF-8"})
    @ResponseBody
    public String sendEmail(String email) {
        if (email != null && !email.trim().equals(""))
            return userService.sendVerify(email);
        return null;
    }

    /**
     * 登陆验证
     *
     * @param email
     * @param password
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST, produces = {"application/json; charset=UTF-8"})
    @ResponseBody
    public String login(String email, String password) {
        return "" + userService.login(email, password);
    }

    /**
     * 获取用户信息
     *
     * @param uid
     * @return
     */
    @RequestMapping(value = "/get", method = RequestMethod.POST, produces = {"application/json; charset=UTF-8"})
    @ResponseBody
    public User getUser(int uid) {
        return userService.getUser(uid);
    }

    @RequestMapping(value = "/get/orders", method = RequestMethod.POST, produces = {"application/json; charset=UTF-8"})
    @ResponseBody
    public Set<Order> getOrders(int uid) {
        return this.orderService.getOrdersByUser(uid);
    }

    @RequestMapping(value = "/get/order", method = RequestMethod.POST, produces = {"application/json; charset=UTF-8"})
    @ResponseBody
    public Order getOrder(int oid) {
        return this.orderService.getOrder(oid);
    }

    /**
     * 获取充值记录
     *
     * @param uid
     * @return
     */
    @RequestMapping(value = "/get/chargeRecords", method = RequestMethod.POST, produces = {"application/json; charset=UTF-8"})
    @ResponseBody
    public Set<ChargeRecord> getChargeRecords(int uid) {
        return this.userService.getChargeRecords(uid);
    }

    @RequestMapping(value = "/update/user", method = RequestMethod.POST, produces = {"application/json; charset=UTF-8"})
    @ResponseBody
    public String updateUser(@RequestBody User user) {
        System.out.println(user.getEmail()+" "+user.getPassword());
        return "" + this.userService.updateUser(user);
    }

    @RequestMapping(value = "/add/order", method = RequestMethod.POST, produces = {"application/json; charset=UTF-8"})
    @ResponseBody
    public String addOrder(@RequestBody Order order) {
        return "" + this.orderService.addOrder(order);
    }

    @RequestMapping(value = "/add/order/serial", method = RequestMethod.POST, produces = {"application/json; charset=UTF-8"})
    @ResponseBody
    public String addOrderBackSerial(@RequestBody Order order) {
        return "" + this.orderDao.addOrderBackSerial(order);
    }

    @RequestMapping(value = "/add/order/unpayed", method = RequestMethod.POST, produces = {"application/json; charset=UTF-8"})
    @ResponseBody
    public String addUnpayedOrder(@RequestBody Order order) {
        return "" + this.orderDao.addUnpayedOrder(order);
    }


    @RequestMapping(value = "/update/order/pay", method = RequestMethod.POST, produces = {"application/json; charset=UTF-8"})
    @ResponseBody
    public String payOrder(int oid) {
        return "" + this.orderDao.payUnpayedOrder(oid);
    }

    @RequestMapping(value = "/update/order", method = RequestMethod.POST, produces = {"application/json; charset=UTF-8"})
    @ResponseBody
    public String updateOrder(Order order) {
        return "" + this.orderService.updateOrder(order);
    }

    /**
     * 获取会员当前地址下可配送的所有商家
     *
     * @param address_code
     * @return
     */
    @RequestMapping(value = "/get/merchants/address", method = RequestMethod.POST, produces = {"application/json; charset=UTF-8"})
    @ResponseBody
    public Set<Merchant> getMerchantsByNowAddress(String address_code) {
        return this.merchantService.getMerchantsByAddress(address_code);
    }

    /**
     * 通过类型查找商家
     *
     * @param type
     * @return
     */
    @RequestMapping(value = "/get/merchants/type", method = RequestMethod.POST, produces = {"application/json; charset=UTF-8"})
    @ResponseBody
    public Set<Merchant> getMerchantsByType(int type,String address_code) {
        System.out.println(type+" "+address_code);
        return this.merchantService.getMerchantsByType(type,address_code);
    }

    /**
     * 模糊查询商家
     *
     * @param info
     * @return
     */
    @RequestMapping(value = "/get/merchants/search", method = RequestMethod.POST, produces = {"application/json; charset=UTF-8"})
    @ResponseBody
    public Set<Merchant> searchMerchants(String info) {
        return this.merchantService.searchMerchant(info);
    }

    /**
     * 获取购物车
     *
     * @param uid
     * @return
     */
    @RequestMapping(value = "/get/cart", method = RequestMethod.POST, produces = {"application/json; charset=UTF-8"})
    @ResponseBody
    public Cart getCart(int uid) {
        return this.userService.getCart(uid);
    }


    @RequestMapping(value = "/delete/address", method = RequestMethod.POST, produces = {"application/json; charset=UTF-8"})
    @ResponseBody
    public String deleteAddress(int aid) {
        return userService.deleteAddress(aid)+"";
    }

    @RequestMapping(value = "/delete/cartItem", method = RequestMethod.POST, produces = {"application/json; charset=UTF-8"})
    @ResponseBody
    public String deleteCartItem(int ciid) {
        return userService.deleteCartItem(ciid)+"";
    }

    @RequestMapping(value = "/clear/cart", method = RequestMethod.POST, produces = {"application/json; charset=UTF-8"})
    @ResponseBody
    public String clearCart(int uid,int mid) {
        return userDao.clearCart(uid,mid)+"";
    }

    @RequestMapping(value = "/order/finish", method = RequestMethod.POST, produces = {"application/json; charset=UTF-8"})
    @ResponseBody
    public String finishOrder(int oid) {
        return orderDao.finishOrder(oid)+"";
    }

}
