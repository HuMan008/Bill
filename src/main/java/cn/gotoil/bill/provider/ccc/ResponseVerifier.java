package cn.gotoil.bill.provider.ccc;

import cn.gotoil.bill.tools.encoder.Hash;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.HashMap;


/**
 * 响应验证器
 *
 * @author think <syj247@qq.com>、
 * @date 2018-12-21、16:22
 */
class ResponseVerifier {

    private static Logger logger = LoggerFactory.getLogger(RequestSigner.class);

    private static ThreadLocal<SimpleDateFormat> threadLocalDateFormatter = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
        }
    };




    public  boolean verifyResponse(Response response,String copartnerPassword){
        if(copartnerPassword==null || copartnerPassword.length()==0){
            return false;
        }
      return verifyMD5(response,copartnerPassword) && verifyMD52(response,copartnerPassword);
    }


    private boolean verifyMD5(Response response,String copartnerPassword) {
        //[response-id][request-flow][response-flow][copartner-id][card][money][balance][time] [合作方密码] 生成
        String payload = stringfly(response.getResponseId()) +
                stringfly(response.getRequestFlow()) +
                stringfly(response.getResponseFlow()) +
                stringfly(response.getCopartnerId()) +
                stringfly(response.getCard()) +
                String.valueOf(response.getMoney()) +
                String.valueOf(response.getBalance()) +
                response.getTime() +
                copartnerPassword;
        String myMd5 = Hash.md5(payload);
        logger.info("ResponseStr:\n{}\nmd5:\n{}\nresponse.md5\n{}",payload,myMd5,response.getMd5());
        return myMd5.equals(response.getMd5());

    }



    private boolean verifyMD52(Response response , String copartnerPassword ) {
        HashMap<String, String> parameters = response.getParameters();
        String payload;
        if (parameters == null || parameters.size() < 1) {
            payload =copartnerPassword;
        } else {
            StringBuilder stringBuilder = new StringBuilder();
            String[] seqKey = response.getSeq().split(":");
            if(response.getSeq().length()==0 || seqKey.length==0){
               //pass
            }else{
                for(String key:seqKey){
                    String v = response.getParameters().containsKey(key)?response.getParameters().get(key):"";
                    stringBuilder.append(v);
                }
            }
            stringBuilder.append(copartnerPassword);
            payload = stringBuilder.toString();
        }
        String myMd52 = Hash.md5(payload);
        logger.info("ResponseStr:\n{}\nmd52:\n{}\nresponse.md52\n{}",payload,myMd52,response.getMd5());
        return myMd52.equals(response.getMd52());
    }

    private static String stringfly(String value) {
        return null == value ? "" : value;
    }
}
