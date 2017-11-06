package com.radware.appwall.old;

import com.sun.net.httpserver.HttpExchange;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;

public class ClientContent {

    private HashMap<String, String> outHeaders;
    private String snapshotId;
    private Long requestId;
    private HashMap<String, String> optionalBody;

    public String getSnapshotId() {
        return snapshotId;
    }

    public void setSnapshotId(String snapshotId) {
        this.snapshotId = snapshotId;
    }

    public HashMap<String, String> getOptionalBody() {
        return optionalBody;
    }

    public void setOptionalBody(HashMap<String, String> optionalBody) {
        this.optionalBody = optionalBody;
    }

    public HashMap<String, String> getOutHeaders() {
        return outHeaders;
    }

    public ClientContent() {
        outHeaders = new HashMap<String, String>();
    }

    public void setOutHeaders(HttpServletRequest req) {
        Enumeration<String> inHeaders = req.getHeaderNames();
        while(inHeaders.hasMoreElements()) {
            String headerName = inHeaders.nextElement();
            if(headerName.toUpperCase().startsWith("X-FORWORDED")) {
                String headerValue = req.getHeader(headerName);
                outHeaders.put(headerName, headerValue);
            }
        }
        outHeaders.put("X-AW-XFF", req.getRemoteHost());
    }

    public void setOutheaders(HttpExchange httpExchange) {

    }

    public Long getRequestId() {
        return requestId;
    }

    public void setRequestId(Long requestId) {
        this.requestId = requestId;
    }


}
