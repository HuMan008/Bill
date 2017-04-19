/*
 * Copyright (C) 2017.  Iusworks, Inc - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Dolphin cn.gotoil.dolphin.common.tools.stream.StreamUtils
 *
 * cluries <cluries@me.com>,  February 2017
 *
 * LastModified: 2/14/17 9:36 AM
 *
 */

package cn.gotoil.bill.tools.stream;


import java.io.*;

public class StreamUtils {

    public final static String stringFromInputStream(InputStream in) throws IOException {
        if (null == in) {
            return null;
        }
        StringBuilder stringBuilder = new StringBuilder();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
        char[] charBuffer = new char[512];
        int bytesRead;
        while ((bytesRead = bufferedReader.read(charBuffer, 0, charBuffer.length)) > 0) {
            stringBuilder.append(charBuffer, 0, bytesRead);
        }
        return stringBuilder.toString();
    }

    public final static byte[] bytesFromInputStream(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        byte[] b = new byte[1024];
        int bytesRead;
        while ((bytesRead = inputStream.read(b, 0, b.length)) > 0) {
            byteArrayOutputStream.write(b, 0, bytesRead);
        }
        return byteArrayOutputStream.toByteArray();
    }

}
