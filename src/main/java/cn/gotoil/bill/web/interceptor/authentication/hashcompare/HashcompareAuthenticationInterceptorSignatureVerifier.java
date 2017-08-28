/*
 * Copyright (C) 2017.  Iusworks, Inc - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Dolphin cn.gotoil.dolphin.web.interceptor.AuthenticationInterceptorSignatureVerifier
 *
 * cluries <cluries@me.com>,  February 2017
 *
 * LastModified: 2/14/17 9:36 AM
 *
 */

package cn.gotoil.bill.web.interceptor.authentication.hashcompare;


import cn.gotoil.bill.exception.*;
import cn.gotoil.bill.tools.ObjectHelper;
import cn.gotoil.bill.tools.encoder.Hmac;
import cn.gotoil.bill.web.interceptor.authentication.AuthenticationType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;


public class HashcompareAuthenticationInterceptorSignatureVerifier {


    private static Logger logger = LoggerFactory.getLogger(HashcompareAuthenticationInterceptorSignatureVerifier.class);

//    private final static ObjectMapper objectMapper = new ObjectMapper();

    private Method authKeyMethod = null;

    private HttpServletRequest request;

    private String jsonBody;

    private StringRedisTemplate stringRedisTemplate;

    private int current;

    private int XT;

    private String XU;
    private String XS;
    private String XR;


    public HashcompareAuthenticationInterceptorSignatureVerifier(HttpServletRequest request, String jsonBody) {
        this.request = request;
        this.jsonBody = jsonBody;

        current = (int) Instant.now().getEpochSecond();
    }

    private boolean integrityStrandChcker() {
        if (!userAgentChecker()) {
            debug("UA Error");
            return false;
        }

        XU = request.getHeader("X-U");
        XT = request.getIntHeader("X-T");
        XS = request.getHeader("X-S");
        XR = request.getHeader("X-R");

        if (StringUtils.isEmpty(XU)
                || StringUtils.isEmpty(XS)
                || StringUtils.isEmpty(XR)) {
            debug("Empty XU or XS or XR");
            return false;
        }

        if (XU.length() != 24 || XS.length() != 40 || XR.length() > 32 || XR.length() < 1) {
            debug("XU XS XR Format Error");
            return false;
        }

        return XT > 1477375755;
    }

    private boolean integrityOnlyChecker() {
        if (!userAgentChecker()) {
            debug("UA Error");
            return false;
        }

        XU = request.getHeader("X-U");
        XT = request.getIntHeader("X-T");
        XR = request.getHeader("X-R");

        if (StringUtils.isEmpty(XR)) {
            debug("XR Empty");
            return false;
        }

        if (XR.length() > 32 || XR.length() < 1) {
            debug("XR Length Error");
            return false;
        }

        return XT > 0;
    }


    private boolean userAgentChecker() {
        return true;
    }

