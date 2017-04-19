package cn.gotoil.bill.provider.ccc;


import cn.gotoil.bill.config.property.BillProperties;

class CCCConfig {

    public static String url;

    public static String copartnerId;

    public static String copartnerPassword;

    public static String getUrl() {
        return url;
    }

    public static void setUrl(String url) {
        CCCConfig.url = url;
    }

    public static String getCopartnerId() {
        return copartnerId;
    }

    public static void setCopartnerId(String copartnerId) {
        CCCConfig.copartnerId = copartnerId;
    }

    public static String getCopartnerPassword() {
        return copartnerPassword;
    }

    public static void setCopartnerPassword(String copartnerPassword) {
        CCCConfig.copartnerPassword = copartnerPassword;
    }

    public static void loadFromBillProperties(BillProperties billProperties) {
        CCCConfig.setUrl(billProperties.getCcc().get("url"));
        CCCConfig.setCopartnerId(billProperties.getCcc().get("copartnerId"));
        CCCConfig.setCopartnerPassword(billProperties.getCcc().get("copartnerPassword"));
    }

}

