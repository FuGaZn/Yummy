package com.yummy.controller;

import com.yummy.dao.ManagerDao;
import com.yummy.factory.DaoFactory;
import com.yummy.factory.ServiceFactory;
import com.yummy.model.ApprovalMerchant;
import com.yummy.model.Manager;
import com.yummy.service.ManagerService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Set;

@CrossOrigin
@Controller
@RequestMapping("/end/manager")
public class ManagerController {
    private static ManagerService managerService = ServiceFactory.getManagerService();
    ManagerDao managerDao = DaoFactory.getManagerDao();

    @RequestMapping(value = "/get/merchants", method = RequestMethod.POST, produces = {"application/json; charset=UTF-8"})
    @ResponseBody
    public Set<ApprovalMerchant> getApprovalMerchants() {
        return managerService.getApprovalMerchants();
    }

    @RequestMapping(value = "/approve", method = RequestMethod.POST, produces = {"application/json; charset=UTF-8"})
    @ResponseBody
    public String approve(int amid) {
        return "" + managerService.approveMerchantModify(amid);
    }

    @RequestMapping(value = "/refuse", method = RequestMethod.POST, produces = {"application/json; charset=UTF-8"})
    @ResponseBody
    public String refuse(int amid) {
        return "" + managerService.refuseMerchantModify(amid);
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST, produces = {"application/json; charset=UTF-8"})
    @ResponseBody
    public String login(String email, String password) {
        return "" + managerService.login(email,password);
    }

    @RequestMapping(value = "/get/unhandle", method = RequestMethod.POST, produces = {"application/json; charset=UTF-8"})
    @ResponseBody
    public Set<ApprovalMerchant> getUnhandledMerchants() {
        System.out.println("get");
        return managerService.getUnhandledEvents();
    }

    @RequestMapping(value = "/get/refused", method = RequestMethod.POST, produces = {"application/json; charset=UTF-8"})
    @ResponseBody
    public Set<ApprovalMerchant> getRefusedMerchants() {
        return managerService.getRefusedMerchants();
    }

    @RequestMapping(value = "/get/approved", method = RequestMethod.POST, produces = {"application/json; charset=UTF-8"})
    @ResponseBody
    public Set<ApprovalMerchant> getApprovedMerchants() {
        return managerService.getApprovedMerchants();
    }

    @RequestMapping(value = "/statistic/user/level", method = RequestMethod.POST, produces = {"application/json; charset=UTF-8"})
    @ResponseBody
    public int[] getUserLevelStatistic() {
        return managerService.getUserLevelStatistic();
    }

    @RequestMapping(value = "/statistic/merchant/type", method = RequestMethod.POST, produces = {"application/json; charset=UTF-8"})
    @ResponseBody
    public int[] getMerchantTypeStatistic() {
        return managerService.getMerchantTypeStatistic();
    }

    @RequestMapping(value = "/get/merchant/number", method = RequestMethod.POST, produces = {"application/json; charset=UTF-8"})
    @ResponseBody
    public int getMerchantNumber() {
        return managerDao.getMerchantNumber();
    }
    @RequestMapping(value = "/get/user/number", method = RequestMethod.POST, produces = {"application/json; charset=UTF-8"})
    @ResponseBody
    public int getUserNumber() {
        return managerDao.getUserNumber();
    }
    @RequestMapping(value = "/get", method = RequestMethod.POST, produces = {"application/json; charset=UTF-8"})
    @ResponseBody
    public Manager getManager(int mid) {
        return managerDao.getManager(mid);
    }
}
