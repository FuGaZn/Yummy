import com.yummy.controller.MerchantController;
import com.yummy.dao.OrderDao;
import com.yummy.model.LastMerchantSerial;
import com.yummy.model.Merchant;
import com.yummy.model.Order;
import com.yummy.model.User;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TestClass {

    @Test
    public void test(){
        MerchantController controller = new MerchantController();
        Merchant merchant = controller.getMerchantBySerial("3lJ37qU");
        merchant.setBulletin("做个小测试");
        controller.updateMerchant(merchant);
    }
}
