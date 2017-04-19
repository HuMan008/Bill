package cn.gotoil.bill;

import cn.gotoil.bill.provider.ccc.GTAccount;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest
public class CCCTests {

    private GTAccount gtAccount = new GTAccount();

    private Logger logger = LoggerFactory.getLogger(CCCTests.class);

    @Test
    public void exampleTest() {
        gtAccount.setGTUid("U00000000131");
        logger.info("{}", gtAccount.getMobile());
    }


}
