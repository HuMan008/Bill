package cn.gotoil.bill.provider.ccc;


import org.springframework.util.StringUtils;

import java.util.List;

public class GTAccount {

    private String mobile;

    private String GTUid;

    List<GTCard> bindCards;

    public String getMobile() {
        if (StringUtils.isEmpty(mobile)) {
            if (StringUtils.isEmpty(GTUid)) {
                throw new RuntimeException("GTUid can not be null");
            }
            mobile = Gateway.relationWithMobileAndUserID(GTUid);
        }

        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getGTUid() {
        if (StringUtils.isEmpty(GTUid)) {
            if (StringUtils.isEmpty(mobile)) {
                throw new RuntimeException("Mobile can not be null");
            }
            GTUid = Gateway.relationWithMobileAndUserID(mobile);
        }

        return GTUid;
    }

    public void setGTUid(String GTUid) {
        this.GTUid = GTUid;
    }

    public List<GTCard> getBindCards() {
        return bindCards;
    }
}
