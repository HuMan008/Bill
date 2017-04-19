/*
 * Copyright (C) 2017.  Iusworks, Inc - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Dolphin cn.gotoil.dolphin.web.filter.BodyContentHttpServletRequestWrapper
 *
 * cluries <cluries@me.com>,  February 2017
 *
 * LastModified: 2/14/17 9:36 AM
 *
 */

package cn.gotoil.bill.web.filter;


import cn.gotoil.bill.tools.stream.StreamUtils;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;


public class BodyContentHttpServletRequestWrapper extends HttpServletRequestWrapper {

    private final String httpBody;

    public BodyContentHttpServletRequestWrapper(HttpServletRequest request) throws IOException {
        super(request);
        httpBody = StreamUtils.stringFromInputStream(request.getInputStream());
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(httpBody.getBytes());
        ServletInputStream servletInputStream = new ServletInputStream() {
            @Override
            public int read() throws IOException {
                return byteArrayInputStream.read();
            }

            @Override
            public boolean isFinished() {
                return byteArrayInputStream.available() < 1;
            }

            @Override
            public boolean isReady() {
                return byteArrayInputStream.available() > 0;
            }

            @Override
            public void setReadListener(ReadListener listener) {

            }
        };

        return servletInputStream;
    }

    @Override
    public BufferedReader getReader() throws IOException {
        return new BufferedReader(new InputStreamReader(this.getInputStream()));
    }

    public String getHttpBody() {
        return this.httpBody;
    }
}
