package com.yummy.model;

import javax.persistence.*;
import java.util.Stack;

@Entity
@Table(name = "last_merchant_serial")
public class LastMerchantSerial {
    private static final String C_CODES_STRING = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    int lid;
    String serial;

    public LastMerchantSerial(){

    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lid")
    public int getLid() {
        return lid;
    }

    public void setLid(int lid) {
        this.lid = lid;
    }

    @Column(name = "serial")
    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public static String int2CodeString(long intVal)  {
        int base = 62;
        StringBuilder sb = new StringBuilder();
        Stack<String> s=new Stack<String>();
        while (intVal!=0){
            s.push(C_CODES_STRING.charAt((int)(intVal%base))+"");
            intVal/=base;
        }
        while (!s.empty()){
            sb.append(s.pop());
        }
        return sb.length()==0?"0":sb.toString();
    }
}
