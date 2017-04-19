/*
 * Copyright (C) 2017.  Iusworks, Inc - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Dolphin cn.gotoil.dolphin.web.interceptor.AuthenticationInterceptorJsonPayloadBuilder
 *
 * cluries <cluries@me.com>,  February 2017
 *
 * LastModified: 1/4/17 10:14 AM
 *
 */

package cn.gotoil.bill.web.interceptor.authentication.hashcompare;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class HashcompareAuthenticationInterceptorJsonPayloadBuilder {

    private Object jsonObject;


    public HashcompareAuthenticationInterceptorJsonPayloadBuilder() {

    }

    public void setJsonObject(Object jsonObject) {
        this.jsonObject = jsonObject;
    }

    private String build(Object object) {
        if (null == object) {
            return "";
        }

        if (object instanceof Map) {
            Map<String, Object> map = (Map<String, Object>) object;
            ArrayList<String> keylist = new ArrayList<>();
            keylist.addAll(map.keySet());
            keylist.sort((o1, o2) -> o1.compareTo(o2));
            StringBuilder builder = new StringBuilder(64);
            builder.append('{');
            for (int i = 0; i < keylist.size(); i++) {
                String key = keylist.get(i);
                builder.append(key);
                builder.append("=");
                builder.append(build(map.get(key)));

                if (i < keylist.size() - 1) {
                    builder.append('&');
                }
            }
            builder.append('}');

            return builder.toString();
        }

        if (object instanceof List) {
            StringBuilder builder = new StringBuilder(64);
            builder.append('[');
            List listTypeObject = (List) object;
            for (int i = 0; i < listTypeObject.size(); i++) {
                Object one = listTypeObject.get(i);
                builder.append(build(one));
                if (i < listTypeObject.size() - 1) {
                    builder.append(',');
                }
            }
            builder.append(']');

            return builder.toString();
        }


        return object.toString();
    }


    public String payload() {
        return build(jsonObject);
    }

}
