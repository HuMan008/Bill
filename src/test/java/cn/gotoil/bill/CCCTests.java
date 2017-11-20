package cn.gotoil.bill;

import cn.gotoil.bill.provider.ccc.GTAccount;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CCCTests {

    private GTAccount gtAccount = new GTAccount();


    @Test
    public void exampleTest() {
        gtAccount.setGTUid("U00000000131");
        assertThat(gtAccount.getMobile()).isEqualTo("13637702080");
    }


}
