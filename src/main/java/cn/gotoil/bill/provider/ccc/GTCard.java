package cn.gotoil.bill.provider.ccc;

public class GTCard {

    private String no;

    private String password;

    public GTCard(String no) {
        this.no = no;
    }

    public GTCard(String no, String password) {
        this.no = no;
        this.password = password;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