    public void verify(AuthenticationType billApiAuthenticationType, String allowDevSkip) throws Exception {
        if (billApiAuthenticationType == AuthenticationType.None) {
            return;
        }

        boolean onlyIntegrity = billApiAuthenticationType == billApiAuthenticationType.Integrity;

        boolean integrityCheckResult;
        if (onlyIntegrity) {
            integrityCheckResult = integrityOnlyChecker();
        } else {
            integrityCheckResult = integrityStrandChcker();
        }

        if (!integrityCheckResult) {
            debug("");
            throw new AuthenticationException(AuthenticationError.Entegrity);
        }

        if (Math.abs(current - XT) > 300) {
            debug("Time Range Error");
            throw new AuthenticationException(AuthenticationError.Timeout);
        }

        if (onlyIntegrity) {
            return;
        }

        StringBuilder payload = new StringBuilder(256);
        headerPayload(payload);

        Object calObject = null;
        if (!StringUtils.isEmpty(jsonBody) || jsonBody.length() > 1) {
            calObject = jsonObject();
        }

        Map<String, String[]> parameterMap = request.getParameterMap();
        if (parameterMap != null && parameterMap.size() > 0) {
            if (calObject == null) {
                Map<String, String> paramMap = new HashMap<>();
                parameterMap.forEach((String key, String[] vals) -> paramMap.put(key, vals[0]));
                calObject = paramMap;
            } else if (calObject instanceof Map) {
                for (Map.Entry<String, String[]> entry : parameterMap.entrySet()) {
                    if (((Map) calObject).containsKey(entry.getKey())) {
                        continue;
                    }
                    String[] vals = entry.getValue();
                    ((Map) calObject).put(entry.getKey(), entry.getValue()[0]);
                }
            }
        }

        if (calObject != null) {
            HashcompareAuthenticationInterceptorJsonPayloadBuilder jsonPayloadBuilder = new HashcompareAuthenticationInterceptorJsonPayloadBuilder();
            jsonPayloadBuilder.setJsonObject(calObject);
            payload.append(jsonPayloadBuilder.payload());
        }

        if (authKeyMethod == null) {
            throw new BillException(CommonError.Unknown);
        }

        String key = (String) authKeyMethod.invoke(null, XU);
        if (null == key || key.length() != 40) {
            throw new AuthenticationException(AuthenticationError.FlightError);
        }

        /*
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String n = headerNames.nextElement();
            logger.info("---->{} : {}", n, request.getHeader(n));
        }
        */

        if (!StringUtils.isEmpty(jsonBody)) {
            logger.info("RequestBoyd:{}", jsonBody);
        }
        String signature = Hmac.SHA1(payload.toString(), key);
        if (!XS.equals(signature)) {
            AuthenticationDebuger debuger = new AuthenticationDebuger();
            debuger.setKey(key);
            debuger.setSignature(signature);
            debuger.setPayload(payload.toString());
            debuger.setRandom(XR);
            logger.info("{}", debuger);
            request.setAttribute(AuthenticationDebuger.AuthenticationDebugerKey, debuger);

            if ("YES".equals(allowDevSkip) && "YES".equals(request.getHeader("DevSkip"))) {
                logger.warn("DevSkiped");
            } else {
                throw new AuthenticationException(AuthenticationError.SignatureError);
            }
        }

        if (!randomVerify()) {
            throw new AuthenticationException(AuthenticationError.RandomTokenDuplicated);
        }
    }


    private boolean randomVerify() {
        String tokenKey = String.format("UT_%s", XU);
        ZSetOperations<String, String> zSetOperations = stringRedisTemplate.opsForZSet();
        Double score = zSetOperations.score(tokenKey, XR);

        if (score != null && Math.abs(current - score.longValue()) < 300) {
            return false;
        }

        zSetOperations.add(tokenKey, XR, XT);
        zSetOperations.removeRangeByScore(tokenKey, Double.MIN_VALUE, current - 300);

        return true;
    }

    private void headerPayload(StringBuilder builder) {
        builder.append(request.getMethod().toUpperCase());
        builder.append('|');
        builder.append(request.getRequestURI());
        builder.append('|');
        builder.append(String.valueOf(XU));
        builder.append('|');
        builder.append(String.valueOf(XT));
        builder.append('|');
        builder.append(XR);
        builder.append('|');
    }

    private Object jsonObject() {
        try {
//            Object object = objectMapper.readValue(jsonBody, Object.class);
            Object object = ObjectHelper.getObjectMapper().readValue(jsonBody, Object.class);
            return object;
        } catch (Exception ex) {
            return null;
        }
    }

    public void setStringRedisTemplate(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    private void debug(String message) {
        AuthenticationDebuger debuger = (AuthenticationDebuger) request.getAttribute(AuthenticationDebuger.AuthenticationDebugerKey);
        if (null == debuger) {
            debuger = new AuthenticationDebuger();
            request.setAttribute(AuthenticationDebuger.AuthenticationDebugerKey, debuger);
        }

        if (debuger.getMessage() == null) {
            debuger.setMessage(message);
        } else {
            debuger.setMessage(debuger.getMessage() + "|" + message);
        }
    }

    public Method getAuthKeyMethod() {
        return authKeyMethod;
    }

    public void setAuthKeyMethod(Method authKeyMethod) {
        this.authKeyMethod = authKeyMethod;
    }
}

