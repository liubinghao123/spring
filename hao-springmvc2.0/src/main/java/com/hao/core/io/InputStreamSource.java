package com.hao.core.io;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Keeper on 2019-04-13
 */
public interface InputStreamSource {
    InputStream getInputStream() throws IOException;
    void setInputStream(InputStream inputStream);
}
