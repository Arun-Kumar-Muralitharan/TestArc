package com.TestArc.core.model;

public enum BrowserType
{
    CHROME("chrome"),
    FIREFOX("firefox"),
    EDGE("msedge"),
    SAFARI("webkit");

    private final String code;

    BrowserType(String code)
    {
        this.code = code;
    }

    public String getCode()
    {
        return code;
    }

}
