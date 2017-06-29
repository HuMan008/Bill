/*
 * Copyright (C) 2017.  Iusworks, Inc - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Dolphin cn.gotoil.dolphin.common.tools.ObjectHelper
 *
 * cluries <cluries@me.com>,  February 2017
 *
 * LastModified: 1/11/17 10:10 AM
 *
 */

package cn.gotoil.bill.tools;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by cluries on 12/10/2016.
 */
public class ObjectHelper {

    private static ObjectMapper objectMapper;
    private static ObjectWriter objectWriter;
    private static Logger logger = LoggerFactory.getLogger(ObjectHelper.class);

    static {
        objectMapper = new ObjectMapper();
        objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        objectMapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, false);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        objectWriter = objectMapper.writer();
    }


    public static ObjectMapper getObjectMapper() {
        return objectMapper;
    }

    @SuppressWarnings("all")
    public static Map<String, Object> introspect(Object obj) throws Exception {
        Map<String, Object> result = new HashMap<>();
        BeanInfo info = Introspector.getBeanInfo(obj.getClass());
        for (PropertyDescriptor pd : info.getPropertyDescriptors()) {
            Method reader = pd.getReadMethod();
            if (reader != null && !"class".equals(pd.getName())) {
                result.put(pd.getName(), reader.invoke(obj));
            }
        }
        return result;
    }

    @SuppressWarnings("all")
    public static Map<String, String> introspectStringValueMap(Object obj) throws Exception {
        Map<String, String> result = new HashMap<>();
        BeanInfo info = Introspector.getBeanInfo(obj.getClass());
        for (PropertyDescriptor pd : info.getPropertyDescriptors()) {
            Method reader = pd.getReadMethod();
            if (reader != null && !"class".equals(pd.getName())) {
                result.put(pd.getName(), reader.invoke(obj).toString());
            }
        }
        return result;
    }

    @SuppressWarnings("all")
    public static String jsonString(Object object) {
        try {
            return objectWriter.writeValueAsString(object);
        } catch (Exception ex) {
            logger.error("{}", ex);
        }

        return "";
    }

    @SuppressWarnings("all")
    public static boolean allPropertiesIsNull(Object obj) {
        try {
            BeanInfo info = Introspector.getBeanInfo(obj.getClass());
            for (PropertyDescriptor pd : info.getPropertyDescriptors()) {
                Method reader = pd.getReadMethod();
                if (reader != null && !"class".equals(pd.getName())) {
                    if (reader.invoke(obj) != null) {
                        return false;
                    }
                }
            }
            return true;
        } catch (Exception ex) {
            logger.error("{}", ex);
            return true;
        }
    }

    @SuppressWarnings("all")
    public static Object changeAnnotationValue(Annotation annotation, String key, Object newValue) {

        Object handler = Proxy.getInvocationHandler(annotation);
        Field f;
        try {
            f = handler.getClass().getDeclaredField("memberValues");
        } catch (NoSuchFieldException | SecurityException e) {
            throw new IllegalStateException(e);
        }
        f.setAccessible(true);
        Map<String, Object> memberValues;
        try {
            memberValues = (Map<String, Object>) f.get(handler);
        } catch (IllegalArgumentException | IllegalAccessException e) {
            throw new IllegalStateException(e);
        }
        Object oldValue = memberValues.get(key);
        if (oldValue == null || oldValue.getClass() != newValue.getClass()) {
            throw new IllegalArgumentException();
        }
        memberValues.put(key, newValue);
        return oldValue;
    }
}
