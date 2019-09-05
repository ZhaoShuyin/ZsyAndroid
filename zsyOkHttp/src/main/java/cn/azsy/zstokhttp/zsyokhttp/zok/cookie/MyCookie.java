package cn.azsy.zstokhttp.zsyokhttp.zok.cookie;

import java.io.IOException;
import java.net.CookieHandler;
import java.net.URI;
import java.util.List;
import java.util.Map;

/**
 * Created by zsy on 2017/6/12.
 */

public class MyCookie extends CookieHandler{
    @Override
    public Map<String, List<String>> get(URI uri, Map<String, List<String>> requestHeaders) throws IOException {
        return null;
    }

    @Override
    public void put(URI uri, Map<String, List<String>> responseHeaders) throws IOException {

    }
}
