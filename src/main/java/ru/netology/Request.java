package ru.netology;

import org.apache.hc.core5.http.NameValuePair;
import org.apache.hc.core5.net.URLEncodedUtils;

import java.nio.charset.StandardCharsets;
import java.util.*;

public class Request {
    private final String path;
    private final Map<String, List<String>> queryParams = new HashMap<>();

    public Request(String fullPath) {
        String[] parts = fullPath.split("\\?", 2);
        this.path = parts[0];

        if (parts.length > 1) {
            List<NameValuePair> params = URLEncodedUtils.parse(parts[1], StandardCharsets.UTF_8);
            for (NameValuePair param : params) {
                queryParams.computeIfAbsent(param.getName(), k -> new ArrayList<>()).add(param.getValue());
            }
        }
    }

    public String getPath() {
        return path;
    }

    public String getQueryParam(String name) {
        List<String> values = queryParams.get(name);
        return (values != null && !values.isEmpty()) ? values.get(0) : null;
    }

    public Map<String, List<String>> getQueryParams() {
        return Collections.unmodifiableMap(queryParams);
    }
}
