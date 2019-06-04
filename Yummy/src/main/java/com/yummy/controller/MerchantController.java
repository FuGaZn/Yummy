package com.yummy.controller;

import com.yummy.dao.MerchantDao;
import com.yummy.factory.DaoFactory;
import com.yummy.factory.ServiceFactory;
import com.yummy.model.ApprovalMerchant;
import com.yummy.model.Merchant;
import com.yummy.model.Order;
import com.yummy.service.MerchantService;
import com.yummy.service.OrderService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@CrossOrigin
@Controller
@RequestMapping("/end/merchant")
public class MerchantController {
    private MerchantService merchantService = ServiceFactory.getMerchantService();
    private OrderService orderService = ServiceFactory.getOrderService();
    MerchantDao merchantDao = DaoFactory.getMerchantDao();

    @RequestMapping(value = "/register", method = RequestMethod.POST, produces = {"application/json; charset=UTF-8"})
    @ResponseBody
    public String merchantRegister(@RequestBody Merchant merchant) {
        return "" + this.merchantService.merchantRegister(merchant);
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST, produces = {"application/json; charset=UTF-8"})
    @ResponseBody
    public String login(String serial, String password) {
        return "" + this.merchantService.merchantLogin(serial, password);
    }

    @RequestMapping(value = "/getBySerial", method = RequestMethod.POST, produces = {"application/json; charset=UTF-8"})
    @ResponseBody
    public Merchant getMerchantBySerial(String serial) {
        return this.merchantService.getMerchant(serial);
    }

    @RequestMapping(value = "/get", method = RequestMethod.POST, produces = {"application/json; charset=UTF-8"})
    @ResponseBody
    public Merchant getMerchant(int mid) {
        return this.merchantService.getMerchant(mid);
    }

    @RequestMapping(value = "/get/orders", method = RequestMethod.POST, produces = {"application/json; charset=UTF-8"})
    @ResponseBody
    public Set<Order> getOrders(int mid) {
        return orderService.getOrdersByMerchant(mid);
    }

    @RequestMapping(value = "/update/merchant", method = RequestMethod.POST, produces = {"application/json; charset=UTF-8"})
    @ResponseBody
    public String updateMerchant(@RequestBody  Merchant merchant) {
        return "" + this.merchantService.updateMerchant(merchant);
    }

    /**
     * 商家修改信息（要交给经理审查）
     *
     * @param merchant
     * @return
     */
    @RequestMapping(value = "/modify/merchant", method = RequestMethod.POST, produces = {"application/json; charset=UTF-8"})
    @ResponseBody
    public String modifyInfo(ApprovalMerchant merchant) {
        return "" + this.merchantService.modifyInfo(merchant);
    }

    @RequestMapping(value = "/update/order", method = RequestMethod.POST, produces = {"application/json; charset=UTF-8"})
    @ResponseBody
    public String updateOrder(Order order) {
        return "" + this.orderService.updateOrder(order);
    }

    @RequestMapping(value = "/get/order", method = RequestMethod.POST, produces = {"application/json; charset=UTF-8"})
    @ResponseBody
    public Order getOrder(int oid) {
        return this.orderService.getOrder(oid);
    }

    @RequestMapping(value = "/get/logo", method = RequestMethod.POST, produces = {"application/json; charset=UTF-8"})
    @ResponseBody
    public String getLogo(int mid) {
        return this.merchantService.getLogo(mid);
    }

    @RequestMapping(value = "/add/package", method = RequestMethod.POST, produces = {"application/json; charset=UTF-8"})
    @ResponseBody
    public String addPackage(@RequestBody Merchant merchant) {
        return this.merchantService.addPackage(merchant) +"";
    }

    @RequestMapping(value = "/delete/coupon", method = RequestMethod.POST, produces = {"application/json; charset=UTF-8"})
    @ResponseBody
    public String deleteCoupon(int cid) {
        return ""+ merchantService.deleteCoupon(cid);
    }

    @RequestMapping(value = "/delete/package", method = RequestMethod.POST, produces = {"application/json; charset=UTF-8"})
    @ResponseBody
    public String deletePackage(int pid) {
        return ""+ merchantService.deletePackage(pid);
    }

    @RequestMapping(value = "/delete/good", method = RequestMethod.POST, produces = {"application/json; charset=UTF-8"})
    @ResponseBody
    public String deleteGood(int gid) {
        return ""+ merchantService.deleteGood(gid);
    }

    @RequestMapping(value = "/update/baseInfo", method = RequestMethod.POST, produces = {"application/json; charset=UTF-8"})
    @ResponseBody
    public String updateBaseInfo(@RequestBody Merchant merchant) {
        System.out.println(merchant.getName());
        return ""+ merchantService.updateBaseInfo(merchant);
    }

    @RequestMapping(value = "/update/situation", method = RequestMethod.POST, produces = {"application/json; charset=UTF-8"})
    @ResponseBody
    public String updateSituation(int oid, int situation) {
        return ""+orderService.updateSituation(oid,situation);
    }

    @RequestMapping(value = "/accept/cancel", method = RequestMethod.POST, produces = {"application/json; charset=UTF-8"})
    @ResponseBody
    public String acceptCancelOrder(int oid) {
        return ""+merchantDao.acceptCancelOrder(oid);
    }


}

