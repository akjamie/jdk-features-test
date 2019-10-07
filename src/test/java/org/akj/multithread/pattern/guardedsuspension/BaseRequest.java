package org.akj.multithread.pattern.guardedsuspension;

import lombok.Data;

import java.util.Collections;
import java.util.Map;

@Data
public class BaseRequest {
    private Map<String, Object> headers;

    private Object payload;

    public Map<String, Object> getHeaders() {
        return Collections.unmodifiableMap(headers);
    }
}
