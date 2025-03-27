package com.TestArc.core.model;

import java.util.Map;

public class QuarkResponse {
   private String body;
   private Map<String, String> responseHeaders;
   private int status;
   private String statusText;
   private String url;


    public String getBody()
    {
        return body;
    }

    public void setBody(String body)
    {
        this.body = body;
    }

    public Map<String, String> getResponseHeaders()
    {
        return responseHeaders;
    }

    public void setResponseHeaders(Map<String, String> responseHeaders)
    {
        this.responseHeaders = responseHeaders;
    }

    public int getStatus()
    {
        return status;
    }

    public void setStatus(int status)
    {
        this.status = status;
    }

    public String getStatusText()
    {
        return statusText;
    }

    public void setStatusText(String statusText)
    {
        this.statusText = statusText;
    }

    public String getUrl()
    {
        return url;
    }

    public void setUrl(String url)
    {
        this.url = url;
    }

    public boolean isSuccessful()
    {
        return status >= 200 && status < 300;
    }
}
