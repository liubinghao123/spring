package com.hao.framework.io;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Keeper on 2019-04-13
 */
public class Resource implements InputStreamSource {
    private InputStream is;
    @Override
    public InputStream getInputStream() throws IOException {
        return is;
    }

    @Override
    public void setInputStream(InputStream inputStream) {
        this.is = inputStream;
    }
}
