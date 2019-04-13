package com.hao.core.io;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Keeper on 2019-04-13
 */
public class Resource implements InputStreamSource {
    @Override
    public InputStream getInputStream() throws IOException {
        return null;
    }

    @Override
    public void setInputStream(InputStream inputStream) {

    }
}
