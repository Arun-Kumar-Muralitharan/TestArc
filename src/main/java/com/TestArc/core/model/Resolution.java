package com.TestArc.core.model;

public enum Resolution
{
    SMALL(720,1280),
    MEDIUM(1920, 1080),
    LARGE(3840, 2160);

    private final Integer height;
    private final Integer width;

    Resolution(Integer length, Integer width)
    {
        this.height = length;
        this.width = width;
    }

    public  Integer height()
    {
        return height;
    }

    public Integer width()
    {
        return width;
    }

    public static Resolution getResolution(String resolution)
    {
        return switch (resolution)
        {
            case "SMALL" -> SMALL;
            case "MEDIUM" -> MEDIUM;
            case "LARGE" -> LARGE;
            default -> LARGE;
        };
    }
}
